package com.lauzy.freedom.data.local.data;

import android.content.Context;

import com.lauzy.freedom.data.local.data.impl.CacheRepoImpl;
import com.lauzy.freedom.data.local.data.impl.ConfigRepoImpl;

/**
 * Desc : 数据管理
 * Author : Lauzy
 * Date : 2018/4/2
 * Blog : http://www.jianshu.com/u/e76853f863a9
 * Email : freedompaladin@gmail.com
 */
public class DataManager {

    private final CacheRepo mCacheRepo;
    private final ConfigRepo mConfigRepo;
    private volatile static DataManager INSTANCE;

    private DataManager(Context context) {
        mCacheRepo = new CacheRepoImpl(context);
        mConfigRepo = new ConfigRepoImpl(context);
    }

    public static DataManager getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (DataManager.class) {
                if (INSTANCE == null) {
                    INSTANCE = new DataManager(context);
                }
            }
        }
        return INSTANCE;
    }

    public CacheRepo getCacheRepo() {
        return mCacheRepo;
    }

    public ConfigRepo getConfigRepo() {
        return mConfigRepo;
    }
}
