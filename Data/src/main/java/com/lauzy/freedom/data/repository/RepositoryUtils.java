package com.lauzy.freedom.data.repository;

import android.content.Context;

import com.freedom.lauzy.model.SongListBean;
import com.lauzy.freedom.data.api.SongService;
import com.lauzy.freedom.data.net.RetrofitHelper;

import java.util.List;

import io.reactivex.Scheduler;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Desc :
 * Author : Lauzy
 * Date : 2017/7/10
 * Blog : http://www.jianshu.com/u/e76853f863a9
 * Email : freedompaladin@gmail.com
 */
public class RepositoryUtils {

    //  gson  基类提取，封装后抛多种异常 === presentation 中 处理，
    //  防止 自定义DisposableObserver造成的多状态多场景无法处理
    public static void getSongData(Context context) {
        RetrofitHelper.INSTANCE.setContext(context);
        SongService songService = RetrofitHelper.INSTANCE.createApi(SongService.class);
        songService.getSongList("",1,1,1).subscribeOn(Schedulers.io())
                .subscribe(new DisposableObserver<List<SongListBean>>() {
                    @Override
                    public void onNext(@NonNull List<SongListBean> songListBeen) {

                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
