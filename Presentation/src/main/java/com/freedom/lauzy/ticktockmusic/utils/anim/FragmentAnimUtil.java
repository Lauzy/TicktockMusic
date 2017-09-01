package com.freedom.lauzy.ticktockmusic.utils.anim;

import android.support.v4.app.Fragment;
import android.transition.Explode;
import android.transition.Fade;
import android.transition.Scene;
import android.transition.Slide;
import android.transition.Visibility;
import android.view.Gravity;
import android.view.animation.BounceInterpolator;
import android.view.animation.DecelerateInterpolator;

/**
 * Desc : Fragment切换动画
 * Author : Lauzy
 * Date : 2017/8/9
 * Blog : http://www.jianshu.com/u/e76853f863a9
 * Email : freedompaladin@gmail.com
 */
public class FragmentAnimUtil {
    public static void setEnterExitAnim(Fragment fragment) {
        Explode explode = new Explode();
        explode.setDuration(350);
        explode.setInterpolator(new DecelerateInterpolator());
        explode.setMode(Visibility.MODE_IN);
        Slide slide = new Slide();
        slide.setSlideEdge(Gravity.LEFT);
        slide.setDuration(300);
        Fade fade = new Fade();
        fade.setDuration(300);
        fragment.setReturnTransition(fade);
        fragment.setEnterTransition(explode);
        fragment.setSharedElementEnterTransition(new FragmentTransition());
        fragment.setSharedElementReturnTransition(new FragmentTransition());
    }
}
