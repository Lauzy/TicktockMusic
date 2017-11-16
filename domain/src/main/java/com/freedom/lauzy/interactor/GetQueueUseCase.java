package com.freedom.lauzy.interactor;

import com.freedom.lauzy.executor.PostExecutionThread;
import com.freedom.lauzy.executor.ThreadExecutor;
import com.freedom.lauzy.model.LocalSongBean;
import com.freedom.lauzy.model.QueueSongBean;
import com.freedom.lauzy.repository.QueueRepository;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * Desc : 播放队列UseCase
 * Author : Lauzy
 * Date : 2017/9/8
 * Blog : http://www.jianshu.com/u/e76853f863a9
 * Email : freedompaladin@gmail.com
 */
public class GetQueueUseCase extends UseCase<List<QueueSongBean>, String[]> {

    private final QueueRepository mQueueRepository;

    @Inject
    public GetQueueUseCase(QueueRepository queueRepository, ThreadExecutor threadExecutor,
                           PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        mQueueRepository = queueRepository;
    }

    /**
     * 获取播放队列数据集合Observable
     * @param ids 音乐Ids
     * @return Observable
     */
    @Override
    public Observable<List<QueueSongBean>> buildUseCaseObservable(String[] ids) {
        return mQueueRepository.getQueueData(ids);
    }

    /**
     * 删除数据
     * @param ids songIds
     * @return Observable
     */
    public Observable<Integer> deleteQueueObservable(String[] ids) {
        return mQueueRepository.deleteQueueData(ids);
    }

    /**
     * 添加数据到播放队列
     * @param songBeen 音乐数据({@link LocalSongBean} 原设计为本地音乐，后网络数据经映射转换后也采用了此对象)
     * @param ids ids
     * @return Observable
     */
    public Observable<List<QueueSongBean>> addQueueObservable(List<LocalSongBean> songBeen,
                                                                String[] ids) {
        return mQueueRepository.addQueueData(ids, songBeen);
    }
}
