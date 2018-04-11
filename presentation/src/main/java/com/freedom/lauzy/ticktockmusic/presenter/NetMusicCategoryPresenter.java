package com.freedom.lauzy.ticktockmusic.presenter;

import android.graphics.Bitmap;

import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.freedom.lauzy.model.CategoryBean;
import com.freedom.lauzy.ticktockmusic.R;
import com.freedom.lauzy.ticktockmusic.base.BaseRxPresenter;
import com.freedom.lauzy.ticktockmusic.contract.NetMusicCategoryContract;
import com.freedom.lauzy.ticktockmusic.event.PaletteEvent;
import com.freedom.lauzy.ticktockmusic.function.RxBus;
import com.freedom.lauzy.ticktockmusic.utils.ThemeHelper;
import com.lauzy.freedom.librarys.imageload.ImageConfig;
import com.lauzy.freedom.librarys.imageload.ImageLoader;
import com.lauzy.freedom.librarys.view.util.PaletteColor;

import java.util.ArrayList;
import java.util.HashMap;
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
public class NetMusicCategoryPresenter extends BaseRxPresenter<NetMusicCategoryContract.View>
        implements NetMusicCategoryContract.Presenter {

    private HashMap<String, Integer> mColorMap = new HashMap<>();

    @Inject
    NetMusicCategoryPresenter() {
    }

    @Override
    public void loadData() {
        int[] types = {TYPE_NEW, TYPE_HOT, TYPE_ROCK, TYPE_WESTERN,
                TYPE_CLASSIC, TYPE_LOVE, TYPE_MOVIE, TYPE_NETWORK};
        String[] titles = getView().context().getResources().getStringArray(R.array.CategoryArr);
        String[] urls = getView().context().getResources().getStringArray(R.array.CategorySongUrlArr);
        List<CategoryBean> categoryBeen = new ArrayList<>();
        for (int i = 0; i < types.length; i++) {
            CategoryBean categoryBean = new CategoryBean();
            categoryBean.type = types[i];
            categoryBean.title = titles[i];
            categoryBean.imgUrl = urls[i];
            categoryBeen.add(categoryBean);
        }
        getView().loadCategoryData(categoryBeen);
    }

    @Override
    public void setCategoryColor(String imgUrl) {
        if (getView() == null) {
            return;
        }
        ImageLoader.getInstance().display(getView().context(),
                new ImageConfig.Builder()
                        .url(imgUrl)
                        .asBitmap(true)
                        .placeholder(R.drawable.ic_default_horizontal)
                        .crossFade(500)
                        .isRound(false)
                        .intoTarget(new SimpleTarget<Bitmap>() {
                            @Override
                            public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {
                                getView().setCategoryBitmap(resource);
                                if (mColorMap.get(imgUrl) == null) {//map 缓存 color
                                    PaletteColor.mainColorObservable(ThemeHelper.getThemeColorResId(getView().context()),
                                            resource).subscribe(integer -> {
                                                mColorMap.put(imgUrl, integer);
                                                setCategoryBgColor(integer);
                                            }
                                    );
                                } else {
                                    setCategoryBgColor(mColorMap.get(imgUrl));
                                }
                            }
                        })
                        .build());
    }

    private void setCategoryBgColor(Integer color) {
        if (getView() == null) {
            return;
        }
        getView().setCategoryColor(color);
        RxBus.INSTANCE.post(new PaletteEvent(color));
    }
}
