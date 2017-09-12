package com.lauzy.freedom.data.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Desc :
 * Author : Lauzy
 * Date : 2017/8/28
 * Blog : http://www.jianshu.com/u/e76853f863a9
 * Email : freedompaladin@gmail.com
 */
public class TickDaoHelper extends SQLiteOpenHelper {
    static final String FAVORITE_TABLE = "Favorite";
    static final String RECENT_TABLE = "Recent";
    static final String NET_MUSIC_TABLE = "NetMusic";
    static final String PLAY_QUEUE = "PlayQueue";
    private static final String DB_NAME = "ticktock.db";
    private static final int DB_VERSION = 1;
    private Context mContext;

    public TickDaoHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        NetMusicDao.getInstance(mContext).createTable(db);
        PlayQueueDao.getInstance(mContext).createTable(db);
        FavoriteDao.getInstance(mContext).createTable(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        NetMusicDao.getInstance(mContext).upgradeTable(db, oldVersion, newVersion);
        PlayQueueDao.getInstance(mContext).upgradeTable(db, oldVersion, newVersion);
        FavoriteDao.getInstance(mContext).upgradeTable(db, oldVersion, newVersion);
    }
}
