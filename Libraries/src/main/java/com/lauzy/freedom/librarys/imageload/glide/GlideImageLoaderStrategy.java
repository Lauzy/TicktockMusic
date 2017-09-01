package com.lauzy.freedom.librarys.imageload.glide;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.load.resource.bitmap.BitmapTransitionOptions;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.lauzy.freedom.librarys.imageload.IBaseImageStrategy;
import com.lauzy.freedom.librarys.imageload.ImageConfig;

/**
 * Desc : Glide Loader
 * Author : Lauzy
 * Date : 2017/6/30
 * Blog : http://www.jianshu.com/u/e76853f863a9
 * Email : freedompaladin@gmail.com
 */
public class GlideImageLoaderStrategy implements IBaseImageStrategy {

    @Override
    public void display(Context context, ImageConfig imageConfig) {
        RequestOptions options = getOptions(context, imageConfig);
        Object url = getPath(imageConfig);
        if (!imageConfig.isAsBitmap()) {
            RequestBuilder<Drawable> requestBuilder = Glide.with(context)
                    .load(url)
                    .apply(options);
            if (!imageConfig.isRound() && imageConfig.getDuration() != 0) {
                requestBuilder = requestBuilder.transition(new DrawableTransitionOptions()
                        .crossFade(imageConfig.getDuration()));
            }
            requestBuilder.into(imageConfig.getImageView());
        } else {
            RequestBuilder<Bitmap> requestBuilder = Glide.with(context)
                    .asBitmap()
                    .load(url)
                    .apply(options);
            if (!imageConfig.isRound() && imageConfig.getDuration() != 0) {
                requestBuilder = requestBuilder.transition(new BitmapTransitionOptions()
                        .crossFade(imageConfig.getDuration()));
            }
            requestBuilder.into(imageConfig.getTarget());
        }
    }

    /**
     * Glide 配置
     *
     * @param context     context
     * @param imageConfig 配置
     * @return Glide配置
     */
    private RequestOptions getOptions(Context context, ImageConfig imageConfig) {
        RequestOptions options = new RequestOptions()
                .placeholder(imageConfig.getDefaultRes())
                .error(imageConfig.getErrorRes());
        if (imageConfig.isRound()) {
            if (imageConfig.getCornerSize() == 0) {
                options.transform(new GlideCornerTransformation(context));
            } else {
                options.transform(new GlideCornerTransformation(context, imageConfig.getCornerSize()));
            }
        }
        return options;
    }

    /**
     * 获取不同的源路径，默认String类型
     *
     * @param config 配置
     * @return path
     */
    private Object getPath(ImageConfig config) {
      /*  Object url = null;
        switch (config.getUrlType()) {
            case URL:
                url = config.getUrl();
                break;
            case URI:
                url = config.getUri();
                break;
            case FILE:
                break;
        }*/
        return config.getUrl();
    }

    @Override
    public void clean(Context context, ImageConfig imageConfig) {
        Glide.with(context).clear(imageConfig.getImageView());
    }
}
