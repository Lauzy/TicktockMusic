package com.freedom.lauzy.interactor;

import com.freedom.lauzy.executor.PostExecutionThread;
import com.freedom.lauzy.executor.ThreadExecutor;
import com.freedom.lauzy.model.LocalSongBean;
import com.freedom.lauzy.repository.LocalSongRepository;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * Desc : 本地音乐数据列表
 * Author : Lauzy
 * Date : 2017/8/9
 * Blog : http://www.jianshu.com/u/e76853f863a9
 * Email : freedompaladin@gmail.com
 */
public class GetLocalSongUseCase extends UseCase<List<LocalSongBean>, Long> {

    private final LocalSongRepository mLocalSongRepository;

    @Inject
    GetLocalSongUseCase(LocalSongRepository localSongRepository, ThreadExecutor threadExecutor,
                        PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        mLocalSongRepository = localSongRepository;
    }

    @Override
    Observable<List<LocalSongBean>> buildUseCaseObservable(Long id) {
        return mLocalSongRepository.getLocalSongList(id);
    }

    public Observable<Integer> deleteSong(long id) {
        return mLocalSongRepository.deleteSong(id);
    }
}
