package com.lauzy.freedom.librarys.imageload;

import android.widget.ImageView;

/**
 * Desc : 基本配置信息
 * Author : Lauzy
 * Date : 2017/8/8
 * Blog : http://www.jianshu.com/u/e76853f863a9
 * Email : freedompaladin@gmail.com
 */
public class ImageConfig {
    protected String url;
    protected ImageView imageView;
    protected int defaultRes;
    protected int errorRes;

    public ImageConfig(Builder builder) {
        this.url = builder.url;
        this.imageView = builder.imageView;
        this.defaultRes = builder.defaultRes;
        this.errorRes = builder.errorRes;
    }

    public String getUrl() {
        return url;
    }

    public ImageView getImageView() {
        return imageView;
    }

    public int getDefaultRes() {
        return defaultRes;
    }

    public int getErrorRes() {
        return errorRes;
    }

    public static class Builder {
        private String url;
        private ImageView imageView;
        private int defaultRes;
        private int errorRes;

        public Builder url(String url) {
            this.url = url;
            return this;
        }

        public Builder into(ImageView imageView) {
            this.imageView = imageView;
            return this;
        }

        public Builder placeholder(int placeholderRes) {
            this.defaultRes = placeholderRes;
            return this;
        }

        public Builder error(int errorRes) {
            this.errorRes = errorRes;
            return this;
        }

        public ImageConfig build() {
            return new ImageConfig(this);
        }
    }
}
