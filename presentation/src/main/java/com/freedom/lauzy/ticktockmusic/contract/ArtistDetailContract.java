package com.freedom.lauzy.ticktockmusic.contract;

/**
 * Desc : 歌手详情
 * Author : Lauzy
 * Date : 2018/4/3
 * Blog : http://www.jianshu.com/u/e76853f863a9
 * Email : freedompaladin@gmail.com
 */
public interface ArtistDetailContract {
    interface Presenter {
        String getArtistAvatarUrl(String artistName);
    }
}
