package com.lauzy.freedom.librarys.common;

import android.content.Context;
import android.widget.Toast;

/**
 * Desc : Toast
 * Author : Lauzy
 * Date : 2017/9/7
 * Blog : http://www.jianshu.com/u/e76853f863a9
 * Email : freedompaladin@gmail.com
 */
@SuppressWarnings("unused")
public class ToastUtils {

    private ToastUtils() {
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    /**
     * 短时间显示Toast
     *
     * @param context context
     * @param message message
     */
    public static void showShort(Context context, CharSequence message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    /**
     * 短时间显示Toast
     *
     * @param context context
     * @param message message
     */
    public static void showShort(Context context, int message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    /**
     * 长时间显示Toast
     *
     * @param context context
     * @param message message
     */
    public static void showLong(Context context, CharSequence message) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }

    /**
     * 长时间显示Toast
     *
     * @param context context
     * @param message message
     */
    public static void showLong(Context context, int message) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }

    /**
     * 自定义显示Toast时间
     *
     * @param context  context
     * @param message  message
     * @param duration duration
     */
    public static void show(Context context, CharSequence message, int duration) {
        Toast.makeText(context, message, duration).show();
    }

    /**
     * 自定义显示Toast时间
     *
     * @param context  context
     * @param message  message
     * @param duration duration
     */
    public static void show(Context context, int message, int duration) {
        Toast.makeText(context, message, duration).show();
    }
    private static Toast sToast;

    /**
     * 防止重复创建Toast
     *
     * @param context context
     * @param message message
     */
    public static void showSingle(Context context, CharSequence message) {
        if (sToast == null) {
            sToast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
        } else {
            sToast.setText(message);
            sToast.setDuration(Toast.LENGTH_SHORT);
        }
        sToast.show();
    }

    public static void showLongSingle(Context context, CharSequence message) {
        if (sToast == null) {
            sToast = Toast.makeText(context, message, Toast.LENGTH_LONG);
        } else {
            sToast.setText(message);
            sToast.setDuration(Toast.LENGTH_LONG);
        }
        sToast.show();
    }

    public static void showDurSingle(Context context, CharSequence message,int dur) {
        if (sToast == null) {
            sToast = Toast.makeText(context, message, dur);
        } else {
            sToast.setText(message);
            sToast.setDuration(dur);
        }
        sToast.show();
    }
}
