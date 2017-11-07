package com.freedom.lauzy.ticktockmusic.ui.fragment;

import android.content.res.ColorStateList;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.bilibili.magicasakura.utils.ThemeUtils;
import com.freedom.lauzy.ticktockmusic.R;
import com.freedom.lauzy.ticktockmusic.base.BaseFragment;
import com.freedom.lauzy.ticktockmusic.event.ThemeEvent;
import com.freedom.lauzy.ticktockmusic.function.RxBus;
import com.freedom.lauzy.ticktockmusic.ui.adapter.ArtistDetailPagerAdapter;
import com.freedom.lauzy.ticktockmusic.utils.SharePrefHelper;
import com.lauzy.freedom.librarys.imageload.ImageConfig;
import com.lauzy.freedom.librarys.imageload.ImageLoader;
import com.lauzy.freedom.librarys.widght.TickToolbar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.reactivex.disposables.Disposable;

/**
 * Desc : 歌手详情Fragment
 * Author : Lauzy
 * Date : 2017/9/29
 * Blog : http://www.jianshu.com/u/e76853f863a9
 * Email : freedompaladin@gmail.com
 */
public class ArtistDetailFragment extends BaseFragment {

    @BindView(R.id.img_singer)
    ImageView mImgSinger;
    @BindView(R.id.toolbar_common)
    TickToolbar mToolbarCommon;
    @BindView(R.id.ctl_singer_detail)
    CollapsingToolbarLayout mCtlSingerDetail;
    @BindView(R.id.tab_singer_detail)
    TabLayout mTabSingerDetail;
    @BindView(R.id.vp_singer_detail)
    ViewPager mVpSingerDetail;
    private static final String ARTIST_NAME = "artist_name";
    private static final String ARTIST_ID = "artist_id";
    private static final String TRANS_NAME = "trans_name";
    private static final String ALBUM_NUM = "album_num";
    private static final String SONG_NUM = "song_num";
    private String mArtistName;
    private long mArtistId;
    private String mTransName;
    private int mAlbumNum;
    private int mSongNum;

    public static ArtistDetailFragment newInstance(String transName, String artistName,
                                                   long artistId, int albumNum, int songNum) {
        Bundle args = new Bundle();
        args.putString(ARTIST_NAME, artistName);
        args.putLong(ARTIST_ID, artistId);
        args.putString(TRANS_NAME, transName);
        args.putInt(ALBUM_NUM, albumNum);
        args.putInt(SONG_NUM, songNum);
        ArtistDetailFragment fragment = new ArtistDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_artist_detail;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Disposable disposable = RxBus.INSTANCE.doStickySubscribe(ThemeEvent.class,
                themeEvent -> setThemeLayoutBg());
        RxBus.INSTANCE.addDisposable(this, disposable);
        if (getArguments() != null) {
            mArtistName = getArguments().getString(ARTIST_NAME);
            mArtistId = getArguments().getLong(ARTIST_ID);
            mTransName = getArguments().getString(TRANS_NAME);
            mAlbumNum = getArguments().getInt(ALBUM_NUM);
            mSongNum = getArguments().getInt(SONG_NUM);
        }
    }

    /**
     * 换肤时更换特定控件颜色
     */
    private void setThemeLayoutBg() {
        ColorStateList stateTxtList = ThemeUtils.getThemeColorStateList(mActivity, R.color.color_tab_txt);
        ColorStateList stateList = ThemeUtils.getThemeColorStateList(mActivity, R.color.color_tab);
        mCtlSingerDetail.setBackgroundColor(stateList.getDefaultColor());
        mCtlSingerDetail.setContentScrimColor(stateList.getDefaultColor());
        mTabSingerDetail.setTabTextColors(stateTxtList);
        mTabSingerDetail.setSelectedTabIndicatorColor(stateList.getDefaultColor());
    }

    @Override
    protected void initViews() {
        setThemeLayoutBg();
        ActionBar actionBar = ((AppCompatActivity) mActivity).getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        setToolbarPadding();
        mImgSinger.setTransitionName(mTransName);
        mCtlSingerDetail.setTitle(mArtistName);
        initTab();
    }

    private void initTab() {
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(ArtistSongFragment.newInstance(mArtistId));
        fragments.add(ArtistAlbumFragment.newInstance(mArtistId));
        String albumTitle = mAlbumNum == 0 ? getResources().getString(R.string.album)
                : getResources().getString(R.string.album) + "(" + mAlbumNum + ")";
        String songTitle = mSongNum == 0 ? getResources().getString(R.string.music)
                : getResources().getString(R.string.music) + "(" + mSongNum + ")";
        String[] titleArr = {songTitle, albumTitle};
        ArtistDetailPagerAdapter adapter = new ArtistDetailPagerAdapter(getChildFragmentManager(),
                fragments, titleArr);
        mVpSingerDetail.setAdapter(adapter);
        mTabSingerDetail.setupWithViewPager(mVpSingerDetail);
    }

    @Override
    protected void loadData() {
        ImageLoader.INSTANCE.display(mActivity, new ImageConfig.Builder()
                .url(SharePrefHelper.getArtistAvatar(mActivity, mArtistName))
                .isRound(false)
                .cacheStrategy(ImageConfig.CACHE_ALL)
                .placeholder(R.drawable.ic_default)
                .into(mImgSinger)
                .build());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        RxBus.INSTANCE.dispose(this);
    }
}
