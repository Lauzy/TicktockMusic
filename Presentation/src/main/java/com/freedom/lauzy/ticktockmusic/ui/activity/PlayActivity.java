package com.freedom.lauzy.ticktockmusic.ui.activity;


import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.freedom.lauzy.ticktockmusic.R;
import com.freedom.lauzy.ticktockmusic.base.BaseActivity;
import com.freedom.lauzy.ticktockmusic.function.RxBus;
import com.freedom.lauzy.ticktockmusic.model.SongEntity;
import com.freedom.lauzy.ticktockmusic.service.MusicManager;
import com.lauzy.freedom.librarys.imageload.ImageConfig;
import com.lauzy.freedom.librarys.imageload.ImageLoader;
import com.lauzy.freedom.librarys.view.util.PaletteColor;
import com.lauzy.freedom.librarys.widght.TickToolbar;
import com.lauzy.freedom.librarys.widght.music.AlbumCoverView;
import com.lauzy.freedom.librarys.widght.music.PlayPauseView;

import butterknife.BindView;
import io.reactivex.disposables.Disposable;


public class PlayActivity extends BaseActivity implements SeekBar.OnSeekBarChangeListener, PlayPauseView.PlayPauseListener {

    private static final String MUSIC_TITLE = "music_title";
    private static final String SINGER_NAME = "singer_name";
    @BindView(R.id.toolbar_common)
    TickToolbar mToolbarCommon;
//    @BindView(R.id.vp_album_cover)
//    ViewPager mVpAlbumCover;
    @BindView(R.id.acv_music)
    AlbumCoverView mAlbumCoverView;
    @BindView(R.id.txt_current_progress)
    TextView mTxtCurrentProgress;
    @BindView(R.id.seek_play)
    SeekBar mSeekPlay;
    @BindView(R.id.txt_total_length)
    TextView mTxtTotalLength;
    @BindView(R.id.img_play_mode)
    ImageView mImgPlayMode;
    @BindView(R.id.img_play_previous)
    ImageView mImgPlayPrevious;
    @BindView(R.id.play_pause)
    PlayPauseView mPlayPause;
    @BindView(R.id.img_play_next)
    ImageView mImgPlayNext;
    @BindView(R.id.img_play_queue)
    ImageView mImgPlayQueue;
    @BindView(R.id.layout_play)
    LinearLayout mLayoutPlay;

    public static Intent newInstance(Context context) {
        return new Intent(context, PlayActivity.class);
    }

    @Override
    protected void initInject() {

    }

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_play;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Disposable disposable = RxBus.INSTANCE.doStickySubscribe(SongEntity.class, this::setCurData);
        RxBus.INSTANCE.addDisposable(this, disposable);
    }

    @Override
    protected void initViews() {
        showBackIcon();
        mToolbarCommon.setBackgroundColor(Color.TRANSPARENT);
    }

    @Override
    protected void loadData() {
        setCurProgress((int) MusicManager.getInstance().getCurrentProgress(),
                MusicManager.getInstance().getDuration());
        setCurData(MusicManager.getInstance().getCurrentSong());
        mSeekPlay.setOnSeekBarChangeListener(this);
        mPlayPause.setPlayPauseListener(this);
    }

    private void setCurData(SongEntity songEntity) {
        mToolbarCommon.setTitle(songEntity.title);
        mToolbarCommon.setSubtitle(songEntity.artistName);
        mTxtTotalLength.setText(songEntity.songLength);
        mPlayPause.setPlaying(MusicManager.getInstance().isPlaying());
        MusicManager.getInstance().setSeekBarProgressListener(this::setCurProgress);
        ImageLoader.INSTANCE.display(this, new ImageConfig.Builder()
                .asBitmap(true)
                .url(songEntity.albumCover)
                .intoTarget(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {
                        PaletteColor.mainColorObservable(resource)
                                .subscribe(integer -> mLayoutPlay.setBackgroundColor(integer));
                        mAlbumCoverView.setBitmap(resource);
                        mAlbumCoverView.start();
                    }
                }).build());
    }

    private void setCurProgress(int progress, int duration) {
        mSeekPlay.setMax(duration);
        mSeekPlay.setProgress(progress);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        MusicManager.getInstance().seekTo(seekBar.getProgress());
    }

    @Override
    public void play() {
        MusicManager.getInstance().start();
    }

    @Override
    public void pause() {
        MusicManager.getInstance().pause();
    }
}
