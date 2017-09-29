package com.freedom.lauzy.ticktockmusic.contract;

import com.freedom.lauzy.ticktockmusic.base.IBaseView;
import com.freedom.lauzy.ticktockmusic.model.SongEntity;

import java.util.List;

/**
 * Desc : 歌手音乐接口
 * Author : Lauzy
 * Date : 2017/9/29
 * Blog : http://www.jianshu.com/u/e76853f863a9
 * Email : freedompaladin@gmail.com
 */
public interface ArtistSongContract {
    interface Presenter {
        void loadArtistSongs(long id);
    }

    interface View extends IBaseView{
        void loadArtistSongsResult(List<SongEntity> songEntities);

        void loadFailed(Throwable throwable);

        void emptyView();
    }
}
