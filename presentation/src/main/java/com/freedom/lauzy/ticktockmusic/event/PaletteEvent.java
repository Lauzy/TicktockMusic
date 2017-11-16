package com.freedom.lauzy.ticktockmusic.event;

/**
 * Desc : 取色Event
 * Author : Lauzy
 * Date : 2017/8/17
 * Blog : http://www.jianshu.com/u/e76853f863a9
 * Email : freedompaladin@gmail.com
 */
public class PaletteEvent {

    private int mColor;

    public PaletteEvent(int color) {
        mColor = color;
    }

    public int getColor() {
        return mColor;
    }
}
