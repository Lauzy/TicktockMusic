package com.lauzy.freedom.librarys.widght;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;

import com.bilibili.magicasakura.widgets.TintLinearLayout;
import com.lauzy.freedom.librarys.R;

/**
 * Desc : Custom the LinearLayout for better use of the theme changing.
 * Author : Lauzy
 * Date : 2017/7/4
 * Blog : http://www.jianshu.com/u/e76853f863a9
 * Email : freedompaladin@gmail.com
 */
public class TickLinearLayout extends TintLinearLayout {

    private boolean rippleEnabled;

    public TickLinearLayout(Context context) {
        super(context);
    }

    public TickLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public TickLinearLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    public boolean isRippleEnabled() {
        return rippleEnabled;
    }

    public void setRippleEnabled(boolean rippleEnabled) {
        this.rippleEnabled = rippleEnabled;
    }

    private void init(Context context, AttributeSet attrs) {
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.RippleValue);
        rippleEnabled = ta.getBoolean(R.styleable.RippleValue_enable_ripple, false);
        if (rippleEnabled) setRippleForeground(context);
        ta.recycle();
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
