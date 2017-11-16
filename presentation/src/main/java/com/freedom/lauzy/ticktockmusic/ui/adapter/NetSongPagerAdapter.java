package com.freedom.lauzy.ticktockmusic.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.freedom.lauzy.model.CategoryBean;
import com.freedom.lauzy.ticktockmusic.ui.fragment.NetSongListFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Desc :
 * Author : Lauzy
 * Date : 2017/8/14
 * Blog : http://www.jianshu.com/u/e76853f863a9
 * Email : freedompaladin@gmail.com
 */
public class NetSongPagerAdapter extends FragmentPagerAdapter {

    private List<CategoryBean> mCategoryBeen;

    public NetSongPagerAdapter(FragmentManager fm, List<CategoryBean> categoryBeen) {
        super(fm);
        mCategoryBeen = categoryBeen;
    }

    @Override
    public Fragment getItem(int position) {
        CategoryBean categoryBean = mCategoryBeen.get(position);
        return NetSongListFragment.newInstance(categoryBean.type);
    }

    @Override
    public int getCount() {
        return mCategoryBeen != null ? mCategoryBeen.size() : 0;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        List<String> titles = new ArrayList<>();
        if (mCategoryBeen != null) {
//            titles.addAll(mCategoryBeen.stream().map(categoryBean -> categoryBean.title).collect(Collectors.toList()));
            for (int i = 0; i < mCategoryBeen.size(); i++) {
                titles.add(mCategoryBeen.get(i).title);
            }
        }
        return titles.get(position);
    }
}
