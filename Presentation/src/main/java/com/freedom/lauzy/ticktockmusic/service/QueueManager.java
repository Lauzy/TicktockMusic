package com.freedom.lauzy.ticktockmusic.service;

import com.freedom.lauzy.interactor.GetQueueUseCase;
import com.freedom.lauzy.interactor.NetSongUseCase;
import com.freedom.lauzy.interactor.RecentSongUseCase;
import com.freedom.lauzy.ticktockmusic.TicktockApplication;
import com.freedom.lauzy.ticktockmusic.model.SongEntity;
import com.freedom.lauzy.ticktockmusic.model.mapper.NetEntityMapper;
import com.freedom.lauzy.ticktockmusic.model.mapper.RecentMapper;
import com.freedom.lauzy.ticktockmusic.model.mapper.SongMapper;
import com.lauzy.freedom.data.net.constants.NetConstants;
import com.lauzy.freedom.data.repository.NetSongRepositoryImpl;
import com.lauzy.freedom.data.repository.QueueRepositoryImpl;
import com.lauzy.freedom.data.repository.RecentRepositoryImpl;

import java.util.List;

import io.reactivex.Observable;

/**
 * Desc : 播放队列管理类（由于前期考虑不周，故放弃了使用dagger。）
 * Author : Lauzy
 * Date : 2017/8/30
 * Blog : http://www.jianshu.com/u/e76853f863a9
 * Email : freedompaladin@gmail.com
 */
public class QueueManager {

    private GetQueueUseCase mGetQueueUseCase;
    private RecentSongUseCase mRecentSongUseCase;
    private NetSongUseCase mNetSongUseCase;

    /**
     * 初始化 useCase
     */
    public QueueManager() {
        mGetQueueUseCase = new GetQueueUseCase(new QueueRepositoryImpl(TicktockApplication.getInstance()), null, null);
        mRecentSongUseCase = new RecentSongUseCase(new RecentRepositoryImpl(TicktockApplication.getInstance()), null, null);
        mNetSongUseCase = new NetSongUseCase(new NetSongRepositoryImpl(), null, null);
    }

    /**
     * 播放队列 Observable
     * @param ids songIds
     * @return Observable
     */
    public Observable<List<SongEntity>> playQueueObservable(String[] ids) {
        return mGetQueueUseCase.buildUseCaseObservable(ids).map(SongMapper::transform);
    }

    /**
     * 添加并获取播放队列 Observable
     * @param ids songIds
     * @param songEntities 待添加的数据
     * @return Observable
     */
    public Observable<List<SongEntity>> addGetQueueObservable(String[] ids, List<SongEntity> songEntities) {
        return mGetQueueUseCase.addQueueObservable(SongMapper.transformToLocalQueue(songEntities), ids)
                .map(SongMapper::transform);
    }

    /**
     * 添加数据到最近播放队列
     * @param entity 播放的音乐
     * @return Observable
     */
    public Observable<Void> addRecentPlaySong(SongEntity entity) {
        RecentMapper recentMapper = new RecentMapper();
        return mRecentSongUseCase.buildObservable(recentMapper.transform(entity));
    }

    /**
     * 根据songId 获取网络音乐数据
     * @param songId songId
     * @return Observable
     */
    public Observable<SongEntity> netSongEntityObservable(long songId) {
        return mNetSongUseCase.buildObservable(new NetSongUseCase.Params(NetConstants.Value.METHOD_PLAY,
                songId)).map(NetEntityMapper::transform);
    }
}
