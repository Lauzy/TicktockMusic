package com.freedom.lauzy.ticktockmusic.ui.fragment;

import android.content.res.ColorStateList;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;

import com.bilibili.magicasakura.utils.ThemeUtils;
import com.freedom.lauzy.model.SongListBean;
import com.freedom.lauzy.ticktockmusic.R;
import com.freedom.lauzy.ticktockmusic.RxBus;
import com.freedom.lauzy.ticktockmusic.base.BaseFragment;
import com.freedom.lauzy.ticktockmusic.contract.NetMusicContract;
import com.freedom.lauzy.ticktockmusic.event.ThemeEvent;
import com.freedom.lauzy.ticktockmusic.presenter.NetMusicPresenter;
import com.freedom.lauzy.ticktockmusic.ui.adapter.NetSongAdapter;
import com.lauzy.freedom.librarys.imageload.ImageConfig;
import com.lauzy.freedom.librarys.imageload.ImageLoader;
import com.lauzy.freedom.librarys.widght.TickSwipeRefreshLayout;
import com.lauzy.freedom.librarys.widght.TickToolbar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.reactivex.disposables.Disposable;

/**
 * Desc :
 * Author : Lauzy
 * Date : 2017/8/8
 * Blog : http://www.jianshu.com/u/e76853f863a9
 * Email : freedompaladin@gmail.com
 */
public class SongListFragment extends BaseFragment<NetMusicPresenter> implements NetMusicContract.View {
    @BindView(R.id.rv_net_song)
    RecyclerView mRvNetSong;
    @BindView(R.id.srl_net_song)
    TickSwipeRefreshLayout mSrlNetSong;
    @BindView(R.id.ctl_title)
    CollapsingToolbarLayout mCtlTitle;
    @BindView(R.id.toolbar_common)
    TickToolbar mToolbarCommon;
    @BindView(R.id.img_song_title)
    ImageView mImgSongTitle;
    private List<SongListBean> mSongListBeen = new ArrayList<>();
    private NetSongAdapter mAdapter;
    private static final String TYPE = "type";
    private static final String TITLE = "title";
    private static final String IMG_URL = "img_url";
    private int mType;
    private String mTitle;
    private String mImgUrl;

    public static SongListFragment newInstance(int type, String title, String imgUrl) {
        SongListFragment songListFragment = new SongListFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(TYPE, type);
        bundle.putString(TITLE, title);
        bundle.putString(IMG_URL, imgUrl);
        songListFragment.setArguments(bundle);
        return songListFragment;
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.layout_net_music;
    }

    @Override
    protected void initInjector() {
        getFragmentComponent().inject(this);
    }

    @Override
    protected void initViews() {
        setToolbarLayout();
        initRecyclerView();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Disposable disposable = RxBus.INSTANCE.doDefaultSubscribe(ThemeEvent.class,
                themeEvent -> setThemeLayoutBg());
        RxBus.INSTANCE.addDisposable(this, disposable);
        if (getArguments() != null) {
            mType = getArguments().getInt(TYPE);
            mTitle = getArguments().getString(TITLE);
            mImgUrl = getArguments().getString(IMG_URL);
        }
    }

    private void setToolbarLayout() {
        setToolbar();
        setToolbarPadding();
        setThemeLayoutBg();
        ActionBar actionBar = ((AppCompatActivity) mActivity).getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeButtonEnabled(true);
        }
        ImageLoader.INSTANCE.display(mActivity,
                new ImageConfig.Builder()
                        .url(mImgUrl)
                        .placeholder(R.drawable.ic_default)
                        .isRound(false)
                        .into(mImgSongTitle)
                        .build());
    }

    private void setThemeLayoutBg() {
        ColorStateList stateList = ThemeUtils.getThemeColorStateList(mActivity, R.color.theme_color_primary);
        mCtlTitle.setContentScrimColor(stateList.getDefaultColor());
        mSrlNetSong.setColorSchemeColors(stateList.getDefaultColor());
    }

    private void initRecyclerView() {
        mSrlNetSong.setRefreshing(true);
        mSrlNetSong.setOnRefreshListener(() -> mPresenter.loadNetMusicList());
        mRvNetSong.setLayoutManager(new LinearLayoutManager(mActivity));
        mAdapter = new NetSongAdapter(R.layout.layout_song_item, mSongListBeen);
        mRvNetSong.setAdapter(mAdapter);
    }

    @Override
    protected void loadData() {
        mPresenter.setType(mType);
        mToolbarCommon.setTitle(mTitle);
        mPresenter.loadNetMusicList();
        mAdapter.setOnLoadMoreListener(() -> mPresenter.loadMoreNetMusicList(), mRvNetSong);
    }

    @Override
    public void loadSuccess(List<SongListBean> songListBeen) {
        mSongListBeen.clear();
        mSongListBeen.addAll(songListBeen);
        mAdapter.notifyDataSetChanged();
        mSrlNetSong.setRefreshing(false);
    }

    @Override
    public void setEmptyView() {
        mSrlNetSong.setRefreshing(false);
    }

    @Override
    public void loadFail(Throwable throwable) {
        mSrlNetSong.setRefreshing(false);
    }

    @Override
    public void loadMoreSuccess(List<SongListBean> songListBeen) {
        mAdapter.addData(songListBeen);
        mAdapter.loadMoreComplete();
    }

    @Override
    public void loadMoreEnd() {
        mAdapter.loadMoreEnd();
    }

    @Override
    public void loadMoreFail(Throwable throwable) {
        mAdapter.loadMoreFail();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        RxBus.INSTANCE.dispose(this);
    }
}
