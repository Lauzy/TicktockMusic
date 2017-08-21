package com.freedom.lauzy.ticktockmusic.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.RemoteException;
import android.support.v4.app.NotificationCompat;
import android.support.v4.media.MediaMetadataCompat;
import android.support.v4.media.session.MediaControllerCompat;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;

import com.freedom.lauzy.ticktockmusic.R;
import com.freedom.lauzy.ticktockmusic.model.SongEntity;

import java.io.IOException;
import java.util.List;
import java.util.Timer;

/**
 * Desc : 播放工具类
 * Author : Lauzy
 * Date : 2017/8/17
 * Blog : http://www.jianshu.com/u/e76853f863a9
 * Email : freedompaladin@gmail.com
 */
public class MusicPlayerHelper implements MediaPlayer.OnPreparedListener,
        MediaPlayer.OnBufferingUpdateListener {
    public MediaPlayer mMediaPlayer;
    private PlaybackStateCompat mPlaybackState;
    private MediaSessionCompat mMediaSession;
    private MediaControllerCompat mMediaController;
    private MediaPlayerUpdateCallBack mPlayerUpdateCallBack;
    private Context mContext;
    private Timer mTimer = new Timer(); // 计时器
    private List<SongEntity> mSongData;
    private int mPosition;

    public MusicPlayerHelper(Context context) {
        mContext = context;
        initService(context);
    }


    private MediaSessionCompat.Callback mMediaSessionCallback = new MediaSessionCompat.Callback() {

        @Override
        public void onPlayFromSearch(String query, Bundle extras) {
//            Uri uri = extras.getParcelable(AudioPlayerService.PARAM_TRACK_URI);
//            onPlayFromUri(uri, null);
            onPlayFromUri(null, null);
        }

        //播放网络歌曲
        //就是activity和notification的播放的回调方法。都会走到这里进行加载网络音频
        @Override
        public void onPlayFromUri(Uri uri, Bundle extras) {
            SongEntity entity = mSongData.get(mPosition);
            try {
                switch (mPlaybackState.getState()) {
                    case PlaybackStateCompat.STATE_PLAYING:
                    case PlaybackStateCompat.STATE_PAUSED:
                    case PlaybackStateCompat.STATE_NONE:
                        MediaPlayerReset();
                        //设置播放地址
                        mMediaPlayer.setDataSource(entity.path);
                        //异步进行播放
                        mMediaPlayer.prepareAsync();
                        //设置当前状态为连接中
                        mPlaybackState = new PlaybackStateCompat.Builder()
                                .setState(PlaybackStateCompat.STATE_CONNECTING, 0, 1.0f)
                                .build();
                        //告诉MediaSession当前最新的音频状态。
                        mMediaSession.setPlaybackState(mPlaybackState);
                        //设置音频信息；
                        mMediaSession.setMetadata(getMusicEntity(entity.title,
                                entity.artistName, entity.albumName));
                        break;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onPause() {
            switch (mPlaybackState.getState()) {
                case PlaybackStateCompat.STATE_PLAYING:
                    mMediaPlayer.pause();
                    mPlaybackState = new PlaybackStateCompat.Builder()
                            .setState(PlaybackStateCompat.STATE_PAUSED, 0, 1.0f)
                            .build();
                    mMediaSession.setPlaybackState(mPlaybackState);
//                    updateNotification();
                    break;

            }
        }

        @Override
        public void onPlay() {
            switch (mPlaybackState.getState()) {
                case PlaybackStateCompat.STATE_PAUSED:
                    mMediaPlayer.start();
                    mPlaybackState = new PlaybackStateCompat.Builder()
                            .setState(PlaybackStateCompat.STATE_PLAYING, 0, 1.0f)
                            .build();
                    mMediaSession.setPlaybackState(mPlaybackState);
//                    updateNotification();
                    break;
            }
        }

        //下一曲
        @Override
        public void onSkipToNext() {
            super.onSkipToNext();
            if (mPosition < mSongData.size() - 1)
                mPosition++;
            else
                mPosition = 0;
            onPlayFromUri(null, null);

        }

        //上一曲
        @Override
        public void onSkipToPrevious() {
            super.onSkipToPrevious();
            if (mPosition > 0)
                mPosition--;
            else
                mPosition = mSongData.size() - 1;
            onPlayFromUri(null, null);
        }
    };

    /**
     * 重置player
     */
    private void MediaPlayerReset() {
        mMediaPlayer.reset();
    }

    /**
     * 初始化各服务
     *
     * @param context
     */
    private void initService(Context context) {

        //传递播放的状态信息
        mPlaybackState = new PlaybackStateCompat.Builder().
                setState(PlaybackStateCompat.STATE_NONE, 0, 1.0f)
                .build();

        //初始化MediaSessionCompat
        mMediaSession = new MediaSessionCompat(context, MusicService.SESSION_TAG);
        mMediaSession.setCallback(mMediaSessionCallback);//设置播放控制回调
        //设置可接受媒体控制
        mMediaSession.setActive(true);
        mMediaSession.setFlags(MediaSessionCompat.FLAG_HANDLES_MEDIA_BUTTONS |
                MediaSessionCompat.FLAG_HANDLES_TRANSPORT_CONTROLS);
        mMediaSession.setPlaybackState(mPlaybackState);//状态回调

        //初始化MediaPlayer
        mMediaPlayer = new MediaPlayer();
        // 设置音频流类型
        mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mMediaPlayer.setOnPreparedListener(this);
        mMediaPlayer.setOnBufferingUpdateListener(this);

        // 初始化MediaController
        try {
            mMediaController = new MediaControllerCompat(context, mMediaSession.getSessionToken());
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    //播放前的准备动作回调
    @Override
    public void onPrepared(MediaPlayer mediaPlayer) {
        mMediaPlayer.start();
        mPlaybackState = new PlaybackStateCompat.Builder()
                .setState(PlaybackStateCompat.STATE_PLAYING, 0, 1.0f)
                .build();
        mMediaSession.setPlaybackState(mPlaybackState);
//        updateNotification();
        if (mPlayerUpdateCallBack != null)
            mPlayerUpdateCallBack.onPrepared(mediaPlayer);
    }

    //缓冲更新
    @Override
    public void onBufferingUpdate(MediaPlayer mediaPlayer, int percent) {
        if (mPlayerUpdateCallBack != null)
            mPlayerUpdateCallBack.onBufferingUpdate(mediaPlayer, percent);
    }

    public void destroyService() {
        if (mMediaPlayer != null) {
            mMediaPlayer.release();
            mMediaPlayer = null;
        }
        if (mMediaSession != null) {
            mMediaSession.release();
            mMediaSession = null;
        }
    }

    private NotificationCompat.Action createAction(int iconResId, String title, String action) {
        Intent intent = new Intent(mContext, MusicService.class);
        intent.setAction(action);
        PendingIntent pendingIntent = PendingIntent.getService(mContext, 1, intent, 0);
        return new NotificationCompat.Action(iconResId, title, pendingIntent);
    }

    /**
     * 更新通知栏
     */
    private void updateNotification() {
        /*NotificationCompat.Action playPauseAction = mPlaybackState.getState() == PlaybackStateCompat.STATE_PLAYING ?
                createAction(R.drawable.img_pause, "Pause", AudioPlayerService.ACTION_PAUSE) :
                createAction(R.drawable.img_play, "Play", AudioPlayerService.ACTION_PLAY);*/
        NotificationCompat.Action playPauseAction = null;
        NotificationCompat.Builder notificationCompat = new NotificationCompat.Builder(mContext)
//                .setContentTitle(list_data.get(last_index).getMusicTitle())
//                .setContentText(list_data.get(last_index).getSinger())
                .setOngoing(mPlaybackState.getState() == PlaybackStateCompat.STATE_PLAYING)
                .setShowWhen(false)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setAutoCancel(false)
//                .addAction(createAction(R.drawable.img_last_one_music, "last", AudioPlayerService.ACTION_LAST))
                .addAction(playPauseAction)
//                .addAction(createAction(R.drawable.img_next_music, "next", AudioPlayerService.ACTION_NEXT))
                .setStyle(new android.support.v7.app.NotificationCompat.MediaStyle()
                        .setMediaSession(mMediaSession.getSessionToken())
                        .setShowActionsInCompactView(1, 2));
        Notification notification = notificationCompat.build();
        ((NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE)).notify(1, notification);
        //播放新歌曲时需要更新seekbar的max与总秒数对应。保证每一秒seekbar会走动一格。回传到View层来更新时间
    }

    public int getProgress() {
        return mMediaPlayer.getCurrentPosition();
    }


    public MediaControllerCompat getMediaController() {
        return mMediaController;
    }

    public MediaSessionCompat.Token getMediaSessionToken() {
        return mMediaSession.getSessionToken();
    }

    public MediaPlayer getMediaPlayer() {
        return mMediaPlayer;
    }

    public void setMediaPlayerUpdateListener(MediaPlayerUpdateCallBack listener) {
        mPlayerUpdateCallBack = listener;
    }

    public void setPlayeData(List<SongEntity> songData) {
        mSongData = songData;
    }

    /**
     * 设置通知(mediasession歌曲)信息
     */
    private MediaMetadataCompat getMusicEntity(String musicName, String Singer, String album) {
        MediaMetadataCompat mediaMetadataCompat = new MediaMetadataCompat.Builder().build();
        mediaMetadataCompat.getBundle().putString(MediaMetadataCompat.METADATA_KEY_AUTHOR, Singer);//歌手
        mediaMetadataCompat.getBundle().putString(MediaMetadataCompat.METADATA_KEY_ALBUM, album);//专辑
        mediaMetadataCompat.getBundle().putString(MediaMetadataCompat.METADATA_KEY_TITLE, musicName);//title
        return mediaMetadataCompat;
    }


    public interface MediaPlayerUpdateCallBack {

        void onBufferingUpdate(MediaPlayer mediaPlayer, int percent);

        void onPrepared(MediaPlayer mediaPlayer);
    }
}
