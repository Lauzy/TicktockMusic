package com.lauzy.freedom.data.net.api;

import com.lauzy.freedom.data.entity.BaiduLrcEntity;
import com.lauzy.freedom.data.entity.MusicEntity;
import com.lauzy.freedom.data.entity.OnLineLrcEntity;
import com.lauzy.freedom.data.entity.OnlineSongEntity;
import com.lauzy.freedom.data.entity.SingerAvatarEntity;
import com.lauzy.freedom.data.net.constants.NetConstants;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

/**
 * Desc : 网络音乐Api
 * Author : Lauzy
 * Date : 2017/7/11
 * Blog : http://www.jianshu.com/u/e76853f863a9
 * Email : freedompaladin@gmail.com
 */
public interface SongService {

    //    @Headers({"BaseUrlHead:BaiduApi",
//            "User-Agent:Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML,like Gecko) Chrome/59.0.3071.115 Safari/537.36"})
    @Headers("BaseUrlHead:BaiduApi")
    @GET(NetConstants.URL_PARAM)
    Observable<MusicEntity> getMusicData(@Query(NetConstants.Param.METHOD) String method,
                                         @Query(NetConstants.Param.TYPE) int type,
                                         @Query(NetConstants.Param.OFFSET) int offset,
                                         @Query(NetConstants.Param.SIZE) int size);


    //    @Headers({"BaseUrlHead:BaiduApi",
//            "User-Agent:Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML,like Gecko) Chrome/59.0.3071.115 Safari/537.36"})
    @Headers("BaseUrlHead:BaiduApi")
    @GET(NetConstants.URL_PARAM)
    Observable<OnlineSongEntity> getOnlineSongData(@Query(NetConstants.Param.METHOD) String method,
                                                   @Query(NetConstants.Param.SONG_ID) long songId);

    @Headers("BaseUrlHead:LastFmApi")
    @GET(NetConstants.ARTIST_URL_PARAM)
    Observable<SingerAvatarEntity> getSingerAvatar(@Query(NetConstants.Artist.METHOD) String method,
                                                   @Query(NetConstants.Artist.API_KEY) String apiKey,
                                                   @Query(NetConstants.Artist.ARTIST_NAME) String artistName,
                                                   @Query(NetConstants.Artist.FORMAT) String format);

    @Headers("BaseUrlHead:GeCiMiApi")
    @GET(NetConstants.LRC_PARAM + "/{songName}/{singer}")
    Observable<OnLineLrcEntity> getOnLrcData(@Path("songName") String songName,
                                             @Path("singer") String singer);

    @Headers("BaseUrlHead:BaiduApi")
    @GET(NetConstants.URL_PARAM)
    Observable<BaiduLrcEntity> getBaiduLrcData(@Query(NetConstants.Param.METHOD) String method,
                                               @Query(NetConstants.Param.SONG_ID) long songId);

    @Streaming
    @GET
    Observable<ResponseBody> downloadLrcFile(@Url String lrcUrl);
}
