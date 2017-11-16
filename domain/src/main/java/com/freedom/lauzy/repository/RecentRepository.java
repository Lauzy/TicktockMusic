package com.freedom.lauzy.repository;

import com.freedom.lauzy.model.RecentSongBean;

import java.util.List;

import io.reactivex.Observable;

/**
 * Desc : 最近播放数据仓库接口
 * Author : Lauzy
 * Date : 2017/9/12
 * Blog : http://www.jianshu.com/u/e76853f863a9
 * Email : freedompaladin@gmail.com
 */
public interface RecentRepository {

    /**
     * 添加至最近播放
     * @param songBean 待添加的数据
     * @return Observable
     */
    Observable<Void> addRecentSong(RecentSongBean songBean);

    /**
     * 获取最近播放的数据
     * @return Observable
     */
    Observable<List<RecentSongBean>> getRecentSongs();

    /**
     * 清空最近播放的数据
     * @return Observable
     */
    Observable<Integer> clearRecentSongs();

    /**
     * 删除最近播放的数据
     * @param songId 需要删除的songId
     * @return Observable
     */
    Observable<Long> deleteRecentSong(long songId);
}
