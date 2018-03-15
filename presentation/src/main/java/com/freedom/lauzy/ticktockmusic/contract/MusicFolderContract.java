package com.freedom.lauzy.ticktockmusic.contract;

import com.freedom.lauzy.model.Folder;
import com.freedom.lauzy.ticktockmusic.base.IBaseView;

import java.util.List;

/**
 * Desc : 文件夹接口
 * Author : Lauzy
 * Date : 2018/3/15
 * Blog : http://www.jianshu.com/u/e76853f863a9
 * Email : freedompaladin@gmail.com
 */
public interface MusicFolderContract {

    interface Presenter {

        void loadFolders();
    }

    interface View extends IBaseView {
        void onLoadFoldersSuccess(List<Folder> folders);

        void setEmptyView();

        void loadFailed(Throwable throwable);
    }
}
