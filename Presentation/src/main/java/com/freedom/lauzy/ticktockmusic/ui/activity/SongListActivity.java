package com.freedom.lauzy.ticktockmusic.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.bilibili.magicasakura.utils.ThemeUtils;
import com.freedom.lauzy.model.SongListBean;
import com.freedom.lauzy.ticktockmusic.R;
import com.freedom.lauzy.ticktockmusic.RxBus;
import com.freedom.lauzy.ticktockmusic.base.BaseActivity;
import com.freedom.lauzy.ticktockmusic.contract.NetMusicContract;
import com.freedom.lauzy.ticktockmusic.event.ThemeEvent;
import com.freedom.lauzy.ticktockmusic.presenter.NetMusicPresenter;
import com.freedom.lauzy.ticktockmusic.ui.adapter.NetSongAdapter;
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
public class SongListActivity extends BaseActivity<NetMusicPresenter> implements NetMusicContract.View {
    @BindView(R.id.rv_net_song)
    RecyclerView mRvNetSong;
    @BindView(R.id.srl_net_song)
    TickSwipeRefreshLayout mSrlNetSong;
    @BindView(R.id.ctl_title)
    CollapsingToolbarLayout mCtlTitle;
    @BindView(R.id.toolbar_common)
    TickToolbar mToolbarCommon;
    private List<SongListBean> mSongListBeen = new ArrayList<>();
    private NetSongAdapter mAdapter;
    private static final String TYPE = "type";
    private static final String TITLE = "title";

    public static Intent newInstance(Context context, int type, String title) {
        Intent intent = new Intent(context, SongListActivity.class);
        intent.putExtra(TYPE, type);
        intent.putExtra(TITLE, title);
        return intent;
    }

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.layout_net_music;
    }

    @Override
    protected void initViews() {
        setToolbarLayout();
        initRecyclerView();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Disposable disposable = RxBus.INSTANCE.doDefaultSubscribe(ThemeEvent.class,
                themeEvent -> setThemeLayoutBg());
        RxBus.INSTANCE.addDisposable(this, disposable);
    }

    private void setToolbarLayout() {
        showBackIcon();
        setThemeLayoutBg();
    }

    private void setThemeLayoutBg() {
        ColorStateList stateList = ThemeUtils.getThemeColorStateList(this, R.color.theme_color_primary);
        mCtlTitle.setContentScrimColor(stateList.getDefaultColor());
        mSrlNetSong.setColorSchemeColors(stateList.getDefaultColor());
    }

    private void initRecyclerView() {
        mSrlNetSong.setRefreshing(true);
        mSrlNetSong.setOnRefreshListener(() -> mPresenter.loadNetMusicList());
        mRvNetSong.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new NetSongAdapter(R.layout.layout_song_item, mSongListBeen);
        mRvNetSong.setAdapter(mAdapter);
    }

    @Override
    protected void loadData() {
        mPresenter.setType(getIntent().getIntExtra(TYPE, 1));
        setToolbarTitle(getIntent().getStringExtra(TITLE));
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
    protected void onDestroy() {
        super.onDestroy();
        RxBus.INSTANCE.dispose(this);
    }
}
