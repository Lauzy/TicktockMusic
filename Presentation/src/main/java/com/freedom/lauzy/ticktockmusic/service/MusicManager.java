package com.freedom.lauzy.ticktockmusic.service;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.media.MediaPlayer;
import android.media.session.MediaController;
import android.media.session.PlaybackState;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.NonNull;

import com.freedom.lauzy.ticktockmusic.TicktockApplication;
import com.freedom.lauzy.ticktockmusic.model.SongEntity;
import com.lauzy.freedom.librarys.common.LogUtil;

import java.util.List;

/**
 * Desc : Manager
 * Author : Lauzy
 * Date : 2017/8/22
 * Blog : http://www.jianshu.com/u/e76853f863a9
 * Email : freedompaladin@gmail.com
 */
public class MusicManager {

    private MusicService mMusicService;
    private MediaController mMediaController;
    private final Handler mProgressHandler = new Handler();

    private static class SingleTon {
        private static final MusicManager INSTANCE = new MusicManager();
    }

    public static MusicManager getInstance() {
        return SingleTon.INSTANCE;
    }

    public void setSongEntities(List<SongEntity> songEntities) {
//        mSongEntities = songEntities;
        if (mMusicService != null) {
            mMusicService.setSongData(songEntities);
        } else {
            LogUtil.e("Manager", "NULL");
        }
        //TODO  播放列表数据库
    }

    private MusicService.MediaPlayerUpdateListener mUpdateListener = new MusicService.MediaPlayerUpdateListener() {
        @Override
        public void onCompletion(MediaPlayer mediaPlayer) {
            skipToNext();
        }

        @Override
        public void onBufferingUpdate(MediaPlayer mediaPlayer, int percent) {
            if (mMusicManageListener != null) {
                mMusicManageListener.onBufferingUpdate(mediaPlayer, percent);
            }
        }

        @Override
        public void onProgress(int progress, int duration) {
            if (mMusicManageListener != null) {
                mMusicManageListener.onProgress(progress, duration);
            }
        }

        @Override
        public void currentPlay(SongEntity songEntity) {
            if (mMusicManageListener != null) {
                mMusicManageListener.currentPlay(songEntity);
            }
        }

        @Override
        public void onResume() {
            if (mMusicManageListener != null) {
                mMusicManageListener.onResume();
            }
        }
    };

    private MediaController.Callback mMediaControllerCallback = new MediaController.Callback() {
        @Override
        public void onPlaybackStateChanged(@NonNull PlaybackState state) {
            super.onPlaybackStateChanged(state);
            switch (state.getState()) {
                case PlaybackState.STATE_NONE:
                    LogUtil.i("MusicManager", "NONE_STATE");
                    break;
                case PlaybackState.STATE_PLAYING:
                    LogUtil.i("MusicManager", "STATE_PLAYING");
                    break;
                case PlaybackState.STATE_PAUSED:
                    LogUtil.i("MusicManager", "STATE_PAUSED");
                    if (mMusicManageListener != null) {
                        mMusicManageListener.onPause();
                    }
                    break;
                case PlaybackState.STATE_BUFFERING:
                    break;
                case PlaybackState.STATE_CONNECTING:
                    break;
                case PlaybackState.STATE_ERROR:
                    break;
                case PlaybackState.STATE_FAST_FORWARDING:
                    break;
                case PlaybackState.STATE_REWINDING:
                    break;
                case PlaybackState.STATE_SKIPPING_TO_NEXT:
                    break;
                case PlaybackState.STATE_SKIPPING_TO_PREVIOUS:
                    break;
                case PlaybackState.STATE_SKIPPING_TO_QUEUE_ITEM:
                    break;
                case PlaybackState.STATE_STOPPED:
                    break;
            }
        }
    };

    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mMusicService = ((MusicService.ServiceBinder) service).getService();
            mMediaController = new MediaController(TicktockApplication.getInstance(),
                    mMusicService.getMediaSessionToken());
            mMusicService.setUpdateListener(mUpdateListener);
            mMediaController.registerCallback(mMediaControllerCallback);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    public void bindPlayService() {
        TicktockApplication context = TicktockApplication.getInstance();
        context.bindService(new Intent(context, MusicService.class), mConnection, Context.BIND_AUTO_CREATE);
    }

    public void unbindService() {
        if (null != mMusicService) {
            mMusicService.unbindService(mConnection);
        }
    }

    public void startService() {
        TicktockApplication context = TicktockApplication.getInstance();
        context.startService(new Intent(context, MusicService.class));
    }

    public void stopService() {
        TicktockApplication context = TicktockApplication.getInstance();
        context.stopService(new Intent(context, MusicService.class));
    }

    public SongEntity getCurrentSong() {
        if (mMusicService != null && mMusicService.getCurrentSong() != null) {
            return mMusicService.getCurrentSong();
        }
        return null;
    }

    public void play() {
        mMediaController.getTransportControls().play();
        mProgressHandler.post(mProgressRunnable);
    }

    public void start() {
        if (mMusicService != null) {
            mMusicService.start();
            mProgressHandler.post(mProgressRunnable);
        }
    }

    public void pause() {
        mMediaController.getTransportControls().pause();
        mProgressHandler.removeCallbacks(mProgressRunnable);
    }

    public void open(int position) {
        if (mMusicService != null && mMusicService.getCurrentPosition() != position) {
            mMusicService.setCurrentPosition(position);
            play();
        }
    }

    public void skipToNext() {
        if (mMediaController != null) {
            mMediaController.getTransportControls().skipToNext();
        }
    }

    public void skipToPrevious() {
        if (mMediaController != null) {
            mMediaController.getTransportControls().skipToPrevious();
        }
    }

    private int getMusicState() {
        return mMusicService.getPlaybackState().getState();
    }

    private Runnable mProgressRunnable = new Runnable() {
        @Override
        public void run() {
            if (getMusicState() == PlaybackState.STATE_PLAYING && mUpdateListener != null && getCurrentSong() != null) {
                mUpdateListener.onProgress((int) mMusicService.getCurrentProgress(), (int) getCurrentSong().duration);
            }
            mProgressHandler.postDelayed(this, 100);
        }
    };

    private MusicManageListener mMusicManageListener;

    public void setManageListener(MusicManageListener updateListener) {
        mMusicManageListener = updateListener;
    }

    public interface MusicManageListener {

        void onBufferingUpdate(MediaPlayer mediaPlayer, int percent);

        void onProgress(int progress, int duration);

        void currentPlay(SongEntity songEntity);

        void onPause();

        void onResume();//继续播放
    }
}
