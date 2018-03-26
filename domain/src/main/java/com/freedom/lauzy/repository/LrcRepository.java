package com.freedom.lauzy.repository;

import com.freedom.lauzy.model.LrcBean;

import java.util.List;

import io.reactivex.Observable;

/**
 * Desc : 歌词
 * Author : Lauzy
 * Date : 2018/3/26
 * Blog : http://www.jianshu.com/u/e76853f863a9
 * Email : freedompaladin@gmail.com
 */
public interface LrcRepository {
    /**
     * 获取歌词
     *
     * @param songName 歌曲名
     * @param singer   歌手
     * @return ob
     */
    Observable<List<LrcBean>> getLrcData(String songName, String singer);
}
