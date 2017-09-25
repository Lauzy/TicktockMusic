package com.lauzy.freedom.data.entity;

/**
 * Desc : 在线音乐bean
 * Author : Lauzy
 * Date : 2017/9/17
 * Blog : http://www.jianshu.com/u/e76853f863a9
 * Email : freedompaladin@gmail.com
 */
@SuppressWarnings("unused")
public class OnlineSongEntity {

    public SonginfoBean songinfo;
    public int error_code;
    public BitrateBean bitrate;

    public static class SonginfoBean {
        public String special_type;
        public String pic_huge;
        public String ting_uid;
        public String pic_premium;
        public String havehigh;
        public String si_proxycompany;
        public String author;
        public String toneid;
        public String has_mv;
        public String song_id;
        public String title;
        public String artist_id;
        public String lrclink;
        public String relate_status;
        public String learn;
        public String pic_big;
        public String play_type;
        public String album_id;
        public String pic_radio;
        public String bitrate_fee;
        public String song_source;
        public String all_artist_id;
        public String all_artist_ting_uid;
        public String piao_id;
        public String charge;
        public String copy_type;
        public String all_rate;
        public String korean_bb_song;
        public String is_first_publish;
        public String has_mv_mobile;
        public String album_title;
        public String pic_small;
        public String album_no;
        public String resource_type_ext;
        public String resource_type;
    }

    public static class BitrateBean {
        public String show_link;
        public String free;
        public String song_file_id;
        public String file_size;
        public String file_extension;
        public String file_duration;
        public String file_bitrate;
        public String file_link;
        public String hash;
    }
}
