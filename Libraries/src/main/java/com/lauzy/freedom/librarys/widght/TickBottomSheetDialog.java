package com.lauzy.freedom.librarys.widght;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.StyleRes;
import android.support.design.widget.BottomSheetDialog;
import android.view.ViewGroup;

import com.lauzy.freedom.librarys.common.ScreenUtils;

/**
 * Desc : 自定义修复状态栏变黑的问题
 * Author : Lauzy
 * Date : 2017/10/11
 * Blog : http://www.jianshu.com/u/e76853f863a9
 * Email : freedompaladin@gmail.com
 */
public class TickBottomSheetDialog extends BottomSheetDialog {

    private Context mContext;

    public TickBottomSheetDialog(@NonNull Context context) {
        this(context, 0);
    }

    public TickBottomSheetDialog(@NonNull Context context, @StyleRes int theme) {
        super(context, theme);
        mContext = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getWindow() != null) {
            getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,
                    ScreenUtils.getScreenHeight(mContext) - ScreenUtils.getStatusHeight(mContext));
        }
    }
}
