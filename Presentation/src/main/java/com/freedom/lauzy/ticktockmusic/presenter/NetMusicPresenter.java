package com.freedom.lauzy.ticktockmusic.presenter;

import com.freedom.lauzy.DConstants;
import com.freedom.lauzy.interactor.GetSongListUseCase;
import com.freedom.lauzy.model.SongListBean;
import com.freedom.lauzy.ticktockmusic.DefaultDisposableObserver;
import com.freedom.lauzy.ticktockmusic.base.BaseRxPresenter;
import com.freedom.lauzy.ticktockmusic.contract.NetMusicContract;
import com.lauzy.freedom.data.net.constants.NetConstants;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;


/**
 * Desc : 网络数据Presenter
 * Author : Lauzy
 * Date : 2017/8/4
 * Blog : http://www.jianshu.com/u/e76853f863a9
 * Email : freedompaladin@gmail.com
 */
public class NetMusicPresenter extends BaseRxPresenter<NetMusicContract.View> implements NetMusicContract.Presenter {

    private GetSongListUseCase mSongListUseCase;
    private static final String METHOD = NetConstants.Value.METHOD_SONG_LIST;
    private static final int SIZE = 20;
    private int mType = 1;
    private int mPage = 20;
    private int mStatus;

    @Inject
    public NetMusicPresenter(GetSongListUseCase songListUseCase) {
        mSongListUseCase = songListUseCase;
    }

    public void setType(int type) {
        mType = type;
    }

    @Override
    public void loadNetMusicList() {
        mStatus = DConstants.Status.INIT_STATUS;
        GetSongListUseCase.Params params = GetSongListUseCase.Params.forSongList(METHOD, mType, 0, SIZE);
        Disposable disposable = mSongListUseCase.execute(new NetSongObserver(), params);
        addDisposable(disposable);
    }

    @Override
    public void loadMoreNetMusicList() {
        mStatus = DConstants.Status.LOAD_MORE_STATUS;
        GetSongListUseCase.Params params = GetSongListUseCase.Params.forSongList(METHOD, mType, mPage, SIZE);
        Disposable disposable = mSongListUseCase.execute(new NetSongObserver(), params);
        addDisposable(disposable);
    }

    private class NetSongObserver extends DefaultDisposableObserver<List<SongListBean>> {

        @Override
        public void onNext(@NonNull List<SongListBean> songListBeen) {
            super.onNext(songListBeen);
            if (mStatus == DConstants.Status.INIT_STATUS) {
                if (null != songListBeen && songListBeen.size() != 0) {
                    getView().loadSuccess(songListBeen);
                } else {
                    getView().setEmptyView();
                }
            } else if (mStatus == DConstants.Status.LOAD_MORE_STATUS) {
                if (null != songListBeen && songListBeen.size() != 0) {
                    getView().loadMoreSuccess(songListBeen);
                    mPage += SIZE;
                } else {
                    getView().loadMoreEnd();
                }
            }
        }

        @Override
        public void onError(@NonNull Throwable e) {
            super.onError(e);
            if (mStatus == DConstants.Status.INIT_STATUS) {
                getView().loadFail(e);
            } else if (mStatus == DConstants.Status.LOAD_MORE_STATUS) {
                getView().loadMoreFail(e);
            }
        }
    }
}
