package com.freedom.lauzy.ticktockmusic.ui.fragment;


import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.widget.ImageView;

import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.freedom.lauzy.model.CategoryBean;
import com.freedom.lauzy.ticktockmusic.R;
import com.freedom.lauzy.ticktockmusic.RxBus;
import com.freedom.lauzy.ticktockmusic.base.BaseFragment;
import com.freedom.lauzy.ticktockmusic.event.PaletteEvent;
import com.freedom.lauzy.ticktockmusic.presenter.NetMusicCategoryPresenter;
import com.freedom.lauzy.ticktockmusic.ui.adapter.NetSongPagerAdapter;
import com.lauzy.freedom.librarys.common.DensityUtils;
import com.lauzy.freedom.librarys.common.ScreenUtils;
import com.lauzy.freedom.librarys.imageload.ImageConfig;
import com.lauzy.freedom.librarys.imageload.ImageLoader;
import com.lauzy.freedom.librarys.view.util.PaletteColor;
import com.lauzy.freedom.librarys.widght.TickToolbar;

import java.util.List;

import butterknife.BindView;

public class NetSongFragment extends BaseFragment<NetMusicCategoryPresenter> {

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
    private List<CategoryBean> mCategoryData;
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
            setImage(savedInstanceState.getString(IMG_URL));
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
        mCategoryData = mPresenter.getCategoryData(mActivity);
        NetSongPagerAdapter pagerAdapter = new NetSongPagerAdapter(getChildFragmentManager(), mCategoryData);
        mVpNetSong.setAdapter(pagerAdapter);
        mTabNetMusic.setupWithViewPager(mVpNetSong);
        mVpNetSong.setOffscreenPageLimit(mCategoryData.size());
        setImage(mCategoryData.get(0).imgUrl);
        mCtlTitle.setTitle(mCategoryData.get(0).title);
        mTabNetMusic.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                String imgUrl = mCategoryData.get(tab.getPosition()).imgUrl;
                setImage(imgUrl);
                mCtlTitle.setTitle(mCategoryData.get(tab.getPosition()).title);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void setImage(String imgUrl) {
        ImageLoader.INSTANCE.display(mActivity,
                new ImageConfig.Builder()
                        .url(imgUrl)
                        .asBitmap(true)
                        .placeholder(R.drawable.ic_default_horizontal)
                        .crossFade(500)
                        .isRound(false)
                        .intoTarget(new SimpleTarget<Bitmap>() {
                            @Override
                            public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {
                                mImgSong.setImageBitmap(resource);
                                PaletteColor.mainColorObservable(resource).subscribe(integer -> {
                                            mCtlTitle.setContentScrimColor(integer);
                                            RxBus.INSTANCE.post(new PaletteEvent(integer));
                                        }
                                );
                            }
                        })
                        .build());
    }
}
