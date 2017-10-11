package com.lauzy.freedom.librarys.view.blur;

import android.graphics.Bitmap;

/**
 * Desc :
 * Author : Lauzy
 * Date : 2017/10/11
 * Blog : http://www.jianshu.com/u/e76853f863a9
 * Email : freedompaladin@gmail.com
 */
public class ImageBlur {

    static {
        System.loadLibrary("ImageBlurJni");
    }

    /**
     * Blur Image By Pixels
     *
     * @param img Img pixel array
     * @param w   Img width
     * @param h   Img height
     * @param r   Blur radius
     */
    private static native void blurPixels(int[] img, int w, int h, int r);

    /**
     * Blur Image By Bitmap
     *
     * @param bitmap Img Bitmap
     * @param r      Blur radius
     */
    private static native void blurBitmap(Bitmap bitmap, int r);

    private static native void blurBitmapClip(Bitmap bitmap, int r, int parts);

    /**
     * Building the bitmap
     *
     * @param original Bitmap
     * @return Bitmap
     */
    private static Bitmap checkSource(Bitmap original, int radius) {
        if (radius < 0 || radius > 256) {
            throw new RuntimeException("Blur bitmap radius must >= 1 and <=256.");
        }

        // First we should check the original
        if (original == null) {
            throw new NullPointerException("Blur bitmap original isn't null.");
        }
        if (original.isRecycled()) {
            throw new RuntimeException("Blur bitmap can't blur a recycled bitmap.");
        }

        Bitmap.Config config = original.getConfig();
        if (config != Bitmap.Config.ARGB_8888 && config != Bitmap.Config.RGB_565) {
            throw new RuntimeException("Blur bitmap only supported Bitmap.Config.ARGB_8888 and Bitmap.Config.RGB_565.");
        }

        return (original);
    }

    /**
     * StackBlur By Jni Bitmap
     *
     * @param original Original Image
     * @param radius   Blur radius
     * @return Image Bitmap
     */
    public static Bitmap onStackBlur(Bitmap original, int radius) {
        Bitmap bitmap = checkSource(original, radius);

        // Return this none blur
        if (radius == 1) {
            return bitmap;
        }

        //Jni BitMap Blur
        blurBitmap(bitmap, radius);

        return (bitmap);
    }


    /**
     * StackBlur By Jni Bitmap
     * in this we will cut the source bitmap to some parts.
     * We'll deal(blur) with it one by one. This will reduce the memory consumption.
     *
     * @param original Original Image
     * @param radius   Blur radius
     * @return Image Bitmap
     */
    public static Bitmap onStackBlurClip(Bitmap original, int radius) {
        Bitmap bitmap = checkSource(original, radius);

        // Return this none blur
        if (radius == 1) {
            return bitmap;
        }

        int h = bitmap.getHeight();
        int w = bitmap.getWidth();

        final int clipPixels = 1024 * 256;
        float clipScale = (h * w) / clipPixels;
        int minLen = radius + radius + 50;

        if (clipScale >= 2) {
            float itemLen = h / clipScale;
            itemLen = itemLen < minLen ? minLen : itemLen;
            clipScale = h / itemLen;
        }

        if (clipScale < 2) {
            //Jni BitMap Blur
            blurBitmap(bitmap, radius);
        } else {
            if (clipScale > 12)
                clipScale = 12;
            //Jni BitMap Blur
            onStackBlurClip(bitmap, radius, (int) clipScale);
        }

        return (bitmap);
    }

    /**
     * StackBlur By Jni Bitmap
     * in this we will cut the source bitmap to some parts.
     * We'll deal(blur) with it one by one. This will reduce the memory consumption.
     *
     * @param original Original Image
     * @param radius   Blur radius
     * @param parts    Blur cut parts count
     * @return Image Bitmap
     */
    public static Bitmap onStackBlurClip(Bitmap original, int radius, int parts) {
        Bitmap bitmap = checkSource(original, radius);
        if (parts < 2 || parts > 12) {
            throw new RuntimeException("Blur bitmap parts must >= 2 and <=12.");
        }

        if (original.getHeight() / parts < radius + radius) {
            throw new RuntimeException("Blur bitmap height/partsCount must > radius+radius values.");
        }

        //Jni BitMap Blur
        blurBitmapClip(bitmap, radius, parts);

        return (bitmap);
    }

    /**
     * StackBlur By Jni Pixels
     *
     * @param pix    Original Image, you can call:
     *               <p>
     *               int w = bitmap.getWidth();
     *               int h = bitmap.getHeight();
     *               int[] pix = new int[w * h];
     *               bitmap.getPixels(pix, 0, w, 0, 0, w, h);
     *               <p>
     *               // Jni Pixels Blur
     *               onStackBlurPixels(pix, w, h, radius);
     *               <p>
     *               bitmap.setPixels(pix, 0, w, 0, 0, w, h);
     *               <p>
     * @param radius Blur radius
     * @return Image Bitmap
     */
    public static int[] onStackBlurPixels(int[] pix, int w, int h, int radius) {
        if (radius < 0 || radius > 256) {
            throw new RuntimeException("Blur bitmap radius must >= 1 and <=256.");
        }

        if (pix == null) {
            throw new RuntimeException("Blur bitmap pix isn't null.");
        }

        if (pix.length < w * h) {
            throw new RuntimeException("Blur bitmap pix length must >= w * h.");
        }

        // Jni Pixels Blur
        blurPixels(pix, w, h, radius);

        return (pix);
    }
}
