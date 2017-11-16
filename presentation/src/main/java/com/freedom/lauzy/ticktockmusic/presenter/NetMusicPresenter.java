package com.freedom.lauzy.ticktockmusic.presenter;

import com.freedom.lauzy.ticktockmusic.function.DConstants;
import com.freedom.lauzy.interactor.GetSongListUseCase;
import com.freedom.lauzy.model.NetSongBean;
import com.freedom.lauzy.ticktockmusic.base.BaseRxPresenter;
import com.freedom.lauzy.ticktockmusic.function.DefaultDisposableObserver;
import com.freedom.lauzy.ticktockmusic.contract.NetMusicContract;
import com.freedom.lauzy.ticktockmusic.function.RxHelper;
import com.lauzy.freedom.data.net.constants.NetConstants;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;


/**
 * Desc : 网络数据Presenter
 * Author : Lauzy
 * Date : 2017/8/4
 * Blog : http://www.jianshu.com/u/e76853f863a9
 * Email : freedompaladin@gmail.com
 */
public class NetMusicPresenter extends BaseRxPresenter<NetMusicContract.View>
        implements NetMusicContract.Presenter {

    private GetSongListUseCase mSongListUseCase;
    private static final String METHOD = NetConstants.Value.METHOD_SONG_LIST;
    private static final int SIZE = 20;
    private int mType = 1;
    private int mPage = 20;


    @Inject
    public NetMusicPresenter(GetSongListUseCase songListUseCase) {
        mSongListUseCase = songListUseCase;
    }

    public void setType(int type) {
        mType = type;
    }

    /**
     * 加载缓存数据
     */
    @Override
    public void loadCacheMusicList() {
        Observable<List<NetSongBean>> observable = mSongListUseCase.buildCacheObservable(mType);
        observable.subscribe(songListBeen -> getView().loadCacheData(songListBeen));
    }

    /**
     * 加载网络数据
     */
    @Override
    public void loadNetMusicList() {
        GetSongListUseCase.Params params = GetSongListUseCase.Params.forSongList(METHOD, mType, 0, SIZE);
        mSongListUseCase.buildCacheObservable(mType).flatMap(new Function<List<NetSongBean>,
                ObservableSource<List<NetSongBean>>>() {
            @Override
            public ObservableSource<List<NetSongBean>> apply(@NonNull List<NetSongBean> songListBeen) throws Exception {
                return mSongListUseCase.buildObservable(params);
            }
        }).compose(RxHelper.ioMain())
                .subscribeWith(new NetSongObserver(DConstants.Status.INIT_STATUS));
    }

    /**
     * 加载更多网络数据
     */
    @Override
    public void loadMoreNetMusicList() {
        GetSongListUseCase.Params params = GetSongListUseCase.Params.forSongList(METHOD, mType, mPage, SIZE);
        Disposable disposable = mSongListUseCase.execute(new NetSongObserver(DConstants.Status.LOAD_MORE_STATUS), params);
        addDisposable(disposable);
    }

    private class NetSongObserver extends DefaultDisposableObserver<List<NetSongBean>> {

        private int mStatus;

        NetSongObserver(int status) {
            mStatus = status;
        }

        @Override
        public void onNext(@NonNull List<NetSongBean> songListBeen) {
            super.onNext(songListBeen);
            if (mStatus == DConstants.Status.INIT_STATUS) {
                mPage = SIZE;
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
            e.printStackTrace();
            if (mStatus == DConstants.Status.INIT_STATUS) {
                getView().loadFail(e);
            } else if (mStatus == DConstants.Status.LOAD_MORE_STATUS) {
                getView().loadMoreFail(e);
            }
        }
    }
}
