package com.lauzy.freedom.data.entity.mapper;

import com.freedom.lauzy.model.ArtistAvatar;
import com.lauzy.freedom.data.entity.SingerAvatarEntity;

import java.util.List;

/**
 * Desc : 歌手头像Mapper
 * Author : Lauzy
 * Date : 2017/9/28
 * Blog : http://www.jianshu.com/u/e76853f863a9
 * Email : freedompaladin@gmail.com
 */
public class SingerAvatarMapper {

    public static ArtistAvatar transform(SingerAvatarEntity avatarEntity) {
        if (null == avatarEntity) {
            throw new IllegalArgumentException("avatarEntity cannot be null");
        }
        ArtistAvatar artistAvatar = new ArtistAvatar();
        if (avatarEntity.artist != null && avatarEntity.artist.image != null
                && avatarEntity.artist.image.size() != 0) {
            List<SingerAvatarEntity.ArtistBean.ImageBean> imageBeen = avatarEntity.artist.image;
            artistAvatar.picUrl = imageBeen.get(3) != null ? imageBeen.get(3).imgUrl : "";
            artistAvatar.bigPicUrl = imageBeen.get(4) != null ? imageBeen.get(4).imgUrl : "";
        }
        return artistAvatar;
    }

    /*
    * "image": [{
        "#text": "https://lastfm-img2.akamaized.net/i/u/34s/a0a0915d75ac49c1828559bda7ab1191.png",
        "size": "small"},
        {
        "#text": "https://lastfm-img2.akamaized.net/i/u/64s/a0a0915d75ac49c1828559bda7ab1191.png",
        "size": "medium"},
      {
        "#text": "https://lastfm-img2.akamaized.net/i/u/174s/a0a0915d75ac49c1828559bda7ab1191.png",
        "size": "large"},
      {
        "#text": "https://lastfm-img2.akamaized.net/i/u/300x300/a0a0915d75ac49c1828559bda7ab1191.png",
        "size": "extralarge"},
      {
        "#text": "https://lastfm-img2.akamaized.net/i/u/300x300/a0a0915d75ac49c1828559bda7ab1191.png",
        "size": "mega"},
    ],
    * */
}
