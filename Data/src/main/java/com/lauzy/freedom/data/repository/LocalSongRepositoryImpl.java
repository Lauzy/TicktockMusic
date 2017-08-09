package com.lauzy.freedom.data.repository;

import com.freedom.lauzy.model.LocalSongBean;
import com.freedom.lauzy.repository.LocalSongRepository;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.annotations.NonNull;

/**
 * Desc :
 * Author : Lauzy
 * Date : 2017/8/9
 * Blog : http://www.jianshu.com/u/e76853f863a9
 * Email : freedompaladin@gmail.com
 */
public class LocalSongRepositoryImpl implements LocalSongRepository {
    @Override
    public Observable<List<LocalSongBean>> getLocalSongList() {

        return Observable.create(new ObservableOnSubscribe<List<LocalSongBean>>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<List<LocalSongBean>> e) throws Exception {
                List<LocalSongBean> localSongBeen = new ArrayList<>();
                LocalSongBean localSongBean = new LocalSongBean();
                localSongBean.title = "TEST";
                localSongBeen.add(localSongBean);
                e.onNext(localSongBeen);
            }
        });
    }
}
