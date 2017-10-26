package com.freedom.lauzy.ticktockmusic.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.freedom.lauzy.ticktockmusic.service.MusicManager;
import com.freedom.lauzy.ticktockmusic.ui.fragment.PlayCoverFragment;

/**
 * Desc : 歌词、专辑图片 PagerAdapter
 * Author : Lauzy
 * Date : 2017/10/17
 * Blog : http://www.jianshu.com/u/e76853f863a9
 * Email : freedompaladin@gmail.com
 */
public class PlayCoverPagerAdapter extends FragmentStatePagerAdapter {

    public PlayCoverPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return PlayCoverFragment.newInstance(MusicManager.getInstance().getMusicService()
                .getSongData().get(position));
    }

    @Override
    public int getCount() {
        return MusicManager.getInstance().getMusicService().getSongData().size();
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    /* private List<Fragment> mFragments;
    private List<SongEntity> mSongEntities = new ArrayList<>();

    public PlayCoverPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    public void setSongEntities(List<SongEntity> songEntities) {
        mSongEntities.addAll(songEntities);
        updateFragments(songEntities);
    }

    public void updateFragments(List<SongEntity> songEntities) {
        List<Fragment> fragments = new ArrayList<>();
        for (int i = 0, size = songEntities.size(); i < size; i++) {
            fragments.add(PlayCoverFragment.newInstance(songEntities.get(i)));
        }
        if (mSongEntities != null && !mSongEntities.equals(songEntities)) {
            setFragmentList(fragments);
            notifyDataSetChanged();
        } else {
            setFragmentList(fragments);
        }
    }

    private void setFragmentList(List<Fragment> fragmentList) {
        if (mFragments != null) {
            mFragments.clear();
        }
        mFragments = fragmentList;
    }

  *//*  @Override
    public int getCount() {
        return MusicManager.getInstance().getMusicService().getSongData().size();
    }*//*

    @Override
    public int getCount() {
        return mFragments.size();
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
       *//* if (mChildCount > 0) {
            mChildCount--;
            return POSITION_NONE;
        }
        return super.getItemPosition(object);*//*
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }*/
}
