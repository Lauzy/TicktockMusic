package com.freedom.lauzy.ticktockmusic.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.freedom.lauzy.ticktockmusic.model.SongEntity;
import com.freedom.lauzy.ticktockmusic.ui.fragment.PlayCoverFragment;

import java.util.List;

/**
 * Desc :
 * Author : Lauzy
 * Date : 2018/3/12
 * Blog : http://www.jianshu.com/u/e76853f863a9
 * Email : freedompaladin@gmail.com
 */
public class PlayViewPagerAdapter extends FragmentStatePagerAdapter {

    private List<SongEntity> mSongEntities;

    public PlayViewPagerAdapter(FragmentManager fm, List<SongEntity> songEntities) {
        super(fm);
        mSongEntities = songEntities;
    }

    @Override
    public Fragment getItem(int position) {
        return PlayCoverFragment.newInstance(mSongEntities.get(position));
    }

    @Override
    public int getCount() {
        return mSongEntities.size();
    }

    public void setSongEntities(List<SongEntity> songData) {
        mSongEntities = songData;
    }
}
