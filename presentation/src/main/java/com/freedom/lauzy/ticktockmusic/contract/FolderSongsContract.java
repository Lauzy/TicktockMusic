package com.freedom.lauzy.ticktockmusic.contract;

import com.freedom.lauzy.ticktockmusic.base.IBaseView;
import com.freedom.lauzy.ticktockmusic.model.SongEntity;

import java.util.List;

/**
 * Desc : 文件夹音乐
 * Author : Lauzy
 * Date : 2018/3/15
 * Blog : http://www.jianshu.com/u/e76853f863a9
 * Email : freedompaladin@gmail.com
 */
public interface FolderSongsContract {
    interface Presenter {
        void loadFolderSongs(String folderPath);

        void deleteSong(int position, SongEntity songEntity);
    }

    interface View extends IBaseView {

        void onLoadFolderSongsSuccess(List<SongEntity> songEntities);

        void setEmptyView();

        void loadFail(Throwable throwable);

        void deleteSongSuccess(int position, SongEntity songEntity);
    }
}
