package com.freedom.lauzy.ticktockmusic.utils;

import android.support.annotation.ColorInt;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;

import com.lauzy.freedom.librarys.common.StringUtil;

/**
 * Desc : 文字高亮工具类
 * Author : Lauzy
 * Date : 2017/11/22
 * Blog : http://www.jianshu.com/u/e76853f863a9
 * Email : freedompaladin@gmail.com
 */
public class HighlightFormatUtil {

    private SpannableStringBuilder mBuilder;
    private String mWholeStr;
    private String mHighlightStr;
    private int mColor;

    public HighlightFormatUtil(String wholeStr, String highlightStr, @ColorInt int color) {
        mWholeStr = wholeStr;
        mHighlightStr = highlightStr;
        mColor = color;
    }

    public HighlightFormatUtil fillColor() {
        int start = 0;
        int end = 0;
        if (!StringUtil.isEmpty(mWholeStr) && !StringUtil.isEmpty(mHighlightStr)) {
            if (mWholeStr.toLowerCase().contains(mHighlightStr.toLowerCase())) {
                start = mWholeStr.toLowerCase().indexOf(mHighlightStr.toLowerCase());
                end = start + mHighlightStr.length();
            }
        }
        if (end != 0) {
            mBuilder = new SpannableStringBuilder(mWholeStr);
            mBuilder.setSpan(new ForegroundColorSpan(mColor), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        return this;
    }

    public CharSequence getResult() {
        return mBuilder != null ? mBuilder : (mWholeStr != null ? mWholeStr : "-");
    }
}
