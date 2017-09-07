package com.freedom.lauzy.ticktockmusic.presenter;

import android.graphics.Bitmap;

import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.freedom.lauzy.ticktockmusic.base.BaseRxPresenter;
import com.freedom.lauzy.ticktockmusic.contract.PlayContract;
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

    @Inject
    public PlayPresenter() {
    }

    @Override
    public void setCoverImgUrl(Object url) {
        ImageLoader.INSTANCE.display(getView().getContext(), new ImageConfig.Builder()
                .asBitmap(true)
                .url(url)
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
//                        PaletteColor.mainColorObservable(resource)
//                                .subscribe(color -> getView().setCoverBackground(color));
                    }
                }).build());
    }
}
