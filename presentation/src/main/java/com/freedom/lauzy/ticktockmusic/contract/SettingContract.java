package com.freedom.lauzy.ticktockmusic.contract;

/**
 * Desc : 设置接口
 * Author : Lauzy
 * Date : 2018/4/3
 * Blog : http://www.jianshu.com/u/e76853f863a9
 * Email : freedompaladin@gmail.com
 */
public interface SettingContract {

    interface Presenter {

        boolean isEnablePlayByNetwork();

        void enablePlayByNetwork(boolean isEnable);
    }
}
