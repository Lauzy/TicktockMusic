package com.freedom.lauzy.ticktockmusic.utils.anim;

import android.support.v4.app.Fragment;

/**
 * Desc : Fragment切换动画
 * Author : Lauzy
 * Date : 2017/8/9
 * Blog : http://www.jianshu.com/u/e76853f863a9
 * Email : freedompaladin@gmail.com
 */
public class FragmentAnimUtil {
    public static void setEnterExitAnim(Fragment fragment) {
        fragment.setSharedElementEnterTransition(new FragmentTransition());
        /*Explode explode = new Explode();
        explode.setMode(Visibility.MODE_OUT);
        explode.setDuration(350);
        fragment.setEnterTransition(explode);*/
       /* Slide slide = new Slide();
        slide.setSlideEdge(Gravity.LEFT);
        slide.setDuration(300);
        fragment.setEnterTransition(slide);*/
        fragment.setSharedElementReturnTransition(new FragmentTransition());
    }
}
