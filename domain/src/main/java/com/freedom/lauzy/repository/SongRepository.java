package com.freedom.lauzy.repository;

import com.freedom.lauzy.model.NetSongBean;

import java.util.List;

import io.reactivex.Observable;

/**
 * Desc : SongRepository
 * Author : Lauzy
 * Date : 2017/7/6
 * Blog : http://www.jianshu.com/u/e76853f863a9
 * Email : freedompaladin@gmail.com
 */
public interface SongRepository {
    /**
     * 获取网络数据
     * @param method method
     * @param type 类型
     * @param offset 页数
     * @param size 页面加载量
     * @return ob
     */
    Observable<List<NetSongBean>> getSongList(String method, int type, int offset, int size);

    /**
     * 获取缓存数据
     * @param type 类型
     * @return ob
     */
    Observable<List<NetSongBean>> getCacheSongList(int type);
}
