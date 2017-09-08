package com.freedom.lauzy.ticktockmusic.model.mapper;

import com.freedom.lauzy.model.LocalSongBean;
import com.freedom.lauzy.model.QueueSongBean;
import com.freedom.lauzy.ticktockmusic.model.SongEntity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Desc : NetSong转换，SongListBean转换为SongEntity，统一处理
 * Author : Lauzy
 * Date : 2017/8/18
 * Blog : http://www.jianshu.com/u/e76853f863a9
 * Email : freedompaladin@gmail.com
 */
public class SongMapper {

    public static SongEntity transform(QueueSongBean queueSongBean) {
        if (queueSongBean == null) {
            throw new IllegalArgumentException("Cannot transform a null value");
        }
        SongEntity songEntity = new SongEntity();
        songEntity.id = queueSongBean.id;
        songEntity.albumCover = queueSongBean.albumCover;
        songEntity.albumId = queueSongBean.albumId;
        songEntity.albumName = queueSongBean.albumName;
        songEntity.artistId = queueSongBean.artistId;
        songEntity.artistName = queueSongBean.artistName;
        songEntity.duration = queueSongBean.duration;
        songEntity.path = queueSongBean.path;
        songEntity.size = queueSongBean.size;
        songEntity.title = queueSongBean.title;
        songEntity.songLength = queueSongBean.songLength;
        return songEntity;
    }

    public static List<SongEntity> transform(List<QueueSongBean> queueSongBeen) {
        List<SongEntity> localSongEntities;
        if (queueSongBeen != null && !queueSongBeen.isEmpty()) {
            localSongEntities = new ArrayList<>();
            for (QueueSongBean queueSongBean : queueSongBeen) {
                localSongEntities.add(transform(queueSongBean));
            }
        } else {
            localSongEntities = Collections.emptyList();
        }
        return localSongEntities;
    }

    public static LocalSongBean transformToQueue(SongEntity song) {
        if (song == null) {
            throw new IllegalArgumentException("Cannot transform a null value");
        }
        LocalSongBean songEntity = new LocalSongBean();
        songEntity.id = song.id;
        songEntity.albumCover = song.albumCover;
        songEntity.albumId = song.albumId;
        songEntity.albumName = song.albumName;
        songEntity.artistId = song.artistId;
        songEntity.artistName = song.artistName;
        songEntity.duration = song.duration;
        songEntity.path = song.path;
        songEntity.size = song.size;
        songEntity.title = song.title;
        songEntity.songLength = song.songLength;
        return songEntity;
    }

    public static List<LocalSongBean> transformToLocalQueue(List<SongEntity> songs) {
        List<LocalSongBean> localSongEntities;
        if (songs != null && !songs.isEmpty()) {
            localSongEntities = new ArrayList<>();
            for (SongEntity song : songs) {
                localSongEntities.add(transformToQueue(song));
            }
        } else {
            localSongEntities = Collections.emptyList();
        }
        return localSongEntities;
    }
}
