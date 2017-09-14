package com.lauzy.freedom.data.database;

import android.database.sqlite.SQLiteDatabase;

/**
 * Desc : 数据库接口及常量
 * Author : Lauzy
 * Date : 2017/8/28
 * Blog : http://www.jianshu.com/u/e76853f863a9
 * Email : freedompaladin@gmail.com
 */
public interface BaseDao {

    void createTable(SQLiteDatabase db);

    void upgradeTable(SQLiteDatabase db, int oldVersion, int newVersion);

    class NetParam {
        static final String TYPE = "type";
        static final String SINGER_ID = "singerId";
        static final String SINGER_NAME = "singerName";
        static final String PIC_URL = "imgUrl";
        static final String SONG_ID = "songId";
        static final String SONG_NAME = "songName";
        static final String LRC_LINK = "lrcLink";
        static final String ALBUM_ID = "albumId";
        static final String ALBUM_NAME = "albumName";
        static final String RANK = "rank";
    }

    class QueueParam {
        static final String SOURCE = "source";
        static final String SONG_ID = "songId";
        static final String SONG_NAME = "songName";
        static final String ALBUM_ID = "albumId";
        static final String ALBUM_NAME = "albumName";
        static final String SINGER_NAME = "singerName";
        static final String PLAY_PATH = "playPath";
        static final String ALBUM_COVER = "albumCover";
        static final String DURATION = "duration";
        static final String LENGTH = "length";

        static final String NET = "NET";
        static final String LOCAL = "LOCAL";
    }

    class FavoriteParam {
        static final String SOURCE = "source";
        static final String SONG_ID = "songId";
        static final String SONG_NAME = "songName";
        static final String ALBUM_ID = "albumId";
        static final String ALBUM_NAME = "albumName";
        static final String SINGER_NAME = "singerName";
        static final String PLAY_PATH = "playPath";
        static final String ADD_TIME = "add_time";
        static final String DURATION = "duration";
        static final String LENGTH = "length";
        static final String ALBUM_COVER = "albumCover";
    }

    class RecentParam {
        static final String SOURCE = "source";
        static final String SONG_ID = "songId";
        static final String SONG_NAME = "songName";
        static final String ALBUM_ID = "albumId";
        static final String ALBUM_NAME = "albumName";
        static final String SINGER_NAME = "singerName";
        static final String PLAY_PATH = "playPath";
        static final String PLAY_TIME = "play_time";
        static final String DURATION = "duration";
        static final String LENGTH = "length";
        static final String ALBUM_COVER = "albumCover";
    }
}
