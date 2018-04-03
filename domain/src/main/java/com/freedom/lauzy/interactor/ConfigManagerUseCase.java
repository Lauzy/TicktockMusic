package com.freedom.lauzy.interactor;

import com.freedom.lauzy.repository.IConfigDataManager;

import javax.inject.Inject;

/**
 * Desc : 配置信息
 * Author : Lauzy
 * Date : 2018/4/3
 * Blog : http://www.jianshu.com/u/e76853f863a9
 * Email : freedompaladin@gmail.com
 */
public class ConfigManagerUseCase {

    private IConfigDataManager mConfigDataManager;

    @Inject
    public ConfigManagerUseCase(IConfigDataManager configDataManager) {
        mConfigDataManager = configDataManager;
    }


    public void setRepeatMode(int mode) {
        mConfigDataManager.setRepeatMode(mode);
    }

    public int getRepeatMode(int defaultMode) {
        return mConfigDataManager.getRepeatMode(defaultMode);
    }

    public void enablePlayByNetwork(boolean isEnable) {
        mConfigDataManager.enablePlayByNetwork(isEnable);
    }

    public boolean isEnablePlayByNetwork() {
        return mConfigDataManager.isEnablePlayByNetwork();
    }
}
