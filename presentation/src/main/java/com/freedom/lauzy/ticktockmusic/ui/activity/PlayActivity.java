package com.freedom.lauzy.ticktockmusic.ui.activity;


import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.freedom.lauzy.ticktockmusic.R;
import com.freedom.lauzy.ticktockmusic.base.BaseActivity;
import com.freedom.lauzy.ticktockmusic.contract.PlayContract;
import com.freedom.lauzy.ticktockmusic.event.ChangeFavoriteItemEvent;
import com.freedom.lauzy.ticktockmusic.event.PlayModeEvent;
import com.freedom.lauzy.ticktockmusic.function.RxBus;
import com.freedom.lauzy.ticktockmusic.model.SongEntity;
import com.freedom.lauzy.ticktockmusic.presenter.PlayPresenter;
import com.freedom.lauzy.ticktockmusic.service.MusicManager;
import com.freedom.lauzy.ticktockmusic.service.MusicService;
import com.freedom.lauzy.ticktockmusic.ui.fragment.PlayQueueBottomSheetFragment;
import com.freedom.lauzy.ticktockmusic.utils.SharePrefHelper;
import com.freedom.lauzy.ticktockmusic.utils.ThemeHelper;
import com.lauzy.freedom.data.local.LocalUtil;
import com.lauzy.freedom.librarys.view.util.ScrimUtil;
import com.lauzy.freedom.librarys.widght.TickToolbar;
import com.lauzy.freedom.librarys.widght.music.PlayPauseView;
import com.lauzy.freedom.librarys.widght.music.lrc.Lrc;
import com.lauzy.freedom.librarys.widght.music.lrc.LrcView;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.disposables.Disposable;

/**
 * Desc : 播放界面
 * Author : Lauzy
 * Date : 2017/9/14
 * Blog : http://www.jianshu.com/u/e76853f863a9
 * Email : freedompaladin@gmail.com
 */
