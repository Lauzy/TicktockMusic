package com.freedom.lauzy.ticktockmusic.navigation;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.freedom.lauzy.ticktockmusic.R;
import com.freedom.lauzy.ticktockmusic.ui.activity.SettingActivity;
import com.freedom.lauzy.ticktockmusic.ui.fragment.AlbumDetailFragment;

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
                                             Uri url, long id) {
        Fragment fragment = AlbumDetailFragment.newInstance(url, transName, id);
//        Transition changeImage = TransitionInflater.from(context).inflateTransition(R.transition.image_transform);
        FragmentTransaction transaction = ((AppCompatActivity) context).getSupportFragmentManager().beginTransaction();
//        fragment.setSharedElementEnterTransition(changeImage);
//        fragment.setSharedElementReturnTransition(changeImage);
        transaction.addSharedElement(view, transName)
                .hide(((AppCompatActivity) context).getSupportFragmentManager()
                        .findFragmentById(R.id.layout_main))
                .add(R.id.layout_main, fragment)
                .addToBackStack(null)
                .commit();
    }
}
