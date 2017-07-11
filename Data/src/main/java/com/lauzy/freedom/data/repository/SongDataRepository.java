package com.lauzy.freedom.data.repository;

import android.content.Context;

import com.freedom.lauzy.model.SongListBean;
import com.freedom.lauzy.repository.SongRepository;
import com.lauzy.freedom.data.entity.SongListEntity;
import com.lauzy.freedom.data.net.RetrofitHelper;
import com.lauzy.freedom.data.net.api.SongService;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Desc :
 * Author : Lauzy
 * Date : 2017/7/10
 * Blog : http://www.jianshu.com/u/e76853f863a9
 * Email : freedompaladin@gmail.com
 */
public class SongDataRepository implements SongRepository {

    //  gson  基类提取，封装后抛多种异常 === presentation 中 处理，
    //  防止 自定义DisposableObserver造成的多状态多场景无法处理
    public static void getSongData(Context context) {

        RetrofitHelper retrofitHelper = new RetrofitHelper(context);
        SongService songService = retrofitHelper.createApi(SongService.class);
        songService.getSongList("", 1, 1, 1)
                .subscribe(new DisposableObserver<List<SongListEntity>>() {
                    @Override
                    public void onNext(@NonNull List<SongListEntity> entities) {

                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    public Observable<List<SongListBean>> getSongList(String method, int type, int offset, int size) {
        return null;
    }
}
