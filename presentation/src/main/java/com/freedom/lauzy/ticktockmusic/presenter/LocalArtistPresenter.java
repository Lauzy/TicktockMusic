package com.freedom.lauzy.ticktockmusic.presenter;

import android.widget.ImageView;

import com.freedom.lauzy.interactor.GetLocalArtistUseCase;
import com.freedom.lauzy.model.ArtistAvatar;
import com.freedom.lauzy.model.LocalArtistBean;
import com.freedom.lauzy.ticktockmusic.base.BaseRxPresenter;
import com.freedom.lauzy.ticktockmusic.contract.LocalArtistContract;
import com.freedom.lauzy.ticktockmusic.function.DefaultDisposableObserver;
import com.freedom.lauzy.ticktockmusic.function.RxHelper;
import com.freedom.lauzy.ticktockmusic.utils.SharePrefHelper;
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

    @Inject
    public LocalArtistPresenter(GetLocalArtistUseCase artistUseCase) {
        mArtistUseCase = artistUseCase;
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
        if (artistName == null || imageView == null) {
            return;
        }

        String avatarUrl = SharePrefHelper.getArtistAvatar(getView().getContext(), artistName);
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
                        SharePrefHelper.setArtistAvatar(getView().getContext(), artistName,
                                artistAvatar.picUrl);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        super.onError(e);
                    }
                });
    }

 /*   @Override
    public void loadArtistAvatar(String artistName, ImageView imageView) {
        imageView.setTag(R.id.imageTag, artistName);
        Observable.just(imageView)
                .takeUntil(imageView1 -> !artistName.equals(imageView1.getTag(R.id.imageTag)))
                .flatMap(new Function<ImageView, ObservableSource<ArtistAvatar>>() {
                    @Override
                    public ObservableSource<ArtistAvatar> apply(@NonNull ImageView imageView) throws Exception {
                        return Observable.concat(Observable.create(e -> {
                            String artistAvatar = SharePrefHelper.getArtistAvatar(getView().getContext(), artistName);
                            if (artistAvatar.isEmpty()) {
                                e.onError(new ArtistAvatarException("No Artist Cache!"));
                            } else {
                                ArtistAvatar avatar = new ArtistAvatar();
                                avatar.picUrl = artistAvatar;
                                e.onNext(avatar);
                            }
                            e.onComplete();
                        }), mArtistUseCase.getArtistAvatar(NetConstants.Artist.GET_ARTIST_INFO,
                                NetConstants.Artist.API_KEY_CONTENT, artistName, NetConstants.Artist.FORMAT_JSON)
                                .compose(RxHelper.ioMain())).take(1);
                    }
                })
                .filter(artistAvatar -> artistName.equals(imageView.getTag(R.id.imageTag)))
                .subscribe(new DefaultDisposableObserver<ArtistAvatar>() {
                    @Override
                    public void onNext(@NonNull ArtistAvatar artistAvatar) {
                        super.onNext(artistAvatar);
                        if (getView() != null) {
                            getView().loadAvatarResult(artistAvatar.picUrl, imageView);
                        }
                    }
                });
    }*/
}