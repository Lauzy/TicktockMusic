package com.freedom.lauzy.ticktockmusic.dagger.component;

import android.app.Activity;

import com.freedom.lauzy.ticktockmusic.ui.activity.MainActivity;
import com.freedom.lauzy.ticktockmusic.dagger.module.ActivityModule;
import com.freedom.lauzy.ticktockmusic.dagger.scope.PerActivity;

import dagger.Component;

/**
 * Desc : ActivityComponent
 * Author : Lauzy
 * Date : 2017/7/3
 * Blog : http://www.jianshu.com/u/e76853f863a9
 * Email : freedompaladin@gmail.com
 */

@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {
    Activity getActivity();

    void inject(MainActivity mainActivity);
}
