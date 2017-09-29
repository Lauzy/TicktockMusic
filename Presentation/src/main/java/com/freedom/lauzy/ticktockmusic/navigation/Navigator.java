package com.freedom.lauzy.ticktockmusic.navigation;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.freedom.lauzy.ticktockmusic.R;
import com.freedom.lauzy.ticktockmusic.ui.activity.PlayActivity;
import com.freedom.lauzy.ticktockmusic.ui.activity.SettingActivity;
import com.freedom.lauzy.ticktockmusic.ui.fragment.AlbumDetailFragment;
import com.freedom.lauzy.ticktockmusic.ui.fragment.ArtistDetailFragment;
import com.freedom.lauzy.ticktockmusic.utils.anim.FragmentAnimUtil;

import javax.inject.Inject;

/**
 * Desc : Class used to navigate through the application.
 * Author : Lauzy
 * Date : 2017/7/5
 * Blog : http://www.jianshu.com/u/e76853f863a9
 * Email : freedompaladin@gmail.com
 */
public class Navigator {
    @Inject
    public Navigator() {
    }

    public void navigateToSetting(Context context) {
        if (context != null) {
            Intent intent = SettingActivity.newInstance(context);
            context.startActivity(intent);
        }
    }

    public static void navigateToAlbumDetail(Context context, ImageView view, String transName,
                                             String albumName, long id) {
        Fragment fragment = AlbumDetailFragment.newInstance(transName, albumName, id);
        FragmentTransaction transaction = ((AppCompatActivity) context).getSupportFragmentManager().beginTransaction();
        FragmentAnimUtil.setEnterExitAnim(fragment);
        if (view != null && transName != null)
            transaction
                    .addSharedElement(view, transName)
                    .hide(((AppCompatActivity) context).getSupportFragmentManager()
                            .findFragmentById(R.id.layout_main))
                    .add(R.id.layout_main, fragment)
                    .addToBackStack(null)
                    .commit();
    }

    public void navigateToPlayActivity(Context context) {
        if (context != null) {
            Intent intent = PlayActivity.newInstance(context);
            context.startActivity(intent);
        }
    }

    public static void navigateToArtistDetail(Context context, ImageView view, String transName,
                                              String artistName, long artistId, int albumNum, int songNum) {
        Fragment fragment = ArtistDetailFragment.newInstance(transName, artistName, artistId, albumNum, songNum);
        FragmentTransaction transaction = ((AppCompatActivity) context).getSupportFragmentManager().beginTransaction();
        FragmentAnimUtil.setEnterExitAnim(fragment);
        transaction
                .addSharedElement(view, transName)
                .hide(((AppCompatActivity) context).getSupportFragmentManager()
                        .findFragmentById(R.id.layout_main))
                .add(R.id.layout_main, fragment)
                .addToBackStack(null)
                .commit();
    }
}
