package com.freedom.lauzy.repository;

import io.reactivex.Observable;
import okhttp3.ResponseBody;

/**
 * Desc : 歌词
 * Author : Lauzy
 * Date : 2018/3/26
 * Blog : http://www.jianshu.com/u/e76853f863a9
 * Email : freedompaladin@gmail.com
 */
public interface LrcRepository {
    /**
     * 获取歌词
     *
     * @param songName 歌曲名
     * @param singer   歌手
     * @return ob
     */
    Observable<ResponseBody> getLrcData(String songName, String singer);
}
