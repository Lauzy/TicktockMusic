package com.lauzy.freedom.librarys.imageload;

import android.content.Context;
import android.widget.ImageView;

import com.lauzy.freedom.librarys.imageload.glide.GlideImageLoaderStrategy;

/**
 * Desc : Image Library Util
 * Author : Lauzy
 * Date : 2017/6/29
 * Blog : http://www.jianshu.com/u/e76853f863a9
 * Email : freedompaladin@gmail.com
 */
public enum ImageLoader implements IBaseImageStrategy {

    INSTANCE;

    private IBaseImageStrategy mImageStrategy;

    ImageLoader() {
        mImageStrategy = new GlideImageLoaderStrategy();
    }

    @Override
    public void display(Context context, ImageConfig imageConfig) {
        mImageStrategy.display(context, imageConfig);
    }

    @Override
    public void clean(Context context, ImageView imageView) {
        mImageStrategy.clean(context, imageView);
    }
}
