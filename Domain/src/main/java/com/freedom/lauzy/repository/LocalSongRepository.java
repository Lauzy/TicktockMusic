package com.freedom.lauzy.repository;

import com.freedom.lauzy.model.LocalAlbumBean;
import com.freedom.lauzy.model.LocalSongBean;

import java.util.List;

import io.reactivex.Observable;

/**
 * Desc :  LocalSongRepository
 * Author : Lauzy
 * Date : 2017/8/9
 * Blog : http://www.jianshu.com/u/e76853f863a9
 * Email : freedompaladin@gmail.com
 */
public interface LocalSongRepository {
    Observable<List<LocalSongBean>> getLocalSongList();

    Observable<List<LocalAlbumBean>> getLocalAlbumList(long id);
}
