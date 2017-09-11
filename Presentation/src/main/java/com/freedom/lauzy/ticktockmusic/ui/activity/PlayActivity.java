package com.freedom.lauzy.ticktockmusic.ui.activity;


import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.freedom.lauzy.ticktockmusic.R;
import com.freedom.lauzy.ticktockmusic.base.BaseActivity;
import com.freedom.lauzy.ticktockmusic.contract.PlayContract;
import com.freedom.lauzy.ticktockmusic.event.PlayModeEvent;
import com.freedom.lauzy.ticktockmusic.function.RxBus;
import com.freedom.lauzy.ticktockmusic.model.SongEntity;
import com.freedom.lauzy.ticktockmusic.presenter.PlayPresenter;
import com.freedom.lauzy.ticktockmusic.service.MusicManager;
import com.freedom.lauzy.ticktockmusic.service.MusicService;
import com.freedom.lauzy.ticktockmusic.ui.fragment.PlayQueueBottomSheetFragment;
import com.freedom.lauzy.ticktockmusic.utils.SharePrefHelper;
import com.lauzy.freedom.data.local.LocalUtil;
import com.lauzy.freedom.librarys.widght.CircleImageView;
import com.lauzy.freedom.librarys.widght.TickToolbar;
import com.lauzy.freedom.librarys.widght.music.PlayPauseView;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.disposables.Disposable;


