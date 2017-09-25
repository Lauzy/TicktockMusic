package com.freedom.lauzy.interactor;

import com.freedom.lauzy.executor.PostExecutionThread;
import com.freedom.lauzy.executor.ThreadExecutor;
import com.freedom.lauzy.model.RecentSongBean;
import com.freedom.lauzy.repository.RecentRepository;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * Desc : 最近播放用例
 * Author : Lauzy
 * Date : 2017/9/15
 * Blog : http://www.jianshu.com/u/e76853f863a9
 * Email : freedompaladin@gmail.com
 */
public class RecentSongUseCase extends UseCase<Void, RecentSongBean> {

    private RecentRepository mRecentRepository;

    @Inject
    public RecentSongUseCase(RecentRepository recentRepository, ThreadExecutor threadExecutor,
                             PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        mRecentRepository = recentRepository;
    }

    /**
     * 添加最近播放音乐
     *
     * @param songBean 最近播放音乐
     * @return Observable
     */
    @Override
    Observable<Void> buildUseCaseObservable(RecentSongBean songBean) {
        return mRecentRepository.addRecentSong(songBean);
    }

    /**
     * 获取最近播放数据 Observable
     * @return Observable
     */
    public Observable<List<RecentSongBean>> getRecentSongs() {
        return mRecentRepository.getRecentSongs();
    }

    /**
     * 清空最近播放数据 Observable
     * @return Observable
     */
    public Observable<Integer> clearRecentSongs() {
        return mRecentRepository.clearRecentSongs();
    }

    /**
     * 根据 songId 删除最近播放数据 Observable
     * @param songId songId
     * @return Observable
     */
    public Observable<Long> deleteRecentSong(long songId) {
        return mRecentRepository.deleteRecentSong(songId);
    }
}
