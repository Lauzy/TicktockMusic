package com.freedom.lauzy.ticktockmusic.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.freedom.lauzy.model.LocalSongBean;
import com.freedom.lauzy.ticktockmusic.R;
import com.freedom.lauzy.ticktockmusic.base.BaseActivity;
import com.freedom.lauzy.ticktockmusic.contract.LocalMusicContract;
import com.freedom.lauzy.ticktockmusic.presenter.LocalMusicPresenter;
import com.freedom.lauzy.ticktockmusic.ui.adapter.AlbumDetailAdapter;
import com.lauzy.freedom.librarys.widght.TickToolbar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class AlbumDetailActivity extends BaseActivity<LocalMusicPresenter> implements LocalMusicContract.View {

    @BindView(R.id.toolbar_common)
    TickToolbar mToolbarCommon;
    @BindView(R.id.rv_album_detail)
    RecyclerView mRvAlbumDetail;
    private static final String ALBUM_ID = "_id";
    private List<LocalSongBean> mLocalSongBeen = new ArrayList<>();
    private AlbumDetailAdapter mAdapter;

    public static Intent newInstance(Context context, long id) {
        Intent intent = new Intent(context, AlbumDetailActivity.class);
        intent.putExtra(ALBUM_ID, id);
        return intent;
    }

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_album_detail;
    }

    @Override
    protected void initViews() {
        mRvAlbumDetail.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new AlbumDetailAdapter(R.layout.layout_song_item, mLocalSongBeen);
        mRvAlbumDetail.setAdapter(mAdapter);
    }

    @Override
    protected void loadData() {
        mPresenter.setId(getIntent().getLongExtra(ALBUM_ID, 0));
        mPresenter.loadLocalSong();
    }

    @Override
    public void loadLocalMusic(List<LocalSongBean> localSongBeen) {
        mLocalSongBeen.clear();
        mLocalSongBeen.addAll(localSongBeen);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void setEmptyView() {

    }

    @Override
    public void loadFailed(Throwable throwable) {

    }
}
