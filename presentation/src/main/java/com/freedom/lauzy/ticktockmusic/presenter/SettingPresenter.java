package com.freedom.lauzy.ticktockmusic.presenter;

import com.freedom.lauzy.interactor.ConfigManagerUseCase;
import com.freedom.lauzy.ticktockmusic.base.BasePresenter;
import com.freedom.lauzy.ticktockmusic.contract.SettingContract;

import javax.inject.Inject;

/**
 * Desc : 设置
 * Author : Lauzy
 * Date : 2018/4/3
 * Blog : http://www.jianshu.com/u/e76853f863a9
 * Email : freedompaladin@gmail.com
 */
public class SettingPresenter extends BasePresenter implements SettingContract.Presenter {

    private ConfigManagerUseCase mConfigManagerUseCase;

    @Inject
    SettingPresenter(ConfigManagerUseCase configManagerUseCase) {
        mConfigManagerUseCase = configManagerUseCase;
    }

    @Override
    public boolean isEnablePlayByNetwork() {
        return mConfigManagerUseCase.isEnablePlayByNetwork();
    }

    @Override
    public void enablePlayByNetwork(boolean isEnable) {
        mConfigManagerUseCase.enablePlayByNetwork(isEnable);
    }
}
