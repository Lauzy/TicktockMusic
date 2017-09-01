package com.freedom.lauzy.ticktockmusic.contract;

import com.freedom.lauzy.model.LocalAlbumBean;
import com.freedom.lauzy.ticktockmusic.base.IBaseView;

import java.util.List;

/**
 * Desc :  LocalAlbum
 * Author : Lauzy
 * Date : 2017/8/10
 * Blog : http://www.jianshu.com/u/e76853f863a9
 * Email : freedompaladin@gmail.com
 */
public interface LocalAlbumContract {
    interface Presenter {
        void loadLocalAlbum();
    }

    interface View extends IBaseView {
        void loadLocalAlbum(List<LocalAlbumBean> localAlbumBeen);

        void setEmptyView();

        void loadFailed(Throwable throwable);
    }
}
