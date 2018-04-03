package com.lauzy.freedom.data.local.data.impl;

import android.content.Context;

import com.lauzy.freedom.data.local.data.CacheRepo;

/**
 * Desc : 缓存
 * Author : Lauzy
 * Date : 2018/4/3
 * Blog : http://www.jianshu.com/u/e76853f863a9
 * Email : freedompaladin@gmail.com
 */
public class CacheRepoImpl extends SharedPreferenceDataRepo implements CacheRepo {

    private static final String FILE_NAME = "ticktock_cache_sp";
    private static final String ARTIST_SHARED_KEY = "key_artist_avatar";

    public CacheRepoImpl(Context context) {
        super(context, FILE_NAME, Context.MODE_PRIVATE);
    }

    @Override
    public void setArtistAvatar(String artistName, String avatarUrl) {
        put(artistName, avatarUrl);
    }

    @Override
    public String getArtistAvatar(String artistName) {
        return getString(artistName);
    }
}
