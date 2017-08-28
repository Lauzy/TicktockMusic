package com.lauzy.freedom.data.database;

import android.database.sqlite.SQLiteDatabase;

/**
 * Desc :
 * Author : Lauzy
 * Date : 2017/8/28
 * Blog : http://www.jianshu.com/u/e76853f863a9
 * Email : freedompaladin@gmail.com
 */
public interface BaseDao {


    void createTable(SQLiteDatabase db);

    void upgradeTable();
}
