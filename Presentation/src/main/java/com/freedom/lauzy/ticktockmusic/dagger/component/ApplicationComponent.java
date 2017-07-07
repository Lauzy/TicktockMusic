package com.freedom.lauzy.ticktockmusic.dagger.component;

import android.content.Context;

import com.freedom.lauzy.ticktockmusic.base.BaseActivity;
import com.freedom.lauzy.ticktockmusic.dagger.module.ApplicationModule;
import com.freedom.lauzy.ticktockmusic.dagger.scope.ContextLife;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Desc : ApplicationComponent
 * Author : Lauzy
 * Date : 2017/7/3
 * Blog : http://www.jianshu.com/u/e76853f863a9
 * Email : freedompaladin@gmail.com
 */

@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {

    @ContextLife()
    Context getApplication();
}
