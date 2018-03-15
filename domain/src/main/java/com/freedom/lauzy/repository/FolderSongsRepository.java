package com.freedom.lauzy.repository;

import com.freedom.lauzy.model.LocalSongBean;

import java.util.List;

import io.reactivex.Observable;

/**
 * Desc : 文件夹下音乐
 * Author : Lauzy
 * Date : 2018/3/15
 * Blog : http://www.jianshu.com/u/e76853f863a9
 * Email : freedompaladin@gmail.com
 */
public interface FolderSongsRepository {
    /**
     * 获取文件夹下的音乐
     *
     * @param folderPath 文件夹路径
     * @return ob
     */
    Observable<List<LocalSongBean>> getFolderSongs(String folderPath);
}
