package com.freedom.lauzy.ticktockmusic.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.freedom.lauzy.model.QueueSongBean;

/**
 * Desc : 实现序列化（Domain为Java Library,无法实现Parcelable接口）
 * Author : Lauzy
 * Date : 2017/8/18
 * Blog : http://www.jianshu.com/u/e76853f863a9
 * Email : freedompaladin@gmail.com
 */
public class SongEntity extends QueueSongBean implements Parcelable {

    private boolean isAnim;
    private boolean isStop;

    public boolean isAnim() {
        return isAnim;
    }

    public void setAnim(boolean anim) {
        isAnim = anim;
    }

    public boolean isStop() {
        return isStop;
    }

    public void setStop(boolean stop) {
        isStop = stop;
    }

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
        type = in.readString();
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
        dest.writeString(type);
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof SongEntity)) {
            return false;
        }
        SongEntity obj1 = (SongEntity) obj;
        return this.id == obj1.id
                && this.title.equals(obj1.title)
                && this.artistName.equals(obj1.artistName)
                && String.valueOf(this.albumCover).equals(String.valueOf(obj1.albumCover))
                && this.albumName.equals(obj1.albumName)
                && this.albumId == obj1.albumId
                && this.duration == obj1.duration
                && this.type.equals(obj1.type)
                || super.equals(obj);
    }
}
