package com.lauzy.freedom.data.repository;

import android.content.Context;

import com.freedom.lauzy.model.ArtistAvatar;
import com.freedom.lauzy.model.LocalAlbumBean;
import com.freedom.lauzy.model.LocalArtistBean;
import com.freedom.lauzy.model.LocalSongBean;
import com.freedom.lauzy.repository.LocalSongRepository;
import com.lauzy.freedom.data.entity.SingerAvatarEntity;
import com.lauzy.freedom.data.entity.mapper.SingerAvatarMapper;
import com.lauzy.freedom.data.local.LocalAlbumLoader;
import com.lauzy.freedom.data.local.LocalArtistLoader;
import com.lauzy.freedom.data.local.LocalSongLoader;
import com.lauzy.freedom.data.net.RetrofitHelper;
import com.lauzy.freedom.data.net.api.SongService;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;

/**
 * Desc : Local Data
 * Author : Lauzy
 * Date : 2017/8/9
 * Blog : http://www.jianshu.com/u/e76853f863a9
 * Email : freedompaladin@gmail.com
 */
@Singleton
public class LocalSongRepositoryImpl implements LocalSongRepository {

    private Context mContext;

    @Inject
    public LocalSongRepositoryImpl(Context context) {
        mContext = context;
    }

    @Override
    public Observable<List<LocalSongBean>> getLocalSongList(final long id) {

        return Observable.create(new ObservableOnSubscribe<List<LocalSongBean>>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<List<LocalSongBean>> e) throws Exception {
                e.onNext(LocalSongLoader.getLocalSongList(mContext, id));
                e.onComplete();
            }
        });
    }

    @Override
    public Observable<List<LocalAlbumBean>> getLocalAlbumList(final long id) {
        return Observable.create(new ObservableOnSubscribe<List<LocalAlbumBean>>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<List<LocalAlbumBean>> e) throws Exception {
                e.onNext(LocalAlbumLoader.getLocalAlbums(mContext, id));
                e.onComplete();
            }
        });
    }

    @Override
    public Observable<List<LocalArtistBean>> getLocalArtistList() {
        return Observable.create(new ObservableOnSubscribe<List<LocalArtistBean>>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<List<LocalArtistBean>> e) throws Exception {
                e.onNext(LocalArtistLoader.getLocalArtists(mContext));
                e.onComplete();
            }
        });
    }

    @Override
    public Observable<ArtistAvatar> getArtistAvatar(String method, String apiKey, String artistName, String format) {
        return RetrofitHelper.INSTANCE
                .createApi(SongService.class)
                .getSingerAvatar(method, apiKey, artistName, format)
                .map(new Function<SingerAvatarEntity, ArtistAvatar>() {
                    @Override
                    public ArtistAvatar apply(@NonNull SingerAvatarEntity avatarEntity) throws Exception {
                        return SingerAvatarMapper.transform(avatarEntity);
                    }
                });
    }

    @Override
    public Observable<List<LocalSongBean>> getLocalArtistSongList(final long artistId) {
        return Observable.create(new ObservableOnSubscribe<List<LocalSongBean>>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<List<LocalSongBean>> e) throws Exception {
                e.onNext(LocalSongLoader.getLocalArtistSongList(mContext,artistId));
                e.onComplete();
            }
        });
    }
}
