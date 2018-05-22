package com.freedom.lauzy.repository;

import com.freedom.lauzy.model.LocalSongBean;

import java.util.List;

import io.reactivex.Observable;

/**
 * Desc : 搜索歌曲
 * Author : Lauzy
 * Date : 2018/5/22
 * Blog : http://www.jianshu.com/u/e76853f863a9
 * Email : freedompaladin@gmail.com
 */
public interface SearchSongRepository {

    /**
     * 搜索歌曲
     *
     * @param songName 模糊名称
     * @return ob
     */
    Observable<List<LocalSongBean>> searchSong(String songName);
}
