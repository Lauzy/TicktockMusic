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

    /**
     * 添加歌曲至我的喜欢队列
     * @param songBean songBean
     * @return Observable
     */
    Observable<Long> addFavoriteSong(FavoriteSongBean songBean);

    /**
     * 获取我的喜欢数据
     * @return Observable
     */
    Observable<List<FavoriteSongBean>> getFavoriteSongs();

    /**
     * 清空我的喜欢数据
     * @return Observable
     */
    Observable<Integer> clearFavoriteSongs();

    /**
     * 判断是否为我喜欢的歌曲
     * @param songId songId
     * @return Observable
     */
    Observable<Boolean> isFavoriteSong(long songId);

    /**
     * 删除我喜欢的歌曲
     * @param songId songId
     * @return Observable
     */
    Observable<Long> deleteFavoriteSong(long songId);
}
