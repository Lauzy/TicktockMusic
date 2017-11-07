package com.freedom.lauzy.ticktockmusic.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;

import com.afollestad.materialdialogs.color.ColorChooserDialog;
import com.freedom.lauzy.ticktockmusic.R;
import com.freedom.lauzy.ticktockmusic.function.RxBus;
import com.freedom.lauzy.ticktockmusic.event.ThemeEvent;
import com.freedom.lauzy.ticktockmusic.base.BaseActivity;
import com.freedom.lauzy.ticktockmusic.utils.ThemeHelper;
import com.lauzy.freedom.librarys.widght.CircleImageView;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Desc : SettingActivity
 * Author : Lauzy
 * Date : 2017/7/5
 * Blog : http://www.jianshu.com/u/e76853f863a9
 * Email : freedompaladin@gmail.com
 */
public class SettingActivity extends BaseActivity implements ColorChooserDialog.ColorCallback {

    @BindView(R.id.img_choose_color)
    CircleImageView mImgChooseColor;

    public static Intent newInstance(Context context) {
        return new Intent(context, SettingActivity.class);
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_setting;
    }

    @Override
    protected void initViews() {
        showBackIcon();
        setToolbarTitle(getResources().getString(R.string.drawer_setting));
    }

    @OnClick(R.id.layout_choose_theme)
    public void onClick() {
        ColorChooserDialog.Builder builder = new ColorChooserDialog.Builder(this, R.string.choose_theme)
                .customColors(R.array.ThemeColorArr, null)
                .doneButton(R.string.confirm)
                .cancelButton(R.string.cancel)
                .allowUserColorInput(false)
                .allowUserColorInputAlpha(false)
                .preselect(ThemeHelper.getThemeColorResId(this));
        builder.show();
    }

    @Override
    public void onColorSelection(@NonNull ColorChooserDialog dialog, @ColorInt int selectedColor) {
        ThemeHelper.setSelectedTheme(SettingActivity.this, selectedColor);
        RxBus.INSTANCE.post(new ThemeEvent());
        RxBus.INSTANCE.postSticky(new ThemeEvent());
    }

    @Override
    public void onColorChooserDismissed(@NonNull ColorChooserDialog dialog) {

    }
}
