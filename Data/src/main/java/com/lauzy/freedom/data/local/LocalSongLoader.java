package com.lauzy.freedom.data.local;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.LocaleList;
import android.provider.MediaStore;

import com.freedom.lauzy.model.LocalSongBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Desc : Load Local Song
 * Author : Lauzy
 * Date : 2017/8/10
 * Blog : http://www.jianshu.com/u/e76853f863a9
 * Email : freedompaladin@gmail.com
 */
public class LocalSongLoader {
    public static List<LocalSongBean> getLocalSongList(Context context) {
        ContentResolver contentResolver = context.getContentResolver();
        Cursor cursor = contentResolver.query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, null,
                null, null, MediaStore.Audio.Media.DEFAULT_SORT_ORDER);
        List<LocalSongBean> songBeen = new ArrayList<>();
        if (cursor == null) {
            return null;
        }
        while (cursor.moveToNext()) {
            long songId = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media._ID));
            String title = cursor.getString((cursor.getColumnIndex(MediaStore.Audio.Media.TITLE)));
            long duration = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.DURATION));
            String path = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA));
            long size = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.SIZE));
            long artistId = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST_ID));
            String artistName = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST));
            String albumName = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM));
            long albumId = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID));
            int isMusic = cursor.getInt(cursor.getColumnIndex(MediaStore.Audio.Media.IS_MUSIC));
            if (isMusic != 0 && duration / 1000 >= 45) { //大于45秒的才可以哦
                LocalSongBean localSongBean = new LocalSongBean();
                localSongBean.id = songId;
                localSongBean.title = title;
                localSongBean.duration = duration;
                localSongBean.path = path;
                localSongBean.size = size;
                localSongBean.artistId = artistId;
                localSongBean.artistName = artistName;
                localSongBean.albumId = albumId;
                localSongBean.albumName = albumName;
                localSongBean.albumCover = LocalUtil.getCoverUri(context, albumId);
                localSongBean.songLength = LocalUtil.formatTime(duration);
                songBeen.add(localSongBean);
            }
        }
        cursor.close();
        return songBeen;
    }


}
