package com.freedom.lauzy.repository;

import com.freedom.lauzy.model.LocalSongBean;
import com.freedom.lauzy.model.QueueSongBean;

import java.util.List;

import io.reactivex.Observable;

/**
 * Desc : 播放队列数据
 * Author : Lauzy
 * Date : 2017/9/8
 * Blog : http://www.jianshu.com/u/e76853f863a9
 * Email : freedompaladin@gmail.com
 */
public interface QueueRepository {
    Observable<List<QueueSongBean>> getQueueData(String[] songIds);

    Observable<List<QueueSongBean>> addLocalQueueData(String[] songIds, List<LocalSongBean> queueSongBeen);

//    Observable<List<QueueSongBean>> addNetQueueData(String[] songIds, List<NetSongBean> netSongBeen);

    Observable<Integer> deleteQueueData(String[] songIds);
}
