package com.freedom.lauzy.ticktockmusic.ui.activity;

import android.Manifest;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;

import com.freedom.lauzy.ticktockmusic.R;
import com.freedom.lauzy.ticktockmusic.base.BaseActivity;
import com.lauzy.freedom.librarys.common.IntentUtil;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;

/**
 * Desc : Launch Activity
 * Author : Lauzy
 * Date : 2017/8/17
 * Blog : http://www.jianshu.com/u/e76853f863a9
 * Email : freedompaladin@gmail.com
 */
public class SplashActivity extends BaseActivity {

    private static final int LAUNCH_TIME = 100;
    private static final int SNACK_BAR_DURATION = 5000;

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_splash;
    }

    @Override
    protected void onStart() {
        super.onStart();
        new RxPermissions(this).request(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .subscribe(granted -> {
                    if (granted) {
                        startMainActivity();
                    } else {
                        showSnackBar();
                    }
                });
    }

    private void startMainActivity() {
        Observable.timer(LAUNCH_TIME, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread()).subscribe(aLong -> {
                    startActivity(new Intent(SplashActivity.this, MainActivity.class));
                    SplashActivity.this.finish();
                }
        );
    }

    private void showSnackBar() {
        Snackbar.make(findViewById(android.R.id.content), R.string.permission_denied,
                Snackbar.LENGTH_INDEFINITE)
                .setDuration(SNACK_BAR_DURATION)
                .setAction(R.string.open_permission, v ->
                        startActivity(IntentUtil.openSetting(SplashActivity.this)))
                .setActionTextColor(ContextCompat.getColor(SplashActivity.this
                        , R.color.theme_color_primary)).show();
    }
}
