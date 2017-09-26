package com.freedom.lauzy.ticktockmusic.contract;

import com.freedom.lauzy.ticktockmusic.base.IBaseView;
import com.freedom.lauzy.ticktockmusic.model.SongEntity;

import java.util.List;

/**
 * Desc :  LocalMusic
 * Author : Lauzy
 * Date : 2017/8/10
 * Blog : http://www.jianshu.com/u/e76853f863a9
 * Email : freedompaladin@gmail.com
 */
public interface LocalMusicContract {
    interface SongPresenter {
        void loadLocalSong();

        void setNewQueueData(SongEntity songEntity);
    }

    interface View extends IBaseView {

        void loadLocalMusic(List<SongEntity> localSongBeen);

        void setEmptyView();

        void loadFailed(Throwable throwable);
    }
}
