package com.lauzy.freedom.data.repository;

import android.content.Context;

import com.freedom.lauzy.model.LocalAlbumBean;
import com.freedom.lauzy.model.LocalSongBean;
import com.freedom.lauzy.repository.LocalSongRepository;
import com.lauzy.freedom.data.local.LocalAlbumLoader;
import com.lauzy.freedom.data.local.LocalSongLoader;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

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
@Singleton
public class LocalSongRepositoryImpl implements LocalSongRepository {

    private Context mContext;

    @Inject
    public LocalSongRepositoryImpl(Context context) {
        mContext = context;
    }

    @Override
    public Observable<List<LocalSongBean>> getLocalSongList() {

        return Observable.create(new ObservableOnSubscribe<List<LocalSongBean>>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<List<LocalSongBean>> e) throws Exception {
                e.onNext(LocalSongLoader.getLocalSongList(mContext));
            }
        });
    }

    @Override
    public Observable<List<LocalAlbumBean>> getLocalAlbumList() {
        return Observable.create(new ObservableOnSubscribe<List<LocalAlbumBean>>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<List<LocalAlbumBean>> e) throws Exception {
                e.onNext(LocalAlbumLoader.getLocalAlbums(mContext));
            }
        });
    }
}
