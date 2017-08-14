package com.freedom.lauzy.ticktockmusic.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.support.annotation.ColorRes;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;

import com.bilibili.magicasakura.utils.ThemeUtils;
import com.freedom.lauzy.ticktockmusic.R;

/**
 * Desc : Theme Switch Helper
 * Author : Lauzy
 * Date : 2017/7/4
 * Blog : http://www.jianshu.com/u/e76853f863a9
 * Email : freedompaladin@gmail.com
 */
public class ThemeHelper {
    private static final String CURRENT_THEME = "theme_current";
    private static final String THEME_CONFIG = "theme_config";

    public static final int CARD_SAKURA = 0x1;
    public static final int CARD_HOPE = 0x2;
    public static final int CARD_STORM = 0x3;
    public static final int CARD_WOOD = 0x4;
    public static final int CARD_LIGHT = 0x5;
    public static final int CARD_THUNDER = 0x6;
    public static final int CARD_SAND = 0x7;
    public static final int CARD_FIREY = 0x8;

    public static final String THEME_PINK = "pink";
    public static final String THEME_BLUE = "blue";
    public static final String THEME_PURPLE = "purple";
    public static final String THEME_GREEN = "green";
    public static final String THEME_GREEN_LIGHT = "green_light";
    public static final String THEME_YELLOW = "yellow";
    public static final String THEME_ORANGE = "orange";
    public static final String THEME_RED = "red";

    public static SharedPreferences getSharePreference(Context context) {
        return context.getSharedPreferences(THEME_CONFIG, Context.MODE_PRIVATE);
    }

    public static void setTheme(Context context, int themeId) {
        getSharePreference(context).edit()
                .putInt(CURRENT_THEME, themeId)
                .apply();
    }

    public static int getTheme(Context context) {
        return getSharePreference(context).getInt(CURRENT_THEME, CARD_SAKURA);
    }

    public static boolean isDefaultTheme(Context context) {
        return getTheme(context) == CARD_SAKURA;
    }

    public static String getThemeName(Context context) {
        if (ThemeHelper.getTheme(context) == ThemeHelper.CARD_SAKURA) {
            return THEME_PINK;
        } else if (ThemeHelper.getTheme(context) == ThemeHelper.CARD_STORM) {
            return THEME_BLUE;
        } else if (ThemeHelper.getTheme(context) == ThemeHelper.CARD_HOPE) {
            return THEME_PURPLE;
        } else if (ThemeHelper.getTheme(context) == ThemeHelper.CARD_WOOD) {
            return THEME_GREEN;
        } else if (ThemeHelper.getTheme(context) == ThemeHelper.CARD_LIGHT) {
            return THEME_GREEN_LIGHT;
        } else if (ThemeHelper.getTheme(context) == ThemeHelper.CARD_THUNDER) {
            return THEME_YELLOW;
        } else if (ThemeHelper.getTheme(context) == ThemeHelper.CARD_SAND) {
            return THEME_ORANGE;
        } else if (ThemeHelper.getTheme(context) == ThemeHelper.CARD_FIREY) {
            return THEME_RED;
        }
        return null;
    }


    public static int getThemeColorResId(Context context) {
        switch (ThemeHelper.getTheme(context)) {
            case ThemeHelper.CARD_SAKURA:
                return ContextCompat.getColor(context, R.color.pink);
            case ThemeHelper.CARD_FIREY:
                return ContextCompat.getColor(context, R.color.red);
            case ThemeHelper.CARD_HOPE:
                return ContextCompat.getColor(context, R.color.purple);
            case ThemeHelper.CARD_LIGHT:
                return ContextCompat.getColor(context, R.color.green_light);
            case ThemeHelper.CARD_SAND:
                return ContextCompat.getColor(context, R.color.orange);
            case ThemeHelper.CARD_STORM:
                return ContextCompat.getColor(context, R.color.blue);
            case ThemeHelper.CARD_THUNDER:
                return ContextCompat.getColor(context, R.color.yellow);
            case ThemeHelper.CARD_WOOD:
                return ContextCompat.getColor(context, R.color.green);
            default:
                return -1;
        }
    }

    public static void setSelectedTheme(Context context, int colorRes) {
        int currentTheme = CARD_SAKURA;
        if (colorRes == ContextCompat.getColor(context, R.color.pink)) {
            currentTheme = CARD_SAKURA;
        } else if (colorRes == ContextCompat.getColor(context, R.color.red)) {
            currentTheme = CARD_FIREY;
        } else if (colorRes == ContextCompat.getColor(context, R.color.purple)) {
            currentTheme = CARD_HOPE;
        } else if (colorRes == ContextCompat.getColor(context, R.color.green_light)) {
            currentTheme = CARD_LIGHT;
        } else if (colorRes == ContextCompat.getColor(context, R.color.orange)) {
            currentTheme = CARD_SAND;
        } else if (colorRes == ContextCompat.getColor(context, R.color.blue)) {
            currentTheme = CARD_STORM;
        } else if (colorRes == ContextCompat.getColor(context, R.color.yellow)) {
            currentTheme = CARD_THUNDER;
        } else if (colorRes == ContextCompat.getColor(context, R.color.green)) {
            currentTheme = CARD_WOOD;
        }
        ThemeHelper.setTheme(context, currentTheme);
    }

    @ColorRes
    public static int getThemeColor(Context context, int color, String theme) {
        switch (color) {
            case 0xfffb7299:
                return context.getResources().getIdentifier(theme, "color", context.getPackageName());
            case 0xffb85671:
                return context.getResources().getIdentifier(theme + "_dark", "color", context.getPackageName());
            case 0x99f0486c:
                return context.getResources().getIdentifier(theme + "_trans", "color", context.getPackageName());
        }
        return -1;
    }

    @ColorRes
    public static int getThemeColorId(Context context, int colorId, String theme) {
        switch (colorId) {
            case R.color.theme_color_primary:
                return context.getResources().getIdentifier(theme, "color", context.getPackageName());
            case R.color.theme_color_primary_dark:
                return context.getResources().getIdentifier(theme + "_dark", "color", context.getPackageName());
            case R.color.theme_color_primary_trans:
                return context.getResources().getIdentifier(theme + "_trans", "color", context.getPackageName());
        }
        return colorId;
    }

    public static void setThemeColor(Context context, View... views) {
        ColorStateList stateList = ThemeUtils.getThemeColorStateList(context, R.color.theme_color_primary);
        for (View view : views) {
            view.setBackgroundColor(stateList.getDefaultColor());
        }
    }

    public static void setThemeColor(Context context, SwipeRefreshLayout refreshLayout) {
        ColorStateList stateList = ThemeUtils.getThemeColorStateList(context, R.color.theme_color_primary);
        refreshLayout.setColorSchemeColors(stateList.getDefaultColor());
    }
}
