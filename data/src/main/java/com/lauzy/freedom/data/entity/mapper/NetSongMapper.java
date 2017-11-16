package com.lauzy.freedom.data.entity.mapper;

import com.freedom.lauzy.model.NetSongEntity;
import com.lauzy.freedom.data.entity.OnlineSongEntity;
import com.lauzy.freedom.data.local.LocalUtil;

/**
 * Desc : 将Json默认生成格式的播放信息实体类转化为直接使用的NetSongBean
 * Author : Lauzy
 * Date : 2017/9/17
 * Blog : http://www.jianshu.com/u/e76853f863a9
 * Email : freedompaladin@gmail.com
 */
public class NetSongMapper {

    public NetSongEntity transform(OnlineSongEntity songEntity) {
        NetSongEntity netSongBean = null;
        try {
            if (songEntity != null && songEntity.songinfo != null && songEntity.bitrate != null) {
                netSongBean = new NetSongEntity();
                netSongBean.imgUrl = songEntity.songinfo.pic_big;
                netSongBean.lrcLink = songEntity.songinfo.lrclink;
                netSongBean.songId = songEntity.songinfo.song_id;
                netSongBean.title = songEntity.songinfo.title;
                netSongBean.tingUid = songEntity.songinfo.ting_uid;
                netSongBean.author = songEntity.songinfo.author;
                netSongBean.albumId = songEntity.songinfo.album_id;
                netSongBean.albumTitle = songEntity.songinfo.album_title;
                netSongBean.artistId = songEntity.songinfo.artist_id;
                netSongBean.artistName = songEntity.songinfo.author;
                netSongBean.size = songEntity.bitrate.file_size;
                netSongBean.duration = Long.parseLong(songEntity.bitrate.file_duration);
                netSongBean.songLength = LocalUtil.formatSecondTime(Long.parseLong
                        (songEntity.bitrate.file_duration));
                netSongBean.playPath = songEntity.bitrate.file_link;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return netSongBean;
    }
}
