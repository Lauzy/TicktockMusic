package com.freedom.lauzy.ticktockmusic.utils.anim;

import android.support.v4.view.ViewPager;
import android.view.View;

/**
 * Desc : ViewPager转场动画
 * Author : Lauzy
 * Date : 2018/3/8
 * Blog : http://www.jianshu.com/u/e76853f863a9
 * Email : freedompaladin@gmail.com
 */
public class PlayPagerTransformer implements ViewPager.PageTransformer {
    @Override
    public void transformPage(View page, float position) {
        page.setPivotX(position < 0 ? 0 : page.getWidth());
        page.setTranslationX(-page.getWidth() * position);
        page.setScaleX(position < 0 ? 1 + position : 1 - position);
    }
}
