package com.freedom.lauzy.ticktockmusic.ui.fragment;

import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.bilibili.magicasakura.utils.ThemeUtils;
import com.freedom.lauzy.ticktockmusic.R;
import com.freedom.lauzy.ticktockmusic.base.BaseFragment;
import com.freedom.lauzy.ticktockmusic.contract.LocalMusicContract;
import com.freedom.lauzy.ticktockmusic.event.ThemeEvent;
import com.freedom.lauzy.ticktockmusic.function.RxBus;
import com.freedom.lauzy.ticktockmusic.model.SongEntity;
import com.freedom.lauzy.ticktockmusic.model.mapper.LocalSongMapper;
import com.freedom.lauzy.ticktockmusic.presenter.LocalMusicPresenter;
import com.freedom.lauzy.ticktockmusic.service.MusicManager;
import com.freedom.lauzy.ticktockmusic.service.MusicUtil;
import com.freedom.lauzy.ticktockmusic.ui.adapter.AlbumDetailAdapter;
import com.lauzy.freedom.data.local.LocalUtil;
import com.lauzy.freedom.librarys.imageload.ImageConfig;
import com.lauzy.freedom.librarys.imageload.ImageLoader;
import com.lauzy.freedom.librarys.widght.TickToolbar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.disposables.Disposable;

/**
 * Desc : 专辑详情
 * Author : Lauzy
 * Date : 2017/8/31
 * Blog : http://www.jianshu.com/u/e76853f863a9
 * Email : freedompaladin@gmail.com
 */
public class AlbumDetailFragment extends BaseFragment<LocalMusicPresenter>
        implements LocalMusicContract.View {

    private static final String LTAG = "AlbumDetailFragment";
    @BindView(R.id.ctl_album)
    CollapsingToolbarLayout mCtlAlbum;
    @BindView(R.id.toolbar_common)
    TickToolbar mToolbarCommon;
    @BindView(R.id.rv_album_detail)
    RecyclerView mRvAlbumDetail;
    @BindView(R.id.img_album)
    ImageView mImgAlbum;
    @BindView(R.id.fab_play)
    FloatingActionButton mFabPlay;
    private static final String ALBUM_ID = "_id";
    private static final String ALBUM_NAME = "_name";
    private static final String ALBUM_TRANS_NAME = "trans_name";
    private List<SongEntity> mLocalSongBeen = new ArrayList<>();
    private AlbumDetailAdapter mAdapter;
    private long mAlbumId;
    private String mTransName;
    private String mAlbumName;

    public static AlbumDetailFragment newInstance(String transName, String albumName, long id) {
        AlbumDetailFragment albumDetailFragment = new AlbumDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putString(ALBUM_NAME, albumName);
        bundle.putLong(ALBUM_ID, id);
        bundle.putString(ALBUM_TRANS_NAME, transName);
        albumDetailFragment.setArguments(bundle);
        return albumDetailFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Disposable disposable = RxBus.INSTANCE.doStickySubscribe(ThemeEvent.class,
                themeEvent -> setThemeLayoutBg());
        RxBus.INSTANCE.addDisposable(this, disposable);
        if (getArguments() != null) {
            mAlbumId = getArguments().getLong(ALBUM_ID);
            mTransName = getArguments().getString(ALBUM_TRANS_NAME);
            mAlbumName = getArguments().getString(ALBUM_NAME);
        }
    }

    /**
     * 换肤时更换特定控件颜色
     */
    private void setThemeLayoutBg() {
        ColorStateList stateList = ThemeUtils.getThemeColorStateList(mActivity, R.color.color_tab);
        mCtlAlbum.setBackgroundColor(stateList.getDefaultColor());
        mCtlAlbum.setContentScrimColor(stateList.getDefaultColor());
        mFabPlay.setBackgroundTintList(stateList);
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_album_detail;
    }

    @Override
    protected void initInjector() {
        getFragmentComponent().inject(this);
    }

    @Override
    protected void initViews() {
        setThemeLayoutBg();
        setTitleView();
        mRvAlbumDetail.setLayoutManager(new LinearLayoutManager(mActivity));
        mAdapter = new AlbumDetailAdapter(R.layout.layout_song_item, mLocalSongBeen);
        mRvAlbumDetail.setAdapter(mAdapter);
    }

    private void setTitleView() {
        ActionBar actionBar = ((AppCompatActivity) mActivity).getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        setToolbarPadding();
        mImgAlbum.setTransitionName(mTransName);
        mCtlAlbum.setTitle(mAlbumName);
    }

    @Override
    protected void loadData() {
        ImageLoader.INSTANCE.display(mActivity,
                new ImageConfig.Builder()
                        .url(LocalUtil.getCoverUri(mAlbumId)).isRound(false)
                        .into(mImgAlbum)
                        .build());
        mImgAlbum.setColorFilter(ContextCompat.getColor(mActivity, R.color.colorDarkerTransparent),
                PorterDuff.Mode.SRC_OVER);
        mPresenter.setId(mAlbumId);
        mPresenter.loadLocalSong();
    }

    @OnClick({R.id.fab_play})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fab_play:
                MusicManager.getInstance().playMusic(LocalSongMapper.transformLocal(mLocalSongBeen),
                        MusicUtil.getSongIds(mLocalSongBeen));
                break;
        }
    }

    @Override
    public void loadLocalMusic(List<SongEntity> localSongBeen) {
        mLocalSongBeen.clear();
        mLocalSongBeen.addAll(localSongBeen);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void setEmptyView() {

    }

    @Override
    public void loadFailed(Throwable throwable) {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        RxBus.INSTANCE.dispose(this);
    }
}
