package com.freedom.lauzy.ticktockmusic.ui.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.freedom.lauzy.ticktockmusic.R;
import com.freedom.lauzy.ticktockmusic.base.BaseFragment;
import com.freedom.lauzy.ticktockmusic.model.SongEntity;
import com.freedom.lauzy.ticktockmusic.service.MusicManager;
import com.lauzy.freedom.librarys.common.LogUtil;
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
    public static final String SONG_BUNDLE = "song_bundle";
    private static final String TAG = "PlayCoverFragment";

    @BindView(R.id.cv_music_cover)
    CircleImageView mCvMusicCover;
    private SongEntity mSongEntity;
    private boolean isVisible;

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
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        isVisible = isVisibleToUser;
        //停止转动并复位
        if (!isVisibleToUser && mCvMusicCover != null) {
            mCvMusicCover.stop();
        }
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (MusicManager.getInstance().isPlaying() && mCvMusicCover != null
                && !mCvMusicCover.isPlaying() && isVisible() && isVisible) {
            mCvMusicCover.postDelayed(() -> {
                if (!mCvMusicCover.isPlaying()) {
                    mCvMusicCover.start();
                }
            }, 200);
        }
    }

    public void coverStart(boolean isFromZero, int delay) {
        if (mCvMusicCover != null) {
            if (isFromZero && !mCvMusicCover.isPlaying()) {
                mCvMusicCover.setRotation(0);
            }
            mCvMusicCover.postDelayed(() -> {
                if (!mCvMusicCover.isPlaying()) {
                    mCvMusicCover.start();
                }
            }, delay);
        } else {
            LogUtil.d(TAG, "mCvMusicCover is null");
        }
    }

    public void coverPause() {
        if (mCvMusicCover != null) {
            mCvMusicCover.postDelayed(() -> {
                if (mCvMusicCover.isPlaying()) {
                    mCvMusicCover.pause();
                }
            }, 0);
        }
    }

    @Override
    protected void loadData() {
        if (mCvMusicCover != null) {
            ImageLoader.INSTANCE.display(mActivity, new ImageConfig.Builder()
                    .isRound(false)
                    .url(mSongEntity.albumCover)
                    .cacheStrategy(ImageConfig.CACHE_ALL)
                    .placeholder(R.drawable.ic_default)
                    .into(mCvMusicCover)
                    .build());
        }
    }
}
