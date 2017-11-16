package com.freedom.lauzy.model;

/**
 * Desc : 最近播放bean
 * Author : Lauzy
 * Date : 2017/9/14
 * Blog : http://www.jianshu.com/u/e76853f863a9
 * Email : freedompaladin@gmail.com
 */
public class RecentSongBean {
    public long id; //音乐ID
    public String title; //音乐名
    public String path; //路径
    public long albumId; //专辑ID
    public String albumName; //专辑名
    public String artistName; //歌手名
    public long duration;//时长
    public String songLength;//音乐长度（时间格式化）
    public String type;//来源（本地或网络）
    public String albumCover;
}
