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
import com.lauzy.freedom.librarys.imageload.ImageConfig;
import com.lauzy.freedom.librarys.imageload.ImageLoader;
import com.lauzy.freedom.librarys.view.util.PaletteColor;

import java.util.HashMap;

import javax.inject.Inject;

/**
 * Desc :
 * Author : Lauzy
 * Date : 2017/9/7
 * Blog : http://www.jianshu.com/u/e76853f863a9
 * Email : freedompaladin@gmail.com
 */
public class PlayPresenter extends BaseRxPresenter<PlayContract.View>
        implements PlayContract.Presenter {

    private HashMap<String, Integer> mColorMap = new HashMap<>();
    private FavoriteSongUseCase mFavoriteSongUseCase;
    private FavoriteMapper mFavoriteMapper;

    @Inject
    public PlayPresenter(FavoriteSongUseCase favoriteSongUseCase, FavoriteMapper favoriteMapper) {
        mFavoriteSongUseCase = favoriteSongUseCase;
        mFavoriteMapper = favoriteMapper;
    }

    @Override
    public void setCoverImgUrl(Object url) {
        ImageLoader.INSTANCE.display(getView().getContext(), new ImageConfig.Builder()
                .asBitmap(true)
                .url(url)
                .isRound(false)
                .intoTarget(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {
                        getView().setCoverBitmap(resource);
                        if (mColorMap.get(String.valueOf(url)) == null) {
                            PaletteColor.mainColorObservable(resource).subscribe(color -> {
                                        mColorMap.put((String) url, color);
                                        getView().setCoverBackground(color);
                                    }
                            );
                        } else {
                            getView().setCoverBackground(mColorMap.get(String.valueOf(url)));
                        }
                    }
                }).build());
    }

    @Override
    public void addFavoriteSong(SongEntity entity) {
        mFavoriteSongUseCase.buildObservable(mFavoriteMapper.transform(entity))
                .compose(RxHelper.ioMain())
                .subscribe(value -> {
                    if (value != -1) {
                        getView().addFavoriteSong();
                    }
                });
    }
}
