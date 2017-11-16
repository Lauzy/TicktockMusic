package com.freedom.lauzy.ticktockmusic.injection.module;

import android.app.Activity;
import android.content.Context;

import com.freedom.lauzy.ticktockmusic.injection.scope.PerActivity;

import dagger.Module;
import dagger.Provides;

/**
 * Desc : ActivityModule
 * Author : Lauzy
 * Date : 2017/7/3
 * Blog : http://www.jianshu.com/u/e76853f863a9
 * Email : freedompaladin@gmail.com
 */
@Module
public class ActivityModule {
    private final Activity mActivity;

    public ActivityModule(Activity activity) {
        mActivity = activity;
    }

    @Provides
    @PerActivity
    public Activity provideActivity() {
        return mActivity;
    }

    @Provides
    public Context provideActivityContext() {
        return mActivity;
    }
}
