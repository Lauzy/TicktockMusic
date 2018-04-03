package com.lauzy.freedom.data.repository;

import android.content.Context;

import com.freedom.lauzy.repository.IConfigDataManager;
import com.lauzy.freedom.data.local.data.DataManager;

import javax.inject.Inject;

/**
 * Desc : 配置
 * Author : Lauzy
 * Date : 2018/4/3
 * Blog : http://www.jianshu.com/u/e76853f863a9
 * Email : freedompaladin@gmail.com
 */
public class ConfigDataManagerImpl implements IConfigDataManager {

    private Context mContext;

    @Inject
    public ConfigDataManagerImpl(Context context) {
        mContext = context;
    }

    @Override
    public void setRepeatMode(int mode) {
        DataManager.getInstance(mContext).getConfigRepo().setRepeatMode(mode);
    }

    @Override
    public int getRepeatMode(int defaultMode) {
        return DataManager.getInstance(mContext).getConfigRepo().getRepeatMode(defaultMode);
    }

    @Override
    public void enablePlayByNetwork(boolean isEnable) {
        DataManager.getInstance(mContext).getConfigRepo().enablePlayByNetwork(isEnable);
    }

    @Override
    public boolean isEnablePlayByNetwork() {
        return DataManager.getInstance(mContext).getConfigRepo().isEnablePlayByNetwork();
    }
}
