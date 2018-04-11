package com.freedom.lauzy.ticktockmusic.service;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;

import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.freedom.lauzy.ticktockmusic.R;
import com.freedom.lauzy.ticktockmusic.model.SongEntity;
import com.lauzy.freedom.data.local.LocalUtil;
import com.lauzy.freedom.librarys.imageload.ImageConfig;
import com.lauzy.freedom.librarys.imageload.ImageLoader;

import java.io.File;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;

/**
 * Desc : MusicUtil
 * Author : Lauzy
 * Date : 2017/9/8
 * Blog : http://www.jianshu.com/u/e76853f863a9
 * Email : freedompaladin@gmail.com
 */
public class MusicUtil {
    public static String[] getSongIds(List<SongEntity> songEntities) {
        if (songEntities != null) {
            int size = songEntities.size();
            String[] ids = new String[size];
            for (int i = 0; i < size; i++) {
                ids[i] = String.valueOf(songEntities.get(i).id);
            }
            return ids;
        }
        return null;
    }

    /**
     * 专辑图片 Observable
     * @param context context
     * @param entity 音乐
     * @return Observable
     */
    @SuppressWarnings("WeakerAccess")
    public static Observable<Bitmap> albumCoverObservable(Context context, SongEntity entity) {
        return Observable.create(e -> {
            Bitmap bitmap;
            String coverUri = LocalUtil.getCoverUri(context, entity.albumId);
            if (coverUri != null && new File(coverUri).exists()) {
                bitmap = BitmapFactory.decodeFile(coverUri);
            } else {
                Drawable drawable = ContextCompat.getDrawable(context, R.drawable.ic_album_default);
                bitmap = ((BitmapDrawable) drawable).getBitmap();
            }
            e.onNext(bitmap);
            e.onComplete();
        });
    }

    /**
     * 加载网络音乐的图片，将转化 Observable<SongEntity> 为 ObservableSource<NetMusicData>
     * @param context
     * @return
     */
    @SuppressWarnings("all")
    public static ObservableTransformer<SongEntity, NetMusicData> transformNetData(Context context) {
        return new ObservableTransformer<SongEntity, NetMusicData>() {
            @Override
            public ObservableSource<NetMusicData> apply(@NonNull Observable<SongEntity> upstream) {
                return upstream.flatMap(new Function<SongEntity, ObservableSource<NetMusicData>>() {
                    @Override
                    public ObservableSource<NetMusicData> apply(@NonNull SongEntity entity) throws Exception {
                        return observer ->
                                ImageLoader.getInstance().display(context, new ImageConfig.Builder()
                                        .asBitmap(true)
                                        .url(entity.albumCover)
                                        .placeholder(R.drawable.ic_default)
                                        .isRound(false)
                                        .cacheStrategy(ImageConfig.CACHE_SOURCE)
                                        .intoTarget(new SimpleTarget<Bitmap>() {
                                            @Override
                                            public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {
                                                NetMusicData netMusicData = new NetMusicData();
                                                netMusicData.setSongEntity(entity);
                                                netMusicData.setBitmap(resource);
                                                observer.onNext(netMusicData);
                                                observer.onComplete();
                                            }
                                        }).build());
                    }
                });
            }
        };
    }


    static class NetMusicData {
        private SongEntity mSongEntity;
        private Bitmap mBitmap;

        public SongEntity getSongEntity() {
            return mSongEntity;
        }

        public void setSongEntity(SongEntity songEntity) {
            mSongEntity = songEntity;
        }

        public Bitmap getBitmap() {
            return mBitmap;
        }

        public void setBitmap(Bitmap bitmap) {
            mBitmap = bitmap;
        }
    }
}
