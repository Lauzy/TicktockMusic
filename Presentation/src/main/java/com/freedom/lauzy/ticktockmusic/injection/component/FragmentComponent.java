package com.freedom.lauzy.ticktockmusic.injection.component;

import android.app.Activity;

import com.freedom.lauzy.ticktockmusic.injection.module.FragmentModule;
import com.freedom.lauzy.ticktockmusic.injection.scope.PerFragment;
import com.freedom.lauzy.ticktockmusic.ui.fragment.AlbumFragment;
import com.freedom.lauzy.ticktockmusic.ui.fragment.NetSongFragment;
import com.freedom.lauzy.ticktockmusic.ui.fragment.NetSongListFragment;
import com.freedom.lauzy.ticktockmusic.ui.fragment.SongFragment;

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

    void inject(NetSongFragment songFragment);

    void inject(NetSongListFragment netSongListFragment);

    void inject(SongFragment songFragment);

    void inject(AlbumFragment albumFragment);
}
