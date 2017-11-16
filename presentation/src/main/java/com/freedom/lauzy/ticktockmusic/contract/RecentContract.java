package com.freedom.lauzy.ticktockmusic.contract;

import com.freedom.lauzy.ticktockmusic.base.IBaseView;
import com.freedom.lauzy.ticktockmusic.model.SongEntity;

import java.util.List;

/**
 * Desc : 最近播放接口
 * Author : Lauzy
 * Date : 2017/9/15
 * Blog : http://www.jianshu.com/u/e76853f863a9
 * Email : freedompaladin@gmail.com
 */
public interface RecentContract {
    interface Presenter {
        void loadRecentSongs();

        void clearRecentSongs();
    }

    interface View extends IBaseView{
        void getRecentSongs(List<SongEntity> songEntities);

        void clearAllRecentSongs();

        void emptyView();
    }
}
