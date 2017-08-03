package com.freedom.lauzy.ticktockmusic.module.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Desc : LocalMusicAdapter
 * Author : Lauzy
 * Date : 2017/8/3
 * Blog : http://www.jianshu.com/u/e76853f863a9
 * Email : freedompaladin@gmail.com
 */
public class LocalMusicPagerAdapter extends FragmentPagerAdapter {

    private List<Fragment> mFragments;
    private String[] mTitles;

    public LocalMusicPagerAdapter(FragmentManager fm, List<Fragment> fragments) {
        super(fm);
        mFragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragments != null ? mFragments.size() : 0;
    }

    public void setTitles(String[] titles) {
        mTitles = titles;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles[position];
    }
}
