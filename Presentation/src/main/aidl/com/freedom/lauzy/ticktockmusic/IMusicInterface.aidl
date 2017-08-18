package com.freedom.lauzy.ticktockmusic;

import com.freedom.lauzy.ticktockmusic.model.SongEntity;

interface IMusicInterface {
    void playPos(int position);
    void play(in SongEntity songEntity);
    void pause();
    void playLast();
    void playNext();
    void stop();
    void seekTo(int progress);
}
