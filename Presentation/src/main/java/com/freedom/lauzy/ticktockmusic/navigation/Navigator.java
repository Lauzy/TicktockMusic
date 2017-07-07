package com.freedom.lauzy.ticktockmusic.navigation;

import android.content.Context;
import android.content.Intent;

import com.freedom.lauzy.ticktockmusic.ui.SettingActivity;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Desc : Class used to navigate through the application.
 * Author : Lauzy
 * Date : 2017/7/5
 * Blog : http://www.jianshu.com/u/e76853f863a9
 * Email : freedompaladin@gmail.com
 */
public class Navigator {

    @Inject
    public Navigator() {
    }

    /**
     * Goes to SettingActivity.
     * @param context A Context needed to open the destiny activity.
     */
    public void navigateToSetting(Context context) {
        if (context != null) {
            Intent intent = SettingActivity.newInstance(context);
            context.startActivity(intent);
        }
    }
}
