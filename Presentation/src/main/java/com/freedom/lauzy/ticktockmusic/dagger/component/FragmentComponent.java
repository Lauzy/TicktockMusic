package com.freedom.lauzy.ticktockmusic.dagger.component;

import android.app.Activity;

import com.freedom.lauzy.ticktockmusic.dagger.module.FragmentModule;
import com.freedom.lauzy.ticktockmusic.dagger.scope.PerFragment;
import com.freedom.lauzy.ticktockmusic.ui.fragment.LocalMusicFragment;
import com.freedom.lauzy.ticktockmusic.ui.fragment.NetMusicCategoryFragment;

import dagger.Component;

/**
 * Desc : FragmentComponent
 * Author : Lauzy
 * Date : 2017/7/3
 * Blog : http://www.jianshu.com/u/e76853f863a9
 * Email : freedompaladin@gmail.com
 */
@PerFragment
@Component(dependencies = ApplicationComponent.class, modules = FragmentModule.class)
public interface FragmentComponent {
    Activity getActivity();

    void inject(LocalMusicFragment musicFragment);

    void inject(NetMusicCategoryFragment netMusicCategoryFragment);
}
