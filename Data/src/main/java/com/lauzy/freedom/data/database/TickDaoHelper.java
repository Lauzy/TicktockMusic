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
    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "ticktock.db";

    public TickDaoHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        NetMusicDao.getInstance();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

}
