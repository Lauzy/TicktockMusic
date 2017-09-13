package com.lauzy.freedom.data.repository;

import android.content.Context;

import com.freedom.lauzy.model.FavoriteSongBean;
import com.freedom.lauzy.repository.FavoriteRepository;
import com.lauzy.freedom.data.database.FavoriteDao;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.annotations.NonNull;

/**
 * Desc : 我的喜欢数据实现类
 * Author : Lauzy
 * Date : 2017/9/12
 * Blog : http://www.jianshu.com/u/e76853f863a9
 * Email : freedompaladin@gmail.com
 */
public class FavoriteRepositoryImpl implements FavoriteRepository {

    private Context mContext;

    @Inject
    public FavoriteRepositoryImpl(Context context) {
        mContext = context;
    }

    @Override
    public Observable<Long> addFavoriteSong(final FavoriteSongBean songBean) {
        return Observable.create(new ObservableOnSubscribe<Long>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<Long> e) throws Exception {
                long value = FavoriteDao.getInstance(mContext).addFavoriteSong(songBean);
                e.onNext(value);
                e.onComplete();
            }
        });
    }

    @Override
    public Observable<List<FavoriteSongBean>> getFavoriteSongs() {
        return Observable.create(new ObservableOnSubscribe<List<FavoriteSongBean>>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<List<FavoriteSongBean>> e)
                    throws Exception {
                List<FavoriteSongBean> favoriteSongs = FavoriteDao.getInstance(mContext)
                        .getFavoriteSongs();
                e.onNext(favoriteSongs != null ? favoriteSongs : Collections
                        .<FavoriteSongBean>emptyList());
                e.onComplete();
            }
        });
    }

    @Override
    public Observable<Integer> clearFavoriteSongs() {
        return Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<Integer> e) throws Exception {
                e.onNext(FavoriteDao.getInstance(mContext).clearFavoriteSongs());
                e.onComplete();
            }
        });
    }

    @Override
    public Observable<Boolean> isFavoriteSong(final long songId) {
        return Observable.create(new ObservableOnSubscribe<Boolean>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<Boolean> e) throws Exception {
                e.onNext(FavoriteDao.getInstance(mContext).isFavorite(songId));
                e.onComplete();
            }
        });
    }

    @Override
    public Observable<Long> deleteFavoriteSong(final long songId) {
        return Observable.create(new ObservableOnSubscribe<Long>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<Long> e) throws Exception {
                e.onNext(FavoriteDao.getInstance(mContext).deleteFavoriteSong(songId));
                e.onComplete();
            }
        });
    }
}
