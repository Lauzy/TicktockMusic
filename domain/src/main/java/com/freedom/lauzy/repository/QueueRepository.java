package com.freedom.lauzy.repository;

import com.freedom.lauzy.model.LocalSongBean;
import com.freedom.lauzy.model.QueueSongBean;

import java.util.List;

import io.reactivex.Observable;

/**
 * Desc : 播放队列数据接口。
 * 原设计为分别添加本地及网络数据，后通过定义Type来区分音乐的数据来源，
 * 然后在播放时具体调用本地或音乐的数据，数据统一，简化操作。
 * Author : Lauzy
 * Date : 2017/9/8
 * Blog : http://www.jianshu.com/u/e76853f863a9
 * Email : freedompaladin@gmail.com
 */
public interface QueueRepository {

    /**
     * 获取队列数据方法
     * @param songIds songIds
     * @return Observable
     */
    Observable<List<QueueSongBean>> getQueueData(String[] songIds);

    /**
     * 添加至队列数据Observable
     * @param songIds songIds
     * @param queueSongBeen 队列数据集合 {@link LocalSongBean}
     * @return Observable
     */
    Observable<List<QueueSongBean>> addQueueData(String[] songIds, List<LocalSongBean> queueSongBeen);

//    Observable<List<QueueSongBean>> addNetQueueData(String[] songIds, List<NetSongBean> netSongBeen);

    /**
     * 删除队列数据Observable
     * @param songIds songIds
     * @return Observable
     */
    Observable<Integer> deleteQueueData(String[] songIds);
}
