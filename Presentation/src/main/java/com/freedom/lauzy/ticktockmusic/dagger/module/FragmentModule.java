package com.freedom.lauzy.ticktockmusic.dagger.module;

import android.app.Activity;
import android.support.v4.app.Fragment;

import com.freedom.lauzy.ticktockmusic.dagger.scope.PerFragment;

import dagger.Module;
import dagger.Provides;

/**
 * Desc : FragmentModule
 * Author : Lauzy
 * Date : 2017/7/3
 * Blog : http://www.jianshu.com/u/e76853f863a9
 * Email : freedompaladin@gmail.com
 */
@Module
public class FragmentModule {
    private Fragment mFragment;

    public FragmentModule(Fragment fragment) {
        mFragment = fragment;
    }

    @Provides
    @PerFragment
    public Activity provideActivity() {
        return mFragment.getActivity();
    }

    @Provides
    @PerFragment
    public Fragment provideFragment() {
        return mFragment;
    }
}
