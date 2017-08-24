package com.freedom.lauzy.ticktockmusic.injection.module;

import android.content.Context;
import android.content.SharedPreferences;

import com.freedom.lauzy.executor.PostExecutionThread;
import com.freedom.lauzy.executor.ThreadExecutor;
import com.freedom.lauzy.repository.LocalSongRepository;
import com.freedom.lauzy.repository.SongRepository;
import com.freedom.lauzy.ticktockmusic.TicktockApplication;
import com.freedom.lauzy.ticktockmusic.function.UIThread;
import com.freedom.lauzy.ticktockmusic.injection.scope.ContextLife;
import com.lauzy.freedom.data.executor.JobExecutor;
import com.lauzy.freedom.data.repository.LocalSongRepositoryImpl;
import com.lauzy.freedom.data.repository.SongRepositoryImpl;

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
    ThreadExecutor provideThreadExecutor(JobExecutor jobExecutor) {
        return jobExecutor;
    }

    @Provides
    @Singleton
    PostExecutionThread providePostExecutionThread(UIThread uiThread) {
        return uiThread;
    }

    @Provides
    @Singleton
    SongRepository provideSongRepository(SongRepositoryImpl userDataRepository) {
        return userDataRepository;
    }

    @Provides
    @Singleton
    LocalSongRepository provideLocalSongRepository() {
        return new LocalSongRepositoryImpl(mApplication);
    }
}
