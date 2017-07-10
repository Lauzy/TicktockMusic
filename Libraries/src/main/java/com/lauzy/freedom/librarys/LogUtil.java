package com.lauzy.freedom.librarys;

import android.support.annotation.NonNull;
import android.text.TextUtils;

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

    private static Mode MODE = Mode.DEVOLOP;
    private static final String LTAG = "NullTag";

    public enum Mode {
        DEVOLOP,
        RELEASE
    }

    static {
        Logger.addLogAdapter(new AndroidLogAdapter());
    }

    /**
     * Sets DEVELOP or RELEASE modd
     *
     * @param mode DEBELOP(show log) or RELEASE(not show)
     */
    public static void setLogMode(Mode mode) {
        MODE = mode;
    }

    public static void e(@NonNull String tag, String content) {
        if (MODE == Mode.DEVOLOP && !TextUtils.isEmpty(content))
            Logger.t(checkTag(tag)).e(content);
    }

    public static void i(@NonNull String tag, String content) {
        if (MODE == Mode.DEVOLOP && !TextUtils.isEmpty(content))
            Logger.t(checkTag(tag)).i(content);
    }

    public static void d(@NonNull String tag, String content) {
        if (MODE == Mode.DEVOLOP && !TextUtils.isEmpty(content))
            Logger.t(checkTag(tag)).d(content);
    }

    public static void json(String content) {
        if (MODE == Mode.DEVOLOP && !TextUtils.isEmpty(content))
            Logger.json(content);
    }

    private static String checkTag(String tag) {
        return !TextUtils.isEmpty(tag) ? tag : LTAG;
    }
}
