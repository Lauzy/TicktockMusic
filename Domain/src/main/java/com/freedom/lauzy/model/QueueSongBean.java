package com.freedom.lauzy.model;

/**
 * Desc : 播放队列 Item 实体类
 * Author : Lauzy
 * Date : 2017/8/29
 * Blog : http://www.jianshu.com/u/e76853f863a9
 * Email : freedompaladin@gmail.com
 */
public class QueueSongBean {
    public long id; //音乐ID
    public String title; //音乐名
    public String path; //路径
    public long albumId; //专辑ID
    public String albumName; //专辑名
    public Object albumCover;//专辑封面
    public long artistId; //歌手ID
    public String artistName; //歌手名
    public long size;//大小
    public long duration;//时长
    public String songLength;//音乐长度（时间格式化）
    public String type;//来源（本地或网络）
}
