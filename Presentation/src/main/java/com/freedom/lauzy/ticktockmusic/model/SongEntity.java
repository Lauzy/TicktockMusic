package com.freedom.lauzy.ticktockmusic.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.freedom.lauzy.model.LocalSongBean;
import com.freedom.lauzy.model.Song;

/**
 * Desc : 实现序列化（Domain为Java Library,无法实现Parcelable接口）
 * Author : Lauzy
 * Date : 2017/8/18
 * Blog : http://www.jianshu.com/u/e76853f863a9
 * Email : freedompaladin@gmail.com
 */
public class SongEntity extends Song implements Parcelable {

   /* public long id; //音乐ID
    public String title; //音乐名
    public long duration; //时长
    public String songLength;
    public String path; //路径
    public long albumId; //专辑ID
    public String albumName; //专辑名
    public Object albumCover;//专辑封面
    public long artistId; //歌手ID
    public String artistName; //歌手名
    public long size;//大小*/

    public SongEntity() {
    }

    protected SongEntity(Parcel in) {
        id = in.readLong();
        title = in.readString();
        duration = in.readLong();
        songLength = in.readString();
        path = in.readString();
        albumId = in.readLong();
        albumName = in.readString();
        artistId = in.readLong();
        artistName = in.readString();
        size = in.readLong();
    }

    public static final Creator<SongEntity> CREATOR = new Creator<SongEntity>() {
        @Override
        public SongEntity createFromParcel(Parcel in) {
            return new SongEntity(in);
        }

        @Override
        public SongEntity[] newArray(int size) {
            return new SongEntity[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(title);
        dest.writeLong(duration);
        dest.writeString(songLength);
        dest.writeString(path);
        dest.writeLong(albumId);
        dest.writeString(albumName);
        dest.writeLong(artistId);
        dest.writeString(artistName);
        dest.writeLong(size);
    }
}
