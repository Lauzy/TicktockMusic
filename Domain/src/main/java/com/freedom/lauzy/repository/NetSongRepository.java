package com.freedom.lauzy.repository;

import com.freedom.lauzy.model.NetSongEntity;

import io.reactivex.Observable;

/**
 * Desc : 网络歌曲数据
 * Author : Lauzy
 * Date : 2017/9/17
 * Blog : http://www.jianshu.com/u/e76853f863a9
 * Email : freedompaladin@gmail.com
 */
public interface NetSongRepository {
    Observable<NetSongEntity> getNetSongData(String method, long songId);
}
