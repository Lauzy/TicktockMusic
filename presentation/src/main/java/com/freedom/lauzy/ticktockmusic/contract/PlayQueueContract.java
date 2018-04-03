package com.freedom.lauzy.ticktockmusic.contract;

import com.freedom.lauzy.ticktockmusic.base.IBaseView;
import com.freedom.lauzy.ticktockmusic.model.SongEntity;

import java.util.List;

/**
 * Desc : 播放队列接口
 * Author : Lauzy
 * Date : 2017/9/8
 * Blog : http://www.jianshu.com/u/e76853f863a9
 * Email : freedompaladin@gmail.com
 */
public interface PlayQueueContract {
    interface Presenter {
        void loadQueueData(String[] ids);

        void deleteQueueData(String[] ids, int position, SongEntity entity);

        void deleteAllQueueData(String[] ids);

        void setRepeatMode(int mode);

        int getRepeatMode(int defaultMode);
    }

    interface View extends IBaseView {
        void loadQueueData(List<SongEntity> songEntities);

        void deleteItem(int position);

        void emptyView();

        void deleteAllQueueData();
    }
}
