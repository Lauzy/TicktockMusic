package com.lauzy.freedom.data.local.data;

/**
 * Desc : 配置
 * Author : Lauzy
 * Date : 2018/4/3
 * Blog : http://www.jianshu.com/u/e76853f863a9
 * Email : freedompaladin@gmail.com
 */
public interface ConfigRepo extends DataRepo{

    void setRepeatMode(int mode);

    int getRepeatMode(int defaultMode);

    void enablePlayByNetwork(boolean isEnable);

    boolean isEnablePlayByNetwork();
}
