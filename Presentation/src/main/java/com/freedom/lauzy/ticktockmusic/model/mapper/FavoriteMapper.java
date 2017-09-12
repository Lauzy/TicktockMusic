package com.freedom.lauzy.ticktockmusic.model.mapper;

import com.freedom.lauzy.model.FavoriteSongBean;
import com.freedom.lauzy.ticktockmusic.model.SongEntity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

/**
 * Desc : 我的喜欢Mapper
 * Author : Lauzy
 * Date : 2017/9/12
 * Blog : http://www.jianshu.com/u/e76853f863a9
 * Email : freedompaladin@gmail.com
 */
public class FavoriteMapper {

    @Inject
    public FavoriteMapper() {
    }

    public SongEntity transform(FavoriteSongBean favoriteBean) {
        if (favoriteBean == null) {
            throw new IllegalArgumentException("Cannot transform a null value");
        }
        SongEntity songEntity = new SongEntity();
        songEntity.id = favoriteBean.id;
        songEntity.albumId = favoriteBean.albumId;
        songEntity.albumName = favoriteBean.albumName;
        songEntity.artistName = favoriteBean.artistName;
        songEntity.duration = favoriteBean.duration;
        songEntity.path = favoriteBean.path;
        songEntity.title = favoriteBean.title;
        songEntity.songLength = favoriteBean.songLength;
        songEntity.type = favoriteBean.type;
        return songEntity;
    }


    public List<SongEntity> transform(List<FavoriteSongBean> favoriteSongBeen) {
        List<SongEntity> localSongEntities;
        if (favoriteSongBeen != null && !favoriteSongBeen.isEmpty()) {
            localSongEntities = new ArrayList<>();
            for (FavoriteSongBean favoriteSongBean : favoriteSongBeen) {
                localSongEntities.add(transform(favoriteSongBean));
            }
        } else {
            localSongEntities = Collections.emptyList();
        }
        return localSongEntities;
    }

    public FavoriteSongBean transform(SongEntity songEntity) {
        if (songEntity == null) {
            throw new IllegalArgumentException("Cannot transform a null value");
        }
        FavoriteSongBean favoriteBean = new FavoriteSongBean();
        favoriteBean.id = songEntity.id;
        favoriteBean.albumId = songEntity.albumId;
        favoriteBean.albumName = songEntity.albumName;
        favoriteBean.artistName = songEntity.artistName;
        favoriteBean.duration = songEntity.duration;
        favoriteBean.path = songEntity.path;
        favoriteBean.title = songEntity.title;
        favoriteBean.songLength = songEntity.songLength;
        favoriteBean.type = songEntity.type;
        return favoriteBean;
    }
}
