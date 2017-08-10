package com.lauzy.freedom.data.local;

import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;

import com.freedom.lauzy.model.LocalAlbumBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Desc :  Load Local Albums
 * Author : Lauzy
 * Date : 2017/8/10
 * Blog : http://www.jianshu.com/u/e76853f863a9
 * Email : freedompaladin@gmail.com
 */
public class LocalAlbumLoader {
    public static List<LocalAlbumBean> getLocalAlbums(Context context) {
        String[] projections = new String[]{MediaStore.Audio.Media._ID, MediaStore.Audio.Media.ALBUM,
                MediaStore.Audio.Media.ARTIST, MediaStore.Audio.Media.ARTIST_ID,
                MediaStore.Audio.AlbumColumns.NUMBER_OF_SONGS};
        Cursor cursor = context.getContentResolver()
                .query(MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI, projections, null, null,
                        MediaStore.Audio.Albums.DEFAULT_SORT_ORDER);
        //MediaStore.Audio.Media.DEFAULT_SORT_ORDER
        List<LocalAlbumBean> albumBeen = new ArrayList<>();
        if (cursor == null) {
            return null;
        }
        while (cursor.moveToNext()) {
            String artistName = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST));
            String albumName = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM));
//            long albumId = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID));
            long id = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media._ID));
            int songsNum = cursor.getInt(cursor.getColumnIndex(MediaStore.Audio.AlbumColumns.NUMBER_OF_SONGS));
            LocalAlbumBean albumBean = new LocalAlbumBean();
//            albumBean.albumId = albumId;
            albumBean.id = id;
            albumBean.artistName = artistName;
            albumBean.albumName = albumName;
            albumBean.songsNum = songsNum;
            albumBean.albumCover = LocalUtil.getCoverUri(context, id);
            albumBeen.add(albumBean);
        }
        cursor.close();
        return albumBeen;
    }
}
