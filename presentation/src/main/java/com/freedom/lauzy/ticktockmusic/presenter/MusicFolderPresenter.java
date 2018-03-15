package com.freedom.lauzy.ticktockmusic.presenter;

import com.freedom.lauzy.interactor.MusicFolderUseCase;
import com.freedom.lauzy.model.Folder;
import com.freedom.lauzy.ticktockmusic.base.BaseRxPresenter;
import com.freedom.lauzy.ticktockmusic.contract.MusicFolderContract;
import com.freedom.lauzy.ticktockmusic.function.DefaultDisposableObserver;

import java.util.List;

import javax.inject.Inject;

/**
 * Desc : 文件夹
 * Author : Lauzy
 * Date : 2018/3/15
 * Blog : http://www.jianshu.com/u/e76853f863a9
 * Email : freedompaladin@gmail.com
 */
public class MusicFolderPresenter extends BaseRxPresenter<MusicFolderContract.View>
        implements MusicFolderContract.Presenter {

    private MusicFolderUseCase mMusicFolderUseCase;

    @Inject
    MusicFolderPresenter(MusicFolderUseCase musicFolderUseCase) {
        mMusicFolderUseCase = musicFolderUseCase;
    }

    @Override
    public void loadFolders() {
        mMusicFolderUseCase.execute(new DefaultDisposableObserver<List<Folder>>() {
            @Override
            public void onNext(List<Folder> folders) {
                super.onNext(folders);
                if (getView() == null) {
                    return;
                }
                if (folders == null || folders.isEmpty()) {
                    getView().setEmptyView();
                } else {
                    getView().onLoadFoldersSuccess(folders);
                }
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                if (getView() == null) {
                    return;
                }
                getView().loadFailed(e);
            }
        }, null);
    }
}
