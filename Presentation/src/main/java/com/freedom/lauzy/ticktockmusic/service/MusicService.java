package com.freedom.lauzy.ticktockmusic.service;

import android.app.Service;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaMetadata;
import android.media.MediaPlayer;
import android.media.session.MediaController;
import android.media.session.MediaSession;
import android.media.session.PlaybackState;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.freedom.lauzy.ticktockmusic.model.SongEntity;

import java.io.IOException;
import java.util.List;

import static android.media.session.PlaybackState.STATE_NONE;
import static android.media.session.PlaybackState.STATE_PAUSED;
import static android.media.session.PlaybackState.STATE_PLAYING;

/**
 * Desc : Music Service
 * Author : Lauzy
 * Date : 2017/8/17
 * Blog : http://www.jianshu.com/u/e76853f863a9
 * Email : freedompaladin@gmail.com
 */
public class MusicService extends Service {

    public static final String SESSION_TAG = "ticktock";
    public static final String ACTION_PLAY = "play";
    public static final String ACTION_PAUSE = "pause";
    public static final String ACTION_NEXT = "next";
    public static final String ACTION_LAST = "last";
    public static final String PARAM_TRACK_URI = "uri";
    public static final String PLAY_DATA = "data";

    private PlaybackState mPlaybackState;
    private MediaSession mMediaSession;
    private MediaController mMediaController;
    private MediaPlayer mMediaPlayer;
    private int mPosition = 0;
    private List<SongEntity> mSongData;

    public void setSongData(List<SongEntity> songData) {
        mSongData = songData;
    }

    public MediaController getMediaController() {
        return mMediaController;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new ServiceBinder();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        setUpMedia();
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
        mMediaPlayer.setOnPreparedListener(mp -> {
            mp.start();
            setState(STATE_PLAYING);
        });
        mMediaPlayer.setOnBufferingUpdateListener((mp, percent) -> {

        });
        mMediaController = new MediaController(this, mMediaSession.getSessionToken());
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
                        PlaybackState.ACTION_PLAY_FROM_SEARCH |
                        PlaybackState.ACTION_SKIP_TO_QUEUE_ITEM |
                        PlaybackState.ACTION_SEEK_TO)
                .setState(state, PlaybackState.PLAYBACK_POSITION_UNKNOWN, 1.0f)
                .build();
        mMediaSession.setPlaybackState(mPlaybackState);
        mMediaSession.setActive(state != PlaybackState.STATE_NONE && state != PlaybackState.STATE_STOPPED);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent != null && intent.getAction() != null) {
            switch (intent.getAction()) {
                case ACTION_PLAY:
                    break;
                case ACTION_NEXT:
                    break;
                case ACTION_LAST:
                    break;
                case ACTION_PAUSE:
                    break;
            }
        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }


    private void play() {
        SongEntity entity = mSongData.get(mPosition);
        int state = mPlaybackState.getState();
        if (state == STATE_PLAYING || state == STATE_PAUSED || state == STATE_NONE) {
            try {
                mMediaPlayer.reset();
                mMediaPlayer.setDataSource(entity.path);
                mMediaPlayer.prepareAsync();
                setState(PlaybackState.STATE_CONNECTING);
                mMediaSession.setMetadata(getMediaData(entity));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private MediaMetadata getMediaData(SongEntity entity) {
        MediaMetadata.Builder builder = new MediaMetadata.Builder();
        builder.putString(MediaMetadata.METADATA_KEY_TITLE, entity.title)
                .putString(MediaMetadata.METADATA_KEY_ARTIST, entity.artistName)
                .putString(MediaMetadata.METADATA_KEY_ALBUM, entity.albumName)
                .putLong(MediaMetadata.METADATA_KEY_DURATION, entity.duration)
                .putString(MediaMetadata.METADATA_KEY_ALBUM_ART_URI, String.valueOf(entity.albumCover));
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
        }

        @Override
        public void onSkipToNext() {
            super.onSkipToNext();
        }

        @Override
        public void onSkipToPrevious() {
            super.onSkipToPrevious();
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

    public MediaSession.Token getMediaSessionToken() {
        return mMediaSession.getSessionToken();
    }

    public class ServiceBinder extends Binder {
        public MusicService getService() {
            return MusicService.this;
        }
    }
}
