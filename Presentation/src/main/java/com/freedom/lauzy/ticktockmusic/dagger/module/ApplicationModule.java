package com.freedom.lauzy.ticktockmusic.dagger.module;

import android.content.Context;
import android.content.SharedPreferences;

import com.freedom.lauzy.ticktockmusic.TicktockApplication;
import com.freedom.lauzy.ticktockmusic.dagger.scope.ContextLife;
import com.lauzy.freedom.data.RetrofitHelper;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Desc : ApplicationModule
 * Author : Lauzy
 * Date : 2017/7/3
 * Blog : http://www.jianshu.com/u/e76853f863a9
 * Email : freedompaladin@gmail.com
 */
@Module
public class ApplicationModule {
    private TicktockApplication mApplication;
    private static final String CONFIG_NAME = "config_shared_prefs";

    public ApplicationModule(TicktockApplication application) {
        mApplication = application;
    }

    @Provides
    @Singleton
    @ContextLife()
    protected Context provideApplicationContext() {
        return mApplication;
    }

    @Provides
    @Singleton
    SharedPreferences provideSharedPrefs() {
        return mApplication.getSharedPreferences(CONFIG_NAME, Context.MODE_PRIVATE);
    }

    @Provides
    @Singleton
    RetrofitHelper provideRetrofitHelper() {
        return new RetrofitHelper();
    }
}
