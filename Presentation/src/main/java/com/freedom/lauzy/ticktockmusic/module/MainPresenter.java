package com.freedom.lauzy.ticktockmusic.module;

import android.util.Log;

import com.freedom.lauzy.interactor.GetSongListUseCase;
import com.freedom.lauzy.model.SongListBean;
import com.freedom.lauzy.ticktockmusic.base.BasePresenter;
import com.lauzy.freedom.data.net.constants.NetConstants;
import com.lauzy.freedom.librarys.LogUtil;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.annotations.NonNull;
import io.reactivex.observers.DisposableObserver;


public class MainPresenter extends BasePresenter {

    private GetSongListUseCase mSongListUseCase;

    @Inject
    public MainPresenter(GetSongListUseCase songListUseCase) {
        mSongListUseCase = songListUseCase;
    }

    public void getData() {
        GetSongListUseCase.Params params = new GetSongListUseCase.Params(NetConstants.Value.METHOD_SONG_LIST, 1, 0, 10);
        mSongListUseCase.execute(new DisposableObserver<List<SongListBean>>() {
            @Override
            public void onNext(@NonNull List<SongListBean> songListBeen) {
                for (SongListBean songListBean : songListBeen) {
                    Log.e("MainActivity", "onNext: " + songListBean.title);
                }
            }

            @Override
            public void onError(@NonNull Throwable e) {
                LogUtil.e("Main", e.getMessage());
            }

            @Override
            public void onComplete() {

            }
        }, params);
    }
}
