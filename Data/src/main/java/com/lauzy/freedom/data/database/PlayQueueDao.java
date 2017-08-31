package com.lauzy.freedom.data.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.freedom.lauzy.model.LocalSongBean;
import com.freedom.lauzy.model.Song;
import com.freedom.lauzy.model.SongListBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Desc : 播放队列数据库
 * Author : Lauzy
 * Date : 2017/8/29
 * Blog : http://www.jianshu.com/u/e76853f863a9
 * Email : freedompaladin@gmail.com
 */
public class PlayQueueDao implements BaseDao {

    private static PlayQueueDao sInstance;
    private TickDaoHelper mTickDaoHelper;

    private PlayQueueDao(Context context) {
        mTickDaoHelper = new TickDaoHelper(context);
    }

    public static PlayQueueDao getInstance(final Context context) {
        if (sInstance == null) {
            synchronized (PlayQueueDao.class) {
                if (sInstance == null) {
                    sInstance = new PlayQueueDao(context.getApplicationContext());
                }
            }
        }
        return sInstance;
    }

    @Override
    public void createTable(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS " + TickDaoHelper.PLAY_QUEUE + " ("
                + QueueParam.SONG_ID + " VARCHAR(255),"
                + QueueParam.SOURCE + " VARCHAR(255),"
                + QueueParam.SONG_NAME + " VARCHAR(255),"
                + QueueParam.SINGER_NAME + " VARCHAR(255),"
                + QueueParam.ALBUM_NAME + " VARCHAR(255),"
                + QueueParam.PLAY_PATH + " VARCHAR(255),"
                + QueueParam.DURATION + " LONG,"
                + QueueParam.LENGTH + " VARCHAR(255),"
                + QueueParam.ALBUM_COVER + " VARCHAR(255),"
                + "CONSTRAINT UC_PlayQueue UNIQUE ("
                + QueueParam.SOURCE + "," + QueueParam.SONG_ID + " ));");
    }

    @Override
    public void upgradeTable(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public List<Song> queryQueue(String[] songIds) {

        SQLiteDatabase db = mTickDaoHelper.getReadableDatabase();
        Cursor cursor = null;
        try {
            cursor = db.query(TickDaoHelper.PLAY_QUEUE, null,
                    QueueParam.SONG_ID + " = ? ", songIds, null, null, null);
            if (cursor.getCount() > 0) {
                List<Song> listBeen = new ArrayList<>(cursor.getCount());
                while (cursor.moveToNext()) {
                    Song listBean = new Song();
                    listBean.id = Long.parseLong(cursor.getString(cursor.getColumnIndex(QueueParam.SONG_ID)));
                    listBean.title = cursor.getString(cursor.getColumnIndex(QueueParam.SONG_NAME));
                    listBean.artistName = cursor.getString(cursor.getColumnIndex(QueueParam.SINGER_NAME));
                    listBean.albumCover = cursor.getString(cursor.getColumnIndex(QueueParam.ALBUM_COVER));
                    listBean.albumName = cursor.getString(cursor.getColumnIndex(QueueParam.ALBUM_NAME));
                    listBean.path = cursor.getString(cursor.getColumnIndex(QueueParam.PLAY_PATH));
                    listBean.type = cursor.getString(cursor.getColumnIndex(QueueParam.SOURCE));
                    listBean.duration = cursor.getShort(cursor.getColumnIndex(QueueParam.DURATION));
                    listBeen.add(listBean);
                }
                return listBeen;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            db.close();
        }

        return null;
    }

    public void addLocalQueue(List<LocalSongBean> songBeen) {
        SQLiteDatabase db = mTickDaoHelper.getReadableDatabase();
        db.beginTransaction();
        try {
            for (LocalSongBean localSongBean : songBeen) {
                ContentValues values = addItem(QueueParam.LOCAL, String.valueOf(localSongBean.id),
                        localSongBean.title, localSongBean.albumName, localSongBean.artistName,
                        String.valueOf(localSongBean.albumCover), localSongBean.duration,
                        localSongBean.songLength);
                values.put(QueueParam.PLAY_PATH, localSongBean.path);
                db.insertWithOnConflict(TickDaoHelper.PLAY_QUEUE, null, values,
                        SQLiteDatabase.CONFLICT_IGNORE);
            }
        } finally {
            db.setTransactionSuccessful();
            db.endTransaction();
        }
    }

    public void addNetQueue(List<SongListBean> songListBeen) {
        SQLiteDatabase db = mTickDaoHelper.getReadableDatabase();
        db.beginTransaction();
        try {
            for (SongListBean localSongBean : songListBeen) {
                ContentValues values = addItem(QueueParam.NET, localSongBean.songId,
                        localSongBean.title, localSongBean.albumTitle, localSongBean.artistName,
                        localSongBean.imgUrl, localSongBean.duration, localSongBean.songLength);
                db.insertWithOnConflict(TickDaoHelper.PLAY_QUEUE, null, values,
                        SQLiteDatabase.CONFLICT_IGNORE);
            }
        } finally {
            db.setTransactionSuccessful();
            db.endTransaction();
        }
    }

    private ContentValues addItem(String source, String id, String title, String albumName,
                                  String artistName, String albumCover, long duration, String length) {
        ContentValues values = new ContentValues();
        values.put(QueueParam.SOURCE, source);
        values.put(QueueParam.SONG_ID, id);
        values.put(QueueParam.SONG_NAME, title);
        values.put(QueueParam.ALBUM_NAME, albumName);
        values.put(QueueParam.SINGER_NAME, artistName);
        values.put(QueueParam.ALBUM_COVER, albumCover);
        values.put(QueueParam.DURATION, duration);
        values.put(QueueParam.LENGTH, length);
        return values;
    }
}
