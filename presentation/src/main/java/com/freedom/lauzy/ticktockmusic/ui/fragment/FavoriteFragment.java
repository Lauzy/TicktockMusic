package com.freedom.lauzy.ticktockmusic.ui.fragment;

import android.content.res.ColorStateList;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.bilibili.magicasakura.utils.ThemeUtils;
import com.freedom.lauzy.ticktockmusic.R;
import com.freedom.lauzy.ticktockmusic.base.BaseFragment;
import com.freedom.lauzy.ticktockmusic.contract.FavoriteContract;
import com.freedom.lauzy.ticktockmusic.event.ChangeFavoriteItemEvent;
import com.freedom.lauzy.ticktockmusic.event.ClearFavoriteEvent;
import com.freedom.lauzy.ticktockmusic.function.RxBus;
import com.freedom.lauzy.ticktockmusic.model.SongEntity;
import com.freedom.lauzy.ticktockmusic.presenter.FavoritePresenter;
import com.freedom.lauzy.ticktockmusic.ui.adapter.FavoriteAdapter;
import com.lauzy.freedom.librarys.widght.TickToolbar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.reactivex.disposables.Disposable;

/**
 * Desc : 我的喜欢Fragment
 * Author : Lauzy
 * Date : 2017/9/13
 * Blog : http://www.jianshu.com/u/e76853f863a9
 * Email : freedompaladin@gmail.com
 */
public class FavoriteFragment extends BaseFragment<FavoritePresenter> implements
        FavoriteContract.View {

    @BindView(R.id.toolbar_common)
    TickToolbar mToolbarCommon;
    @BindView(R.id.rv_favorite)
    RecyclerView mRvFavorite;
    private List<SongEntity> mSongEntities = new ArrayList<>();
    private FavoriteAdapter mAdapter;

    public static FavoriteFragment newInstance() {
        FavoriteFragment favoriteFragment = new FavoriteFragment();
        Bundle bundle = new Bundle();
        favoriteFragment.setArguments(bundle);
        return favoriteFragment;
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_favorite;
    }

    @Override
    protected void initInjector() {
        getFragmentComponent().inject(this);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Disposable disposable = RxBus.INSTANCE.doDefaultSubscribe(ClearFavoriteEvent.class,
                clearFavoriteEvent -> clearFavoriteData());
        //订阅喜欢及取消喜欢事件，延迟加载，防止数据库未关闭就读取
        Disposable refreshDisposable = RxBus.INSTANCE.doDefaultSubscribe(ChangeFavoriteItemEvent.class,
                changeFavoriteItemEvent -> new Handler().postDelayed(() -> mPresenter.loadFavoriteSongs(), 100));
        RxBus.INSTANCE.addDisposable(this, refreshDisposable);
        RxBus.INSTANCE.addDisposable(this, disposable);
    }

    private void clearFavoriteData() {
        if (mSongEntities != null && mSongEntities.size() != 0) {
            ColorStateList stateList = ThemeUtils.getThemeColorStateList(mActivity, R.color.color_tab);
            new MaterialDialog.Builder(mActivity)
                    .content(R.string.clear_favorite_songs)
                    .positiveColor(stateList)
                    .negativeColor(stateList)
                    .positiveText(R.string.confirm)
                    .negativeText(R.string.cancel)
                    .onPositive((dialog, which) -> mPresenter.clearFavoriteSongs())
                    .build()
                    .show();
        }
    }

    @Override
    protected void initViews() {
        setToolbarPadding();
        setDrawerSync();
        mToolbarCommon.setTitle(R.string.drawer_favorite);
        mRvFavorite.setLayoutManager(new LinearLayoutManager(mActivity));
        mAdapter = new FavoriteAdapter(R.layout.layout_song_item, mSongEntities);
        mRvFavorite.setAdapter(mAdapter);
        mAdapter.setOnDeleteListener((songEntity, position) ->
                mPresenter.deleteFavoriteSong(songEntity.id, position));
    }

    @Override
    protected void loadData() {
        mPresenter.loadFavoriteSongs();
    }

    @Override
    public void getFavoriteSongs(List<SongEntity> songEntities) {
        mSongEntities.clear();
        mSongEntities.addAll(songEntities);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void emptyView() {
        mSongEntities.clear();
        mAdapter.notifyDataSetChanged();
        mAdapter.setEmptyView(R.layout.layout_empty, mRvFavorite);
    }

    @Override
    public void clearSongs() {
        mSongEntities.clear();
        mAdapter.notifyDataSetChanged();
        mAdapter.setEmptyView(R.layout.layout_empty, mRvFavorite);
    }

    @Override
    public void deleteFavoriteSong(int position) {
        mSongEntities.remove(position);
        mAdapter.notifyItemRemoved(position);
        if (mSongEntities.isEmpty()) {
            mAdapter.setEmptyView(R.layout.layout_empty, mRvFavorite);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        RxBus.INSTANCE.dispose(this);
    }
}