public class PlayActivity extends BaseActivity<PlayPresenter> implements
        SeekBar.OnSeekBarChangeListener, PlayPauseView.OnPlayPauseListener, PlayContract.View,
        MusicManager.PlayProgressListener {

    private static final String TAG = "PlayActivity";

    @BindView(R.id.img_play_previous)
    ImageView mImgPlayPrevious;
    @BindView(R.id.img_play_next)
    ImageView mImgPlayNext;
    @BindView(R.id.img_play_queue)
    ImageView mImgPlayQueue;
    @BindView(R.id.toolbar_common)
    TickToolbar mToolbarCommon;
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
    @BindView(R.id.img_favorite)
    ImageView mImgFavorite;
    @BindView(R.id.img_play_bg)
    ImageView mImageViewBg;
    @BindView(R.id.cl_play)
    CoordinatorLayout mClPlay;
    @BindView(R.id.iv_play)
    ImageView mIvPlay;
    @BindView(R.id.fl_play)
    FrameLayout mFlPlay;
    @BindView(R.id.lv_simple)
    LrcView mLvSimple;
    @BindView(R.id.lv_full)
    LrcView mLvFull;
    private boolean isDarkStyle = true;
    private boolean isFavorite;

    public static Intent newInstance(Context context) {
        return new Intent(context, PlayActivity.class);
    }

    @Override
    public Context getContext() {
        return PlayActivity.this;
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
        //订阅播放模式设置View
        Disposable playModeDisposable = RxBus.INSTANCE.doDefaultSubscribe(PlayModeEvent.class,
                playModeEvent -> setModeView());
        RxBus.INSTANCE.addDisposable(this, playModeDisposable);
    }

    @Override
    protected void initViews() {
        mIvPlay.setTransitionName(getString(R.string.play_view_transition_name));
        showBackIcon();
        setModeView();
        mToolbarCommon.setBackgroundColor(Color.TRANSPARENT);
    }

    /**
     * 设置播放模式
     */
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
        mImageViewBg.setVisibility(View.INVISIBLE);

        mPresenter.isFavoriteSong((int) MusicManager.getInstance().getCurrentSong().id);
        MusicManager.getInstance().setPlayProgressListener(this);
        mSeekPlay.setOnSeekBarChangeListener(this);
        mPlayPause.setOnPlayPauseListener(this);

        currentPlay(MusicManager.getInstance().getCurrentSong());
        mPresenter.loadLrc(MusicManager.getInstance().getCurrentSong());
    }

    @Override
    public void currentPlay(SongEntity songEntity) {
        if (songEntity == null) {
            return;
        }
        mToolbarCommon.setTitle(songEntity.title);
        mToolbarCommon.setSubtitle(songEntity.artistName);
        mTxtTotalLength.setText(songEntity.songLength);
        mPresenter.setCoverImgUrl(songEntity.albumCover);
        mPresenter.isFavoriteSong(songEntity.id);

        if (MusicManager.getInstance().isPlaying() && !mPlayPause.isPlaying()) {
            mPlayPause.playWithoutAnim();
        }
    }

    @Override
    public void onProgress(int progress, int duration) {
        setCurProgress(progress, duration);
    }

    @Override
    public void onPlayerPause() {
        if (mPlayPause.isPlaying()) {
            mPlayPause.pause();
        }
    }

    @Override
    public void onPlayerResume() {
        if (!mPlayPause.isPlaying()) {
            mPlayPause.play();
        }
    }

    @Override
    public void updateQueue(int position) {

    }

    private void setCurProgress(int progress, int duration) {
        mSeekPlay.setMax(duration);
        mSeekPlay.setProgress(progress);
        mLvSimple.updateTime(progress);
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
        if (MusicManager.getInstance().getCurrentSong() != null) {
            MusicManager.getInstance().start();
        }
    }

    @Override
    public void pause() {
        if (MusicManager.getInstance().getCurrentSong() != null) {
            MusicManager.getInstance().pause();
        }
    }


    @Override
    public void setCoverBackground(Bitmap background) {
        mImageViewBg.setImageBitmap(background);
//        mImageViewBg.setColorFilter(ContextCompat.getColor(this, R.color.colorDarkerTransparent),
//                PorterDuff.Mode.SRC_OVER);
    }

    @Override
    public void addFavoriteSong() {
        setImageTint();
    }

    @Override
    public void deleteFavoriteSong() {
        mImgFavorite.setImageResource(isDarkStyle ? R.drawable.ic_favorite_border_white : R.drawable.ic_favorite_border_black);
    }

    @Override
    public void isFavoriteSong(boolean favorite) {
        isFavorite = favorite;
        mImgFavorite.setImageResource(favorite ? R.drawable.ic_favorite_white : (isDarkStyle
                ? R.drawable.ic_favorite_border_white : R.drawable.ic_favorite_border_black));
        if (favorite) {
            setImageTint();
        }
    }

    @Override
    public void setViewBgColor(int paletteColor) {
        mClPlay.setBackgroundColor(paletteColor);
        Drawable drawable = ScrimUtil.makeCubicGradientScrimDrawable(paletteColor, 8, Gravity.BOTTOM);
        mFlPlay.setForeground(drawable);
    }

    @Override
    public void showLightViews() {
        isDarkStyle = true;
        setViewsColor(Color.WHITE);
    }

    @Override
    public void showDarkViews() {
        isDarkStyle = false;
        setViewsColor(Color.BLACK);
    }

    @Override
    public void startDownloadLrc() {
        mLvSimple.setDefaultContent("start loading");
    }

    @Override
    public void downloadLrcSuccess(List<Lrc> lrcs) {
        if (lrcs == null || lrcs.isEmpty()) {
            mLvSimple.setDefaultContent("no lrc for now");
            return;
        }
        mLvSimple.setCurrentPlayLineColor(ThemeHelper.getThemeColorResId(this));
        mLvSimple.setLrcData(lrcs);
    }

    @Override
    public void downloadFailed(Throwable e) {
        mLvSimple.setDefaultContent("loading failed");
    }

    @Override
    public void setPlayView(Bitmap resource) {
        mIvPlay.setImageBitmap(resource);
    }

    private void setViewsColor(int color) {
        mSeekPlay.getProgressDrawable().setTint(color);
        mSeekPlay.getThumb().setTint(color);
        mImgPlayPrevious.getDrawable().setTint(color);
        mImgPlayNext.getDrawable().setTint(color);
        mImgPlayMode.getDrawable().setTint(color);
        mImgPlayQueue.getDrawable().setTint(color);
        mTxtCurrentProgress.setTextColor(color);
        mTxtTotalLength.setTextColor(color);
        mPlayPause.setBtnColor(color);
        mToolbarCommon.setTitleTextColor(color);
        mToolbarCommon.setSubtitleTextColor(color);
        Drawable navigationIcon = mToolbarCommon.getNavigationIcon();
        if (navigationIcon != null) {
            navigationIcon.setTint(color);
        }
    }

    /**
     * 设置喜欢图标
     */
    private void setImageTint() {
        Drawable drawable = ContextCompat.getDrawable(this, R.drawable.ic_favorite_white);
        DrawableCompat.setTint(drawable, ContextCompat.getColor(this, R.color.red_trans));
        mImgFavorite.setImageDrawable(drawable);
    }

    @OnClick({R.id.img_play_mode, R.id.img_play_previous, R.id.img_play_next, R.id.img_play_queue,
            R.id.img_favorite})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_play_mode:
                switchMode();
                break;
            case R.id.img_play_previous:
                playPrevious();
                break;
            case R.id.img_play_next:
                playNext();
                break;
            case R.id.img_play_queue:
                PlayQueueBottomSheetFragment sheetFragment = new PlayQueueBottomSheetFragment();
                sheetFragment.show(getSupportFragmentManager(), sheetFragment.getTag());
                break;
            case R.id.img_favorite:
                addOrDeleteFavoriteSong();
                break;
        }
    }

    private void playNext() {
        MusicManager.getInstance().skipToNext();
        refreshSongData();
    }

    private void playPrevious() {
        MusicManager.getInstance().skipToPrevious();
        refreshSongData();
    }

    /**
     * 切换歌曲时刷新喜欢图标
     */
    private void refreshSongData() {
        new Handler().postDelayed(() -> {
            mPresenter.isFavoriteSong(MusicManager.getInstance().getCurrentSong().id);
            mPresenter.loadLrc(MusicManager.getInstance().getCurrentSong());
        }, 50);
    }

    /**
     * 添加删除喜欢歌曲
     */
    private void addOrDeleteFavoriteSong() {
        if (!isFavorite) {
            mPresenter.addFavoriteSong(MusicManager.getInstance().getCurrentSong());
        } else {
            mPresenter.deleteFavoriteSong(MusicManager.getInstance().getCurrentSong().id);
        }
        isFavorite = !isFavorite;
        //若Navigation目录为喜欢的歌曲，则发送事件，更新喜欢列表
        RxBus.INSTANCE.post(new ChangeFavoriteItemEvent());
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
        mImgPlayMode.getDrawable().setTint(isDarkStyle ? Color.WHITE : Color.BLACK);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RxBus.INSTANCE.dispose(this);
    }
}
