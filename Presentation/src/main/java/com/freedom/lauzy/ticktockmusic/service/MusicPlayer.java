package com.freedom.lauzy.ticktockmusic.service;

import android.content.Context;
import android.os.RemoteException;

import com.freedom.lauzy.ticktockmusic.IMusicInterface;

/**
 * Desc : 播放工具类
 * Author : Lauzy
 * Date : 2017/8/17
 * Blog : http://www.jianshu.com/u/e76853f863a9
 * Email : freedompaladin@gmail.com
 */
public class MusicPlayer {
    private static IMusicInterface mMusicService;

    public static void bindService(Context context){

    }

    public static void play(){
        try {
            mMusicService.play();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public static void pause(){
        try {
            mMusicService.pause();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}
