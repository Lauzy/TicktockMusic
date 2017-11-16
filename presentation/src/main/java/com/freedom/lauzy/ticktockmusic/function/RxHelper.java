package com.freedom.lauzy.ticktockmusic.function;

import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Desc : Rx转换
 * Author : Lauzy
 * Date : 2017/8/25
 * Blog : http://www.jianshu.com/u/e76853f863a9
 * Email : freedompaladin@gmail.com
 */
public class RxHelper {
    public static <T> ObservableTransformer<T, T> ioMain() {
        return upstream -> upstream
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
