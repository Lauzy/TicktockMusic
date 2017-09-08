package com.freedom.lauzy.ticktockmusic.service;

import android.content.Context;

import com.freedom.lauzy.interactor.GetQueueUseCase;
import com.freedom.lauzy.model.NetSongBean;
import com.freedom.lauzy.model.QueueSongBean;
import com.freedom.lauzy.ticktockmusic.TicktockApplication;
import com.freedom.lauzy.ticktockmusic.model.SongEntity;
import com.freedom.lauzy.ticktockmusic.model.mapper.SongMapper;
import com.lauzy.freedom.data.database.PlayQueueDao;
import com.lauzy.freedom.data.repository.QueueRepositoryImpl;

import java.util.List;

import io.reactivex.Observable;

/**
 * Desc : 播放队列
 * Author : Lauzy
 * Date : 2017/8/30
 * Blog : http://www.jianshu.com/u/e76853f863a9
 * Email : freedompaladin@gmail.com
 */
public class QueueManager {

    private GetQueueUseCase mGetQueueUseCase;

    public QueueManager() {
        mGetQueueUseCase = new GetQueueUseCase(new QueueRepositoryImpl(TicktockApplication.getInstance()), null, null);
    }

    public Observable<List<SongEntity>> playQueueObservable(String[] ids) {
        return mGetQueueUseCase.buildUseCaseObservable(ids).map(SongMapper::transform);
    }

    public Observable<List<SongEntity>> localQueueObservable(String[] ids, List<SongEntity> songEntities) {
        return mGetQueueUseCase.localQueueObservable(SongMapper.transformToLocalQueue(songEntities), ids)
                .map(SongMapper::transform);
    }

//    public static List<SongEntity> getPlayQueue(Context context, String[] songIds) {
//        List<QueueSongBean> queueSongBeen = PlayQueueDao.getInstance(context).queryQueue(songIds);
//        return SongMapper.transform(queueSongBeen);
//    }


//    public static void addLocalQueue(Context context, List<SongEntity> songEntities) {
//        PlayQueueDao.getInstance(context).addLocalQueue(SongMapper.transformToLocalQueue(songEntities));
//    }

//    public static void addNetQueue(Context context, List<NetSongBean> netSongBeen) {
//        PlayQueueDao.getInstance(context).addNetQueue(netSongBeen);
//    }
}
