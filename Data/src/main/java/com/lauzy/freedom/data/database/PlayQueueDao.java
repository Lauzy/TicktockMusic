package com.lauzy.freedom.data.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.freedom.lauzy.model.LocalSongBean;
import com.freedom.lauzy.model.QueueSongBean;

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

    private static final String TAG = "PlayQueueDao";
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
                + QueueParam.ALBUM_ID + " VARCHAR(255),"
                + QueueParam.ALBUM_NAME + " VARCHAR(255),"
                + QueueParam.PLAY_PATH + " VARCHAR(255),"
                + QueueParam.DURATION + " LONG,"
                + QueueParam.LENGTH + " VARCHAR(255),"
                + QueueParam.ALBUM_COVER + " VARCHAR(255),"
//                + QueueParam.ALBUM_BITMAP + " BLOB,"
                + "CONSTRAINT UC_PlayQueue UNIQUE ("
                + QueueParam.SOURCE + "," + QueueParam.SONG_ID + " ));");
    }

    @Override
    public void upgradeTable(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    /**
     * 根据songId获取播放队列
     *
     * @param songIds songIds
     * @return 播放队列数据集合
     */
    public List<QueueSongBean> queryQueue(String[] songIds) {
        Log.d(TAG, "The length of songIds is : " + songIds.length);
        SQLiteDatabase db = mTickDaoHelper.getReadableDatabase();
        Cursor cursor = null;
        try {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < songIds.length; i++) {
                sb.append("?");
                if (i != songIds.length - 1) {
                    sb.append(",");
                }
            }
            cursor = db.query(TickDaoHelper.PLAY_QUEUE, null,
                    QueueParam.SONG_ID + " in (" + sb.toString() + ")", songIds, null, null, null);
            if (cursor != null && cursor.getCount() > 0) {
                List<QueueSongBean> listBeen = new ArrayList<>(cursor.getCount());
                while (cursor.moveToNext()) {
                    QueueSongBean listBean = new QueueSongBean();
                    listBean.id = Long.parseLong(cursor.getString(cursor.getColumnIndex(QueueParam.SONG_ID)));
                    listBean.title = cursor.getString(cursor.getColumnIndex(QueueParam.SONG_NAME));
                    listBean.artistName = cursor.getString(cursor.getColumnIndex(QueueParam.SINGER_NAME));
                    listBean.albumCover = cursor.getString(cursor.getColumnIndex(QueueParam.ALBUM_COVER));
                    listBean.albumName = cursor.getString(cursor.getColumnIndex(QueueParam.ALBUM_NAME));
                    listBean.albumId = Long.parseLong(cursor.getString(cursor.getColumnIndex(QueueParam.ALBUM_ID)));
                    listBean.path = cursor.getString(cursor.getColumnIndex(QueueParam.PLAY_PATH));
                    listBean.type = cursor.getString(cursor.getColumnIndex(QueueParam.SOURCE));
                    listBean.duration = cursor.getLong(cursor.getColumnIndex(QueueParam.DURATION));
                    listBean.songLength = cursor.getString(cursor.getColumnIndex(QueueParam.LENGTH));
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

    /**
     * 添加播放队列数据
     *
     * @param songBeen 添加的数据集合
     */
    public void addToQueue(List<LocalSongBean> songBeen) {
        SQLiteDatabase db = mTickDaoHelper.getReadableDatabase();
        db.beginTransaction();
        try {
            for (LocalSongBean localSongBean : songBeen) {
                ContentValues values = addItem(localSongBean.type, String.valueOf(localSongBean.id),
                        localSongBean.title, String.valueOf(localSongBean.albumId),
                        localSongBean.albumName, localSongBean.artistName,
                        String.valueOf(localSongBean.albumCover), localSongBean.duration,
                        localSongBean.songLength);
                values.put(QueueParam.PLAY_PATH, localSongBean.path);
//                db.insertWithOnConflict(TickDaoHelper.PLAY_QUEUE, null, values,
//                        SQLiteDatabase.CONFLICT_IGNORE);
                db.replace(TickDaoHelper.PLAY_QUEUE, null, values);
            }
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
    }

    /**
     * 删除播放队列
     *
     * @param ids ids
     * @return 删除结果
     */
    public int deleteQueueData(String[] ids) {
        SQLiteDatabase db = mTickDaoHelper.getReadableDatabase();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < ids.length; i++) {
            sb.append("?");
            if (i != ids.length - 1) {
                sb.append(",");
            }
        }
        db.beginTransaction();
        int delete = db.delete(TickDaoHelper.PLAY_QUEUE,
                QueueParam.SONG_ID + " in (" + sb.toString() + ")", ids);
        db.setTransactionSuccessful();
        db.endTransaction();
        db.close();
        return delete;
    }

    private ContentValues addItem(String source, String id, String title, String albumId, String albumName,
                                  String artistName, String albumCover, long duration, String length) {
        ContentValues values = new ContentValues();
        values.put(QueueParam.SOURCE, source);
        values.put(QueueParam.SONG_ID, id);
        values.put(QueueParam.SONG_NAME, title);
        values.put(QueueParam.ALBUM_ID, albumId);
        values.put(QueueParam.ALBUM_NAME, albumName);
        values.put(QueueParam.SINGER_NAME, artistName);
        values.put(QueueParam.ALBUM_COVER, albumCover);
        values.put(QueueParam.DURATION, duration);
        values.put(QueueParam.LENGTH, length);
        return values;
    }
}
