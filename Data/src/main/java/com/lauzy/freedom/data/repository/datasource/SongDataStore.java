package com.lauzy.freedom.data.repository.datasource;


import com.lauzy.freedom.data.entity.SongListEntity;

import java.util.List;

import io.reactivex.Observable;

/**
 * Desc : 歌曲数据接口
 * Author : Lauzy
 * Date : 2017/7/11
 * Blog : http://www.jianshu.com/u/e76853f863a9
 * Email : freedompaladin@gmail.com
 */
public interface SongDataStore {

    Observable<List<SongListEntity>> getSongListEntities(String method, int type, int offset, int size);
}
