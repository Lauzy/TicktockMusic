package com.lauzy.freedom.librarys.widght.fonts;

import android.support.annotation.NonNull;

/**
 * Desc : 截取字符串工具类
 * Author : Lauzy
 * Date : 2017/9/6
 * Blog : http://www.jianshu.com/u/e76853f863a9
 * Email : freedompaladin@gmail.com
 */
public class SubTextUtil {

    /**
     * 截取添加省略号
     * @param content 内容
     * @param subLength 截取长度
     * @return 截取后的字符串
     */
    public static String addEllipsis(@NonNull String content, int subLength) {
        if (content.length() > subLength) {
            content = content.substring(0, subLength) + "...";
        }
        return content;
    }
}
