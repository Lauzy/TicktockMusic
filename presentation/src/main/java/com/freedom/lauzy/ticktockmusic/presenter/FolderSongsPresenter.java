package com.freedom.lauzy.ticktockmusic.presenter;

import com.freedom.lauzy.interactor.FolderSongsUseCase;
import com.freedom.lauzy.model.LocalSongBean;
import com.freedom.lauzy.ticktockmusic.base.BaseRxPresenter;
import com.freedom.lauzy.ticktockmusic.contract.FolderSongsContract;
import com.freedom.lauzy.ticktockmusic.function.DefaultDisposableObserver;
import com.freedom.lauzy.ticktockmusic.model.mapper.LocalSongMapper;

import java.util.List;

import javax.inject.Inject;

/**
 * Desc : 文件夹音乐
 * Author : Lauzy
 * Date : 2018/3/15
 * Blog : http://www.jianshu.com/u/e76853f863a9
 * Email : freedompaladin@gmail.com
 */
public class FolderSongsPresenter extends BaseRxPresenter<FolderSongsContract.View>
        implements FolderSongsContract.Presenter {

    private FolderSongsUseCase mFolderSongsUseCase;
    private LocalSongMapper mLocalSongMapper;

    @Inject
    FolderSongsPresenter(FolderSongsUseCase folderSongsUseCase, LocalSongMapper localSongMapper) {
        mFolderSongsUseCase = folderSongsUseCase;
        mLocalSongMapper = localSongMapper;
    }

    @Override
    public void loadFolderSongs(String folderPath) {
        mFolderSongsUseCase.execute(new DefaultDisposableObserver<List<LocalSongBean>>() {
            @Override
            public void onNext(List<LocalSongBean> localSongBeans) {
                super.onNext(localSongBeans);
                if (getView() == null) {
                    return;
                }
                if (localSongBeans != null && localSongBeans.size() != 0) {
                    getView().onLoadFolderSongsSuccess(mLocalSongMapper.transform(localSongBeans));
                }else {
                    getView().setEmptyView();
                }
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                if (getView()==null) {
                    return;
                }
                getView().loadFail(e);
            }
        }, folderPath);
    }
}
