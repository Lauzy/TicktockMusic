package com.freedom.lauzy.ticktockmusic.injection.component;

import android.app.Activity;

import com.freedom.lauzy.ticktockmusic.injection.module.ActivityModule;
import com.freedom.lauzy.ticktockmusic.injection.scope.PerActivity;
import com.freedom.lauzy.ticktockmusic.ui.activity.MainActivity;
import com.freedom.lauzy.ticktockmusic.ui.activity.PlayActivity;
import com.freedom.lauzy.ticktockmusic.ui.activity.PlayActivity1;

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

    void inject(PlayActivity playActivity);

    void inject(PlayActivity1 playActivity1);

}
