package com.freedom.lauzy.ticktockmusic.ui.fragment;


import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.freedom.lauzy.ticktockmusic.R;
import com.freedom.lauzy.ticktockmusic.base.BaseFragment;
import com.freedom.lauzy.ticktockmusic.contract.LocalMusicContract;
import com.freedom.lauzy.ticktockmusic.event.ClearQueueEvent;
import com.freedom.lauzy.ticktockmusic.event.MediaUpdateEvent;
import com.freedom.lauzy.ticktockmusic.event.ThemeEvent;
import com.freedom.lauzy.ticktockmusic.function.RxBus;
import com.freedom.lauzy.ticktockmusic.model.SongEntity;
import com.freedom.lauzy.ticktockmusic.presenter.LocalMusicPresenter;
import com.freedom.lauzy.ticktockmusic.service.MusicManager;
import com.freedom.lauzy.ticktockmusic.service.MusicUtil;
import com.freedom.lauzy.ticktockmusic.ui.adapter.LocalSongAdapter;
import com.freedom.lauzy.ticktockmusic.utils.ThemeHelper;
import com.lauzy.freedom.librarys.widght.TickSwipeRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.reactivex.disposables.Disposable;

/**
 * Desc : 本地音乐音乐列表Fragment
 * Author : Lauzy
 * Date : 2017/11/1
 * Blog : http://www.jianshu.com/u/e76853f863a9
 * Email : freedompaladin@gmail.com
 */
public class SongFragment extends BaseFragment<LocalMusicPresenter> implements LocalMusicContract.View {

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
                themeEvent -> {
                    setThemeLayoutBg();
                    mAdapter.notifyDataSetChanged();
                });
        RxBus.INSTANCE.addDisposable(this, disposable);

        subscribeQueueEvent();

        Disposable updateDisposable = RxBus.INSTANCE.doStickySubscribe(MediaUpdateEvent.class,
                mediaUpdateEvent -> mPresenter.loadLocalSong());
        RxBus.INSTANCE.addDisposable(this, updateDisposable);
    }

    private void subscribeQueueEvent() {
        Disposable disposable = RxBus.INSTANCE.doDefaultSubscribe(ClearQueueEvent.class, clearQueueEvent -> {
            if (mAdapter == null) {
                return;
            }
            mAdapter.notifyDataSetChanged();
            MusicManager.getInstance().addPlayQueueListener(() -> mAdapter.notifyDataSetChanged());
        });
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
        mAdapter.setOnDeleteSongListener((position, songEntity) -> mPresenter.deleteSong(position, songEntity));
        MusicManager.getInstance().addPlayQueueListener(() -> mAdapter.notifyDataSetChanged());
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
    public void deleteSongSuccess(int position, SongEntity songEntity) {
        List<SongEntity> songData = MusicManager.getInstance().getSongData();
        mLocalSongBeen.remove(position);
        int deletePos = songData.indexOf(songEntity);
        songData.remove(songEntity);
        if (MusicManager.getInstance().getCurrentSong() != null && MusicManager.getInstance().getCurrentSong().equals(songEntity)) {
            MusicManager.getInstance().setMusicServiceData(MusicUtil.getSongIds(songData), MusicManager.getInstance().getCurPosition());
        } else {
            MusicManager.getInstance().setMusicServiceData(MusicUtil.getSongIds(songData), deletePos);
        }
        mAdapter.notifyItemRemoved(position);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        RxBus.INSTANCE.dispose(this);
    }
}
