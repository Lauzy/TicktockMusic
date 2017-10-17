package com.freedom.lauzy.ticktockmusic.ui.fragment;


import android.os.Bundle;

import com.freedom.lauzy.ticktockmusic.R;
import com.freedom.lauzy.ticktockmusic.base.BaseFragment;
import com.freedom.lauzy.ticktockmusic.model.SongEntity;
import com.lauzy.freedom.librarys.imageload.ImageConfig;
import com.lauzy.freedom.librarys.imageload.ImageLoader;
import com.lauzy.freedom.librarys.widght.CircleImageView;

import butterknife.BindView;

/**
 * Desc : 播放界面专辑图片Fragment
 * Author : Lauzy
 * Date : 2017/10/18
 * Blog : http://www.jianshu.com/u/e76853f863a9
 * Email : freedompaladin@gmail.com
 */
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

    public void coverStart(boolean isFromZero, int delay) {
        if (mCvMusicCover != null) {
            if (isFromZero) {
                mCvMusicCover.setRotation(0);
            }
            mCvMusicCover.postDelayed(() -> mCvMusicCover.start(), delay);
        }
    }

    public void coverPause(int delay) {
        if (mCvMusicCover != null) {
            mCvMusicCover.postDelayed(() -> mCvMusicCover.pause(), delay);
        }
    }

    @Override
    protected void loadData() {
        if (mCvMusicCover != null) {
            ImageLoader.INSTANCE.display(mActivity, new ImageConfig.Builder()
                    .url(mSongEntity.albumCover)
                    .placeholder(R.drawable.ic_default)
                    .into(mCvMusicCover)
                    .build());
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        //不可见时停止转动并复位
        if (mSongEntity != null && mCvMusicCover != null) {
            mCvMusicCover.stop();
        }
    }
}
