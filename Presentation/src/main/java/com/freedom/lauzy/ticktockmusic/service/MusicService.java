package com.freedom.lauzy.ticktockmusic.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;

import com.freedom.lauzy.ticktockmusic.IMusicInterface;

/**
 * Desc : Music Service
 * Author : Lauzy
 * Date : 2017/8/17
 * Blog : http://www.jianshu.com/u/e76853f863a9
 * Email : freedompaladin@gmail.com
 */
public class MusicService extends Service {

    private IBinder mIBinder = new IMusicInterface.Stub() {
        @Override
        public void play() throws RemoteException {
        }

        @Override
        public void pause() throws RemoteException {

        }

        @Override
        public void playLast() throws RemoteException {

        }

        @Override
        public void playNext() throws RemoteException {

        }

        @Override
        public void stop() throws RemoteException {

        }

        @Override
        public void seekTo(int progress) throws RemoteException {

        }
    };

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mIBinder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }
}
