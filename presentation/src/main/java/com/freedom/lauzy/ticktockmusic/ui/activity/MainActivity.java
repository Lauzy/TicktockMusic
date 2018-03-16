package com.freedom.lauzy.ticktockmusic.ui.activity;

import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
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
import com.freedom.lauzy.ticktockmusic.event.ClearFavoriteEvent;
import com.freedom.lauzy.ticktockmusic.event.ClearQueueEvent;
import com.freedom.lauzy.ticktockmusic.event.ClearRecentEvent;
import com.freedom.lauzy.ticktockmusic.event.ThemeEvent;
import com.freedom.lauzy.ticktockmusic.function.RxBus;
import com.freedom.lauzy.ticktockmusic.model.SongEntity;
import com.freedom.lauzy.ticktockmusic.presenter.MainPresenter;
import com.freedom.lauzy.ticktockmusic.service.MusicManager;
import com.freedom.lauzy.ticktockmusic.ui.fragment.AlbumDetailFragment;
import com.freedom.lauzy.ticktockmusic.ui.fragment.ArtistDetailFragment;
import com.freedom.lauzy.ticktockmusic.ui.fragment.FavoriteFragment;
import com.freedom.lauzy.ticktockmusic.ui.fragment.MusicFolderFragment;
import com.freedom.lauzy.ticktockmusic.ui.fragment.LocalMusicFragment;
import com.freedom.lauzy.ticktockmusic.ui.fragment.NetSongFragment;
import com.freedom.lauzy.ticktockmusic.ui.fragment.RecentFragment;
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
public class MainActivity extends BaseActivity<MainPresenter> implements
        NavigationView.OnNavigationItemSelectedListener, PlayPauseView.OnPlayPauseListener,
        MusicManager.MusicManageListener {

    private static final String TAG = "MainActivity";
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
    private TextView mNavTitle;
    private TextView mNavSinger;
    private ImageView mImgNavHeadBg;

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
        View headerView = mNavView.getHeaderView(0);
        mNavTitle = ((TextView) headerView.findViewById(R.id.txt_nav_song_title));
        mNavSinger = ((TextView) headerView.findViewById(R.id.txt_nav_song_singer));
        mImgNavHeadBg = ((ImageView) headerView.findViewById(R.id.img_nav_song));
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        loadFragments(savedInstanceState);
        subscribeDrawerEvent();
        subscribeQueueEvent();
        setDrawItemColor();
    }

    private void subscribeQueueEvent() {
        Disposable disposable = RxBus.INSTANCE.doDefaultSubscribe(ClearQueueEvent.class, clearQueueEvent -> {
            mPbCurSong.setProgress(0);
            mTxtCurSong.setText(R.string.app_name);
            mTxtCurSinger.setText(R.string.app_name);
            ImageLoader.INSTANCE.clean(MainActivity.this, mImgCurSong);
            mImgCurSong.setImageResource(R.drawable.ic_default);
            mPlayPauseView.pause();
            mPlayPauseView.setPlaying(false);
        });
        RxBus.INSTANCE.addDisposable(this, disposable);
    }

    /**
     * 订阅换肤事件
     */
    private void subscribeDrawerEvent() {
        Disposable disposable = RxBus.INSTANCE.doDefaultSubscribe(ThemeEvent.class,
                (themeEvent) -> setDrawItemColor());
        RxBus.INSTANCE.addDisposable(this, disposable);
    }

    /**
     * 更换指定View的皮肤
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
        mPlayPauseView.setEnable(MusicManager.getInstance().getCurrentSong() != null);
        mPlayPauseView.setOnPlayPauseListener(this);
        MusicManager.getInstance().setManageListener(this);
    }

    private Runnable mLmRunnable = () ->
            getSupportFragmentManager().beginTransaction().replace(R.id.layout_main,
                    LocalMusicFragment.newInstance()).commit();

    private Runnable mNcRunnable = () ->
            getSupportFragmentManager().beginTransaction().replace(R.id.layout_main,
                    NetSongFragment.newInstance()).commit();

    private Runnable mFolderRunnable = () ->
            getSupportFragmentManager().beginTransaction().replace(R.id.layout_main,
                    MusicFolderFragment.newInstance()).commit();

    private Runnable mFavoriteRunnable = () ->
            getSupportFragmentManager().beginTransaction().replace(R.id.layout_main,
                    FavoriteFragment.newInstance()).commit();

    private Runnable mRecentRunnable = () ->
            getSupportFragmentManager().beginTransaction().replace(R.id.layout_main,
                    RecentFragment.newInstance()).commit();

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Runnable drawerRunnable = null;
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.layout_main);
        switch (item.getItemId()) {
            case R.id.nav_music:
                if (!(fragment instanceof LocalMusicFragment) && !(fragment instanceof
                        AlbumDetailFragment) && !(fragment instanceof ArtistDetailFragment)) {
                    drawerRunnable = mLmRunnable;
                }
                break;
            case R.id.nav_net_song:
                if (!(fragment instanceof NetSongFragment)) {
                    drawerRunnable = mNcRunnable;
                }
                break;
            case R.id.nav_file_folder:
                if (!(fragment instanceof MusicFolderFragment)) {
                    drawerRunnable = mFolderRunnable;
                }
                break;
            case R.id.nav_favorite:
                if (!(fragment instanceof FavoriteFragment)) {
                    drawerRunnable = mFavoriteRunnable;
                }
                break;
            case R.id.nav_recent:
                if (!(fragment instanceof RecentFragment)) {
                    drawerRunnable = mRecentRunnable;
                }
                break;
            case R.id.nav_setting:
                mNavigator.navigateToSetting(MainActivity.this);
                break;
            case R.id.nav_about:
                break;
            case R.id.nav_exit:
                MusicManager.getInstance().quit();
                finish();
                break;

        }
        if (item.getItemId() != R.id.nav_setting && !(fragment instanceof AlbumDetailFragment) &&
                !(fragment instanceof ArtistDetailFragment)) {
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
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.layout_main);
        getMenuInflater().inflate(!(fragment instanceof FavoriteFragment || fragment instanceof RecentFragment)
                ? R.menu.menu_search : R.menu.menu_delete, menu);
        return !(fragment instanceof MusicFolderFragment);
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
                LogUtil.i(TAG, "Search");
                return true;
            case R.id.action_clear:
                postClearEvent();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * 发送清空列表事件
     */
    private void postClearEvent() {
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.layout_main);
        if (fragment instanceof FavoriteFragment) {
            RxBus.INSTANCE.post(new ClearFavoriteEvent());
        }
        if (fragment instanceof RecentFragment) {
            RxBus.INSTANCE.post(new ClearRecentEvent());
        }
    }

    /**
     * 判断是否为指定的fragment
     *
     * @return true or false
     */
    private boolean isNavigatingMain() {
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.layout_main);
        return (fragment instanceof LocalMusicFragment || fragment instanceof NetSongFragment
                || fragment instanceof FavoriteFragment || fragment instanceof RecentFragment
                || fragment instanceof MusicFolderFragment);
    }

    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
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
                navigateToPlayPage();
                break;
        }
    }

    private void navigateToPlayPage() {
        if (MusicManager.getInstance().getCurrentSong() == null) {
            return;
        }
        mNavigator.navigateToPlayActivity(this, mImgCurSong);
    }

    @Override
    public void play() {
        if (MusicManager.getInstance().getCurrentSong() != null) MusicManager.getInstance().start();
    }

    @Override
    public void pause() {
        if (MusicManager.getInstance().getCurrentSong() != null) MusicManager.getInstance().pause();
    }

    @Override
    public void onBufferingUpdate(MediaPlayer mediaPlayer, int percent) {
        //加载缓冲进度（100%后开始播放）
    }

    @Override
    public void onProgress(int progress, int duration) {
        mPbCurSong.setMax(duration);
        mPbCurSong.setProgress(progress);
    }

    @Override
    public void currentPlay(SongEntity songEntity) {
        mPlayPauseView.setEnable(true);
        if (!mPlayPauseView.isPlaying()) {
            mPlayPauseView.play();
        }
        setMusicBarView(songEntity);
        setNavHeadView(songEntity);
    }

    /**
     * 设置侧滑抽屉的属性
     *
     * @param songEntity 当前播放音乐
     */
    private void setNavHeadView(SongEntity songEntity) {
        mNavTitle.setText(songEntity.title);
        mNavSinger.setText(songEntity.artistName);
        ImageLoader.INSTANCE.display(MainActivity.this, new ImageConfig.Builder()
                .url(songEntity.albumCover)
                .isRound(false)
                .placeholder(R.drawable.ic_default)
                .into(mImgNavHeadBg).build());
        mImgNavHeadBg.setColorFilter(ContextCompat.getColor(MainActivity.this,
                R.color.colorDarkerTransparent), PorterDuff.Mode.SRC_OVER);
    }

    /**
     * 设置底部快捷控制条的属性
     *
     * @param songEntity 当前播放音乐
     */
    private void setMusicBarView(SongEntity songEntity) {
        if (!this.isDestroyed() && songEntity != null) {
            mTxtCurSong.setText(SubTextUtil.addEllipsis(songEntity.title, 15));
            mTxtCurSinger.setText(SubTextUtil.addEllipsis(songEntity.artistName, 15));
            ImageLoader.INSTANCE.display(MainActivity.this,
                    new ImageConfig.Builder()
                            .url(songEntity.albumCover)
                            .placeholder(R.drawable.ic_default)
                            .into(mImgCurSong).build());
        }
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

    @Override
    public void currentPauseSong(SongEntity songEntity) {
        mPbCurSong.setMax(MusicManager.getInstance().getDuration());
        mPbCurSong.setProgress((int) MusicManager.getInstance().getCurrentProgress());
        mPlayPauseView.pause();
        setMusicBarView(songEntity);
        setNavHeadView(songEntity);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RxBus.INSTANCE.dispose(this);
    }
}
