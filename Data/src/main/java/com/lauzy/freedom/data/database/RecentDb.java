package com.lauzy.freedom.data.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.freedom.lauzy.model.RecentSongBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Desc : 最近播放数据库
 * Author : Lauzy
 * Date : 2017/9/14
 * Blog : http://www.jianshu.com/u/e76853f863a9
 * Email : freedompaladin@gmail.com
 */
public class RecentDb implements BaseDb {

    private static RecentDb sInstance;
    private TickDaoHelper mTickDaoHelper;
    private static final int RECENT_LIMIT = 50;//最多50首

    private RecentDb(Context context) {
        mTickDaoHelper = new TickDaoHelper(context);
    }

    public static RecentDb getInstance(final Context context) {
        if (sInstance == null) {
            synchronized (RecentDb.class) {
                if (sInstance == null) {
                    sInstance = new RecentDb(context.getApplicationContext());
                }
            }
        }
        return sInstance;
    }

    @Override
    public void createTable(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS " + TickDaoHelper.RECENT_TABLE + " ("
                + RecentParam.SONG_ID + " VARCHAR(255),"
                + RecentParam.SOURCE + " VARCHAR(255),"
                + RecentParam.SONG_NAME + " VARCHAR(255),"
                + RecentParam.SINGER_NAME + " VARCHAR(255),"
                + RecentParam.ALBUM_ID + " VARCHAR(255),"
                + RecentParam.ALBUM_NAME + " VARCHAR(255),"
                + RecentParam.PLAY_PATH + " VARCHAR(255),"
                + RecentParam.DURATION + " LONG,"
                + RecentParam.LENGTH + " VARCHAR(255),"
                + RecentParam.PLAY_TIME + " VARCHAR(255),"
                + RecentParam.ALBUM_COVER + " VARCHAR(255),"
                + "CONSTRAINT UC_RECENT UNIQUE ("
                + RecentParam.SOURCE + "," + RecentParam.SONG_ID + " ));");
    }

