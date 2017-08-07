package com.freedom.lauzy.ticktockmusic.ui.fragment;


import android.content.res.ColorStateList;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.bilibili.magicasakura.utils.ThemeUtils;
import com.freedom.lauzy.ticktockmusic.R;
import com.freedom.lauzy.ticktockmusic.RxBus;
import com.freedom.lauzy.ticktockmusic.base.BaseFragment;
import com.freedom.lauzy.ticktockmusic.event.ThemeEvent;
import com.freedom.lauzy.ticktockmusic.presenter.LocalMusicPresenter;
import com.freedom.lauzy.ticktockmusic.ui.adapter.LocalMusicPagerAdapter;
import com.lauzy.freedom.librarys.widght.TickToolbar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindArray;
import butterknife.BindView;
import io.reactivex.disposables.Disposable;

public class LocalMusicFragment extends BaseFragment<LocalMusicPresenter> {

    @BindView(R.id.toolbar_common)
    TickToolbar mToolbarCommon;
    @BindView(R.id.status_bar)
    View mStatusBar;
    @BindView(R.id.tab_local_music)
    TabLayout mTabLocalMusic;
    @BindView(R.id.vp_local_music)
    ViewPager mVpLocalMusic;
    @BindArray(R.array.LocalMusicTitleArr)
    String[] mMusicTitle;

    public static LocalMusicFragment newInstance() {
        LocalMusicFragment musicFragment = new LocalMusicFragment();
        Bundle bundle = new Bundle();
        musicFragment.setArguments(bundle);
        return musicFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Disposable disposable = RxBus.INSTANCE.doDefaultSubscribe(ThemeEvent.class,
                themeEvent -> setTabBackground());
        RxBus.INSTANCE.addDisposable(this, disposable);
    }

    private void setTabBackground() {
        ColorStateList stateList = ThemeUtils.getThemeColorStateList(mActivity, R.color.color_tab);
        mTabLocalMusic.setBackgroundColor(stateList.getDefaultColor());
        mStatusBar.setBackgroundColor(stateList.getDefaultColor());
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
        setTabBackground();
        mToolbarCommon.setElevation(0f);
        setViewPager();
    }

    private void setViewPager() {
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(SongFragment.newInstance());
        fragments.add(SingerFragment.newInstance());
        LocalMusicPagerAdapter adapter = new LocalMusicPagerAdapter(getChildFragmentManager(), fragments);
        adapter.setTitles(mMusicTitle);
        mVpLocalMusic.setAdapter(adapter);
        mTabLocalMusic.setupWithViewPager(mVpLocalMusic);
    }

    @Override
    protected void loadData() {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        RxBus.INSTANCE.dispose(this);
    }
}
