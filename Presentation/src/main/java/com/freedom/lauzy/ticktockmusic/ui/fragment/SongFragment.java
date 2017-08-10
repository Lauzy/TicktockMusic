package com.freedom.lauzy.ticktockmusic.ui.fragment;


import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.freedom.lauzy.model.LocalSongBean;
import com.freedom.lauzy.ticktockmusic.R;
import com.freedom.lauzy.ticktockmusic.base.BaseFragment;
import com.freedom.lauzy.ticktockmusic.contract.LocalMusicContract;
import com.freedom.lauzy.ticktockmusic.presenter.LocalMusicPresenter;
import com.freedom.lauzy.ticktockmusic.ui.adapter.LocalSongAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class SongFragment extends BaseFragment<LocalMusicPresenter> implements LocalMusicContract.View {

    @BindView(R.id.rv_local_song)
    RecyclerView mRvLocalSong;
    private List<LocalSongBean> mLocalSongBeen = new ArrayList<>();
    private LocalSongAdapter mAdapter;

    public static SongFragment newInstance() {
        SongFragment fragment = new SongFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_song;
    }

    @Override
    protected void initInjector() {
        getFragmentComponent().inject(this);
    }

    @Override
    protected void initViews() {
        mRvLocalSong.setLayoutManager(new LinearLayoutManager(mActivity));
    }

    @Override
    protected void loadData() {
        mPresenter.loadLocalSong();
        mAdapter = new LocalSongAdapter(R.layout.layout_song_item, mLocalSongBeen);
        mRvLocalSong.setAdapter(mAdapter);
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
