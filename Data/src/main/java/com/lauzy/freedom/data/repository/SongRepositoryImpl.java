package com.lauzy.freedom.data.repository;

import android.content.Context;

import com.freedom.lauzy.model.NetSongBean;
import com.freedom.lauzy.repository.SongRepository;
import com.lauzy.freedom.data.database.NetMusicDao;
import com.lauzy.freedom.data.entity.MusicEntity;
import com.lauzy.freedom.data.entity.mapper.SongListMapper;
import com.lauzy.freedom.data.net.RetrofitHelper;
import com.lauzy.freedom.data.net.api.SongService;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

/**
 * Desc :  百度Api原因，无法简介封装
 * http://www.jianshu.com/p/f3f0eccbcd6f
 * Author : Lauzy
 * Date : 2017/7/10
 * Blog : http://www.jianshu.com/u/e76853f863a9
 * Email : freedompaladin@gmail.com
 */
@Singleton
public class SongRepositoryImpl implements SongRepository {

    private Context mContext;

    @Inject
    public SongRepositoryImpl(Context context) {
        mContext = context;
    }

    @Override
    public Observable<List<NetSongBean>> getSongList(final String method, final int type,
                                                     final int offset, final int size) {

        return RetrofitHelper.INSTANCE.createApi(SongService.class)
                .getMusicData(method, type, offset, size)
                .map(new Function<MusicEntity, List<NetSongBean>>() {
                    @Override
                    public List<NetSongBean> apply(@NonNull MusicEntity musicEntity) throws Exception {
//                        if (musicEntity.error_code != NetConstants.ErrorCode.CODE_SUCCESS) {
//                            throw new ErrorMsgException(musicEntity.error_code, musicEntity.error_msg);
//                        }
                        SongListMapper mapper = new SongListMapper();
                        return mapper.transform(musicEntity.song_list);
                    }
                }).doOnNext(new Consumer<List<NetSongBean>>() {
                    @Override
                    public void accept(@NonNull List<NetSongBean> songListBeen) throws Exception {
                        if (!songListBeen.isEmpty()) {
                            NetMusicDao.getInstance(mContext).removeData(type);
                            NetMusicDao.getInstance(mContext).addNetSongData(type, songListBeen);
                        }
                    }
                });
    }

    @Override
    public Observable<List<NetSongBean>> getCacheSongList(final int type) {
        return Observable.create(new ObservableOnSubscribe<List<NetSongBean>>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<List<NetSongBean>> e) throws Exception {
                List<NetSongBean> listBeen = NetMusicDao.getInstance(mContext).querySongData(type);
                e.onNext(listBeen != null ? listBeen : Collections.<NetSongBean>emptyList());
                e.onComplete();
            }
        });
    }
}
