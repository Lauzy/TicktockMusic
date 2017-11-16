package com.lauzy.freedom.librarys.imageload.glide;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.NonNull;

import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;
import com.bumptech.glide.load.resource.bitmap.TransformationUtils;

import java.security.MessageDigest;

/**
 * Desc : 圆角 Transformation
 * Author : Lauzy
 * Date : 2017/8/9
 * Blog : http://www.jianshu.com/u/e76853f863a9
 * Email : freedompaladin@gmail.com
 */
public class GlideCornerTransformation extends BitmapTransformation {

    private static float mRadius = 0f;

    public GlideCornerTransformation(Context context) {
        this(context, 4);
    }

    public GlideCornerTransformation(Context context, int dpValue) {
        mRadius = Resources.getSystem().getDisplayMetrics().density * dpValue;
    }

    @Override
    protected Bitmap transform(@NonNull BitmapPool pool, @NonNull Bitmap toTransform, int outWidth, int outHeight) {
        Bitmap bitmap = TransformationUtils.centerCrop(pool, toTransform, outWidth, outHeight);
        return cornerTransform(pool, bitmap);
    }

    private static Bitmap cornerTransform(BitmapPool pool, Bitmap source) {
        if (source == null) return null;
        Bitmap result = pool.get(source.getWidth(), source.getHeight(), Bitmap.Config.ARGB_8888);
        /*if (null == result) {
            result = Bitmap.createBitmap(source.getWidth(), source.getHeight(), Bitmap.Config.RGB_565);
        }*/
        Canvas canvas = new Canvas(result);
        Paint paint = new Paint();
        paint.setShader(new BitmapShader(source, BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP));
        paint.setAntiAlias(true);
        RectF rectF = new RectF(0f, 0f, source.getWidth(), source.getHeight());
        canvas.drawRoundRect(rectF, mRadius, mRadius, paint);
        return result;
    }

    public String getId() {
        return "GlideCornerTransformation" + mRadius;
    }

    @Override
    public void updateDiskCacheKey(MessageDigest messageDigest) {
    }
}
