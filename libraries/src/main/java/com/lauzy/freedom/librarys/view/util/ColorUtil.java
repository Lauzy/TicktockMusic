package com.lauzy.freedom.librarys.view.util;

import android.graphics.Color;
import android.support.annotation.ColorInt;

/**
 * Desc : 颜色工具类
 * Author : Lauzy
 * Date : 2018/3/13
 * Blog : http://www.jianshu.com/u/e76853f863a9
 * Email : freedompaladin@gmail.com
 */
public class ColorUtil {

    /**
     * 判断深浅色
     * @param color 颜色值
     * @return 是否为深色
     */
    public static boolean isDarkColor(@ColorInt int color) {
        double rgb = Color.red(color) * 0.299 + Color.green(color) * 0.587 + Color.blue(color) * 0.114;
        return rgb <= 192;
    }
}
