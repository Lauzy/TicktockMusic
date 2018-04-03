package com.lauzy.freedom.data.repository;

import android.content.Context;

import com.freedom.lauzy.repository.ICacheDataManager;
import com.lauzy.freedom.data.local.data.DataManager;

import javax.inject.Inject;

/**
 * Desc : 缓存
 * Author : Lauzy
 * Date : 2018/4/3
 * Blog : http://www.jianshu.com/u/e76853f863a9
 * Email : freedompaladin@gmail.com
 */
public class CacheDataManagerImpl implements ICacheDataManager {

    private Context mContext;

    @Inject
    public CacheDataManagerImpl(Context context) {
        mContext = context;
    }

    @Override
    public void setArtistAvatar(String artistName, String avatarUrl) {
        DataManager.getInstance(mContext).getCacheRepo().setArtistAvatar(artistName, avatarUrl);
    }

    @Override
    public String getArtistAvatar(String artistName) {
        return DataManager.getInstance(mContext).getCacheRepo().getArtistAvatar(artistName);
    }
}