public class PlayActivity extends BaseActivity<PlayPresenter> implements
        SeekBar.OnSeekBarChangeListener, PlayPauseView.PlayPauseListener, PlayContract.View, MusicManager.SeekBarProgressListener {

    @BindView(R.id.toolbar_common)
    TickToolbar mToolbarCommon;
    //    @BindView(R.id.vp_album_cover)
//    ViewPager mVpAlbumCover;
    @BindView(R.id.acv_music)
    CircleImageView mAlbumCoverView;
    @BindView(R.id.txt_current_progress)
    TextView mTxtCurrentProgress;
    @BindView(R.id.seek_play)
    SeekBar mSeekPlay;
    @BindView(R.id.txt_total_length)
    TextView mTxtTotalLength;
    @BindView(R.id.img_play_mode)
    ImageView mImgPlayMode;
    @BindView(R.id.play_pause)
    PlayPauseView mPlayPause;
    @BindView(R.id.layout_play)
    LinearLayout mLayoutPlay;
    private static final String TAG = "PlayActivity";

    public static Intent newInstance(Context context) {
        return new Intent(context, PlayActivity.class);
    }

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_play;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Disposable disposable = RxBus.INSTANCE.doStickySubscribe(SongEntity.class, this::setCurData);
        Disposable playModeDisposable = RxBus.INSTANCE.doDefaultSubscribe(PlayModeEvent.class,
                playModeEvent -> setModeView());
        RxBus.INSTANCE.addDisposable(this, disposable);
        RxBus.INSTANCE.addDisposable(this, playModeDisposable);
    }

    @Override
    protected void initViews() {
        showBackIcon();
        setModeView();
        mToolbarCommon.setBackgroundColor(Color.TRANSPARENT);
    }

    private void setModeView() {
        switch (SharePrefHelper.getRepeatMode(this)) {
            case MusicService.REPEAT_SINGLE_MODE:
                mImgPlayMode.setImageResource(R.drawable.ic_repeat_one_black);
                break;
            case MusicService.REPEAT_ALL_MODE:
                mImgPlayMode.setImageResource(R.drawable.ic_repeat_black);
                break;
            case MusicService.REPEAT_RANDOM_MODE:
                mImgPlayMode.setImageResource(R.drawable.ic_shuffle_black);
                break;
        }
    }

    @Override
    protected void loadData() {
        setCurProgress((int) MusicManager.getInstance().getCurrentProgress(),
                MusicManager.getInstance().getDuration());
        mTxtCurrentProgress.setText(LocalUtil.formatTime(MusicManager.getInstance().getCurrentProgress()));
        setCurData(MusicManager.getInstance().getCurrentSong());
        mSeekPlay.setOnSeekBarChangeListener(this);
        mPlayPause.setPlayPauseListener(this);
    }

    private void setCurData(SongEntity songEntity) {
        if (songEntity != null) {
            mToolbarCommon.setTitle(songEntity.title);
            mToolbarCommon.setSubtitle(songEntity.artistName);
            mTxtTotalLength.setText(songEntity.songLength);
            if (MusicManager.getInstance().isPlaying() && !mPlayPause.isPlaying()) {
                mPlayPause.playWithoutAnim();
                mAlbumCoverView.pause();
            }
            MusicManager.getInstance().setSeekBarProgressListener(this);
            mPresenter.setCoverImgUrl(songEntity.albumCover);
        }
    }

    @Override
    public void onProgress(int progress, int duration) {
        setCurProgress(progress, duration);
    }

    private void setCurProgress(int progress, int duration) {
        mSeekPlay.setMax(duration);
        mSeekPlay.setProgress(progress);
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        mTxtCurrentProgress.setText(LocalUtil.formatTime(seekBar.getProgress()));
        if (fromUser) {
            MusicManager.getInstance().pauseProgress();
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        MusicManager.getInstance().resumeProgress();
        MusicManager.getInstance().seekTo(seekBar.getProgress());
        if (!mPlayPause.isPlaying()) {
            mPlayPause.play();
        }
    }

    @Override
    public void play() {
        MusicManager.getInstance().start();
        mAlbumCoverView.start();
    }

    @Override
    public void pause() {
        MusicManager.getInstance().pause();
        mAlbumCoverView.pause();
    }

    @Override
    public Context getContext() {
        return PlayActivity.this;
    }

    @Override
    public void setCoverBitmap(Bitmap bitmap) {
        mAlbumCoverView.setImageBitmap(bitmap);
        if (MusicManager.getInstance().isPlaying()) {
            mAlbumCoverView.post(() -> mAlbumCoverView.start());
        }
    }

    @Override
    public void setCoverBackground(int color) {
        mLayoutPlay.setBackgroundColor(color);
//        ColorStateList stateList = new ColorStateList(new int[][]{new int[]{}}, new int[]{color});
//        mSeekPlay.setThumbTintList(stateList);
//        mSeekPlay.setProgressTintList(stateList);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RxBus.INSTANCE.dispose(this);
    }

    @OnClick({R.id.img_play_mode, R.id.img_play_previous, R.id.img_play_next, R.id.img_play_queue})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_play_mode:
                switchMode();
                break;
            case R.id.img_play_previous:
                MusicManager.getInstance().skipToPrevious();
                break;
            case R.id.img_play_next:
                MusicManager.getInstance().skipToNext();
                break;
            case R.id.img_play_queue:
//                View queueView = View.inflate(this, R.layout.layout_play_queue, null);
//                BottomSheetDialog dialog = new BottomSheetDialog(this);
//                dialog.setContentView(queueView);
//                dialog.show();
                PlayQueueBottomSheetFragment sheetFragment = new PlayQueueBottomSheetFragment();
                sheetFragment.show(getSupportFragmentManager(), sheetFragment.getTag());
                break;
        }
    }

    private void switchMode() {
        switch (SharePrefHelper.getRepeatMode(this)) {
            case MusicService.REPEAT_SINGLE_MODE:
                SharePrefHelper.setRepeatMode(this, MusicService.REPEAT_RANDOM_MODE);
                mImgPlayMode.setImageResource(R.drawable.ic_shuffle_black);
                break;
            case MusicService.REPEAT_ALL_MODE:
                SharePrefHelper.setRepeatMode(this, MusicService.REPEAT_SINGLE_MODE);
                mImgPlayMode.setImageResource(R.drawable.ic_repeat_one_black);
                break;
            case MusicService.REPEAT_RANDOM_MODE:
                SharePrefHelper.setRepeatMode(this, MusicService.REPEAT_ALL_MODE);
                mImgPlayMode.setImageResource(R.drawable.ic_repeat_black);
                break;
        }
    }
}
