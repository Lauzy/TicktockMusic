package com.freedom.lauzy.interactor;

import com.freedom.lauzy.executor.PostExecutionThread;
import com.freedom.lauzy.executor.ThreadExecutor;
import com.freedom.lauzy.model.LocalSongBean;
import com.freedom.lauzy.repository.FolderSongsRepository;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * Desc : 音乐文件夹
 * Author : Lauzy
 * Date : 2018/3/15
 * Blog : http://www.jianshu.com/u/e76853f863a9
 * Email : freedompaladin@gmail.com
 */
public class FolderSongsUseCase extends UseCase<List<LocalSongBean>, String> {

    private final FolderSongsRepository mFolderSongsRepository;

    @Inject
    FolderSongsUseCase(ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread,
                       FolderSongsRepository folderSongsRepository) {
        super(threadExecutor, postExecutionThread);
        mFolderSongsRepository = folderSongsRepository;
    }

    @Override
    Observable<List<LocalSongBean>> buildUseCaseObservable(String folderPath) {
        return mFolderSongsRepository.getFolderSongs(folderPath);
    }
}
