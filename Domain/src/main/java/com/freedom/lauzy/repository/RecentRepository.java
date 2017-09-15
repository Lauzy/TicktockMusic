package com.freedom.lauzy.repository;

import com.freedom.lauzy.model.RecentSongBean;

import java.util.List;

import io.reactivex.Observable;

/**
 * Desc : 最近播放数据接口
 * Author : Lauzy
 * Date : 2017/9/12
 * Blog : http://www.jianshu.com/u/e76853f863a9
 * Email : freedompaladin@gmail.com
 */
public interface RecentRepository {

    Observable<Void> addRecentSong(RecentSongBean songBean);

    Observable<List<RecentSongBean>> getRecentSongs();

    Observable<Integer> clearRecentSongs();

    Observable<Long> deleteRecentSong(long songId);
}
