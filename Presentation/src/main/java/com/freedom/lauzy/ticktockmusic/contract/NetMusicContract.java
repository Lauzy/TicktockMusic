package com.freedom.lauzy.ticktockmusic.contract;

import com.freedom.lauzy.model.SongListBean;
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

        void loadCacheData(List<SongListBean> songListBeen);

        void loadSuccess(List<SongListBean> songListBeen);

        void setEmptyView();

        void loadFail(Throwable throwable);

        void loadMoreSuccess(List<SongListBean> songListBeen);

        void loadMoreEnd();

        void loadMoreFail(Throwable throwable);
    }
}
