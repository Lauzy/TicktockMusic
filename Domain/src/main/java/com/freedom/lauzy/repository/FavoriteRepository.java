package com.freedom.lauzy.repository;

import com.freedom.lauzy.model.FavoriteSongBean;

import java.util.List;

import io.reactivex.Observable;

/**
 * Desc : 我的喜欢数据接口
 * Author : Lauzy
 * Date : 2017/9/12
 * Blog : http://www.jianshu.com/u/e76853f863a9
 * Email : freedompaladin@gmail.com
 */
public interface FavoriteRepository {
    Observable<Long> addFavoriteSong(FavoriteSongBean songBean);

    Observable<List<FavoriteSongBean>> getFavoriteSongs();

    Observable<Integer> clearFavoriteSongs();

    Observable<Boolean> isFavoriteSong(long songId);

    Observable<Long> deleteFavoriteSong(long songId);
}
