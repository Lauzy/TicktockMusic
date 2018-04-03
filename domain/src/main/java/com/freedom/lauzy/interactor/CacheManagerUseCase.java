package com.freedom.lauzy.interactor;

import com.freedom.lauzy.repository.ICacheDataManager;

import javax.inject.Inject;

/**
 * Desc : 缓存
 * Author : Lauzy
 * Date : 2018/4/3
 * Blog : http://www.jianshu.com/u/e76853f863a9
 * Email : freedompaladin@gmail.com
 */
public class CacheManagerUseCase {

    private ICacheDataManager mCacheDataManager;

    @Inject
    public CacheManagerUseCase(ICacheDataManager cacheDataManager) {
        mCacheDataManager = cacheDataManager;
    }

    public void setArtistAvatar(String artistName, String avatarUrl) {
        mCacheDataManager.setArtistAvatar(artistName, avatarUrl);
    }

    public String getArtistAvatar(String artistName) {
        return mCacheDataManager.getArtistAvatar(artistName);
    }
}
