package com.lauzy.freedom.data.local.data;

/**
 * Desc : 缓存
 * Author : Lauzy
 * Date : 2018/4/3
 * Blog : http://www.jianshu.com/u/e76853f863a9
 * Email : freedompaladin@gmail.com
 */
public interface CacheRepo extends DataRepo{

    void setArtistAvatar(String artistName, String avatarUrl);

    String getArtistAvatar(String artistName);


}
