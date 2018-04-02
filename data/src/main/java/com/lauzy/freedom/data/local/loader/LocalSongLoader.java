package com.lauzy.freedom.data.local.loader;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import com.freedom.lauzy.model.LocalSongBean;
import com.lauzy.freedom.data.local.LocalUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Desc : 本地音乐加载类
 * Author : Lauzy
 * Date : 2017/8/10
 * Blog : http://www.jianshu.com/u/e76853f863a9
 * Email : freedompaladin@gmail.com
 */
public class LocalSongLoader {


    public static List<LocalSongBean> getLocalSongList(Context context) {
        Cursor cursor = getSongCursor(context, null, null);
        return queryLocalSongs(cursor);
    }

    /**
     * 获取本地音乐列表
     *
     * @param context context
     * @param id      专辑Id，为0时返回所有本地音乐数据
     * @return 本地音乐数据集合
     */
    public static List<LocalSongBean> getLocalSongList(Context context, long id) {
        Cursor cursor = getSongCursor(context, id != 0 ? "album_id = ? " : null,
                id != 0 ? new String[]{String.valueOf(id)} : null);
        return queryLocalSongs(cursor);
    }

    public static List<LocalSongBean> getLocalArtistSongList(Context context, long artistId) {
        Cursor cursor = getSongCursor(context, artistId != 0 ? "artist_id = ? " : null,
                artistId != 0 ? new String[]{String.valueOf(artistId)} : null);
        return queryLocalSongs(cursor);
    }

    public static List<LocalSongBean> getLocalFolderSongList(Context context, String filePath) {
        Cursor cursor = getSongCursor(context, MediaStore.Files.FileColumns.DATA
                + " like ? ", new String[]{filePath + "%"});
        return queryLocalSongs(cursor);
    }

    private static List<LocalSongBean> queryLocalSongs(Cursor cursor) {
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
                localSongBean.albumCover = LocalUtil.getCoverUri(albumId);
                localSongBean.songLength = LocalUtil.formatTime(duration);
                songBeen.add(localSongBean);
            }
        }
        cursor.close();
        return songBeen;
    }

    private static Cursor getSongCursor(Context context, String selection, String[] paramArr) {
        return context.getContentResolver().query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, null,
                selection, paramArr, MediaStore.Audio.Media.DEFAULT_SORT_ORDER);
    }

    public static int deleteSong(Context context, long id) {
        ContentResolver resolver = context.getContentResolver();
        Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        return resolver.delete(uri, MediaStore.Audio.Media._ID + " = ? ", new String[]{String.valueOf(id)});
    }
}
