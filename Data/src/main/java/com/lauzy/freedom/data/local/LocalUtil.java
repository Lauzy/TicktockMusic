package com.lauzy.freedom.data.local;

import android.content.Context;

/**
 * Desc : 音乐工具类
 * Author : Lauzy
 * Date : 2017/8/10
 * Blog : http://www.jianshu.com/u/e76853f863a9
 * Email : freedompaladin@gmail.com
 */
class LocalUtil {

    static String getCoverUri(Context context, long albumId) {
        /*String albumCoverUri = null;
        String uriAlbums = "content://media/external/audio/albums";
//        String[] projection = new String[]{"album_art"};
        String[] projection = new String[]{MediaStore.Audio.AlbumColumns.ALBUM_ART};
        Cursor cursor = context.getContentResolver().query(
                Uri.parse(uriAlbums + "/" + albumId),
                projection, null, null, null);
        if (cursor != null && cursor.getCount() > 0 && cursor.getColumnCount() > 0) {
            cursor.moveToNext();
            albumCoverUri = cursor.getString(0);
            cursor.close();
        }*/
        String uriAlbums = "content://media/external/audio/albumart";
        return uriAlbums + "/" + albumId;
    }

    static String formatTime(long time) {
        String min = time / (1000 * 60) + "";
        String sec = time % (1000 * 60) + "";
        if (min.length() < 2) {
            min = "0" + time / (1000 * 60) + "";
        } else {
            min = time / (1000 * 60) + "";
        }
        if (sec.length() == 4) {
            sec = "0" + (time % (1000 * 60)) + "";
        } else if (sec.length() == 3) {
            sec = "00" + (time % (1000 * 60)) + "";
        } else if (sec.length() == 2) {
            sec = "000" + (time % (1000 * 60)) + "";
        } else if (sec.length() == 1) {
            sec = "0000" + (time % (1000 * 60)) + "";
        }
        return min + ":" + sec.trim().substring(0, 2);
    }
}
