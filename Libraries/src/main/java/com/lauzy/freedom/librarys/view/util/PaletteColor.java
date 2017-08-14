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
@SuppressWarnings("all")
public class PaletteColor {

    public static PaletteColor getInstance() {
        return new PaletteColor();
    }

    public interface OnMainColorListener {
        void getMainColor(int color);
    }

    private OnMainColorListener mOnMainColorListener;

    /**
     * 接口回调版
     *
     * @param bitmap              bitmap
     * @param onMainColorListener 回调接口
     */
    public void getMainColor(Bitmap bitmap, final OnMainColorListener onMainColorListener) {

        Palette.Builder builder = new Palette.Builder(bitmap);
        builder.maximumColorCount(1);
        builder.generate(new Palette.PaletteAsyncListener() {
            @Override
            public void onGenerated(Palette palette) {
                Palette.Swatch swatch = palette.getSwatches().get(0);
                int rgb = swatch.getRgb();
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
    public static Observable<Integer> mainColorObservable(final Bitmap bitmap) {
        return Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(@NonNull final ObservableEmitter<Integer> e) throws Exception {
                Palette.Builder builder = new Palette.Builder(bitmap);
                builder.maximumColorCount(1);
                builder.generate(new Palette.PaletteAsyncListener() {
                    @Override
                    public void onGenerated(Palette palette) {
                        Palette.Swatch swatch = palette.getSwatches().get(0);
                        int rgb = swatch.getRgb();
                        e.onNext(rgb);
                    }
                });
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
