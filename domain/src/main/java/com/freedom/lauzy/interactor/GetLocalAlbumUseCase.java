package com.freedom.lauzy.interactor;

import com.freedom.lauzy.executor.PostExecutionThread;
import com.freedom.lauzy.executor.ThreadExecutor;
import com.freedom.lauzy.model.LocalAlbumBean;
import com.freedom.lauzy.repository.LocalSongRepository;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * Desc : 本地专辑用例
 * Author : Lauzy
 * Date : 2017/8/9
 * Blog : http://www.jianshu.com/u/e76853f863a9
 * Email : freedompaladin@gmail.com
 */
public class GetLocalAlbumUseCase extends UseCase<List<LocalAlbumBean>, Long> {

    private final LocalSongRepository mLocalSongRepository;

    @Inject
    GetLocalAlbumUseCase(LocalSongRepository localSongRepository, ThreadExecutor threadExecutor,
                         PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        mLocalSongRepository = localSongRepository;
    }

    @Override
    Observable<List<LocalAlbumBean>> buildUseCaseObservable(Long id) {
        return mLocalSongRepository.getLocalAlbumList(id);
    }
}
