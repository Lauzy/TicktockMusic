package com.freedom.lauzy.ticktockmusic.presenter;

import com.freedom.lauzy.interactor.GetLocalSongUseCase;
import com.freedom.lauzy.model.LocalSongBean;
import com.freedom.lauzy.ticktockmusic.base.BasePresenter;
import com.lauzy.freedom.librarys.common.LogUtil;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.annotations.NonNull;
import io.reactivex.observers.DisposableObserver;

/**
 * Desc :
 * Author : Lauzy
 * Date : 2017/8/2
 * Blog : http://www.jianshu.com/u/e76853f863a9
 * Email : freedompaladin@gmail.com
 */
public class LocalMusicPresenter extends BasePresenter {
    GetLocalSongUseCase mGetLocalSongUseCase;

    @Inject
    public LocalMusicPresenter(GetLocalSongUseCase getLocalSongUseCase) {
        mGetLocalSongUseCase = getLocalSongUseCase;
    }

    public void getLocalData() {
        mGetLocalSongUseCase.execute(new DisposableObserver<List<LocalSongBean>>() {
            @Override
            public void onNext(@NonNull List<LocalSongBean> localSongBeen) {
                /*for (LocalSongBean localSongBean : localSongBeen) {
                    LogUtil.e("PPPPP", localSongBean.title);
                }*/
                LogUtil.e("PPPPP", "LLL");
            }

            @Override
            public void onError(@NonNull Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        }, null);
    }
}
