
package com.freedom.lauzy.ticktockmusic;

interface IMusicInterface {
    void play();
    void pause();
    void playLast();
    void playNext();
    void stop();
    void seekTo(int progress);
}
