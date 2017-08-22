package com.freedom.lauzy.ticktockmusic.model;

import com.lauzy.freedom.data.repository.LocalSongRepositoryImpl;

import java.util.List;

/**
 * Desc : 播放列表数据
 * Author : Lauzy
 * Date : 2017/8/22
 * Blog : http://www.jianshu.com/u/e76853f863a9
 * Email : freedompaladin@gmail.com
 */
public class MediaDataProvider {
    private List<SongEntity> mSongEntities;

    public MediaDataProvider() {

    }

    public MediaDataProvider(List<SongEntity> songEntities) {
        mSongEntities = songEntities;
    }

    public void setSongEntities(List<SongEntity> songEntities) {
        mSongEntities = songEntities;
    }

    public List<SongEntity> getSongEntities() {
        return mSongEntities;
    }
}
