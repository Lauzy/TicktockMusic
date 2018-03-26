package com.lauzy.freedom.data.entity.mapper;

import com.freedom.lauzy.model.LrcBean;
import com.lauzy.freedom.data.entity.OnLineLrcEntity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Desc : 歌词转换
 * Author : Lauzy
 * Date : 2018/3/26
 * Blog : http://www.jianshu.com/u/e76853f863a9
 * Email : freedompaladin@gmail.com
 */
public class LrcMapper {

    private static LrcBean transform(OnLineLrcEntity.ResultBean resultBean) {
        if (resultBean == null) {
            return null;
        }
        LrcBean lrcBean = new LrcBean();
        lrcBean.lrc = resultBean.lrc;
        lrcBean.song = resultBean.song;
        return lrcBean;
    }

    public static List<LrcBean> transform(List<OnLineLrcEntity.ResultBean> resultBeans) {
        if (resultBeans == null || resultBeans.isEmpty()) {
            return Collections.emptyList();
        }
        List<LrcBean> lrcBeans = new ArrayList<>();
        for (OnLineLrcEntity.ResultBean resultBean : resultBeans) {
            lrcBeans.add(transform(resultBean));
        }
        return lrcBeans;
    }
}
