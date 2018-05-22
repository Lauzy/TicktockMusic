package com.freedom.lauzy.ticktockmusic.contract;

import com.freedom.lauzy.ticktockmusic.base.IBaseView;
import com.freedom.lauzy.ticktockmusic.base.IPresenter;
import com.freedom.lauzy.ticktockmusic.model.SongEntity;

import java.util.List;

/**
 * Desc : 搜索
 * Author : Lauzy
 * Date : 2018/5/22
 * Blog : http://www.jianshu.com/u/e76853f863a9
 * Email : freedompaladin@gmail.com
 */
public interface SearchContract {

    interface View extends IBaseView {
        void onSearchSuccess(List<SongEntity> songEntities);

        void onSearchFailed();

        void setEmptyView();
    }

    interface Presenter extends IPresenter<View>{
        /**
         * 搜索
         * @param searchContent 内容
         */
        void searchSongs(String searchContent);
    }
}
