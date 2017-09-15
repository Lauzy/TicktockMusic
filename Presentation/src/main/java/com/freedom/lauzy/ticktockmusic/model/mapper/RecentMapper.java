package com.freedom.lauzy.ticktockmusic.model.mapper;

import com.freedom.lauzy.model.RecentSongBean;
import com.freedom.lauzy.ticktockmusic.model.SongEntity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

/**
 * Desc : 最近播放Mapper
 * Author : Lauzy
 * Date : 2017/9/15
 * Blog : http://www.jianshu.com/u/e76853f863a9
 * Email : freedompaladin@gmail.com
 */
public class RecentMapper {
    @Inject
    public RecentMapper() {
    }

    public SongEntity transform(RecentSongBean recentSongBean) {
        if (recentSongBean == null) {
            throw new IllegalArgumentException("Cannot transform a null value");
        }
        SongEntity songEntity = new SongEntity();
        songEntity.id = recentSongBean.id;
        songEntity.albumId = recentSongBean.albumId;
        songEntity.albumName = recentSongBean.albumName;
        songEntity.artistName = recentSongBean.artistName;
        songEntity.duration = recentSongBean.duration;
        songEntity.path = recentSongBean.path;
        songEntity.title = recentSongBean.title;
        songEntity.songLength = recentSongBean.songLength;
        songEntity.type = recentSongBean.type;
        songEntity.albumCover = recentSongBean.albumCover;
        return songEntity;
    }


    public List<SongEntity> transform(List<RecentSongBean> recentSongBeen) {
        List<SongEntity> localSongEntities;
        if (recentSongBeen != null && !recentSongBeen.isEmpty()) {
            localSongEntities = new ArrayList<>();
            for (RecentSongBean recentSongBean : recentSongBeen) {
                localSongEntities.add(transform(recentSongBean));
            }
        } else {
            localSongEntities = Collections.emptyList();
        }
        return localSongEntities;
    }

    public RecentSongBean transform(SongEntity songEntity) {
        if (songEntity == null) {
            throw new IllegalArgumentException("Cannot transform a null value");
        }
        RecentSongBean recentSongBean = new RecentSongBean();
        recentSongBean.id = songEntity.id;
        recentSongBean.albumId = songEntity.albumId;
        recentSongBean.albumName = songEntity.albumName;
        recentSongBean.artistName = songEntity.artistName;
        recentSongBean.duration = songEntity.duration;
        recentSongBean.path = songEntity.path;
        recentSongBean.title = songEntity.title;
        recentSongBean.songLength = songEntity.songLength;
        recentSongBean.type = songEntity.type;
        recentSongBean.albumCover = String.valueOf(songEntity.albumCover);
        return recentSongBean;
    }
}
