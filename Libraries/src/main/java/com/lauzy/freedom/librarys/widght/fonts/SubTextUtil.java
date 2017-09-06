package com.lauzy.freedom.librarys.widght.fonts;

import android.support.annotation.NonNull;

/**
 * Desc : 截取字符串
 * Author : Lauzy
 * Date : 2017/9/6
 * Blog : http://www.jianshu.com/u/e76853f863a9
 * Email : freedompaladin@gmail.com
 */
public class SubTextUtil {

    public static String addEllipsis(@NonNull String content, int subLength) {
        if (content.length() > subLength) {
            content = content.substring(0, subLength) + "...";
        }
        return content;
    }
}
