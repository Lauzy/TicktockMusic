package com.lauzy.freedom.librarys.widght;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;

import com.bilibili.magicasakura.widgets.TintTextView;
import com.lauzy.freedom.librarys.R;
import com.lauzy.freedom.librarys.widght.fonts.FontsCache;

/**
 * Desc : Custom the TextView for better use of the theme changing.
 * Author : Lauzy
 * Date : 2017/7/4
 * Blog : http://www.jianshu.com/u/e76853f863a9
 * Email : freedompaladin@gmail.com
 */
public class TickTextView extends TintTextView {

    private boolean rippleEnabled;

    public TickTextView(Context context) {
        super(context);
    }

    public TickTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }


    public TickTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.TickTextView);
        rippleEnabled = ta.getBoolean(R.styleable.TickTextView_tv_enable_ripple, false);
        boolean fontEnable = ta.getBoolean(R.styleable.TickTextView_tv_enable_font_type, true);
        if (fontEnable) {
            setTypeface(FontsCache.getTypeface("fonts/fzltzxh.TTF", context));
            setPadding(0, 0, 30, 0);
        }
        if (rippleEnabled) setRippleForeground(context);
        ta.recycle();
    }

    public boolean isRippleEnabled() {
        return rippleEnabled;
    }

    public void setRippleEnabled(boolean rippleEnabled) {
        this.rippleEnabled = rippleEnabled;
    }

    private void setRippleForeground(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int[] ripple = new int[]{R.attr.selectableItemBackgroundBorderless};
            TypedArray typedArray = context.obtainStyledAttributes(ripple);
            int backgroundResource = typedArray.getResourceId(0, 0);
            typedArray.recycle();
            setForeground(ContextCompat.getDrawable(context, backgroundResource));
        }
    }
}
