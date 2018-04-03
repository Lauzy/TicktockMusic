package com.freedom.lauzy.repository;

/**
 * Desc : 配置管理
 * Author : Lauzy
 * Date : 2018/4/3
 * Blog : http://www.jianshu.com/u/e76853f863a9
 * Email : freedompaladin@gmail.com
 */
public interface IConfigDataManager {

    void setRepeatMode(int mode);

    int getRepeatMode(int defaultMode);

    void enablePlayByNetwork(boolean isEnable);

    boolean isEnablePlayByNetwork();
}
