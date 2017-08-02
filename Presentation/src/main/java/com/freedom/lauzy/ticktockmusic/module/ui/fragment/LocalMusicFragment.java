package com.freedom.lauzy.ticktockmusic.module.ui.fragment;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;

import com.freedom.lauzy.ticktockmusic.R;
import com.freedom.lauzy.ticktockmusic.base.BaseLazyFragment;
import com.freedom.lauzy.ticktockmusic.module.LocalMusicPresenter;
import com.lauzy.freedom.librarys.widght.TickToolbar;

import butterknife.BindView;

public class LocalMusicFragment extends BaseLazyFragment<LocalMusicPresenter> {

    @BindView(R.id.toolbar_common)
    TickToolbar mToolbarCommon;
    @BindView(R.id.tab_local_music)
    TabLayout mTabLocalMusic;
    @BindView(R.id.vp_local_music)
    ViewPager mVpLocalMusic;

    public static LocalMusicFragment newInstance() {
        LocalMusicFragment musicFragment = new LocalMusicFragment();
        Bundle bundle = new Bundle();
        musicFragment.setArguments(bundle);
        return musicFragment;
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_local_music;
    }

    @Override
    protected void initInjector() {
        getFragmentComponent().inject(this);
    }

    @Override
    protected void initViews() {

    }

    @Override
    protected void loadData() {

    }
}
