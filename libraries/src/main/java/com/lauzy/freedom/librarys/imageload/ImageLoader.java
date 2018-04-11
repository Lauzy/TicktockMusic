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
public class ImageLoader implements IBaseImageStrategy {

    private static ImageLoader INSTANCE;
    private IBaseImageStrategy mImageStrategy;


    {
        mImageStrategy = new GlideImageLoaderStrategy();
    }

    public static ImageLoader getInstance() {
        if (INSTANCE == null) {
            synchronized (ImageLoader.class) {
                if (INSTANCE == null) {
                    INSTANCE = new ImageLoader();
                }
            }
        }
        return INSTANCE;
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
