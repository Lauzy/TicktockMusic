package com.lauzy.freedom.librarys.common;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.lauzy.freedom.librarys.BuildConfig;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;

/**
 * Desc : Utils for log
 * Author : Lauzy
 * Date : 2017/6/29
 * Blog : http://www.jianshu.com/u/e76853f863a9
 * Email : freedompaladin@gmail.com
 */
public class LogUtil {

    private static final String LTAG = "NullTag";

    static {
        Logger.addLogAdapter(new AndroidLogAdapter());
    }

    public static void e(@NonNull String tag, String content) {
        if (BuildConfig.DEBUG && !TextUtils.isEmpty(content))
            Logger.t(checkTag(tag)).e(content);
    }

    public static void i(@NonNull String tag, String content) {
        if (BuildConfig.DEBUG && !TextUtils.isEmpty(content))
            Logger.t(checkTag(tag)).i(content);
    }

    public static void d(@NonNull String tag, String content) {
        if (BuildConfig.DEBUG && !TextUtils.isEmpty(content))
            Logger.t(checkTag(tag)).d(content);
    }

    public static void json(String content) {
        if (BuildConfig.DEBUG && !TextUtils.isEmpty(content))
            Logger.json(content);
    }

    private static String checkTag(String tag) {
        return !TextUtils.isEmpty(tag) ? tag : LTAG;
    }
}
