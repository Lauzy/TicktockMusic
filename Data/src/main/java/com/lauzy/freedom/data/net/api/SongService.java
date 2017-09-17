package com.lauzy.freedom.data.net.api;

import com.lauzy.freedom.data.entity.MusicEntity;
import com.lauzy.freedom.data.entity.OnlineSongEntity;
import com.lauzy.freedom.data.net.constants.NetConstants;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Desc : 网络音乐Api
 * Author : Lauzy
 * Date : 2017/7/11
 * Blog : http://www.jianshu.com/u/e76853f863a9
 * Email : freedompaladin@gmail.com
 */
public interface SongService {
    @GET(NetConstants.URL_PARAM)
    Observable<MusicEntity> getMusicData(@Query(NetConstants.Param.METHOD) String method,
                                         @Query(NetConstants.Param.TYPE) int type,
                                         @Query(NetConstants.Param.OFFSET) int offset,
                                         @Query(NetConstants.Param.SIZE) int size);

    @GET(NetConstants.URL_PARAM)
    Observable<OnlineSongEntity> getOnlineSongData(@Query(NetConstants.Param.METHOD) String method,
                                                   @Query(NetConstants.Param.SONG_ID) long songId);
}
