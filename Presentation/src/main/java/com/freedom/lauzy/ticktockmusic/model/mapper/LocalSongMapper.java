package com.freedom.lauzy.ticktockmusic.model.mapper;

import com.freedom.lauzy.model.LocalSongBean;
import com.freedom.lauzy.ticktockmusic.model.SongEntity;
import com.lauzy.freedom.data.database.BaseDb;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

/**
 * Desc : LocalSong转换，LocalSongBean转换为SongEntity，以实现序列化接口
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
        songEntity.type = localSongBean.type;
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

    /**
     * 重要：本地默认修改type为LOCAL
     * @param localSongBean SongEntity
     * @return SongEntity
     */
    public static SongEntity transform(SongEntity localSongBean) {
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
        songEntity.type = BaseDb.QueueParam.LOCAL;
        return songEntity;
    }

    public static List<SongEntity> transformLocal(List<SongEntity> localSongBeen) {
        List<SongEntity> localSongEntities;
        if (localSongBeen != null && !localSongBeen.isEmpty()) {
            localSongEntities = new ArrayList<>();
            for (SongEntity localSongBean : localSongBeen) {
                localSongEntities.add(transform(localSongBean));
            }
        } else {
            localSongEntities = Collections.emptyList();
        }
        return localSongEntities;
    }

    public LocalSongBean transformToLocal(SongEntity songEntity) {
        if (songEntity == null) {
            throw new IllegalArgumentException("Cannot transform a null value");
        }
        LocalSongBean localSongBean = new LocalSongBean();
        localSongBean.id = songEntity.id;
        localSongBean.albumCover = songEntity.albumCover;
        localSongBean.albumId = songEntity.albumId;
        localSongBean.albumName = songEntity.albumName;
        localSongBean.artistId = songEntity.artistId;
        localSongBean.artistName = songEntity.artistName;
        localSongBean.duration = songEntity.duration;
        localSongBean.path = songEntity.path;
        localSongBean.size = songEntity.size;
        localSongBean.title = songEntity.title;
        localSongBean.songLength = songEntity.songLength;
        localSongBean.type = songEntity.type;
        return localSongBean;
    }
}
