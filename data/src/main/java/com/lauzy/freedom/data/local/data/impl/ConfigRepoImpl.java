package com.lauzy.freedom.data.local.data.impl;

import android.content.Context;

import com.lauzy.freedom.data.local.data.ConfigRepo;

/**
 * Desc : 配置
 * Author : Lauzy
 * Date : 2018/4/3
 * Blog : http://www.jianshu.com/u/e76853f863a9
 * Email : freedompaladin@gmail.com
 */
public class ConfigRepoImpl extends SharedPreferenceDataRepo implements ConfigRepo {

    private static final String FILE_NAME = "ticktock_config_sp";
    private static final String REPEAT_MODE = "repeat_mode";
    private static final String ENABLE_NETWORK_PLAY_KEY = "enable_play_key";

    public ConfigRepoImpl(Context context) {
        super(context, FILE_NAME, Context.MODE_PRIVATE);
    }

    @Override
    public void setRepeatMode(int mode) {
        put(REPEAT_MODE, mode);
    }

    @Override
    public int getRepeatMode(int defaultMode) {
        return getInt(REPEAT_MODE, defaultMode);
    }

    @Override
    public void enablePlayByNetwork(boolean isEnable) {
        put(ENABLE_NETWORK_PLAY_KEY, isEnable);
    }

    @Override
    public boolean isEnablePlayByNetwork() {
        return getBoolean(ENABLE_NETWORK_PLAY_KEY);
    }
}
