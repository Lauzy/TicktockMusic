package com.lauzy.freedom.data.repository;

import android.content.Context;

import com.freedom.lauzy.model.LocalSongBean;
import com.freedom.lauzy.model.QueueSongBean;
import com.freedom.lauzy.repository.QueueRepository;
import com.lauzy.freedom.data.database.PlayQueueDb;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.annotations.NonNull;

/**
 * Desc : 队列数据仓库实现类
 * Author : Lauzy
 * Date : 2017/9/8
 * Blog : http://www.jianshu.com/u/e76853f863a9
 * Email : freedompaladin@gmail.com
 */
public class QueueRepositoryImpl implements QueueRepository {

    private Context mContext;

    @Inject
    public QueueRepositoryImpl(Context context) {
        mContext = context;
    }

    @Override
    public Observable<List<QueueSongBean>> getQueueData(final String[] songIds) {
        return Observable.create(new ObservableOnSubscribe<List<QueueSongBean>>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<List<QueueSongBean>> e) throws Exception {
                List<QueueSongBean> songBeen = PlayQueueDb.getInstance(mContext).queryQueue(songIds);
                e.onNext(songBeen != null ? songBeen : Collections.<QueueSongBean>emptyList());
                e.onComplete();
            }
        });
    }

    @Override
    public Observable<List<QueueSongBean>> addQueueData(final String[] songIds, final List<LocalSongBean> queueSongBeen) {
        return Observable.create(new ObservableOnSubscribe<List<QueueSongBean>>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<List<QueueSongBean>> e) throws Exception {
                PlayQueueDb.getInstance(mContext).addToQueue(queueSongBeen);
                List<QueueSongBean> songBeen = PlayQueueDb.getInstance(mContext).queryQueue(songIds);
                e.onNext(songBeen != null ? songBeen : Collections.<QueueSongBean>emptyList());
                e.onComplete();
            }
        });
    }

    @Override
    public Observable<Integer> deleteQueueData(final String[] songIds) {
        return Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<Integer> e) throws Exception {
                int value = PlayQueueDb.getInstance(mContext).deleteQueueData(songIds);
                e.onNext(value);
                e.onComplete();
            }
        });
    }
}
