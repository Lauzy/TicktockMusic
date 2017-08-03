package com.freedom.lauzy.ticktockmusic.module.ui.activity;

import android.content.res.ColorStateList;
import android.os.Bundle;
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
import com.freedom.lauzy.ticktockmusic.R;
import com.freedom.lauzy.ticktockmusic.RxBus;
import com.freedom.lauzy.ticktockmusic.base.BaseActivity;
import com.freedom.lauzy.ticktockmusic.event.ThemeEvent;
import com.freedom.lauzy.ticktockmusic.module.MainPresenter;
import com.freedom.lauzy.ticktockmusic.module.ui.fragment.LocalMusicFragment;
import com.freedom.lauzy.ticktockmusic.module.ui.fragment.NetMusicFragment;

import butterknife.BindView;
import io.reactivex.disposables.Disposable;

public class MainActivity extends BaseActivity<MainPresenter>
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final String NET_MUSIC_FRAGMENT = "NetMusicFragment";
    private static final String LOCAL_MUSIC_FRAGMENT = "LocalMusicFragment";
    @BindView(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;
    @BindView(R.id.nav_view)
    NavigationView mNavView;
    private LocalMusicFragment mLocalMusicFragment;
    private NetMusicFragment mNetMusicFragment;

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
        super.onCreate(savedInstanceState);
        loadFragments(savedInstanceState);
        subscribeDrawerEvent();
        setDrawItemColor();
    }

    private void subscribeDrawerEvent() {
        Disposable disposable = RxBus.INSTANCE.doDefaultSubscribe(ThemeEvent.class,
                (themeEvent) -> setDrawItemColor());
        RxBus.INSTANCE.addDisposable(this, disposable);
    }

    /**
     * 更换抽屉的主题色彩
     */
    private void setDrawItemColor() {
        ColorStateList stateList = ThemeUtils.getThemeColorStateList(MainActivity.this, R.color.color_drawer_item);
        mNavView.setItemTextColor(stateList);
        mNavView.setItemIconTintList(stateList);
    }

    private void loadFragments(Bundle savedInstanceState) {
        if (null == savedInstanceState) {
            mLocalMusicFragment = LocalMusicFragment.newInstance();
            mNetMusicFragment = NetMusicFragment.newInstance();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.add(R.id.layout_main, mLocalMusicFragment, LOCAL_MUSIC_FRAGMENT)
                    .add(R.id.layout_main, mNetMusicFragment, NET_MUSIC_FRAGMENT)
                    .hide(mNetMusicFragment)
                    .show(mLocalMusicFragment)
                    .commit();
        } else {
            mLocalMusicFragment = (LocalMusicFragment) getSupportFragmentManager()
                    .findFragmentByTag(LOCAL_MUSIC_FRAGMENT);
            mNetMusicFragment = (NetMusicFragment) getSupportFragmentManager()
                    .findFragmentByTag(NET_MUSIC_FRAGMENT);
            getSupportFragmentManager().beginTransaction()
                    .show(mLocalMusicFragment)
                    .hide(mNetMusicFragment)
                    .commit();
        }
    }

    @Override
    protected void loadData() {
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        switch (item.getItemId()) {
            case R.id.nav_music:
                transaction.show(mLocalMusicFragment).hide(mNetMusicFragment);
                break;
            case R.id.nav_favorite:
                transaction.show(mNetMusicFragment).hide(mLocalMusicFragment);
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
        transaction.commit();
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
        return (fragment instanceof LocalMusicFragment || fragment instanceof NetMusicFragment);
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
    }
}
