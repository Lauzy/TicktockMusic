package com.freedom.lauzy.ticktockmusic.presenter;

import android.widget.ImageView;

import com.freedom.lauzy.interactor.CacheManagerUseCase;
import com.freedom.lauzy.interactor.GetLocalArtistUseCase;
import com.freedom.lauzy.model.ArtistAvatar;
import com.freedom.lauzy.model.LocalArtistBean;
import com.freedom.lauzy.ticktockmusic.base.BaseRxPresenter;
import com.freedom.lauzy.ticktockmusic.contract.LocalArtistContract;
import com.freedom.lauzy.ticktockmusic.function.DefaultDisposableObserver;
import com.freedom.lauzy.ticktockmusic.function.RxHelper;
import com.lauzy.freedom.data.net.constants.NetConstants;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.annotations.NonNull;

/**
 * Desc : 本地歌手Presenter
 * Author : Lauzy
 * Date : 2017/9/28
 * Blog : http://www.jianshu.com/u/e76853f863a9
 * Email : freedompaladin@gmail.com
 */
@SuppressWarnings("WeakerAccess")
public class LocalArtistPresenter extends BaseRxPresenter<LocalArtistContract.View>
        implements LocalArtistContract.Presenter {

    private GetLocalArtistUseCase mArtistUseCase;
    private CacheManagerUseCase mCacheManagerUseCase;

    @Inject
    public LocalArtistPresenter(GetLocalArtistUseCase artistUseCase,
                                CacheManagerUseCase cacheManagerUseCase) {
        mArtistUseCase = artistUseCase;
        mCacheManagerUseCase = cacheManagerUseCase;
    }

    @Override
    public void loadLocalArtists() {
        mArtistUseCase.execute(new DefaultDisposableObserver<List<LocalArtistBean>>() {
            @Override
            public void onNext(@NonNull List<LocalArtistBean> artistBeen) {
                super.onNext(artistBeen);
                if (getView() == null) {
                    return;
                }

                if (artistBeen != null && artistBeen.size() != 0) {
                    getView().loadArtistResult(artistBeen);
                } else {
                    getView().emptyView();
                }
            }

            @Override
            public void onError(@NonNull Throwable e) {
                super.onError(e);
                e.printStackTrace();
                if (getView() == null) {
                    return;
                }
                getView().loadFailed(e);
            }
        }, null);
    }

    @Override
    public void loadArtistAvatar(String artistName, ImageView imageView) {
        if (getView() == null || artistName == null || imageView == null) {
            return;
        }
        String avatarUrl = mCacheManagerUseCase.getArtistAvatar(artistName);
        if (!avatarUrl.isEmpty()) {
            getView().loadAvatarResult(avatarUrl, imageView);
            return;
        }

        mArtistUseCase.getArtistAvatar(NetConstants.Artist.GET_ARTIST_INFO,
                NetConstants.Artist.API_KEY_CONTENT, artistName, NetConstants.Artist.FORMAT_JSON)
                .compose(RxHelper.ioMain())
                .subscribeWith(new DefaultDisposableObserver<ArtistAvatar>() {
                    @Override
                    public void onNext(@NonNull ArtistAvatar artistAvatar) {
                        super.onNext(artistAvatar);
                        if (getView() == null) {
                            return;
                        }
                        getView().loadAvatarResult(artistAvatar.picUrl, imageView);
                        mCacheManagerUseCase.setArtistAvatar(artistName, artistAvatar.picUrl);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        super.onError(e);
                    }
                });
    }
}