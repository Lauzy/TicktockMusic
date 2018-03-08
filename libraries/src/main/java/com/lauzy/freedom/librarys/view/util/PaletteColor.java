package com.lauzy.freedom.librarys.view.util;

import android.graphics.Bitmap;
import android.support.v7.graphics.Palette;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.schedulers.Schedulers;

/**
 * Desc : 提取颜色
 * Author : Lauzy
 * Date : 2017/8/14
 * Blog : http://www.jianshu.com/u/e76853f863a9
 * Email : freedompaladin@gmail.com
 */
public class PaletteColor {

    public static PaletteColor getInstance() {
        return new PaletteColor();
    }

    public interface OnMainColorListener {
        void getMainColor(int color);
    }

    /**
     * 接口回调版
     *
     * @param bitmap              bitmap
     * @param onMainColorListener 回调接口
     */
    public void getMainColor(final int defaultColor, Bitmap bitmap, final OnMainColorListener onMainColorListener) {

        Palette.Builder builder = new Palette.Builder(bitmap);
//        builder.maximumColorCount(1);
        builder.generate(new Palette.PaletteAsyncListener() {
            @Override
            public void onGenerated(Palette palette) {
                Palette.Swatch populousSwatch = getMostPopulousSwatch(palette);
                int rgb = defaultColor;
                if (populousSwatch != null) {
                    rgb = populousSwatch.getRgb();
                } else {
                    populousSwatch = palette.getMutedSwatch() == null ?
                            palette.getVibrantSwatch() : palette.getMutedSwatch();
                    if (populousSwatch != null) {
                        rgb = populousSwatch.getRgb();
                    }
                }
                if (onMainColorListener != null) {
                    onMainColorListener.getMainColor(rgb);
                }
            }
        });
    }

    /**
     * RxJava版
     *
     * @param bitmap bitmap
     * @return Observable
     */
    public static Observable<Integer> mainColorObservable(final int defaultColor, final Bitmap bitmap) {
        return Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(@NonNull final ObservableEmitter<Integer> e) throws Exception {
                Palette.Builder builder = new Palette.Builder(bitmap);
//                builder.maximumColorCount(1);
                builder.generate(new Palette.PaletteAsyncListener() {
                    @Override
                    public void onGenerated(Palette palette) {
                        Palette.Swatch populousSwatch = getMostPopulousSwatch(palette);
                        int rgb = defaultColor;
                        if (populousSwatch != null) {
                            rgb = populousSwatch.getRgb();
                        } else {
                            populousSwatch = palette.getMutedSwatch() == null ? palette.getVibrantSwatch() : palette.getMutedSwatch();
                            if (populousSwatch != null) {
                                rgb = populousSwatch.getRgb();
                            }
                        }
                        e.onNext(rgb);
                        e.onComplete();
                    }
                });
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    private static Palette.Swatch getMostPopulousSwatch(Palette palette) {
        Palette.Swatch mostPopulous = null;
        if (palette != null) {
            for (Palette.Swatch swatch : palette.getSwatches()) {
                if (mostPopulous == null || swatch.getPopulation() > mostPopulous.getPopulation()) {
                    mostPopulous = swatch;
                }
            }
        }
        return mostPopulous;
    }
}
