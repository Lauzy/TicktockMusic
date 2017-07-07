package com.lauzy.freedom.data.net;

/**
 * Desc :
 * 1、获取列表：method=baidu.ting.billboard.billList&type=1&size=10&offset=0
 * 2、搜索：method=baidu.ting.search.catalogSug&query=海阔天空
 * 3、播放：method=baidu.ting.song.play&songid=877578 或 method=baidu.ting.song.playAAC&songid=877578
 * 4、歌词：method=baidu.ting.song.lry&songid=877578
 * 5、推荐：method=baidu.ting.song.getRecommandSongList&song_id=877578&num=5
 * 6、下载：method=baidu.ting.song.downWeb&songid=877578&bit=24&_t=1393123213
 * 7、歌手信息：method=baidu.ting.artist.getInfo&tinguid=877578
 * 8、歌手歌曲列表：method=baidu.ting.artist.getSongList&tinguid=877578&limits=6&use_cluster=1&order=2
 * Author : Lauzy
 * Date : 2017/7/7
 * Blog : http://www.jianshu.com/u/e76853f863a9
 * Email : freedompaladin@gmail.com
 */
public class NetConstants {
    //http://tingapi.ting.baidu.com/v1/restserver/ting?format=json&calback=&from=webapp_music&method=baidu.ting.billboard.billList&type=2&size=10&offset=0
    public static final String BASE_API = "http://tingapi.ting.baidu.com/v1/restserver/ting";

    public static class Param {
        public static final String FORMAT = "format";
        public static final String FROM = "from";
        public static final String METHOD = "method";
        public static final String TYPE = "type";
        public static final String SIZE = "size";
        public static final String OFFSET = "offset";

        public static final String SEARCH_QUERY = "query";
    }

    public static class Value {

        public static final String JSON = "json";   //FORMAT
        public static final String APP = "webapp_music";  //FROM
        public static final int SIZE = 10;

        public static final String METHOD_SONG_LIST = "baidu.ting.billboard.billList";
        public static final String METHOD_SEARCH = "baidu.ting.search.catalogSug";
        public static final String METHOD_PLAY = "baidu.ting.song.play";
        public static final String METHOD_LRC = "baidu.ting.song.lry";
        public static final String METHOD_DOWNLOAD = "baidu.ting.song.downWeb";

        //type = 1-新歌榜,2-热歌榜,11-摇滚榜,12-爵士,16-流行,21-欧美金曲榜,22-经典老歌榜,23-情歌对唱榜,24-影视金曲榜,25-网络歌曲榜
        public static final int TYPE_NEW = 1;
        public static final int TYPE_HOT = 2;
        public static final int TYPE_ROCK = 11;
        public static final int TYPE_JAZZ = 12;
        public static final int TYPE_POPULAR = 16;
        public static final int TYPE_WESTERN = 21;
        public static final int TYPE_CLASSIC = 22;
        public static final int TYPE_LOVE = 23;
        public static final int TYPE_MOVIE = 24;
        public static final int TYPE_NETWORK = 25;
    }

    public static class ErrorCode {
        public static final int CODE_SUCCESS = 22000;
    }

}
