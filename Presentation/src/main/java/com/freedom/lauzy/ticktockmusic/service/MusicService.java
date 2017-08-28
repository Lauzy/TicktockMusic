package com.freedom.lauzy.ticktockmusic.service;

import android.app.Service;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.media.MediaMetadata;
import android.media.MediaPlayer;
import android.media.session.MediaSession;
import android.media.session.PlaybackState;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;

import com.freedom.lauzy.ticktockmusic.R;
import com.freedom.lauzy.ticktockmusic.model.SongEntity;
import com.lauzy.freedom.data.local.LocalUtil;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static android.media.session.PlaybackState.STATE_PLAYING;

/**
 * Desc : Music Service
 * Author : Lauzy
 * Date : 2017/8/17
 * Blog : http://www.jianshu.com/u/e76853f863a9
 * Email : freedompaladin@gmail.com
 */
public class MusicService extends Service {

    public static final String SESSION_TAG = "com.freedom.lauzy.ticktockmusic";
    public static final String ACTION_PLAY = "play";
    public static final String ACTION_PAUSE = "pause";
    public static final String ACTION_NEXT = "next";
    public static final String ACTION_LAST = "last";

    private PlaybackState mPlaybackState;
    private MediaSession mMediaSession;
    private MediaPlayer mMediaPlayer;
    private int mCurrentPosition;
    private List<SongEntity> mSongData;
    private TickNotification mTickNotification;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new ServiceBinder();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        setUpMedia();
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

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent != null && intent.getAction() != null) {
            switch (intent.getAction()) {
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

    private void play() {
//        int state = mPlaybackState.getState();
        try {
            SongEntity entity = mSongData.get(mCurrentPosition);
            mMediaPlayer.reset();
            mMediaPlayer.setDataSource(entity.path);
            mMediaPlayer.prepareAsync();
            setState(PlaybackState.STATE_CONNECTING);
            mMediaSession.setMetadata(getMediaData(entity));
            if (mUpdateListener != null) {
                mUpdateListener.currentPlay(entity);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void start() {
        mMediaPlayer.start();
        setState(PlaybackState.STATE_PLAYING);
        mTickNotification.notifyPlay(this);
    }

    private void pause() {
        if (mPlaybackState.getState() == STATE_PLAYING) {
            mMediaPlayer.pause();
            setState(PlaybackState.STATE_PAUSED);
            mTickNotification.notifyPause(this);
        }
    }

    private void skipToNext() {
        if (mSongData != null && !mSongData.isEmpty()) {
            if (mCurrentPosition < mSongData.size() - 1) {
                mCurrentPosition++;
            } else {
                mCurrentPosition = 0;
            }
            setState(PlaybackState.STATE_SKIPPING_TO_NEXT);
            play();
        }
    }

    private void skipToPrevious() {
        if (mSongData != null && !mSongData.isEmpty()) {
            if (mCurrentPosition > 0) {
                mCurrentPosition--;
            } else {
                mCurrentPosition = mSongData.size() - 1;
            }
            setState(PlaybackState.STATE_SKIPPING_TO_PREVIOUS);
            play();
        }
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

    private MediaMetadata getMediaData(SongEntity entity) {
        MediaMetadata.Builder builder = new MediaMetadata.Builder();
        builder.putString(MediaMetadata.METADATA_KEY_TITLE, entity.title)
                .putString(MediaMetadata.METADATA_KEY_ARTIST, entity.artistName)
                .putString(MediaMetadata.METADATA_KEY_ALBUM, entity.albumName)
                .putLong(MediaMetadata.METADATA_KEY_DURATION, entity.duration)
                .putString(MediaMetadata.METADATA_KEY_ALBUM_ART_URI, String.valueOf(entity.albumCover));
        String coverUri = LocalUtil.getCoverUri(this, entity.albumId);
        Bitmap bitmap;
        if (coverUri != null && new File(coverUri).exists()) {
            bitmap = BitmapFactory.decodeFile(coverUri);
        } else {
            Drawable drawable = ContextCompat.getDrawable(this, R.drawable.ic_default);
            bitmap = ((BitmapDrawable) drawable).getBitmap();
        }
        builder.putBitmap(MediaMetadata.METADATA_KEY_ALBUM_ART, bitmap);
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
        mSongData = songData;
    }

    public SongEntity getCurrentSong() {
        if (mSongData != null) {
            return mSongData.get(mCurrentPosition);
        }
        return null;
    }

    public long getCurrentProgress() {
        return mMediaPlayer != null ? mMediaPlayer.getCurrentPosition() : 0;
    }

    public MediaSession getMediaSession() {
        return mMediaSession;
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
    }
}
