package com.lauzy.freedom.data.entity.mapper;

import com.freedom.lauzy.model.NetSongBean;
import com.lauzy.freedom.data.database.BaseDb;
import com.lauzy.freedom.data.entity.SongListEntity;
import com.lauzy.freedom.data.local.LocalUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Desc : SongListMapper 将Json默认生成格式的SongListEntity转化为直接使用的NetSongBean
 * Author : Lauzy
 * Date : 2017/7/11
 * Blog : http://www.jianshu.com/u/e76853f863a9
 * Email : freedompaladin@gmail.com
 */
@Singleton
public class SongListMapper {

    @Inject
    public SongListMapper() {
    }

    /**
     * 转换 entity
     *
     * @param entity 实体对象
     * @return 转换后的实体对象
     */
    @SuppressWarnings("all")
    public NetSongBean transform(SongListEntity entity) {
        NetSongBean netSongBean = null;
        if (entity != null) {
            netSongBean = new NetSongBean();
            netSongBean.language = entity.language;
            netSongBean.imgUrl = entity.pic_big;
            netSongBean.publishTime = entity.publishtime;
            netSongBean.lrcLink = entity.lrclink;
            netSongBean.versions = entity.versions;
            netSongBean.info = entity.info;
            netSongBean.songId = entity.song_id;
            netSongBean.title = entity.title;
            netSongBean.tingUid = entity.ting_uid;
            netSongBean.author = entity.author;
            netSongBean.albumId = entity.album_id;
            netSongBean.albumTitle = entity.album_title;
            netSongBean.artistId = entity.artist_id;
            netSongBean.artistName = entity.artist_name;
            netSongBean.rank = entity.rank;
            netSongBean.duration = entity.file_duration;
            netSongBean.songLength = LocalUtil.formatTime(entity.file_duration);
            netSongBean.type = BaseDb.QueueParam.NET;
        }
        return netSongBean;
    }

    /**
     * 转换entity集合
     *
     * @param entities 集合
     * @return 转换后的集合
     */
    public List<NetSongBean> transform(List<SongListEntity> entities) {
        List<NetSongBean> songListBeen;
        if (entities != null && !entities.isEmpty()) {
            songListBeen = new ArrayList<>();
            for (SongListEntity entity : entities) {
                NetSongBean bean = transform(entity);
                if (bean != null) {
                    songListBeen.add(bean);
                }
            }
        } else {
            songListBeen = Collections.emptyList();
        }
        return songListBeen;
    }
}
