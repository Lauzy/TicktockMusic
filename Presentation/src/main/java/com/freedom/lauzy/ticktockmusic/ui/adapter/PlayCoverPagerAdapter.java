package com.freedom.lauzy.ticktockmusic.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.freedom.lauzy.ticktockmusic.model.SongEntity;
import com.freedom.lauzy.ticktockmusic.ui.fragment.PlayCoverFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Desc : 歌词、专辑图片 PagerAdapter
 * Author : Lauzy
 * Date : 2017/10/17
 * Blog : http://www.jianshu.com/u/e76853f863a9
 * Email : freedompaladin@gmail.com
 */
public class PlayCoverPagerAdapter extends FragmentStatePagerAdapter {

    private List<Fragment> mFragments;
    private List<SongEntity> mSongEntities;

    public PlayCoverPagerAdapter(FragmentManager fm, List<SongEntity> songEntities) {
        super(fm);
        mSongEntities = songEntities;
        updateFragments(songEntities);
    }

    public void updateFragments(List<SongEntity> songEntities) {
        List<Fragment> fragments = new ArrayList<>();
        for (int i = 0, size = songEntities.size(); i < size; i++) {
            fragments.add(PlayCoverFragment.newInstance(songEntities.get(i)));
        }
        if (!mSongEntities.equals(songEntities)) {
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

    @Override
    public int getCount() {
        return mFragments.size();
    }

    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }
}
