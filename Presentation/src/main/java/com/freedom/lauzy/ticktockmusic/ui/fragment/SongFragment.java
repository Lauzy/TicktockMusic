package com.freedom.lauzy.ticktockmusic.ui.fragment;


import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.freedom.lauzy.ticktockmusic.R;
import com.freedom.lauzy.ticktockmusic.base.BaseFragment;
import com.freedom.lauzy.ticktockmusic.contract.LocalMusicContract;
import com.freedom.lauzy.ticktockmusic.event.ThemeEvent;
import com.freedom.lauzy.ticktockmusic.function.RxBus;
import com.freedom.lauzy.ticktockmusic.model.SongEntity;
import com.freedom.lauzy.ticktockmusic.presenter.LocalMusicPresenter;
import com.freedom.lauzy.ticktockmusic.ui.adapter.LocalSongAdapter;
import com.freedom.lauzy.ticktockmusic.utils.ThemeHelper;
import com.lauzy.freedom.librarys.widght.TickSwipeRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.reactivex.disposables.Disposable;

public class SongFragment extends BaseFragment<LocalMusicPresenter> implements LocalMusicContract.View, LocalSongAdapter.PlayNextListener {

    @BindView(R.id.rv_local_song)
    RecyclerView mRvLocalSong;
    @BindView(R.id.srl_local_song)
    TickSwipeRefreshLayout mSrlLocalSong;
    private List<SongEntity> mLocalSongBeen = new ArrayList<>();
    private LocalSongAdapter mAdapter;

    public static SongFragment newInstance() {
        SongFragment fragment = new SongFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
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
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Disposable disposable = RxBus.INSTANCE.doDefaultSubscribe(ThemeEvent.class,
                themeEvent -> setThemeLayoutBg());
        RxBus.INSTANCE.addDisposable(this, disposable);
    }

    @Override
    protected void initViews() {
        setThemeLayoutBg();
        mSrlLocalSong.setRefreshing(true);
        mSrlLocalSong.setOnRefreshListener(() -> mPresenter.loadLocalSong());
        mRvLocalSong.setLayoutManager(new LinearLayoutManager(mActivity));
    }

    private void setThemeLayoutBg() {
        ThemeHelper.setThemeColor(mActivity, mSrlLocalSong);
    }

    @Override
    protected void loadData() {
        mPresenter.loadLocalSong();
        mAdapter = new LocalSongAdapter(R.layout.layout_song_item, mLocalSongBeen);
        mRvLocalSong.setAdapter(mAdapter);
        mAdapter.setPlayNextListener(this);
    }

    @Override
    public void loadLocalMusic(List<SongEntity> localSongBeen) {
        mLocalSongBeen.clear();
        mLocalSongBeen.addAll(localSongBeen);
        mAdapter.notifyDataSetChanged();
        mSrlLocalSong.setRefreshing(false);
    }

    @Override
    public void setEmptyView() {
        mSrlLocalSong.setRefreshing(false);
    }

    @Override
    public void loadFailed(Throwable throwable) {
        mSrlLocalSong.setRefreshing(false);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        RxBus.INSTANCE.dispose(this);
    }

    @Override
    public void playNext(SongEntity entity) {
        mPresenter.setNewQueueData(entity);
    }
}
