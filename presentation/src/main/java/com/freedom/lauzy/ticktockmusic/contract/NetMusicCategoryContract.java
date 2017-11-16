package com.freedom.lauzy.ticktockmusic.contract;

import android.content.Context;
import android.graphics.Bitmap;

import com.freedom.lauzy.model.CategoryBean;
import com.freedom.lauzy.ticktockmusic.base.IBaseView;

import java.util.List;

/**
 * Desc : CategoryPresenter
 * Author : Lauzy
 * Date : 2017/8/21
 * Blog : http://www.jianshu.com/u/e76853f863a9
 * Email : freedompaladin@gmail.com
 */
public interface NetMusicCategoryContract {
    interface Presenter{
        void loadData();
        void setCategoryColor(String imgUrl);
    }
    interface View extends IBaseView{
        Context context();
        void loadCategoryData(List<CategoryBean> categoryBeen);
        void setCategoryBitmap(Bitmap bitmap);
        void setCategoryColor(int color);
    }
}
