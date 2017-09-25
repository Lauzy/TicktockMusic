package com.lauzy.freedom.data.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.freedom.lauzy.model.NetSongBean;

import java.util.ArrayList;
import java.util.List;

import static com.lauzy.freedom.data.database.TickDaoHelper.NET_MUSIC_TABLE;

/**
 * Desc : 网络音乐数据库，本地缓存
 * Author : Lauzy
 * Date : 2017/8/28
 * Blog : http://www.jianshu.com/u/e76853f863a9
 * Email : freedompaladin@gmail.com
 */
public class NetMusicDao implements BaseDao {

    private static final String LTAG = "NetMusicDao";
    private static NetMusicDao sInstance;
    private TickDaoHelper mTickDaoHelper;

    private NetMusicDao(Context context) {
        mTickDaoHelper = new TickDaoHelper(context);
    }

    public static NetMusicDao getInstance(final Context context) {
        if (sInstance == null) {
            synchronized (NetMusicDao.class) {
                if (sInstance == null) {
                    sInstance = new NetMusicDao(context.getApplicationContext());
                }
            }
        }
        return sInstance;
    }

    @Override
    public void createTable(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS " + NET_MUSIC_TABLE + " ("
                + NetParam.TYPE + " INTEGER NOT NULL,"
                + NetParam.SONG_ID + " VARCHAR(255),"
                + NetParam.SONG_NAME + " VARCHAR(255),"
                + NetParam.SINGER_ID + " VARCHAR(255),"
                + NetParam.SINGER_NAME + " VARCHAR(255),"
                + NetParam.PIC_URL + " VARCHAR(255),"
                + NetParam.LRC_LINK + " VARCHAR(255),"
                + NetParam.ALBUM_ID + " VARCHAR(255),"
                + NetParam.ALBUM_NAME + " VARCHAR(255),"
                + NetParam.RANK + " INTEGER,"
                + "CONSTRAINT UC_NetMusic UNIQUE ("
                + NetParam.TYPE + "," + NetParam.SONG_ID + " ));");
    }

    @Override
    public void upgradeTable(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    /**
     * 获取缓存数据
     *
     * @param type 类型（新歌，摇滚等）
     * @return 缓存的数据集合
     */
    public List<NetSongBean> querySongData(int type) {
        SQLiteDatabase db = mTickDaoHelper.getReadableDatabase();
        Cursor cursor = null;
        try {
            cursor = db.query(TickDaoHelper.NET_MUSIC_TABLE, null,
                    NetParam.TYPE + " = ?",
                    new String[]{String.valueOf(type)},
                    null, null, NetParam.RANK + " ASC", null);
            if (cursor.getCount() > 0) {
                List<NetSongBean> listBeen = new ArrayList<>();
                while (cursor.moveToNext()) {
                    NetSongBean listBean = new NetSongBean();
                    listBean.songId = cursor.getString(cursor.getColumnIndex(NetParam.SONG_ID));
                    listBean.title = cursor.getString(cursor.getColumnIndex(NetParam.SONG_NAME));
                    listBean.artistId = cursor.getString(cursor.getColumnIndex(NetParam.SINGER_ID));
                    listBean.artistName = cursor.getString(cursor.getColumnIndex(NetParam.SINGER_NAME));
                    listBean.imgUrl = cursor.getString(cursor.getColumnIndex(NetParam.PIC_URL));
                    listBean.lrcLink = cursor.getString(cursor.getColumnIndex(NetParam.LRC_LINK));
                    listBean.albumId = cursor.getString(cursor.getColumnIndex(NetParam.ALBUM_ID));
                    listBean.albumTitle = cursor.getString(cursor.getColumnIndex(NetParam.ALBUM_NAME));
                    listBean.rank = cursor.getInt(cursor.getColumnIndex(NetParam.RANK));
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
     * 根据type添加网络数据
     *
     * @param type         type
     * @param songListBeen 网络数据集合
     */
    public void addNetSongData(int type, List<NetSongBean> songListBeen) {
        SQLiteDatabase db = mTickDaoHelper.getReadableDatabase();
        db.beginTransaction();
        try {
            for (NetSongBean netSongBean : songListBeen) {
                ContentValues values = new ContentValues();
                values.put(NetParam.TYPE, type);
                values.put(NetParam.SONG_ID, netSongBean.songId);
                values.put(NetParam.SONG_NAME, netSongBean.title);
                values.put(NetParam.SINGER_ID, netSongBean.artistId);
                values.put(NetParam.SINGER_NAME, netSongBean.artistName);
                values.put(NetParam.PIC_URL, netSongBean.imgUrl);
                values.put(NetParam.LRC_LINK, netSongBean.lrcLink);
                values.put(NetParam.ALBUM_ID, netSongBean.albumId);
                values.put(NetParam.ALBUM_NAME, netSongBean.albumTitle);
                values.put(NetParam.RANK, netSongBean.rank);
//                long conflict = db.insertWithOnConflict(TickDaoHelper.NET_MUSIC_TABLE, null, values,
//                        SQLiteDatabase.CONFLICT_IGNORE);
                long conflict = db.replace(TickDaoHelper.NET_MUSIC_TABLE, null, values);
                Log.i(LTAG, "DbConflict --- " + conflict);
            }
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
            db.close();
        }
    }

    /**
     * 移除特定分类数据
     * @param type 分类
     */
    public void removeData(int type) {
        SQLiteDatabase db = mTickDaoHelper.getReadableDatabase();
        db.beginTransaction();
        db.delete(TickDaoHelper.NET_MUSIC_TABLE, NetParam.TYPE + " = ?",
                new String[]{String.valueOf(type)});
        db.setTransactionSuccessful();
        db.endTransaction();
        db.close();
    }
}
