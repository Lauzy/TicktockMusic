package com.lauzy.freedom.data.api;

import com.freedom.lauzy.model.SongListBean;
import com.lauzy.freedom.data.net.NetConstants;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Desc : Song Restful Api interface
 * Author : Lauzy
 * Date : 2017/7/10
 * Blog : http://www.jianshu.com/u/e76853f863a9
 * Email : freedompaladin@gmail.com
 */
public interface SongService {

    @GET
    Observable<List<SongListBean>> getSongList(@Query(NetConstants.Param.METHOD) String method,
                                               @Query(NetConstants.Param.TYPE) int type,
                                               @Query(NetConstants.Param.OFFSET) int offset,
                                               @Query(NetConstants.Param.SIZE) int size);
}
