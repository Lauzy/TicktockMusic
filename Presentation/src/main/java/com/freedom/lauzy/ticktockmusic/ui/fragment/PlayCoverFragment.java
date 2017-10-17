package com.freedom.lauzy.ticktockmusic.ui.fragment;


import android.os.Bundle;

import com.freedom.lauzy.ticktockmusic.R;
import com.freedom.lauzy.ticktockmusic.base.BaseFragment;
import com.freedom.lauzy.ticktockmusic.model.SongEntity;
import com.lauzy.freedom.librarys.imageload.ImageConfig;
import com.lauzy.freedom.librarys.imageload.ImageLoader;
import com.lauzy.freedom.librarys.widght.CircleImageView;

import butterknife.BindView;

public class PlayCoverFragment extends BaseFragment {
    private static final String SONG_BUNDLE = "song_bundle";

    @BindView(R.id.cv_music_cover)
    CircleImageView mCvMusicCover;
    private SongEntity mSongEntity;

    public static PlayCoverFragment newInstance(SongEntity songEntity) {
        PlayCoverFragment fragment = new PlayCoverFragment();
        Bundle args = new Bundle();
        args.putParcelable(SONG_BUNDLE, songEntity);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mSongEntity = getArguments().getParcelable(SONG_BUNDLE);
        }
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_play_cover;
    }

    @Override
    protected void initInjector() {

    }

    @Override
    protected void initViews() {

    }

    @Override
    protected void loadData() {
        ImageLoader.INSTANCE.display(mActivity, new ImageConfig.Builder()
                .url(mSongEntity.albumCover)
                .placeholder(R.drawable.ic_default)
                .into(mCvMusicCover)
                .build());
    }
}
