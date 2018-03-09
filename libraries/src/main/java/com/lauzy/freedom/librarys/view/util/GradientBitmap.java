package com.lauzy.freedom.librarys.view.util;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Shader;

/**
 * Desc : 渐变bitmap
 * Author : Lauzy
 * Date : 2018/3/8
 * Blog : http://www.jianshu.com/u/e76853f863a9
 * Email : freedompaladin@gmail.com
 */
public class GradientBitmap {

    public static Bitmap getGradientBitmap(Bitmap originalBitmap, int gradientColor) {
        int width = originalBitmap.getWidth();
        int height = originalBitmap.getHeight();
        Bitmap updatedBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(updatedBitmap);
        canvas.drawBitmap(originalBitmap, 0, 0, null);

        Paint paint = new Paint();
        LinearGradient linearGradient = new LinearGradient(0, height / 2, 0, height,
                Color.TRANSPARENT, gradientColor, Shader.TileMode.MIRROR);
        paint.setShader(linearGradient);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_ATOP));
        canvas.drawRect(0, 0, width, height, paint);
        return updatedBitmap;
    }
}
