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

    @Override
    public Observable<List<QueueSongBean>> buildUseCaseObservable(String[] ids) {
        return mQueueRepository.getQueueData(ids);
    }

    public Observable<List<QueueSongBean>> localQueueObservable(List<LocalSongBean> localSongBeen,
                                                                String[] ids) {
        return mQueueRepository.addLocalQueueData(ids, localSongBeen);
    }
}
