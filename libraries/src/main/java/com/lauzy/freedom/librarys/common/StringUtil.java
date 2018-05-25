package com.lauzy.freedom.librarys.common;

/**
 * Desc : 字符串工具类
 * Author : Lauzy
 * Date : 2018/5/25
 * Blog : http://www.jianshu.com/u/e76853f863a9
 * Email : freedompaladin@gmail.com
 */
public class StringUtil {

    public static boolean isEmpty(CharSequence charSequence) {
        return charSequence == null || charSequence.length() <= 0;
    }
}
