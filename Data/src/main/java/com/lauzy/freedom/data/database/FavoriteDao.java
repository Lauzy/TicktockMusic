package com.lauzy.freedom.data.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.freedom.lauzy.model.FavoriteSongBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Desc : 收藏喜欢歌曲数据库
 * Author : Lauzy
 * Date : 2017/9/12
 * Blog : http://www.jianshu.com/u/e76853f863a9
 * Email : freedompaladin@gmail.com
 */
public class FavoriteDao implements BaseDao {

    private static FavoriteDao sInstance;
    private TickDaoHelper mTickDaoHelper;

    private FavoriteDao(Context context) {
        mTickDaoHelper = new TickDaoHelper(context);
    }

    public static FavoriteDao getInstance(final Context context) {
        if (sInstance == null) {
            synchronized (FavoriteDao.class) {
                if (sInstance == null) {
                    sInstance = new FavoriteDao(context.getApplicationContext());
                }
            }
        }
        return sInstance;
    }

    @Override
    public void createTable(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS " + TickDaoHelper.FAVORITE_TABLE + " ("
                + FavoriteParam.SONG_ID + " VARCHAR(255),"
                + FavoriteParam.SOURCE + " VARCHAR(255),"
                + FavoriteParam.SONG_NAME + " VARCHAR(255),"
                + FavoriteParam.SINGER_NAME + " VARCHAR(255),"
                + FavoriteParam.ALBUM_ID + " VARCHAR(255),"
                + FavoriteParam.ALBUM_NAME + " VARCHAR(255),"
                + FavoriteParam.PLAY_PATH + " VARCHAR(255),"
                + FavoriteParam.DURATION + " LONG,"
                + FavoriteParam.LENGTH + " VARCHAR(255),"
                + FavoriteParam.ADD_TIME + " VARCHAR(255),"
                + FavoriteParam.ALBUM_COVER + " VARCHAR(255),"
                + "CONSTRAINT UC_FAVORITE UNIQUE ("
                + FavoriteParam.SOURCE + "," + FavoriteParam.SONG_ID + " ));");
    }

    @Override
    public void upgradeTable(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    /**
     * 添加喜欢的歌曲
     * @param songBean songBean
     * @return 添加结果
     */
    public long addFavoriteSong(FavoriteSongBean songBean) {
        SQLiteDatabase db = mTickDaoHelper.getReadableDatabase();
        db.beginTransaction();
        Cursor cursor = null;
        long result = -1;
        try {
            cursor = db.query(TickDaoHelper.FAVORITE_TABLE, null,
                    FavoriteParam.SONG_ID + " = ? ", new String[]{String.valueOf(songBean.id)},
                    null, null, null);
            if (cursor != null && cursor.getCount() == 0) {
                ContentValues values = new ContentValues();
                values.put(FavoriteParam.SOURCE, songBean.type);
                values.put(FavoriteParam.SONG_ID, songBean.id);
                values.put(FavoriteParam.SONG_NAME, songBean.title);
                values.put(FavoriteParam.ALBUM_ID, songBean.albumId);
                values.put(FavoriteParam.ALBUM_NAME, songBean.albumName);
                values.put(FavoriteParam.SINGER_NAME, songBean.artistName);
                values.put(FavoriteParam.DURATION, songBean.duration);
                values.put(FavoriteParam.LENGTH, songBean.songLength);
                values.put(FavoriteParam.PLAY_PATH, songBean.path);
                values.put(FavoriteParam.ALBUM_COVER, songBean.albumCover);
                values.put(FavoriteParam.ADD_TIME, System.currentTimeMillis());
                result = db.insert(TickDaoHelper.FAVORITE_TABLE, null, values);
            }
            db.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            db.endTransaction();
            db.close();
        }
        return result;
    }

    /**
     * 获取所有喜欢的歌曲
     * @return 喜欢的歌曲集合
     */
    public List<FavoriteSongBean> getFavoriteSongs() {
        SQLiteDatabase db = mTickDaoHelper.getReadableDatabase();
        Cursor cursor = null;
        try {
            cursor = db.query(TickDaoHelper.FAVORITE_TABLE, null, null, null, null, null,
                    FavoriteParam.ADD_TIME + " DESC", null);
            if (cursor != null && cursor.getCount() != 0) {
                List<FavoriteSongBean> favoriteSongBeen = new ArrayList<>();
                while (cursor.moveToNext()) {
                    FavoriteSongBean listBean = new FavoriteSongBean();
                    listBean.id = Long.parseLong(cursor.getString(cursor.getColumnIndex(FavoriteParam.SONG_ID)));
                    listBean.title = cursor.getString(cursor.getColumnIndex(FavoriteParam.SONG_NAME));
                    listBean.artistName = cursor.getString(cursor.getColumnIndex(FavoriteParam.SINGER_NAME));
                    listBean.albumName = cursor.getString(cursor.getColumnIndex(FavoriteParam.ALBUM_NAME));
                    listBean.albumId = Long.parseLong(cursor.getString(cursor.getColumnIndex(FavoriteParam.ALBUM_ID)));
                    listBean.path = cursor.getString(cursor.getColumnIndex(FavoriteParam.PLAY_PATH));
                    listBean.type = cursor.getString(cursor.getColumnIndex(FavoriteParam.SOURCE));
                    listBean.duration = cursor.getLong(cursor.getColumnIndex(FavoriteParam.DURATION));
                    listBean.songLength = cursor.getString(cursor.getColumnIndex(FavoriteParam.LENGTH));
                    listBean.albumCover = cursor.getString(cursor.getColumnIndex(FavoriteParam.ALBUM_COVER));
                    favoriteSongBeen.add(listBean);
                }
                return favoriteSongBeen;
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
     * 判断是否是喜欢的歌曲
     * @param songId songId
     * @return 是否是喜欢的歌曲
     */
    public boolean isFavorite(long songId) {
        SQLiteDatabase db = mTickDaoHelper.getReadableDatabase();
        Cursor cursor = db.query(TickDaoHelper.FAVORITE_TABLE, new String[]{FavoriteParam.SONG_ID},
                FavoriteParam.SONG_ID + " = ? ", new String[]{String.valueOf(songId)},
                null, null, null);
        if (cursor != null && cursor.getCount() > 0) {
            return true;
        }
        if (cursor != null) {
            cursor.close();
        }
        return false;
    }

    /**
     * 删除喜欢的歌曲
     * @param songId songId
     * @return 删除结果
     */
    public long deleteFavoriteSong(long songId) {
        SQLiteDatabase db = mTickDaoHelper.getReadableDatabase();
        db.beginTransaction();
        int delete = db.delete(TickDaoHelper.FAVORITE_TABLE, FavoriteParam.SONG_ID + " = ? ",
                new String[]{String.valueOf(songId)});
        db.setTransactionSuccessful();
        db.endTransaction();
        db.close();
        return delete;
    }

    /**
     * 清空喜欢的歌曲
     * @return 清空结果
     */
    public int clearFavoriteSongs() {
        SQLiteDatabase db = mTickDaoHelper.getReadableDatabase();
        db.beginTransaction();
        int delete = db.delete(TickDaoHelper.FAVORITE_TABLE, null, null);
        db.setTransactionSuccessful();
        db.endTransaction();
        db.close();
        return delete;
    }
}
