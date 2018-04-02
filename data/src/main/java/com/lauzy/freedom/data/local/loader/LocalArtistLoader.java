package com.lauzy.freedom.data.local.loader;

import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;

import com.freedom.lauzy.model.LocalArtistBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Desc : 歌手数据加载
 * Author : Lauzy
 * Date : 2017/9/28
 * Blog : http://www.jianshu.com/u/e76853f863a9
 * Email : freedompaladin@gmail.com
 */
public class LocalArtistLoader {

    public static List<LocalArtistBean> getLocalArtists(Context context) {
        Cursor cursor = getArtistCursor(context, null, null);
        return queryResult(cursor);
    }

    private static List<LocalArtistBean> queryResult(Cursor cursor) {
        if (cursor == null) {
            return null;
        }
        List<LocalArtistBean> artistBeen = new ArrayList<>();
        while (cursor.moveToNext()) {
            long id = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Artists._ID));
            String artistName = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Artists.ARTIST));
            int albumsNum = cursor.getInt(cursor.getColumnIndex(MediaStore.Audio.Artists.NUMBER_OF_ALBUMS));
            int songsNum = cursor.getInt(cursor.getColumnIndex(MediaStore.Audio.Artists.NUMBER_OF_TRACKS));
            LocalArtistBean artistBean = new LocalArtistBean();
            artistBean.artistId = id;
            artistBean.artistName = artistName;
            artistBean.albumsNum = albumsNum;
            artistBean.songsNum = songsNum;
            artistBeen.add(artistBean);
        }
        cursor.close();
        return artistBeen;
    }


    private static Cursor getArtistCursor(Context context, String selection, String[] paramArr) {
        return context.getContentResolver().query(MediaStore.Audio.Artists.EXTERNAL_CONTENT_URI,
                null, selection, paramArr, MediaStore.Audio.Artists.DEFAULT_SORT_ORDER);
    }
}
