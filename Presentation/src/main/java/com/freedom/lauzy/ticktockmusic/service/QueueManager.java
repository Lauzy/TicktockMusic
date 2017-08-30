package com.freedom.lauzy.ticktockmusic.service;

import android.content.Context;

import com.freedom.lauzy.model.Song;
import com.freedom.lauzy.ticktockmusic.model.SongEntity;
import com.freedom.lauzy.ticktockmusic.model.mapper.SongMapper;
import com.lauzy.freedom.data.database.PlayQueueDao;

import java.util.List;

/**
 * Desc : 播放队列
 * Author : Lauzy
 * Date : 2017/8/30
 * Blog : http://www.jianshu.com/u/e76853f863a9
 * Email : freedompaladin@gmail.com
 */
public class QueueManager {

    public List<SongEntity> getPlayQueue(Context context) {
        List<Song> songs = PlayQueueDao.getInstance(context).queryQueue();
        return SongMapper.transform(songs);
    }
}
