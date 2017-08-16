package com.freedom.lauzy.ticktockmusic.navigation;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.freedom.lauzy.ticktockmusic.R;
import com.freedom.lauzy.ticktockmusic.ui.SettingActivity;
import com.freedom.lauzy.ticktockmusic.ui.activity.AlbumDetailActivity;
import com.freedom.lauzy.ticktockmusic.ui.fragment.NetSongListFragment;
import com.freedom.lauzy.ticktockmusic.utils.anim.FragmentAnimUtil;

import javax.inject.Inject;

/**
 * Desc : Class used to navigate through the application.
 * Author : Lauzy
 * Date : 2017/7/5
 * Blog : http://www.jianshu.com/u/e76853f863a9
 * Email : freedompaladin@gmail.com
 */
@SuppressWarnings("unused")
public class Navigator {

    @Inject
    public Navigator() {
    }

    /**
     * Goes to SettingActivity.
     *
     * @param context A Context needed to open the destiny activity.
     */
    public void navigateToSetting(Context context) {
        if (context != null) {
            Intent intent = SettingActivity.newInstance(context);
            context.startActivity(intent);
        }
    }

    public void navigateToAlbumDetail(Context context, long id) {
        if (context != null) {
            Intent intent = AlbumDetailActivity.newInstance(context, id);
            context.startActivity(intent);
        }
    }

    public static void navigateToSongList(Activity context, ImageView view, int type) {
        FragmentTransaction transaction = ((AppCompatActivity) context)
                .getSupportFragmentManager().beginTransaction();
        Fragment fragment = NetSongListFragment.newInstance(type);
        transaction.hide(((AppCompatActivity) context).getSupportFragmentManager()
                .findFragmentById(R.id.layout_main));
        FragmentAnimUtil.setEnterExitAnim(fragment);
        transaction.addSharedElement(view, ViewCompat.getTransitionName(view))
                .add(R.id.layout_main, fragment)
                .addToBackStack(null)
                .commit();
    }
}
