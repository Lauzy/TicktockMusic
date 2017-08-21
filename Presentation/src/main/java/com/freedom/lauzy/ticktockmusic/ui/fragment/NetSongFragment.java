package com.freedom.lauzy.ticktockmusic.ui.fragment;


import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.widget.ImageView;

import com.freedom.lauzy.model.CategoryBean;
import com.freedom.lauzy.ticktockmusic.R;
import com.freedom.lauzy.ticktockmusic.base.BaseFragment;
import com.freedom.lauzy.ticktockmusic.contract.NetMusicCategoryContract;
import com.freedom.lauzy.ticktockmusic.presenter.NetMusicCategoryPresenter;
import com.freedom.lauzy.ticktockmusic.ui.adapter.NetSongPagerAdapter;
import com.lauzy.freedom.librarys.common.DensityUtils;
import com.lauzy.freedom.librarys.common.ScreenUtils;
import com.lauzy.freedom.librarys.widght.TickToolbar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class NetSongFragment extends BaseFragment<NetMusicCategoryPresenter>
        implements NetMusicCategoryContract.View {

    @BindView(R.id.toolbar_common)
    TickToolbar mToolbarCommon;
    @BindView(R.id.tab_net_music)
    TabLayout mTabNetMusic;
    @BindView(R.id.vp_net_song)
    ViewPager mVpNetSong;
    @BindView(R.id.img_song)
    ImageView mImgSong;
    @BindView(R.id.ctl_title)
    CollapsingToolbarLayout mCtlTitle;
    private List<CategoryBean> mCategoryData = new ArrayList<>();
    private static final String IMG_URL = "img_url";
    private static final int TAB_HEIGHT = 45;//45dp

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
    protected void initViews() {
        setToolbar();
        setTitlePadding();
        setDrawerSync();
        mToolbarCommon.setElevation(0f);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(IMG_URL, mCategoryData.get(mTabNetMusic.getSelectedTabPosition()).imgUrl);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null) {
            mPresenter.setCategoryColor(savedInstanceState.getString(IMG_URL));
        }
    }

    private void setTitlePadding() {
        mToolbarCommon.getLayoutParams().height = ScreenUtils.getActionBarHeight(mActivity.getApplicationContext())
                + ScreenUtils.getStatusHeight(mActivity.getApplicationContext())
                + DensityUtils.dp2px(mActivity, TAB_HEIGHT);
        mToolbarCommon.setPadding(0, ScreenUtils.getStatusHeight(mActivity.getApplicationContext()),
                0, DensityUtils.dp2px(mActivity, TAB_HEIGHT));
    }

    @Override
    protected void loadData() {
        mPresenter.loadData();
    }

    @Override
    public Context context() {
        return mActivity;
    }

    @Override
    public void loadCategoryData(List<CategoryBean> categoryBeen) {
        mCategoryData.addAll(categoryBeen);
        NetSongPagerAdapter pagerAdapter = new NetSongPagerAdapter(getChildFragmentManager(), categoryBeen);
        mVpNetSong.setAdapter(pagerAdapter);
        mTabNetMusic.setupWithViewPager(mVpNetSong);
        mVpNetSong.setOffscreenPageLimit(categoryBeen.size());
        mPresenter.setCategoryColor(categoryBeen.get(0).imgUrl);
        mCtlTitle.setTitle(categoryBeen.get(0).title);
        mTabNetMusic.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                String imgUrl = categoryBeen.get(tab.getPosition()).imgUrl;
                mPresenter.setCategoryColor(imgUrl);
                mCtlTitle.setTitle(categoryBeen.get(tab.getPosition()).title);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    @Override
    public void setCategoryBitmap(Bitmap bitmap) {
        mImgSong.setImageBitmap(bitmap);
    }

    @Override
    public void setCategoryColor(int color) {
        mCtlTitle.setContentScrimColor(color);
    }
}
