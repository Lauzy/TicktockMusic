package com.freedom.lauzy.ticktockmusic;

import android.app.Application;
import android.content.Context;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.v4.content.ContextCompat;

import com.bilibili.magicasakura.utils.ThemeUtils;
import com.freedom.lauzy.ticktockmusic.dagger.component.ApplicationComponent;
import com.freedom.lauzy.ticktockmusic.dagger.component.DaggerApplicationComponent;
import com.freedom.lauzy.ticktockmusic.dagger.module.ApplicationModule;
import com.freedom.lauzy.ticktockmusic.utils.ThemeHelper;

/**
 * Desc : App
 * Author : Lauzy
 * Date : 2017/7/3
 * Blog : http://www.jianshu.com/u/e76853f863a9
 * Email : freedompaladin@gmail.com
 */
public class TicktockApplication extends Application implements ThemeUtils.switchColor {

    private ApplicationComponent mApplicationComponent;
    private static TicktockApplication sTicktockApplication;

    @Override
    public void onCreate() {
        super.onCreate();
        sTicktockApplication = this;
        ThemeUtils.setSwitchColor(this);
        initApplicationComponent();
    }

    private void initApplicationComponent() {
        mApplicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build();
    }

    public ApplicationComponent getApplicationComponent() {
        return mApplicationComponent;
    }

    public static TicktockApplication getInstance() {
        return sTicktockApplication;
    }

    @Override
    public int replaceColorById(Context context, @ColorRes int colorId) {
        if (ThemeHelper.isDefaultTheme(context)) {
            return ContextCompat.getColor(context, colorId);
        }
        String theme = ThemeHelper.getThemeName(context);
        if (theme != null) {
            colorId = ThemeHelper.getThemeColorId(context, colorId, theme);
        }
        return ContextCompat.getColor(context, colorId);
    }

    @Override
    public int replaceColor(Context context, @ColorInt int color) {
        if (ThemeHelper.isDefaultTheme(context)) {
            return color;
        }
        String theme = ThemeHelper.getThemeName(context);
        int colorId = -1;
        if (theme != null) {
            colorId = ThemeHelper.getThemeColor(context, color, theme);
        }
        return colorId != -1 ? ContextCompat.getColor(context, colorId) : color;
    }
}
