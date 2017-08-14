package com.lauzy.freedom.librarys.imageload;

import android.graphics.Bitmap;
import android.widget.ImageView;

import com.bumptech.glide.request.target.Target;

/**
 * Desc : 基本配置信息
 * Author : Lauzy
 * Date : 2017/8/8
 * Blog : http://www.jianshu.com/u/e76853f863a9
 * Email : freedompaladin@gmail.com
 */
public class ImageConfig {
    private String url;
    private ImageView imageView;
    private int defaultRes;
    private int errorRes;
    private boolean isRound = true;
    private int cornerSize;
    private Target<Bitmap> target;
    private boolean isAsBitmap;

    public ImageConfig(Builder builder) {
        this.url = builder.url;
        this.imageView = builder.imageView;
        this.defaultRes = builder.defaultRes;
        this.errorRes = builder.errorRes;
        this.isRound = builder.isRound;
        this.cornerSize = builder.cornerSize;
        this.target = builder.target;
        this.isAsBitmap = builder.isAsBitmap;
    }

    public Target<Bitmap> getTarget() {
        return target;
    }

    public boolean isAsBitmap() {
        return isAsBitmap;
    }

    public boolean isRound() {
        return isRound;
    }

    public int getCornerSize() {
        return cornerSize;
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
        private boolean isRound = true;
        private int cornerSize;
        private boolean isAsBitmap;
        private Target<Bitmap> target;

        public Builder asBitmap(boolean isAsBitmap) {
            this.isAsBitmap = isAsBitmap;
            return this;
        }

        public Builder intoTarget(Target<Bitmap> target) {
            this.target = target;
            return this;
        }

        public Builder corner(int cornerSize) {
            this.cornerSize = cornerSize;
            return this;
        }

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

        public Builder isRound(boolean isRound) {
            this.isRound = isRound;
            return this;
        }

        public ImageConfig build() {
            return new ImageConfig(this);
        }
    }
}
