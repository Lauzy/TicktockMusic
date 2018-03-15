package com.freedom.lauzy.interactor;

import com.freedom.lauzy.executor.PostExecutionThread;
import com.freedom.lauzy.executor.ThreadExecutor;
import com.freedom.lauzy.model.Folder;
import com.freedom.lauzy.repository.MusicFolderRepository;

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
public class MusicFolderUseCase extends UseCase<List<Folder>, Void> {

    private final MusicFolderRepository mMusicFolderRepository;

    @Inject
    MusicFolderUseCase(ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread,
                       MusicFolderRepository musicFolderRepository) {
        super(threadExecutor, postExecutionThread);
        mMusicFolderRepository = musicFolderRepository;
    }

    @Override
    Observable<List<Folder>> buildUseCaseObservable(Void aVoid) {
        return mMusicFolderRepository.getMusicFolders();
    }
}
