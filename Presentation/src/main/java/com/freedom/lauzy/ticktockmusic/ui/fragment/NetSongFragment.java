package com.freedom.lauzy.ticktockmusic.ui.fragment;


import android.content.res.ColorStateList;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.widget.ImageView;

import com.bilibili.magicasakura.utils.ThemeUtils;
import com.freedom.lauzy.ticktockmusic.R;
import com.freedom.lauzy.ticktockmusic.RxBus;
import com.freedom.lauzy.ticktockmusic.base.BaseFragment;
import com.freedom.lauzy.ticktockmusic.event.ThemeEvent;
import com.freedom.lauzy.ticktockmusic.presenter.NetMusicCategoryPresenter;
import com.freedom.lauzy.ticktockmusic.ui.adapter.NetSongPagerAdapter;
import com.lauzy.freedom.librarys.common.DensityUtils;
import com.lauzy.freedom.librarys.common.ScreenUtils;
import com.lauzy.freedom.librarys.imageload.ImageConfig;
import com.lauzy.freedom.librarys.imageload.ImageLoader;
import com.lauzy.freedom.librarys.widght.TickToolbar;

import butterknife.BindView;
import io.reactivex.disposables.Disposable;

public class NetSongFragment extends BaseFragment<NetMusicCategoryPresenter> {

    @BindView(R.id.toolbar_common)
    TickToolbar mToolbarCommon;
    @BindView(R.id.tab_net_music)
    TabLayout mTabNetMusic;
    @BindView(R.id.vp_net_song)
    ViewPager mVpNetSong;
    @BindView(R.id.img_song)
    ImageView mImgSong;
    /*@BindView(R.id.status_bar)
    View mStatusBar;*/
    @BindView(R.id.ctl_title)
    CollapsingToolbarLayout mCtlTitle;
    private String mImgUrl;

    public NetSongFragment() {
    }

    public static NetSongFragment newInstance() {
        NetSongFragment fragment = new NetSongFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_net_song;
    }

    @Override
    protected void initInjector() {
        getFragmentComponent().inject(this);
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
//        mTabNetMusic.setBackgroundColor(stateList.getDefaultColor());
        mCtlTitle.setContentScrimColor(stateList.getDefaultColor());
    }

    @Override
    protected void initViews() {
        setToolbar();
        setTitlePadding();
        setTabBackground();
        setDrawerSync();
        mToolbarCommon.setElevation(0f);
    }

    private void setTitlePadding() {
        mToolbarCommon.getLayoutParams().height = ScreenUtils.getActionBarHeight(mActivity.getApplicationContext())
                + ScreenUtils.getStatusHeight(mActivity.getApplicationContext())
                + DensityUtils.dp2px(mActivity, 45);
        mToolbarCommon.setPadding(0, ScreenUtils.getStatusHeight(mActivity.getApplicationContext()),
                0, DensityUtils.dp2px(mActivity, 45));
    }

    @Override
    protected void loadData() {
        NetSongPagerAdapter pagerAdapter = new NetSongPagerAdapter(getChildFragmentManager(),
                mPresenter.getCategoryData(mActivity));
        mVpNetSong.setAdapter(pagerAdapter);
        mTabNetMusic.setupWithViewPager(mVpNetSong);
        mImgUrl = mPresenter.getCategoryData(mActivity).get(0).imgUrl;
        setImage();
        mTabNetMusic.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void setImage() {
        ImageLoader.INSTANCE.display(mActivity,
                new ImageConfig.Builder()
                        .url(mImgUrl)
                        .placeholder(R.drawable.ic_default)
                        .isRound(false)
                        .into(mImgSong)
                        .build());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        RxBus.INSTANCE.dispose(this);
    }
}
