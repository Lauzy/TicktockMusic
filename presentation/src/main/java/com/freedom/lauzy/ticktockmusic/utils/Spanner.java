package com.freedom.lauzy.ticktockmusic.utils;

import android.graphics.Typeface;
import android.support.annotation.ColorInt;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;

/**
 * 拼接字符串工具
 * Created by Lauzy on 2017/2/17.
 */
public class Spanner {

    private SpannableStringBuilder spannableStringBuilder;

    public Spanner() {
        spannableStringBuilder = new SpannableStringBuilder();
    }

    public Spanner(String content) {
        spannableStringBuilder = new SpannableStringBuilder(content);
    }

    public Spanner setSize(int dpSize, int start, int end) {
        spannableStringBuilder.setSpan(new AbsoluteSizeSpan(dpSize, true), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return this;
    }

    public Spanner setColor(@ColorInt int color, int start, int end) {
        spannableStringBuilder.setSpan(new ForegroundColorSpan(color), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return this;
    }

    public Spanner setStyle(int start, int end) {
        spannableStringBuilder.setSpan(new StyleSpan(Typeface.BOLD), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return this;
    }

    public Spanner appendSpan(CharSequence text, Object span) {

        if (TextUtils.isEmpty(text)) {
            return this;
        }

        int start = spannableStringBuilder.length();
        int end = start + text.length();
        spannableStringBuilder.append(text);
        spannableStringBuilder.setSpan(span, start, end, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        return this;
    }

    public SpannableStringBuilder build() {

        return spannableStringBuilder;
    }
}
