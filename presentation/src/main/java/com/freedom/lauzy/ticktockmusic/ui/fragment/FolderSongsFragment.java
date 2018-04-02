package com.freedom.lauzy.ticktockmusic.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.freedom.lauzy.ticktockmusic.R;
import com.freedom.lauzy.ticktockmusic.base.BaseFragment;
import com.freedom.lauzy.ticktockmusic.contract.FolderSongsContract;
import com.freedom.lauzy.ticktockmusic.model.SongEntity;
import com.freedom.lauzy.ticktockmusic.presenter.FolderSongsPresenter;
import com.freedom.lauzy.ticktockmusic.service.MusicManager;
import com.freedom.lauzy.ticktockmusic.service.MusicUtil;
import com.freedom.lauzy.ticktockmusic.ui.adapter.FolderSongsAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Desc : 文件夹音乐
 * Author : Lauzy
 * Date : 2018/3/15
 * Blog : http://www.jianshu.com/u/e76853f863a9
 * Email : freedompaladin@gmail.com
 */
public class FolderSongsFragment extends BaseFragment<FolderSongsPresenter>
        implements FolderSongsContract.View {

    private static final String FOLDER_PATH = "folder_path";
    private static final String FOLDER_NAME = "folder_name";
    @BindView(R.id.rv_folder_songs)
    RecyclerView mRvFolderSongs;
    @BindView(R.id.toolbar_common)
    Toolbar mToolbar;
    private List<SongEntity> mSongEntities = new ArrayList<>();
    private FolderSongsAdapter mAdapter;
    private String mFolderPath;
    private String mFolderName;

    public static FolderSongsFragment newInstance(String folderPath, String folderName) {
        Bundle args = new Bundle();
        args.putString(FOLDER_PATH, folderPath);
        args.putString(FOLDER_NAME, folderName);
        FolderSongsFragment fragment = new FolderSongsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mFolderPath = getArguments().getString(FOLDER_PATH);
            mFolderName = getArguments().getString(FOLDER_NAME);
        }
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_folder_songs;
    }

    @Override
    protected void initInjector() {
        super.initInjector();
        getFragmentComponent().inject(this);
    }

    @Override
    protected void initViews() {
        super.initViews();

        setToolbarPadding();
        ActionBar actionBar = ((AppCompatActivity) mActivity).getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        mToolbar.setTitle(mFolderName);

        mRvFolderSongs.setLayoutManager(new LinearLayoutManager(mActivity));
        mAdapter = new FolderSongsAdapter(R.layout.layout_song_item, mSongEntities);
        mRvFolderSongs.setAdapter(mAdapter);
        mAdapter.setOnDeleteSongListener((position, songEntity) -> mPresenter.deleteSong(position, songEntity));
    }

    @Override
    protected void loadData() {
        super.loadData();
        mPresenter.loadFolderSongs(mFolderPath);
    }

    @Override
    public void onLoadFolderSongsSuccess(List<SongEntity> songEntities) {
        mSongEntities.clear();
        mSongEntities.addAll(songEntities);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void setEmptyView() {

    }

    @Override
    public void loadFail(Throwable throwable) {

    }

    @Override
    public void deleteSongSuccess(int position, SongEntity songEntity) {
        List<SongEntity> songData = MusicManager.getInstance().getSongData();
        mSongEntities.remove(position);
        int deletePos = songData.indexOf(songEntity);
        songData.remove(songEntity);
        if (MusicManager.getInstance().getCurrentSong() != null && MusicManager.getInstance().getCurrentSong().equals(songEntity)) {
            MusicManager.getInstance().setMusicServiceData(MusicUtil.getSongIds(songData), MusicManager.getInstance().getCurPosition());
        } else {
            MusicManager.getInstance().setMusicServiceData(MusicUtil.getSongIds(songData), deletePos);
        }
        mAdapter.notifyItemRemoved(position);
    }
}
