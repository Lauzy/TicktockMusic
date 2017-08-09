package com.lauzy.freedom.librarys.widght.fonts;

import android.content.Context;
import android.graphics.Typeface;

import java.util.HashMap;

/**
 * Desc : 字体缓存
 * Author : Lauzy
 * Date : 2017/8/9
 * Blog : http://www.jianshu.com/u/e76853f863a9
 * Email : freedompaladin@gmail.com
 */
public class FontsCache {
    private static HashMap<String, Typeface> mFontCache = new HashMap<>();

    public static Typeface getTypeface(String fontName, Context context) {
        Typeface typeface = mFontCache.get(fontName);
        if (typeface == null) {
            try {
                typeface = Typeface.createFromAsset(context.getAssets(), fontName);
            } catch (Exception e) {
                return null;
            }
            mFontCache.put(fontName, typeface);
        }
        return typeface;
    }
}
