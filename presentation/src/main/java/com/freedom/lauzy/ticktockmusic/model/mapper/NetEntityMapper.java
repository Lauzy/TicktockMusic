package com.freedom.lauzy.ticktockmusic.model.mapper;

import com.freedom.lauzy.model.NetSongBean;
import com.freedom.lauzy.model.NetSongEntity;
import com.freedom.lauzy.model.SongType;
import com.freedom.lauzy.ticktockmusic.model.SongEntity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Desc : 网络歌曲信息转换为SongEntity
 * Author : Lauzy
 * Date : 2017/9/17
 * Blog : http://www.jianshu.com/u/e76853f863a9
 * Email : freedompaladin@gmail.com
 */
public class NetEntityMapper {
    public static SongEntity transform(NetSongEntity netSongEntity) {
        if (netSongEntity == null) {
            throw new IllegalArgumentException("Cannot transform a null value");
        }
        SongEntity songEntity = null;
        try {
            songEntity = new SongEntity();
            songEntity.id = Long.parseLong(netSongEntity.songId);
            songEntity.albumCover = netSongEntity.imgUrl;
            songEntity.albumId = Long.parseLong(netSongEntity.albumId);
            songEntity.albumName = netSongEntity.albumTitle;
            songEntity.artistId = Long.parseLong(netSongEntity.artistId);
            songEntity.artistName = netSongEntity.artistName;
            songEntity.duration = netSongEntity.duration;
            songEntity.path = netSongEntity.playPath;
            songEntity.title = netSongEntity.title;
            songEntity.size = Long.parseLong(netSongEntity.size);
            songEntity.songLength = netSongEntity.songLength;
            songEntity.type = SongType.NET;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return songEntity;
    }


    public static SongEntity transform(NetSongBean netSongEntity) {
        if (netSongEntity == null) {
            throw new IllegalArgumentException("Cannot transform a null value");
        }
        SongEntity songEntity = null;
        try {
            songEntity = new SongEntity();
            songEntity.id = Long.parseLong(netSongEntity.songId);
            songEntity.albumCover = netSongEntity.imgUrl;
            songEntity.albumId = Long.parseLong(netSongEntity.albumId);
            songEntity.albumName = netSongEntity.albumTitle;
            songEntity.artistId = Long.parseLong(netSongEntity.artistId);
            songEntity.artistName = netSongEntity.artistName;
            songEntity.duration = netSongEntity.duration;
            songEntity.title = netSongEntity.title;
            songEntity.songLength = netSongEntity.songLength;
            songEntity.type = SongType.NET;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return songEntity;
    }

    public static List<SongEntity> transform(List<NetSongBean> netSongBeen) {
        List<SongEntity> netSongEntities;
        if (netSongBeen != null && !netSongBeen.isEmpty()) {
            netSongEntities = new ArrayList<>();
            for (NetSongBean netSongBean : netSongBeen) {
                netSongEntities.add(transform(netSongBean));
            }
        } else {
            netSongEntities = Collections.emptyList();
        }
        return netSongEntities;
    }
}
