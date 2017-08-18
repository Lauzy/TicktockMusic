package com.freedom.lauzy.ticktockmusic.service;

import com.freedom.lauzy.ticktockmusic.model.SongEntity;

/**
 * Desc : 声明AIDL的方法，方便MusicPlayer类控制
 * Author : Lauzy
 * Date : 2017/8/18
 * Blog : http://www.jianshu.com/u/e76853f863a9
 * Email : freedompaladin@gmail.com
 */
public interface IMusicPlayerInterface {
    void play(SongEntity songEntity);
    void pause();
    void playLast();
    void playNext();
    void stop();
    void seekTo(int progress);
}
