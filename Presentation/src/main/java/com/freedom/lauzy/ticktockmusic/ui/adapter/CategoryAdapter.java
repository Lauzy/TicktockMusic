package com.freedom.lauzy.ticktockmusic.ui.adapter;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.freedom.lauzy.model.CategoryBean;
import com.freedom.lauzy.ticktockmusic.R;
import com.lauzy.freedom.librarys.imageload.ImageConfig;
import com.lauzy.freedom.librarys.imageload.ImageLoader;

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
        helper.setText(R.id.txt_category, item.title)
                .setImageResource(R.id.img_category, item.imgRes);
        ImageLoader.INSTANCE.display(mContext,
                new ImageConfig.Builder()
                        .url(item.imgUrl)
                        .placeholder(R.drawable.ic_music)
                        .into(helper.getView(R.id.img_category))
                        .build());
    }
}