    @Override
    public void upgradeTable(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    /**
     * 添加最近播放数据
     *
     * @param songBean 音乐Bean
     */
    public void addRecentSong(RecentSongBean songBean) {
        SQLiteDatabase db = mTickDaoHelper.getReadableDatabase();
        db.beginTransaction();
        try {
            //判断是否存在
            Cursor exitCursor = null;
            try {
                exitCursor = db.query(TickDaoHelper.RECENT_TABLE, new String[]{RecentParam.SONG_ID},
                        RecentParam.SONG_ID + " = ? ", new String[]{String.valueOf(songBean.id)},
                        null, null, null, null);
                if (exitCursor != null && exitCursor.getCount() > 0) {
                    ContentValues values = putContentValue(songBean);
                    db.update(TickDaoHelper.RECENT_TABLE, values, RecentParam.SONG_ID + " = ? ",
                            new String[]{String.valueOf(songBean.id)});
                } else {
                    //插入数据
                    ContentValues values = putContentValue(songBean);
                    db.replace(TickDaoHelper.RECENT_TABLE, null, values);
                }
            } finally {
                if (exitCursor != null) {
                    exitCursor.close();
                }
            }

            //限制条数
            Cursor limitCursor = null;
            try {
                limitCursor = db.query(TickDaoHelper.RECENT_TABLE, new String[]{RecentParam.PLAY_TIME},
                        null, null, null, null, RecentParam.PLAY_TIME + " ASC");
                if (limitCursor != null && limitCursor.getCount() > RECENT_LIMIT) {
                    limitCursor.moveToPosition(limitCursor.getCount() - RECENT_LIMIT);
                    long playTime = limitCursor.getLong(limitCursor.getColumnIndex(RecentParam.PLAY_TIME));
                    db.delete(TickDaoHelper.RECENT_TABLE, RecentParam.PLAY_TIME + " < ?",
                            new String[]{String.valueOf(playTime)});
                }
            } finally {
                if (limitCursor != null) {
                    limitCursor.close();
                }
            }
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
            db.close();
        }
    }

    private ContentValues putContentValue(RecentSongBean songBean) {
        ContentValues values = new ContentValues();
        values.put(RecentParam.SOURCE, songBean.type);
        values.put(RecentParam.SONG_ID, songBean.id);
        values.put(RecentParam.SONG_NAME, songBean.title);
        values.put(RecentParam.ALBUM_ID, songBean.albumId);
        values.put(RecentParam.ALBUM_NAME, songBean.albumName);
        values.put(RecentParam.SINGER_NAME, songBean.artistName);
        values.put(RecentParam.DURATION, songBean.duration);
        values.put(RecentParam.LENGTH, songBean.songLength);
        values.put(RecentParam.PLAY_PATH, songBean.path);
        values.put(RecentParam.ALBUM_COVER, songBean.albumCover);
        values.put(RecentParam.PLAY_TIME, System.currentTimeMillis());
        return values;
    }

    /**
     * 获取最近播放的数据集合
     * @return 最近播放的数据集合
     */
    public List<RecentSongBean> getRecentSongBean() {
        SQLiteDatabase db = mTickDaoHelper.getReadableDatabase();
        Cursor cursor = null;
        try {
            cursor = db.query(TickDaoHelper.RECENT_TABLE, null, null, null, null, null,
                    RecentParam.PLAY_TIME + " DESC");
            if (cursor != null && cursor.getCount() > 0) {
                List<RecentSongBean> songBeen = new ArrayList<>();
                while (cursor.moveToNext()) {
                    RecentSongBean listBean = new RecentSongBean();
                    listBean.id = Long.parseLong(cursor.getString(cursor.getColumnIndex(RecentParam.SONG_ID)));
                    listBean.title = cursor.getString(cursor.getColumnIndex(RecentParam.SONG_NAME));
                    listBean.artistName = cursor.getString(cursor.getColumnIndex(RecentParam.SINGER_NAME));
                    listBean.albumName = cursor.getString(cursor.getColumnIndex(RecentParam.ALBUM_NAME));
                    listBean.albumId = Long.parseLong(cursor.getString(cursor.getColumnIndex(RecentParam.ALBUM_ID)));
                    listBean.path = cursor.getString(cursor.getColumnIndex(RecentParam.PLAY_PATH));
                    listBean.type = cursor.getString(cursor.getColumnIndex(RecentParam.SOURCE));
                    listBean.duration = cursor.getLong(cursor.getColumnIndex(RecentParam.DURATION));
                    listBean.songLength = cursor.getString(cursor.getColumnIndex(RecentParam.LENGTH));
                    listBean.albumCover = cursor.getString(cursor.getColumnIndex(RecentParam.ALBUM_COVER));
                    songBeen.add(listBean);
                }
                return songBeen;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            db.close();
        }
        return null;
    }

    /**
     * 删除最近播放的数据
     * @param songId songId
     * @return 删除结果
     */
    public long deleteRecentSong(long songId) {
        SQLiteDatabase db = mTickDaoHelper.getReadableDatabase();
        db.beginTransaction();
        int delete = db.delete(TickDaoHelper.RECENT_TABLE, RecentParam.SONG_ID + " = ? ",
                new String[]{String.valueOf(songId)});
        db.setTransactionSuccessful();
        db.endTransaction();
        db.close();
        return delete;
    }

    /**
     * 清空最近播放数据
     * @return 清除结果
     */
    public int clearRecentSongs() {
        SQLiteDatabase db = mTickDaoHelper.getReadableDatabase();
        db.beginTransaction();
        int delete = db.delete(TickDaoHelper.RECENT_TABLE, null, null);
        db.setTransactionSuccessful();
        db.endTransaction();
        db.close();
        return delete;
    }
}
