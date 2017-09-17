package com.lauzy.freedom.data.repository;

import android.content.Context;

import com.freedom.lauzy.model.LocalSongBean;
import com.freedom.lauzy.model.QueueSongBean;
import com.freedom.lauzy.repository.QueueRepository;
import com.lauzy.freedom.data.database.PlayQueueDao;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.annotations.NonNull;

/**
 * Desc : 队列数据实现
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
                List<QueueSongBean> songBeen = PlayQueueDao.getInstance(mContext).queryQueue(songIds);
                e.onNext(songBeen != null ? songBeen : Collections.<QueueSongBean>emptyList());
                e.onComplete();
            }
        });
    }

    @Override
    public Observable<List<QueueSongBean>> addLocalQueueData(final String[] songIds, final List<LocalSongBean> queueSongBeen) {
        return Observable.create(new ObservableOnSubscribe<List<QueueSongBean>>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<List<QueueSongBean>> e) throws Exception {
                PlayQueueDao.getInstance(mContext).addLocalQueue(queueSongBeen);
                List<QueueSongBean> songBeen = PlayQueueDao.getInstance(mContext).queryQueue(songIds);
                e.onNext(songBeen != null ? songBeen : Collections.<QueueSongBean>emptyList());
                e.onComplete();
            }
        });
    }

//    @Override
//    public Observable<List<QueueSongBean>> addNetQueueData(final String[] songIds,
//                                                           final List<NetSongBean> netSongBeen) {
//        return Observable.create(new ObservableOnSubscribe<List<QueueSongBean>>() {
//            @Override
//            public void subscribe(@NonNull ObservableEmitter<List<QueueSongBean>> e) throws Exception {
//                PlayQueueDao.getInstance(mContext).addNetQueue(netSongBeen);
//                List<QueueSongBean> songBeen = PlayQueueDao.getInstance(mContext).queryQueue(songIds);
//                e.onNext(songBeen != null ? songBeen : Collections.<QueueSongBean>emptyList());
//                e.onComplete();
//            }
//        });
//    }

    @Override
    public Observable<Integer> deleteQueueData(final String[] songIds) {
        return Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<Integer> e) throws Exception {
                int value = PlayQueueDao.getInstance(mContext).deleteQueueData(songIds);
                e.onNext(value);
                e.onComplete();
            }
        });
    }
}
