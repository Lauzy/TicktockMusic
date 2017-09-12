package com.lauzy.freedom.data.repository;

import android.content.Context;

import com.freedom.lauzy.model.FavoriteSongBean;
import com.freedom.lauzy.repository.FavoriteRepository;
import com.lauzy.freedom.data.database.FavoriteDao;

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
}
