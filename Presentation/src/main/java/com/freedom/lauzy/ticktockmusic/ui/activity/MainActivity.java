package com.freedom.lauzy.ticktockmusic.ui.activity;

import android.content.res.ColorStateList;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bilibili.magicasakura.utils.ThemeUtils;
import com.freedom.lauzy.ticktockmusic.R;
import com.freedom.lauzy.ticktockmusic.base.BaseActivity;
import com.freedom.lauzy.ticktockmusic.event.ThemeEvent;
import com.freedom.lauzy.ticktockmusic.function.RxBus;
import com.freedom.lauzy.ticktockmusic.model.SongEntity;
import com.freedom.lauzy.ticktockmusic.presenter.MainPresenter;
import com.freedom.lauzy.ticktockmusic.service.MusicManager;
import com.freedom.lauzy.ticktockmusic.ui.fragment.AlbumDetailFragment;
import com.freedom.lauzy.ticktockmusic.ui.fragment.LocalMusicFragment;
import com.freedom.lauzy.ticktockmusic.ui.fragment.NetSongFragment;
import com.lauzy.freedom.librarys.common.LogUtil;
import com.lauzy.freedom.librarys.imageload.ImageConfig;
import com.lauzy.freedom.librarys.imageload.ImageLoader;
import com.lauzy.freedom.librarys.widght.TickProgressBar;
import com.lauzy.freedom.librarys.widght.fonts.SubTextUtil;
import com.lauzy.freedom.librarys.widght.music.PlayPauseView;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.disposables.Disposable;

/**
 * Desc : Main
 * Author : Lauzy
 * Date : 2017/6/30
 * Blog : http://www.jianshu.com/u/e76853f863a9
 * Email : freedompaladin@gmail.com
 */
public class MainActivity extends BaseActivity<MainPresenter>
        implements NavigationView.OnNavigationItemSelectedListener, PlayPauseView.PlayPauseListener,
        MusicManager.MusicManageListener {

    private static final String LYTAG = "MainActivity";
    @BindView(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;
    @BindView(R.id.nav_view)
    NavigationView mNavView;
    @BindView(R.id.pb_cur_song)
    TickProgressBar mPbCurSong;
    @BindView(R.id.play_pause)
    PlayPauseView mPlayPauseView;
    @BindView(R.id.img_cur_song)
    ImageView mImgCurSong;
    @BindView(R.id.txt_cur_song)
    TextView mTxtCurSong;
    @BindView(R.id.txt_cur_singer)
    TextView mTxtCurSinger;
    private static final int FRAGMENT_CHANGE_DELAY = 400;
    private Handler mDrawerHandler = new Handler();

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
        MusicManager.getInstance().bindPlayService();
        MusicManager.getInstance().startService();
        playMusic();
    }

    private void playMusic() {
        mPlayPauseView.setPlayPauseListener(this);
        MusicManager.getInstance().setManageListener(this);
    }

    private Runnable mLmRunnable = () ->
            getSupportFragmentManager().beginTransaction().replace(R.id.layout_main,
                    LocalMusicFragment.newInstance()).commit();

    private Runnable mNcRunnable = () ->
            getSupportFragmentManager().beginTransaction().replace(R.id.layout_main,
                    NetSongFragment.newInstance()).commit();

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Runnable drawerRunnable = null;
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.layout_main);
        switch (item.getItemId()) {
            case R.id.nav_music:
                if (!(fragment instanceof LocalMusicFragment) && !(fragment instanceof
                        AlbumDetailFragment)) {
                    drawerRunnable = mLmRunnable;
                }
                break;
            case R.id.nav_net_song:
                if (!(fragment instanceof NetSongFragment)) {
                    drawerRunnable = mNcRunnable;
                }
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
        if (item.getItemId() != R.id.nav_setting && !(fragment instanceof AlbumDetailFragment)) {
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
    }

    @OnClick({R.id.img_play_next, R.id.img_play_last, R.id.layout_music_bar})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_play_next:
                MusicManager.getInstance().skipToNext();
                break;
            case R.id.img_play_last:
                MusicManager.getInstance().skipToPrevious();
                break;
            case R.id.layout_music_bar:
                mNavigator.navigateToPlayFragment(this);
                break;
        }
    }

    @Override
    public void play() {
        MusicManager.getInstance().start();
    }

    @Override
    public void pause() {
        MusicManager.getInstance().pause();
    }

    @Override
    public void onBufferingUpdate(MediaPlayer mediaPlayer, int percent) {
        mPbCurSong.setSecondaryProgress(percent);
        LogUtil.i(LYTAG, "mPreferences");
    }

    @Override
    public void onProgress(int progress, int duration) {
        mPbCurSong.setMax(duration);
        mPbCurSong.setProgress(progress);
    }


    @Override
    public void currentPlay(SongEntity songEntity) {
        if (!mPlayPauseView.isPlaying()) {
            mPlayPauseView.play();
        }
        RxBus.INSTANCE.postSticky(songEntity);
        setMusicBarView(songEntity);
    }

    @Override
    public void onPlayerPause() {
        if (mPlayPauseView.isPlaying()) {
            mPlayPauseView.pause();
        }
    }

    @Override
    public void onPlayerResume() {
        if (!mPlayPauseView.isPlaying()) {
            mPlayPauseView.play();
        }
    }

    private void setMusicBarView(SongEntity songEntity) {
        if (!this.isDestroyed()) {
            mTxtCurSong.setText(SubTextUtil.addEllipsis(songEntity.title, 15));
            mTxtCurSinger.setText(SubTextUtil.addEllipsis(songEntity.artistName, 15));
            ImageLoader.INSTANCE.display(MainActivity.this,
                    new ImageConfig.Builder()
                            .url(songEntity.albumCover)
                            .placeholder(R.drawable.ic_album_default)
                            .into(mImgCurSong).build());
        }
    }
}
