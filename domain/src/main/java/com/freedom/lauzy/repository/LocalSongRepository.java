package com.freedom.lauzy.repository;

import com.freedom.lauzy.model.ArtistAvatar;
import com.freedom.lauzy.model.LocalAlbumBean;
import com.freedom.lauzy.model.LocalArtistBean;
import com.freedom.lauzy.model.LocalSongBean;

import java.util.List;

import io.reactivex.Observable;

/**
 * Desc :  LocalSongRepository
 * Author : Lauzy
 * Date : 2017/8/9
 * Blog : http://www.jianshu.com/u/e76853f863a9
 * Email : freedompaladin@gmail.com
 */
public interface LocalSongRepository {
    /**
     * 获取本地音乐列表
     *
     * @param albumId 专辑id，为0时返回所有音乐
     * @return Observable
     */
    Observable<List<LocalSongBean>> getLocalSongList(long albumId);

    /**
     * 获取本地专辑列表
     *
     * @param id 专辑Id，为0是返回所有专辑（未使用）
     * @return Observable
     */
    Observable<List<LocalAlbumBean>> getLocalAlbumList(long id);

    /**
     * 获取本地歌曲列表
     *
     * @return Observable
     */
    Observable<List<LocalArtistBean>> getLocalArtistList();

    /**
     * 获取歌手头像
     *
     * @param artistName 歌手名称
     * @return Observable
     */
    Observable<ArtistAvatar> getArtistAvatar(String method, String apiKey, String artistName, String format);

    /**
     * 获取本地歌手的音乐
     * @param artistId 歌手ID
     * @return Observable
     */
    Observable<List<LocalSongBean>> getLocalArtistSongList(long artistId);

}
