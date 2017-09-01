package com.freedom.lauzy.ticktockmusic.contract;

import com.freedom.lauzy.model.NetSongBean;
import com.freedom.lauzy.ticktockmusic.base.IBaseView;

import java.util.List;

/**
 * Desc : Contract Interface
 * Author : Lauzy
 * Date : 2017/8/4
 * Blog : http://www.jianshu.com/u/e76853f863a9
 * Email : freedompaladin@gmail.com
 */
public interface NetMusicContract {
    interface Presenter {

        void loadCacheMusicList();

        void loadNetMusicList();

        void loadMoreNetMusicList();
    }

    interface View extends IBaseView {

        void loadCacheData(List<NetSongBean> songListBeen);

        void loadSuccess(List<NetSongBean> songListBeen);

        void setEmptyView();

        void loadFail(Throwable throwable);

        void loadMoreSuccess(List<NetSongBean> songListBeen);

        void loadMoreEnd();

        void loadMoreFail(Throwable throwable);
    }
}
