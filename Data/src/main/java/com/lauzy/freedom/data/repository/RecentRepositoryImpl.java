package com.lauzy.freedom.data.repository;

import android.content.Context;

import com.freedom.lauzy.model.RecentSongBean;
import com.freedom.lauzy.repository.RecentRepository;
import com.lauzy.freedom.data.database.RecentDao;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.annotations.NonNull;

/**
 * Desc : 最近播放数据接口实现类
 * Author : Lauzy
 * Date : 2017/9/15
 * Blog : http://www.jianshu.com/u/e76853f863a9
 * Email : freedompaladin@gmail.com
 */
public class RecentRepositoryImpl implements RecentRepository {

    private Context mContext;

    @Inject
    public RecentRepositoryImpl(Context context) {
        mContext = context;
    }

    @Override
    public Observable<Void> addRecentSong(final RecentSongBean songBean) {
        return Observable.create(new ObservableOnSubscribe<Void>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<Void> e) throws Exception {
                RecentDao.getInstance(mContext).addRecentSong(songBean);
            }
        });
    }

    @Override
    public Observable<List<RecentSongBean>> getRecentSongs() {
        return Observable.create(new ObservableOnSubscribe<List<RecentSongBean>>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<List<RecentSongBean>> e) throws Exception {
                List<RecentSongBean> songBeen = RecentDao.getInstance(mContext).getRecentSongBean();
                e.onNext(songBeen != null ? songBeen : Collections.<RecentSongBean>emptyList());
                e.onComplete();
            }
        });
    }

    @Override
    public Observable<Integer> clearRecentSongs() {
        return Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<Integer> e) throws Exception {
                int value = RecentDao.getInstance(mContext).clearRecentSongs();
                e.onNext(value);
                e.onComplete();
            }
        });
    }

    @Override
    public Observable<Long> deleteRecentSong(final long songId) {
        return Observable.create(new ObservableOnSubscribe<Long>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<Long> e) throws Exception {
                long value = RecentDao.getInstance(mContext).deleteRecentSong(songId);
                e.onNext(value);
                e.onComplete();
            }
        });
    }
}
