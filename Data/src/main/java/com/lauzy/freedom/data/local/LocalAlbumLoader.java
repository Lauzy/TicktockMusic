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

    /**
     * 获取全部专辑
     * @param context context
     * @return 专辑列表
     */
    public static List<LocalAlbumBean> getLocalAlbums(Context context) {
        Cursor cursor = getAlbumCursor(context, null, null);
        return queryResult(context, cursor);
    }

    /**
     * 根据ID，获取本地专辑
     *
     * @param context context
     * @param id      专辑ID，若ID为0，则获取全部数据；不为0，则查找指定ID的数据
     * @return 专辑列表
     */
    public static List<LocalAlbumBean> getLocalAlbums(Context context, long id) {
        Cursor cursor = getAlbumCursor(context, id != 0 ? "_id = ?" : null,
                id != 0 ? new String[]{String.valueOf(id)} : null);
        return queryResult(context, cursor);
    }

    private static List<LocalAlbumBean> queryResult(Context context, Cursor cursor) {
        List<LocalAlbumBean> albumBeen = new ArrayList<>();
        if (cursor == null) {
            return null;
        }
        while (cursor.moveToNext()) {
            String artistName = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST));
            String albumName = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM));
            long id = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media._ID));
            int songsNum = cursor.getInt(cursor.getColumnIndex(MediaStore.Audio.AlbumColumns.NUMBER_OF_SONGS));
            LocalAlbumBean albumBean = new LocalAlbumBean();
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

    /**
     * 专辑Cursor
     *
     * @param context   context
     * @param selection 筛选
     * @param paramArr  条件
     * @return cursor
     */
    private static Cursor getAlbumCursor(Context context, String selection, String[] paramArr) {
        String[] projections = new String[]{MediaStore.Audio.Media._ID, MediaStore.Audio.Media.ALBUM,
                MediaStore.Audio.Media.ARTIST, MediaStore.Audio.Media.ARTIST_ID,
                MediaStore.Audio.AlbumColumns.NUMBER_OF_SONGS};
        return context.getContentResolver().query(MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI,
                projections, selection, paramArr, MediaStore.Audio.Albums.DEFAULT_SORT_ORDER);
    }
}
