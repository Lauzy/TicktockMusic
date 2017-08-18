package com.freedom.lauzy.ticktockmusic.presenter;

import android.content.Context;

import com.freedom.lauzy.model.CategoryBean;
import com.freedom.lauzy.ticktockmusic.R;
import com.freedom.lauzy.ticktockmusic.base.BasePresenter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import static com.lauzy.freedom.data.net.constants.NetConstants.Value.TYPE_CLASSIC;
import static com.lauzy.freedom.data.net.constants.NetConstants.Value.TYPE_HOT;
import static com.lauzy.freedom.data.net.constants.NetConstants.Value.TYPE_LOVE;
import static com.lauzy.freedom.data.net.constants.NetConstants.Value.TYPE_MOVIE;
import static com.lauzy.freedom.data.net.constants.NetConstants.Value.TYPE_NETWORK;
import static com.lauzy.freedom.data.net.constants.NetConstants.Value.TYPE_NEW;
import static com.lauzy.freedom.data.net.constants.NetConstants.Value.TYPE_ROCK;
import static com.lauzy.freedom.data.net.constants.NetConstants.Value.TYPE_WESTERN;

/**
 * Desc : 分类
 * Author : Lauzy
 * Date : 2017/8/4
 * Blog : http://www.jianshu.com/u/e76853f863a9
 * Email : freedompaladin@gmail.com
 */
public class NetMusicCategoryPresenter extends BasePresenter {

    @Inject
    NetMusicCategoryPresenter() {
    }

    public List<CategoryBean> getCategoryData(Context context) {
        int[] types = {TYPE_NEW, TYPE_HOT, TYPE_ROCK, TYPE_WESTERN,
                TYPE_CLASSIC, TYPE_LOVE, TYPE_MOVIE, TYPE_NETWORK};
        String[] titles = context.getResources().getStringArray(R.array.CategoryArr);
        String[] urls = context.getResources().getStringArray(R.array.CategorySongUrlArr);
        List<CategoryBean> categoryBeen = new ArrayList<>();
        for (int i = 0; i < types.length; i++) {
            CategoryBean categoryBean = new CategoryBean();
            categoryBean.type = types[i];
            categoryBean.title = titles[i];
            categoryBean.imgUrl = urls[i];
            categoryBeen.add(categoryBean);
        }
        return categoryBeen;
    }
}
