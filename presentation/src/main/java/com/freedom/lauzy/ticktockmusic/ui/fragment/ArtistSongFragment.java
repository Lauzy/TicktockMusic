package com.freedom.lauzy.ticktockmusic.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.freedom.lauzy.ticktockmusic.R;
import com.freedom.lauzy.ticktockmusic.base.BaseFragment;
import com.freedom.lauzy.ticktockmusic.contract.ArtistSongContract;
import com.freedom.lauzy.ticktockmusic.model.SongEntity;
import com.freedom.lauzy.ticktockmusic.presenter.ArtistSongPresenter;
import com.freedom.lauzy.ticktockmusic.ui.adapter.ArtistSongAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Desc : 歌手音乐Fragment
 * Author : Lauzy
 * Date : 2017/9/29
 * Blog : http://www.jianshu.com/u/e76853f863a9
 * Email : freedompaladin@gmail.com
 */
public class ArtistSongFragment extends BaseFragment<ArtistSongPresenter>
        implements ArtistSongContract.View {

    private static final String ARTIST_ID = "artist_id";
    @BindView(R.id.rv_artist_song)
    RecyclerView mRvArtistSong;
    private List<SongEntity> mSongEntities = new ArrayList<>();
    private ArtistSongAdapter mAdapter;
    private long mArtistId;

    public static ArtistSongFragment newInstance(long artistId) {
        Bundle args = new Bundle();
        args.putLong(ARTIST_ID, artistId);
        ArtistSongFragment fragment = new ArtistSongFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_artist_song;
    }

    @Override
    protected void initInjector() {
        getFragmentComponent().inject(this);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mArtistId = getArguments().getLong(ARTIST_ID);
        }
    }

    @Override
    protected void initViews() {
        mRvArtistSong.setLayoutManager(new LinearLayoutManager(mActivity));
        mAdapter = new ArtistSongAdapter(R.layout.layout_song_item, mSongEntities);
        mRvArtistSong.setAdapter(mAdapter);
    }

    @Override
    protected void loadData() {
        mPresenter.loadArtistSongs(mArtistId);
    }

    @Override
    public void loadArtistSongsResult(List<SongEntity> songEntities) {
        mSongEntities.clear();
        mSongEntities.addAll(songEntities);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void loadFailed(Throwable throwable) {

    }

    @Override
    public void emptyView() {

    }
}
