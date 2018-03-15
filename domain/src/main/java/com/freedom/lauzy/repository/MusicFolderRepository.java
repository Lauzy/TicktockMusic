package com.freedom.lauzy.repository;

import com.freedom.lauzy.model.Folder;
import com.freedom.lauzy.model.LocalSongBean;

import java.util.List;

import io.reactivex.Observable;

/**
 * Desc : 文件夹
 * Author : Lauzy
 * Date : 2018/3/15
 * Blog : http://www.jianshu.com/u/e76853f863a9
 * Email : freedompaladin@gmail.com
 */
public interface MusicFolderRepository {

    /**
     * 获取文件夹
     * @return ob
     */
    Observable<List<Folder>> getMusicFolders();

}
