package com.freedom.lauzy.ticktockmusic.presenter;

import android.graphics.Bitmap;

import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.freedom.lauzy.interactor.FavoriteSongUseCase;
import com.freedom.lauzy.ticktockmusic.base.BaseRxPresenter;
import com.freedom.lauzy.ticktockmusic.contract.PlayContract;
import com.freedom.lauzy.ticktockmusic.function.RxHelper;
import com.freedom.lauzy.ticktockmusic.model.SongEntity;
import com.freedom.lauzy.ticktockmusic.model.mapper.FavoriteMapper;
import com.lauzy.freedom.librarys.common.LogUtil;
import com.lauzy.freedom.librarys.imageload.ImageConfig;
import com.lauzy.freedom.librarys.imageload.ImageLoader;
import com.lauzy.freedom.librarys.view.blur.ImageBlur;

import javax.inject.Inject;

/**
 * Desc : 播放Presenter
 * Author : Lauzy
 * Date : 2017/9/7
 * Blog : http://www.jianshu.com/u/e76853f863a9
 * Email : freedompaladin@gmail.com
 */
public class PlayPresenter extends BaseRxPresenter<PlayContract.View>
        implements PlayContract.Presenter {

    private FavoriteSongUseCase mFavoriteSongUseCase;
    private FavoriteMapper mFavoriteMapper;
    private static final String TAG = "PlayPresenter";

    @Inject
    PlayPresenter(FavoriteSongUseCase favoriteSongUseCase, FavoriteMapper favoriteMapper) {
        mFavoriteSongUseCase = favoriteSongUseCase;
        mFavoriteMapper = favoriteMapper;
    }

    @Override
    public void setCoverImgUrl(Object url) {
        if (getView() != null && getView().getContext() != null) {
            ImageLoader.INSTANCE.display(getView().getContext(), new ImageConfig.Builder()
                    .asBitmap(true)
                    .url(url)
                    .isRound(false)
                    .intoTarget(new SimpleTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {
                            Bitmap bg = Bitmap.createBitmap(resource);
//                        BitmapDrawable drawable = new BitmapDrawable(null, ImageBlur.onStackBlur(bg, 50));
//                        getView().setCoverBackground(drawable);
                            getView().setCoverBackground(ImageBlur.onStackBlur(bg, 50));
                        }
                    }).build());
        }
    }

    @Override
    public void addFavoriteSong(SongEntity entity) {
        mFavoriteSongUseCase.buildObservable(mFavoriteMapper.transform(entity))
                .compose(RxHelper.ioMain())
                .subscribe(value -> {
                    LogUtil.i(TAG, "add value is " + value);
                    if (value != -1) {
                        getView().addFavoriteSong();
                    }
                });
    }

    @Override
    public void deleteFavoriteSong(long songId) {
        mFavoriteSongUseCase.deleteFavoriteSong(songId).subscribe(aLong -> {
            LogUtil.i(TAG, "delete value is " + aLong);
            getView().deleteFavoriteSong();
        });
    }

    @Override
    public void isFavoriteSong(long songId) {
        mFavoriteSongUseCase.isFavoriteSong(songId)
                .subscribe(aBoolean -> getView().isFavoriteSong(aBoolean));
    }
}
