package com.freedom.lauzy.ticktockmusic.ui.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;

import com.freedom.lauzy.model.LocalArtistBean;
import com.freedom.lauzy.ticktockmusic.R;
import com.freedom.lauzy.ticktockmusic.base.BaseFragment;
import com.freedom.lauzy.ticktockmusic.contract.LocalArtistContract;
import com.freedom.lauzy.ticktockmusic.presenter.LocalArtistPresenter;
import com.freedom.lauzy.ticktockmusic.ui.adapter.ArtistAdapter;
import com.lauzy.freedom.librarys.imageload.ImageConfig;
import com.lauzy.freedom.librarys.imageload.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Desc : 本地音乐歌手Fragment
 * Author : Lauzy
 * Date : 2017/9/28
 * Blog : http://www.jianshu.com/u/e76853f863a9
 * Email : freedompaladin@gmail.com
 */
public class ArtistFragment extends BaseFragment<LocalArtistPresenter> implements
        LocalArtistContract.View {

    @BindView(R.id.rv_artist)
    RecyclerView mRvArtist;
    private List<LocalArtistBean> mLocalArtistBeen = new ArrayList<>();
    private ArtistAdapter mAdapter;

    public static ArtistFragment newInstance() {
        Bundle args = new Bundle();
        ArtistFragment fragment = new ArtistFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_artist;
    }

    @Override
    protected void initInjector() {
        getFragmentComponent().inject(this);
    }

    @Override
    protected void initViews() {
        mRvArtist.setLayoutManager(new LinearLayoutManager(mActivity));
        mAdapter = new ArtistAdapter(R.layout.layout_artist_item, mLocalArtistBeen);
        mRvArtist.setAdapter(mAdapter);
        mAdapter.setAvatarListener((artistBean, imageView) ->
                mPresenter.loadArtistAvatar(artistBean.artistName, imageView));
    }

    @Override
    protected void loadData() {
        mPresenter.loadLocalArtists();
    }

    @Override
    public void loadArtistResult(List<LocalArtistBean> artistBeen) {
        mLocalArtistBeen.clear();
        mLocalArtistBeen.addAll(artistBeen);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void loadAvatarResult(String picUrl, ImageView imageView) {
        ImageLoader.getInstance().display(mActivity,
                new ImageConfig.Builder()
                        .isRound(false)
                        .url(picUrl)
                        .cacheStrategy(ImageConfig.CACHE_ALL)
                        .placeholder(R.drawable.ic_default)
                        .into(imageView)
                        .build());
    }

    @Override
    public void emptyView() {

    }

    @Override
    public void loadFailed(Throwable throwable) {

    }
}
