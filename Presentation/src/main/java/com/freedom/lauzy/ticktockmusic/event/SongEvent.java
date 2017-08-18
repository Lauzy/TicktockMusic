package com.freedom.lauzy.ticktockmusic.event;

import com.freedom.lauzy.ticktockmusic.model.SongEntity;

/**
 * Desc : SongEvent
 * Author : Lauzy
 * Date : 2017/8/18
 * Blog : http://www.jianshu.com/u/e76853f863a9
 * Email : freedompaladin@gmail.com
 */
public class SongEvent {
    private SongEntity mSongEntity;

    public SongEvent(SongEntity songEntity) {
        mSongEntity = songEntity;
    }

    public SongEntity getSongEntity() {
        return mSongEntity;
    }
}
