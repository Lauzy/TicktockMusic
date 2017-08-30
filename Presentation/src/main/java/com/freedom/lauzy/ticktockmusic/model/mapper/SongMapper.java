package com.freedom.lauzy.ticktockmusic.model.mapper;

import com.freedom.lauzy.model.Song;
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

    public static SongEntity transform(Song song) {
        if (song == null) {
            throw new IllegalArgumentException("Cannot transform a null value");
        }
        SongEntity songEntity = new SongEntity();
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

    public static List<SongEntity> transform(List<Song> songs) {
        List<SongEntity> localSongEntities;
        if (songs != null && !songs.isEmpty()) {
            localSongEntities = new ArrayList<>();
            for (Song song : songs) {
                localSongEntities.add(transform(song));
            }
        } else {
            localSongEntities = Collections.emptyList();
        }
        return localSongEntities;
    }
}
