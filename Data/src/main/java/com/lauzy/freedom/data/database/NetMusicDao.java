package com.lauzy.freedom.data.database;

import android.database.sqlite.SQLiteDatabase;

import static com.lauzy.freedom.data.database.TickDaoHelper.NET_MUSIC_TABLE;

/**
 * Desc :
 * Author : Lauzy
 * Date : 2017/8/28
 * Blog : http://www.jianshu.com/u/e76853f863a9
 * Email : freedompaladin@gmail.com
 */
public class NetMusicDao implements BaseDao {

    private static class SingleTon {
        private static NetMusicDao INSTANCE = new NetMusicDao();
    }

    public static NetMusicDao getInstance() {
        return SingleTon.INSTANCE;
    }

    @Override
    public void createTable(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS " + NET_MUSIC_TABLE + " (");
    }

    @Override
    public void upgradeTable() {

    }

    /*
    *
    *  public String artistId; //歌手ID
    public String imgUrl; //图片链接
    public String lrcLink; //歌词路径
    public String songId; //歌曲ID
    public String title; //名称
    public String tingUid; //歌手ID
    public String author; //作者
    public String albumId; //专辑ID
    public String albumTitle; //专辑标题
    public String artistName; //歌手名字
    * */
}
