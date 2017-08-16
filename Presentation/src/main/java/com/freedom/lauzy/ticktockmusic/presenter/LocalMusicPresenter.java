package com.freedom.lauzy.ticktockmusic.presenter;

import com.freedom.lauzy.interactor.GetLocalSongUseCase;
import com.freedom.lauzy.model.LocalSongBean;
import com.freedom.lauzy.ticktockmusic.base.BaseRxPresenter;
import com.freedom.lauzy.ticktockmusic.contract.LocalMusicContract;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.annotations.NonNull;
import io.reactivex.observers.DisposableObserver;

/**
 * Desc : Local Music Presenter
 * Author : Lauzy
 * Date : 2017/8/2
 * Blog : http://www.jianshu.com/u/e76853f863a9
 * Email : freedompaladin@gmail.com
 */
public class LocalMusicPresenter extends BaseRxPresenter<LocalMusicContract.View>
        implements LocalMusicContract.SongPresenter {

    private GetLocalSongUseCase mGetLocalSongUseCase;
    private long mId;//专辑ID

    @Inject
    LocalMusicPresenter(GetLocalSongUseCase getLocalSongUseCase) {
        mGetLocalSongUseCase = getLocalSongUseCase;
    }

    public void setId(long id) {
        mId = id;
    }

    @Override
    public void loadLocalSong() {
        mGetLocalSongUseCase.execute(new DisposableObserver<List<LocalSongBean>>() {
            @Override
            public void onNext(@NonNull List<LocalSongBean> localSongBeen) {
                if (localSongBeen != null && localSongBeen.size() != 0) {
                    getView().loadLocalMusic(localSongBeen);
                } else {
                    getView().setEmptyView();
                }
            }

            @Override
            public void onError(@NonNull Throwable e) {
                e.printStackTrace();
                getView().loadFailed(e);
            }

            @Override
            public void onComplete() {

            }
        }, mId);
    }
}
