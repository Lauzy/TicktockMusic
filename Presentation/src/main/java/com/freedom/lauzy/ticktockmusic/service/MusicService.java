package com.freedom.lauzy.ticktockmusic.service;

import android.app.Service;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.AudioManager;
import android.media.MediaMetadata;
import android.media.MediaPlayer;
import android.media.session.MediaSession;
import android.media.session.PlaybackState;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.freedom.lauzy.ticktockmusic.function.DefaultDisposableObserver;
import com.freedom.lauzy.ticktockmusic.function.RxHelper;
import com.freedom.lauzy.ticktockmusic.model.SongEntity;
import com.freedom.lauzy.ticktockmusic.utils.SharePrefHelper;
import com.lauzy.freedom.data.database.BaseDao;
import com.lauzy.freedom.librarys.common.LogUtil;
import com.lauzy.freedom.librarys.common.ToastUtils;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import static android.media.session.PlaybackState.STATE_PAUSED;
import static android.media.session.PlaybackState.STATE_PLAYING;

/**
 * Desc : Music Service
 * Author : Lauzy
 * Date : 2017/8/17
 * Blog : http://www.jianshu.com/u/e76853f863a9
 * Email : freedompaladin@gmail.com
 */
@SuppressWarnings("WeakerAccess")
public class MusicService extends Service {

    private static final String TAG = "MusicService";
    public static final String SESSION_TAG = "com.freedom.lauzy.ticktockmusic";
    public static final String ACTION_START = "start";
    public static final String ACTION_PLAY = "play";
    public static final String ACTION_PAUSE = "pause";
    public static final String ACTION_NEXT = "next";
    public static final String ACTION_LAST = "last";
    public static final int REPEAT_ALL_MODE = 0;
    public static final int REPEAT_SINGLE_MODE = 1;
    public static final int REPEAT_RANDOM_MODE = 2;

    private PlaybackState mPlaybackState;
    private MediaSession mMediaSession;
    private MediaPlayer mMediaPlayer;
    private int mCurrentPosition = -1; //默认-1
    private List<SongEntity> mSongData;
    private TickNotification mTickNotification;
    private SongEntity mCurrentSong;
    private QueueManager mQueueManager;
    private int mDuration;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new ServiceBinder();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        setUpMedia();
        LogUtil.i(TAG, "onCreate");
        mTickNotification = new TickNotification(this);
    }

    private void setUpMedia() {
        mMediaPlayer = new MediaPlayer();
        mMediaSession = new MediaSession(this, MusicService.SESSION_TAG);
        mMediaSession.setCallback(mSessionCallback);//设置播放控制回调
        setState(PlaybackState.STATE_NONE);
        //设置可接受媒体控制
        mMediaSession.setFlags(MediaSession.FLAG_HANDLES_MEDIA_BUTTONS |
                MediaSession.FLAG_HANDLES_TRANSPORT_CONTROLS);
        // 设置音频流类型
        mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent != null && intent.getAction() != null) {
            LogUtil.i(TAG, "onStartCommand : " + intent.getAction());
            switch (intent.getAction()) {
                case ACTION_START:
                    if (mPlaybackState.getState() == STATE_PLAYING) {
                        start();
                        if (mUpdateListener != null) {
                            mUpdateListener.currentPlay(mSongData.get(mCurrentPosition));
                        }
                    }
                    break;
                case ACTION_PLAY:
                    start();
                    break;
                case ACTION_NEXT:
                    skipToNext();
                    break;
                case ACTION_LAST:
                    skipToPrevious();
                    break;
                case ACTION_PAUSE:
                    pause();
                    break;
            }
        }
