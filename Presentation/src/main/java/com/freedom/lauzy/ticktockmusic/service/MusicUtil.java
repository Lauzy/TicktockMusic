package com.freedom.lauzy.ticktockmusic.service;

import com.freedom.lauzy.model.NetSongBean;
import com.freedom.lauzy.ticktockmusic.model.SongEntity;

import java.util.List;

/**
 * Desc : MusicUtil
 * Author : Lauzy
 * Date : 2017/9/8
 * Blog : http://www.jianshu.com/u/e76853f863a9
 * Email : freedompaladin@gmail.com
 */
public class MusicUtil {
    public static String[] getSongIds(List<SongEntity> songEntities) {
        if (songEntities != null) {
            int size = songEntities.size();
            String[] ids = new String[size];
            for (int i = 0; i < size; i++) {
                ids[i] = String.valueOf(songEntities.get(i).id);
            }
            return ids;
        }
        return null;
    }

    public static String[] getNetSongIds(List<NetSongBean> songEntities) {
        if (songEntities != null) {
            int size = songEntities.size();
            String[] ids = new String[size];
            for (int i = 0; i < size; i++) {
                ids[i] = String.valueOf(songEntities.get(i).songId);
            }
            return ids;
        }
        return null;
    }
}
