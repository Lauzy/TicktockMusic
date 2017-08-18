package com.freedom.lauzy.ticktockmusic.service;

import android.os.RemoteException;

import com.freedom.lauzy.ticktockmusic.IMusicInterface;
import com.freedom.lauzy.ticktockmusic.model.SongEntity;

/**
 * Desc : 播放工具类
 * Author : Lauzy
 * Date : 2017/8/17
 * Blog : http://www.jianshu.com/u/e76853f863a9
 * Email : freedompaladin@gmail.com
 */
public class MusicPlayer implements IMusicPlayerInterface {
    public static IMusicInterface sMusicService;

    private static class Singleton {
        private static MusicPlayer INSTANCE = new MusicPlayer();
    }

    public static MusicPlayer getInstance() {
        return Singleton.INSTANCE;
    }

    @Override
    public void play(SongEntity songEntity) {
        try {
            sMusicService.play(songEntity);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void pause() {
        try {
            sMusicService.pause();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void playLast() {
        try {
            sMusicService.playLast();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void playNext() {
        try {
            sMusicService.playNext();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void stop() {
        try {
            sMusicService.stop();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void seekTo(int progress) {
        try {
            sMusicService.seekTo(progress);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}