//        return super.onStartCommand(intent, flags, startId);
        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mTickNotification.stopNotify(this);
    }

    /**
     * 播放音乐
     */
    private void play() {
        if (mSongData != null && mSongData.size() != 0) {
//                SongEntity entity = mSongData.get(mCurrentPosition);
            SongEntity entity = getPlaySong();//获取当前播放音乐
            if (getPlaybackState().getState() == PlaybackState.STATE_SKIPPING_TO_NEXT
                    || getPlaybackState().getState() == PlaybackState.STATE_SKIPPING_TO_PREVIOUS) {
                play(entity);//跳转状态直接重新播放
            } else {//其他状态判断重新播放还是继续播放
                if (!entity.equals(mCurrentSong)) {
                    play(entity);
                } else {
                    start();
                }
            }
        }
    }

    /**
     * 播放当前音乐
     *
     * @param entity 当前音乐
     */
    private void play(SongEntity entity) {
        if (entity.type.equals(BaseDao.QueueParam.LOCAL)) {//本地音乐
            //直接获取到音乐专辑图片的bitmap，便于更新通知
            MusicUtil.albumCoverObservable(this, entity)
                    .subscribe(bitmap -> playMusic(entity, bitmap));
        } else if (entity.type.equals(BaseDao.QueueParam.NET)) {//网络音乐
            //根据ID查询播放队列的数据获取当前SongEntity，根据此entity的url获取音乐图片的bitmap对象，
            //将entity和bitmap转化为NetMusicData对象，直接获取此对象的entity和bitmap即可，简化代码
            mQueueManager.netSongEntityObservable(entity.id)
                    .compose(RxHelper.ioMain())
                    .compose(MusicUtil.transformNetData(this))
                    .subscribeWith(new DefaultDisposableObserver<MusicUtil.NetMusicData>() {
                        @Override
                        public void onNext(@io.reactivex.annotations.NonNull MusicUtil.NetMusicData netMusicData) {
                            super.onNext(netMusicData);
                            playMusic(netMusicData.getSongEntity(), netMusicData.getBitmap());
                        }

                        @Override
                        public void onError(@io.reactivex.annotations.NonNull Throwable e) {
                            super.onError(e);
                            ToastUtils.showSingle(MusicService.this, "No Network");
                        }
                    });
        }
    }

    /**
     * 播放音乐
     *
     * @param entity music
     * @param bitmap 通知栏多媒体风格的图片
     */
    private void playMusic(SongEntity entity, Bitmap bitmap) {
        try {
            mCurrentSong = entity;
            mMediaPlayer.reset();
            mMediaPlayer.setDataSource(entity.path);
            setListeners();
            mMediaPlayer.prepareAsync();
            setState(PlaybackState.STATE_CONNECTING);
            mMediaSession.setMetadata(getMediaData(entity, bitmap));
            if (mUpdateListener != null) {
                mUpdateListener.currentPlay(entity);
            }
            //添加到播放队列
            mQueueManager.addRecentPlaySong(entity).subscribe();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setListeners() {
        mMediaPlayer.setOnPreparedListener(mp -> start());
        mMediaPlayer.setOnCompletionListener(mp -> {
            if (mUpdateListener != null) {
                mUpdateListener.onCompletion(mp);
            }
        });
        mMediaPlayer.setOnBufferingUpdateListener((mp, percent) -> {
            if (mUpdateListener != null) {
                mUpdateListener.onBufferingUpdate(mp, percent);
            }
        });
    }

    public void start() {
        mMediaPlayer.start();
        mDuration = mMediaPlayer.getDuration();
        setState(PlaybackState.STATE_PLAYING);
        mTickNotification.notifyPlay(this);
        if (mUpdateListener != null) {
            mUpdateListener.startPlay();
        }
    }

    private void pause() {
        if (mPlaybackState.getState() == STATE_PLAYING) {
            mMediaPlayer.pause();
            setState(STATE_PAUSED);
            mTickNotification.notifyPause(this);
        }
    }

    private void skipToNext() {
        if (mSongData != null && !mSongData.isEmpty()) {
            setState(PlaybackState.STATE_SKIPPING_TO_NEXT);
            switch (SharePrefHelper.getRepeatMode(this.getApplicationContext())) {
                case REPEAT_ALL_MODE:
                    if (mCurrentPosition < mSongData.size() - 1) {
                        mCurrentPosition++;
                    } else {
                        mCurrentPosition = 0;
                    }
                    break;
                case REPEAT_SINGLE_MODE:
                    break;
                case REPEAT_RANDOM_MODE://若随机数等于当前position，则置为0
                    int position = new Random().nextInt(mSongData.size());
                    mCurrentPosition = mCurrentPosition != position ? position : 0;
                    break;
            }
            play();
        }
    }

    private void skipToPrevious() {
        if (mSongData != null && !mSongData.isEmpty()) {
            setState(PlaybackState.STATE_SKIPPING_TO_PREVIOUS);
            switch (SharePrefHelper.getRepeatMode(this.getApplicationContext())) {
                case REPEAT_ALL_MODE:
                    if (mCurrentPosition > 0) {
                        mCurrentPosition--;
                    } else {
                        mCurrentPosition = mSongData.size() - 1;
                    }
                    break;
                case REPEAT_SINGLE_MODE:
                    break;
                case REPEAT_RANDOM_MODE:
                    int position = new Random().nextInt(mSongData.size());
                    mCurrentPosition = mCurrentPosition != position ? position : 0;
                    break;
            }
            play();
        }
    }

    private void seekTo(long pos) {
        if (mPlaybackState.getState() == STATE_PLAYING || mPlaybackState.getState() == STATE_PAUSED) {
            if (pos < 0) {
                pos = 0;
            } else if (pos > mMediaPlayer.getDuration()) {
                pos = mMediaPlayer.getDuration();
            }
            mMediaPlayer.seekTo((int) pos);
            start();
        }
    }

    public void stopPlayer() {
        mMediaPlayer.pause();
        mMediaPlayer.reset();
        mCurrentSong = null;//重置
        mTickNotification.stopNotify(this);
        setState(PlaybackState.STATE_STOPPED);
    }

    private void setState(int state) {
        mPlaybackState = new PlaybackState.Builder()
                .setActions(PlaybackState.ACTION_PLAY |
                        PlaybackState.ACTION_PAUSE |
                        PlaybackState.ACTION_PLAY_PAUSE |
                        PlaybackState.ACTION_SKIP_TO_NEXT |
                        PlaybackState.ACTION_SKIP_TO_PREVIOUS |
                        PlaybackState.ACTION_STOP |
                        PlaybackState.ACTION_PLAY_FROM_MEDIA_ID |
                        PlaybackState.ACTION_SEEK_TO)
                .setState(state, getCurrentProgress(), 1.0f)
                .build();
        mMediaSession.setPlaybackState(mPlaybackState);
        mMediaSession.setActive(state != PlaybackState.STATE_NONE && state != PlaybackState.STATE_STOPPED);
    }

    /**
     * 将当前播放的音乐SongEntity转化为MediaMetadata
     *
     * @param entity      当前播放音乐
     * @param albumBitmap 当前音乐的封面或专辑图片bitmap
     * @return MediaMetadata对象
     */
    private MediaMetadata getMediaData(SongEntity entity, Bitmap albumBitmap) {
        MediaMetadata.Builder builder = new MediaMetadata.Builder();
        builder.putString(MediaMetadata.METADATA_KEY_TITLE, entity.title)
                .putString(MediaMetadata.METADATA_KEY_ARTIST, entity.artistName)
                .putString(MediaMetadata.METADATA_KEY_ALBUM, entity.albumName)
                .putLong(MediaMetadata.METADATA_KEY_DURATION, entity.duration)
                .putBitmap(MediaMetadata.METADATA_KEY_ALBUM_ART, albumBitmap);
        return builder.build();
    }

    private MediaSession.Callback mSessionCallback = new MediaSession.Callback() {

        @Override
        public void onPlay() {
            super.onPlay();
            play();
        }

        @Override
        public void onPause() {
            super.onPause();
            pause();
        }

        @Override
        public void onSkipToNext() {
            super.onSkipToNext();
            skipToNext();
        }

        @Override
        public void onSkipToPrevious() {
            super.onSkipToPrevious();
            skipToPrevious();
        }

        @Override
        public void onStop() {
            super.onStop();
        }

        @Override
        public void onSeekTo(long pos) {
            super.onSeekTo(pos);
            seekTo(pos);
        }

        @Override
        public boolean onMediaButtonEvent(@NonNull Intent mediaButtonEvent) {
            return super.onMediaButtonEvent(mediaButtonEvent);
        }
    };

    /*------------- getter, setter and so on -------------*/

    public PlaybackState getPlaybackState() {
        return mPlaybackState;
    }

    public int getCurrentPosition() {
        return mCurrentPosition;
    }

    public void setCurrentPosition(int currentPosition) {
        mCurrentPosition = currentPosition;
    }

    public void setSongData(List<SongEntity> songData) {
        //pay attention
        if (mSongData != null) {
            mSongData.clear();
        }
        mSongData = songData;
    }

    public SongEntity getPlaySong() {
        if (mSongData != null && mSongData.size() != 0 && mCurrentPosition != -1) {
            return mSongData.get(mCurrentPosition);
        }
        return null;
    }

    public int getDuration() {
        return mDuration;
    }

    public List<SongEntity> getSongData() {
        return mSongData != null ? mSongData : Collections.emptyList();
    }

    public SongEntity getCurrentSong() {
        return mCurrentSong;
    }

    public long getCurrentProgress() {
        return mMediaPlayer != null && mMediaPlayer.isPlaying() || mMediaPlayer != null
                && getPlaybackState() != null && getPlaybackState().getState()
                == PlaybackState.STATE_PAUSED ? mMediaPlayer.getCurrentPosition() : 0;
    }

    public void setQueueManager(QueueManager queueManager) {
        mQueueManager = queueManager;
    }

    public MediaSession getMediaSession() {
        return mMediaSession;
    }

    public MediaPlayer getMediaPlayer() {
        return mMediaPlayer;
    }

    public MediaSession.Token getMediaSessionToken() {
        return mMediaSession.getSessionToken();
    }

    public class ServiceBinder extends Binder {
        public MusicService getService() {
            return MusicService.this;
        }
    }

    private MediaPlayerUpdateListener mUpdateListener;

    public void setUpdateListener(MediaPlayerUpdateListener updateListener) {
        mUpdateListener = updateListener;
    }

    interface MediaPlayerUpdateListener {
        void onCompletion(MediaPlayer mediaPlayer);

        void onBufferingUpdate(MediaPlayer mediaPlayer, int percent);

        void onProgress(int progress, int duration);

        void currentPlay(SongEntity songEntity);

        void startPlay();
    }
}
