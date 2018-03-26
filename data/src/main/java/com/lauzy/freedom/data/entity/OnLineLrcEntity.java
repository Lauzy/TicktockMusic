package com.lauzy.freedom.data.entity;

import java.util.List;

/**
 * Desc : 在线歌词
 * Author : Lauzy
 * Date : 2018/3/26
 * Blog : http://www.jianshu.com/u/e76853f863a9
 * Email : freedompaladin@gmail.com
 */
public class OnLineLrcEntity {

    /**
     * code : 0
     * count : 15
     * result : [{"aid":2848529,"artist_id":2,"lrc":"http://s.gecimi.com/lrc/344/34435/3443588.lrc","sid":3443588,"song":"海阔天空"},{"aid":2346662,"artist_id":2396,"lrc":"http://s.gecimi.com/lrc/274/27442/2744281.lrc","sid":2744281,"song":"海阔天空"},{"aid":1889264,"artist_id":8715,"lrc":"http://s.gecimi.com/lrc/210/21070/2107014.lrc","sid":2107014,"song":"海阔天空"},{"aid":2075717,"artist_id":8715,"lrc":"http://s.gecimi.com/lrc/236/23651/2365157.lrc","sid":2365157,"song":"海阔天空"},{"aid":1563419,"artist_id":9208,"lrc":"http://s.gecimi.com/lrc/166/16685/1668536.lrc","sid":1668536,"song":"海阔天空"},{"aid":1567586,"artist_id":9208,"lrc":"http://s.gecimi.com/lrc/167/16739/1673997.lrc","sid":1673997,"song":"海阔天空"},{"aid":1571906,"artist_id":9208,"lrc":"http://s.gecimi.com/lrc/167/16796/1679605.lrc","sid":1679605,"song":"海阔天空"},{"aid":1573814,"artist_id":9208,"lrc":"http://s.gecimi.com/lrc/168/16819/1681961.lrc","sid":1681961,"song":"海阔天空"},{"aid":1656038,"artist_id":9208,"lrc":"http://s.gecimi.com/lrc/179/17907/1790768.lrc","sid":1790768,"song":"海阔天空"},{"aid":1718741,"artist_id":9208,"lrc":"http://s.gecimi.com/lrc/187/18757/1875769.lrc","sid":1875769,"song":"海阔天空"},{"aid":2003267,"artist_id":9208,"lrc":"http://s.gecimi.com/lrc/226/22642/2264296.lrc","sid":2264296,"song":"海阔天空"},{"aid":2020610,"artist_id":9208,"lrc":"http://s.gecimi.com/lrc/228/22889/2288967.lrc","sid":2288967,"song":"海阔天空"},{"aid":2051678,"artist_id":9208,"lrc":"http://s.gecimi.com/lrc/233/23323/2332322.lrc","sid":2332322,"song":"海阔天空"},{"aid":2412704,"artist_id":9208,"lrc":"http://s.gecimi.com/lrc/283/28376/2837689.lrc","sid":2837689,"song":"海阔天空"},{"aid":2607041,"artist_id":9208,"lrc":"http://s.gecimi.com/lrc/311/31116/3111659.lrc","sid":3111659,"song":"海阔天空"}]
     */
    public int code;
    public int count;
    public List<ResultBean> result;

    public static class ResultBean {
        /**
         * aid : 2848529
         * artist_id : 2
         * lrc : http://s.gecimi.com/lrc/344/34435/3443588.lrc
         * sid : 3443588
         * song : 海阔天空
         */
        public int aid;
        public int artist_id;
        public String lrc;
        public int sid;
        public String song;
    }
}
