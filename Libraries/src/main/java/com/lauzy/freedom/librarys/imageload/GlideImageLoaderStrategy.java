package com.lauzy.freedom.librarys.imageload;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

/**
 * Desc : Glide Loader
 * Author : Lauzy
 * Date : 2017/6/30
 * Blog : http://www.jianshu.com/u/e76853f863a9
 * Email : freedompaladin@gmail.com
 */
public class GlideImageLoaderStrategy implements IBaseImageStrategy {


    @Override
    public void display(Context context, String imgPath, ImageView imageView) {
        Glide.with(context).load(imgPath).into(imageView);
    }

    @Override
    public void display(Context context, String imgPath, ImageView imageView, int defaultRes) {
        Glide.with(context).load(imgPath).apply(new RequestOptions().placeholder(defaultRes))
                .into(imageView);
    }

    public void clear(Context context, ImageView imageView) {
        Glide.with(context).clear(imageView);
    }
}
