package com.freedom.lauzy.ticktockmusic.presenter;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.v4.graphics.ColorUtils;
import android.view.Gravity;

import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.freedom.lauzy.interactor.FavoriteSongUseCase;
import com.freedom.lauzy.ticktockmusic.base.BaseRxPresenter;
import com.freedom.lauzy.ticktockmusic.contract.PlayContract;
import com.freedom.lauzy.ticktockmusic.function.RxHelper;
import com.freedom.lauzy.ticktockmusic.model.SongEntity;
import com.freedom.lauzy.ticktockmusic.model.mapper.FavoriteMapper;
import com.freedom.lauzy.ticktockmusic.utils.ThemeHelper;
import com.lauzy.freedom.librarys.common.LogUtil;
import com.lauzy.freedom.librarys.imageload.ImageConfig;
import com.lauzy.freedom.librarys.imageload.ImageLoader;
import com.lauzy.freedom.librarys.view.blur.ImageBlur;
import com.lauzy.freedom.librarys.view.util.ColorUtil;
import com.lauzy.freedom.librarys.view.util.PaletteColor;
import com.lauzy.freedom.librarys.view.util.ScrimUtil;

import java.util.HashMap;

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
    private HashMap<String, Integer> mColorMap = new HashMap<>();

    @Inject
    PlayPresenter(FavoriteSongUseCase favoriteSongUseCase, FavoriteMapper favoriteMapper) {
        mFavoriteSongUseCase = favoriteSongUseCase;
        mFavoriteMapper = favoriteMapper;
    }

    @Override
    public void setCoverImgUrl(Object url) {
        if (getView() == null) {
            return;
        }
        String urlString = String.valueOf(url);
        ImageLoader.INSTANCE.display(getView().getContext(), new ImageConfig.Builder()
                .asBitmap(true)
                .url(url)
                .isRound(false)
                .intoTarget(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {
                        getView().setPlayView(resource);
                        Bitmap bg = Bitmap.createBitmap(resource);
                        if (mColorMap.get(urlString) == null) {
                            PaletteColor.mainColorObservable(ThemeHelper.getThemeColorResId(getView().getContext()), resource)
                                    .subscribe(color -> {
                                        mColorMap.put(urlString, color);
                                        getView().setViewBgColor(color);
                                        judgeColorDepth(color);
                                    });
                        } else {
                            Integer color = mColorMap.get(urlString);
                            getView().setViewBgColor(color);
                            judgeColorDepth(color);
                        }
                        getView().setCoverBackground(ImageBlur.onStackBlur(bg, 50));
                    }

                    private void judgeColorDepth(Integer color) {
                        if (ColorUtil.isDarkColor(color)) {
                            getView().showLightViews();
                        }else {
                            getView().showDarkViews();
                        }
                    }
                }).build());
    }

    @Override
    public void addFavoriteSong(SongEntity entity) {
        mFavoriteSongUseCase.buildObservable(mFavoriteMapper.transform(entity))
                .compose(RxHelper.ioMain())
                .subscribe(value -> {
                    LogUtil.i(TAG, "add value is " + value);
                    if (value != -1 && getView() != null) {
                        getView().addFavoriteSong();
                    }
                });
    }

    @Override
    public void deleteFavoriteSong(long songId) {
        mFavoriteSongUseCase.deleteFavoriteSong(songId).subscribe(aLong -> {
            LogUtil.i(TAG, "delete value is " + aLong);
            if (getView() != null) {
                getView().deleteFavoriteSong();
            }
        });
    }

    @Override
    public void isFavoriteSong(long songId) {
        mFavoriteSongUseCase.isFavoriteSong(songId)
                .subscribe(aBoolean -> {
                    if (getView() != null) {
                        getView().isFavoriteSong(aBoolean);
                    }
                });
    }
}
