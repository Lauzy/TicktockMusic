package com.freedom.lauzy.ticktockmusic.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.freedom.lauzy.ticktockmusic.IMusicInterface;
import com.freedom.lauzy.ticktockmusic.model.SongEntity;

import java.io.IOException;

/**
 * Desc : Music Service
 * Author : Lauzy
 * Date : 2017/8/17
 * Blog : http://www.jianshu.com/u/e76853f863a9
 * Email : freedompaladin@gmail.com
 */
public class MusicService extends Service implements IMusicPlayerInterface {

    private MediaPlayer mMediaPlayer;
    private AudioManager mAudioManager;
    private SongEntity mSongEntity;

    public SongEntity getSongEntity() {
        return mSongEntity;
    }

    public long getProgress() {
        return mMediaPlayer.getCurrentPosition();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        if (mMediaPlayer == null) {
            mMediaPlayer = new MediaPlayer();
        }
        return new MusicBinder();
    }

    @Override
    public boolean onUnbind(Intent intent) {
        if (mMediaPlayer != null) {
            mMediaPlayer.release();
        }
        return super.onUnbind(intent);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_NOT_STICKY;
    }

    @Override
    public void play(SongEntity entity) {
        mSongEntity = entity;
        try {
            mMediaPlayer.reset();
            mMediaPlayer.setDataSource(entity.path);
            mMediaPlayer.prepareAsync();
            mMediaPlayer.setOnPreparedListener(mp -> startPlay());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void startPlay() {
        mMediaPlayer.start();
        /*if (mMediaPlayer.isPlaying()) {
            mAudioManager.requestAudioFocus(new AudioManager.OnAudioFocusChangeListener() {
                @Override
                public void onAudioFocusChange(int focusChange) {

                }
            }, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN);
        }*/
    }

    @Override
    public void pause() {
        Toast.makeText(this, "Pause", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void playLast() {

    }

    @Override
    public void playNext() {

    }

    @Override
    public void stop() {

    }

    @Override
    public void seekTo(int progress) {

    }

    private class MusicBinder extends IMusicInterface.Stub {

        @Override
        public void playPos(int position) throws RemoteException {

        }

        @Override
        public void play(SongEntity songEntity) throws RemoteException {
            MusicService.this.play(songEntity);
        }

        @Override
        public void pause() throws RemoteException {
            MusicService.this.pause();
        }

        @Override
        public void playLast() throws RemoteException {
            MusicService.this.playLast();
        }

        @Override
        public void playNext() throws RemoteException {
            MusicService.this.playNext();
        }

        @Override
        public void stop() throws RemoteException {
            MusicService.this.stop();
        }

        @Override
        public void seekTo(int progress) throws RemoteException {
            MusicService.this.seekTo(progress);
        }
    }
}
