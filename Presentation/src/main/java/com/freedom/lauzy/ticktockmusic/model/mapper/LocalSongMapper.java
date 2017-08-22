package com.freedom.lauzy.ticktockmusic.model.mapper;

import com.freedom.lauzy.model.LocalSongBean;
import com.freedom.lauzy.ticktockmusic.model.SongEntity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

/**
 * Desc : LocalSong转换，LocalSongBean转换为LocalSongEntity，以实现序列化接口
 * Author : Lauzy
 * Date : 2017/8/18
 * Blog : http://www.jianshu.com/u/e76853f863a9
 * Email : freedompaladin@gmail.com
 */
@SuppressWarnings("unused")
public class LocalSongMapper {
    @Inject
    public LocalSongMapper() {
    }

    public SongEntity transform(LocalSongBean localSongBean) {
        if (localSongBean == null) {
            throw new IllegalArgumentException("Cannot transform a null value");
        }
        SongEntity songEntity = new SongEntity();
        songEntity.id = localSongBean.id;
        songEntity.albumCover = localSongBean.albumCover;
        songEntity.albumId = localSongBean.albumId;
        songEntity.albumName = localSongBean.albumName;
        songEntity.artistId = localSongBean.artistId;
        songEntity.artistName = localSongBean.artistName;
        songEntity.duration = localSongBean.duration;
        songEntity.path = localSongBean.path;
        songEntity.size = localSongBean.size;
        songEntity.title = localSongBean.title;
        songEntity.songLength = localSongBean.songLength;
        return songEntity;
    }

    @SuppressWarnings("all")
    public List<SongEntity> transform(List<LocalSongBean> localSongBeen) {
        List<SongEntity> localSongEntities;
        if (localSongBeen != null && !localSongBeen.isEmpty()) {
            localSongEntities = new ArrayList<>();
            for (LocalSongBean localSongBean : localSongBeen) {
                localSongEntities.add(transform(localSongBean));
            }
        } else {
            localSongEntities = Collections.emptyList();
        }
        return localSongEntities;
    }
}
