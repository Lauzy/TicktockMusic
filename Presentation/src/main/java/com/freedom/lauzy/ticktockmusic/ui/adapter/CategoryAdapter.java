package com.freedom.lauzy.ticktockmusic.ui.adapter;

import android.app.Activity;
import android.graphics.Bitmap;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.freedom.lauzy.model.CategoryBean;
import com.freedom.lauzy.ticktockmusic.R;
import com.freedom.lauzy.ticktockmusic.navigation.Navigator;
import com.lauzy.freedom.librarys.imageload.ImageConfig;
import com.lauzy.freedom.librarys.imageload.ImageLoader;
import com.lauzy.freedom.librarys.view.util.PaletteColor;

import java.util.List;

/**
 * Desc : 分类Adapter
 * Author : Lauzy
 * Date : 2017/8/4
 * Blog : http://www.jianshu.com/u/e76853f863a9
 * Email : freedompaladin@gmail.com
 */
public class CategoryAdapter extends BaseQuickAdapter<CategoryBean, BaseViewHolder> {

    public CategoryAdapter(@LayoutRes int layoutResId, @Nullable List<CategoryBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, CategoryBean item) {
        helper.setText(R.id.txt_category, item.title);
        ImageLoader.INSTANCE.display(mContext,
                new ImageConfig.Builder()
                        .asBitmap(true)
                        .url(item.imgUrl)
                        .placeholder(R.drawable.ic_default)
                        .intoTarget(new SimpleTarget<Bitmap>() {
                            @Override
                            public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {
                                ((ImageView) helper.getView(R.id.img_category)).setImageBitmap(resource);
                               /* PaletteColor.mainColorObservable(resource).subscribe(color ->
                                        helper.getView(R.id.layout_category_item).setBackgroundColor(color));*/
                            }
                        })
                        .build());

        helper.getView(R.id.cv_category).setOnClickListener(v -> Navigator.navigateToSongList
                ((Activity) mContext, helper.getView(R.id.img_category), item.type, item.title, item.imgUrl));
    }
}
