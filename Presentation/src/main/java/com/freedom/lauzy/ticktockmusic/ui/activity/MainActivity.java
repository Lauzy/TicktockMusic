package com.freedom.lauzy.ticktockmusic.ui.activity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.res.ColorStateList;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuItem;

import com.bilibili.magicasakura.utils.ThemeUtils;
import com.freedom.lauzy.ticktockmusic.IMusicInterface;
import com.freedom.lauzy.ticktockmusic.R;
import com.freedom.lauzy.ticktockmusic.RxBus;
import com.freedom.lauzy.ticktockmusic.base.BaseActivity;
import com.freedom.lauzy.ticktockmusic.event.SongEvent;
import com.freedom.lauzy.ticktockmusic.event.ThemeEvent;
import com.freedom.lauzy.ticktockmusic.presenter.MainPresenter;
import com.freedom.lauzy.ticktockmusic.service.MusicPlayer;
import com.freedom.lauzy.ticktockmusic.service.MusicService;
import com.freedom.lauzy.ticktockmusic.ui.fragment.LocalMusicFragment;
import com.freedom.lauzy.ticktockmusic.ui.fragment.NetSongFragment;
import com.lauzy.freedom.librarys.widght.TickProgressBar;
import com.lauzy.freedom.librarys.widght.music.PlayPauseView;

import butterknife.BindView;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

import static com.freedom.lauzy.ticktockmusic.service.MusicPlayer.sMusicService;

/**
 * Desc : Main
 * Author : Lauzy
 * Date : 2017/6/30
 * Blog : http://www.jianshu.com/u/e76853f863a9
 * Email : freedompaladin@gmail.com
 */
public class MainActivity extends BaseActivity<MainPresenter>
        implements NavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;
    @BindView(R.id.nav_view)
    NavigationView mNavView;
    @BindView(R.id.pb_cur_song)
    TickProgressBar mPbCurSong;
    @BindView(R.id.play_pause)
    PlayPauseView mPlayPauseView;
    private static final int FRAGMENT_CHANGE_DELAY = 400;
    private static final String SERVICE_ACTION = "com.freedom.lauzy.ticktockmusic.IMusicInterface";
    private Handler mDrawerHandler = new Handler();
    private ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            sMusicService = IMusicInterface.Stub.asInterface(service);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            sMusicService = null;
        }
    };

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_main;
    }

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected void initViews() {
        initDrawer();
    }

    private void initDrawer() {
        mNavView.setNavigationItemSelectedListener(this);
        mNavView.setCheckedItem(R.id.nav_music);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        loadFragments(savedInstanceState);
        subscribeDrawerEvent();
        subscribeSongEvent();
        setDrawItemColor();
    }

    /**
     * Subscribe the event of the drawer theme.
     */
    private void subscribeDrawerEvent() {
        Disposable disposable = RxBus.INSTANCE.doDefaultSubscribe(ThemeEvent.class,
                (themeEvent) -> setDrawItemColor());
        RxBus.INSTANCE.addDisposable(this, disposable);
    }

    /**
     * Change the theme of the drawer.
     */
    private void setDrawItemColor() {
        ColorStateList stateList = ThemeUtils.getThemeColorStateList(MainActivity.this, R.color.color_drawer_item);
        mNavView.setItemTextColor(stateList);
        mNavView.setItemIconTintList(stateList);
    }

    private void loadFragments(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            mDrawerHandler.post(mLmRunnable);
        }
    }

    @Override
    protected void loadData() {
        Intent intent = new Intent(SERVICE_ACTION);
        intent.setPackage(getPackageName());
        bindService(intent, mServiceConnection, Context.BIND_AUTO_CREATE);
        mPlayPauseView.setPlayPauseListener(new PlayPauseView.PlayPauseListener() {
            @Override
            public void play() {
            }

            @Override
            public void pause() {

            }
        });
    }

    private void subscribeSongEvent() {
        MusicService musicService = new MusicService();
        Disposable disposable = RxBus.INSTANCE.doDefaultSubscribe(SongEvent.class, new Consumer<SongEvent>() {
            @Override
            public void accept(@io.reactivex.annotations.NonNull SongEvent songEvent) throws Exception {
                mPbCurSong.setMax((int) songEvent.getSongEntity().duration);
                mPbCurSong.setProgress((int) musicService.getProgress());
            }
        });
    }

    private Runnable mLmRunnable = () -> {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.layout_main, LocalMusicFragment.newInstance()).commit();
    };

    private Runnable mNcRunnable = () -> {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.layout_main, NetSongFragment.newInstance()).commit();
    };

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Runnable drawerRunnable = null;
        switch (item.getItemId()) {
            case R.id.nav_music:
                drawerRunnable = mLmRunnable;
                break;
            case R.id.nav_net_song:
                drawerRunnable = mNcRunnable;
                break;
            case R.id.nav_favorite:
                break;
            case R.id.nav_recent:
                break;
            case R.id.nav_setting:
                mNavigator.navigateToSetting(MainActivity.this);
                break;
            case R.id.nav_about:
                break;
            case R.id.nav_exit:
                break;

        }
        if (item.getItemId() != R.id.nav_setting) {
            getSupportFragmentManager().popBackStackImmediate();
        }
        if (drawerRunnable != null) {
            mDrawerHandler.postDelayed(drawerRunnable, FRAGMENT_CHANGE_DELAY);
        }
        mDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                if (isNavigatingMain()) {
                    mDrawerLayout.openDrawer(GravityCompat.START);
                } else {
                    super.onBackPressed();
                }
                return true;
            case R.id.action_search:
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private boolean isNavigatingMain() {
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.layout_main);
        return (fragment instanceof LocalMusicFragment || fragment instanceof NetSongFragment);
    }

    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RxBus.INSTANCE.dispose(this);
        if (mServiceConnection != null) {
            unbindService(mServiceConnection);
        }
    }
}
