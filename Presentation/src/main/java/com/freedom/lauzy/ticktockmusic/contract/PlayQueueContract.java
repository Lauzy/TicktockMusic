package com.freedom.lauzy.ticktockmusic.contract;

import com.freedom.lauzy.ticktockmusic.base.IBaseView;
import com.freedom.lauzy.ticktockmusic.model.SongEntity;

import java.util.List;

import io.reactivex.Observable;

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

        Observable<Integer> deleteQueueData(String[] ids);

        void deleteAllQueueData(String[] ids);
    }

    interface View extends IBaseView {
        void loadQueueData(List<SongEntity> songEntities);

        void emptyView();

        void deleteAllQueueData();
    }
}
