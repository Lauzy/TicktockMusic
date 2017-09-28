package com.lauzy.freedom.data.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Desc : 歌手信息Bean
 * Author : Lauzy
 * Date : 2017/9/28
 * Blog : http://www.jianshu.com/u/e76853f863a9
 * Email : freedompaladin@gmail.com
 */
public class SingerAvatarEntity {

    public ArtistBean artist;

    public static class ArtistBean {

        public String name;
        public String url;
        public List<ImageBean> image;

        public static class ImageBean {
            @SerializedName("#text")
            public String imgUrl;
            public String size;
        }
    }
}
