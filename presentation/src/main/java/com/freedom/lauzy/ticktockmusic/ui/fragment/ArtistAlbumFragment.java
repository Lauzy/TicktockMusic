package com.freedom.lauzy.ticktockmusic.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.freedom.lauzy.model.LocalAlbumBean;
import com.freedom.lauzy.ticktockmusic.R;
import com.freedom.lauzy.ticktockmusic.base.BaseFragment;
import com.freedom.lauzy.ticktockmusic.contract.LocalAlbumContract;
import com.freedom.lauzy.ticktockmusic.presenter.LocalAlbumPresenter;
import com.freedom.lauzy.ticktockmusic.ui.adapter.ArtistAlbumAdapter;
import com.lauzy.freedom.librarys.common.LogUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Desc : 歌手专辑Fragment
 * Author : Lauzy
 * Date : 2017/9/29
 * Blog : http://www.jianshu.com/u/e76853f863a9
 * Email : freedompaladin@gmail.com
 */
public class ArtistAlbumFragment extends BaseFragment<LocalAlbumPresenter> implements
        LocalAlbumContract.View {

    private static final String ARTIST_ID = "artist_id";
    @BindView(R.id.rv_artist_album)
    RecyclerView mRvArtistAlbum;
    private List<LocalAlbumBean> mAlbumBeen = new ArrayList<>();
    private ArtistAlbumAdapter mAdapter;
    private long mArtistId;

    public static ArtistAlbumFragment newInstance(long artistId) {
        Bundle args = new Bundle();
        args.putLong(ARTIST_ID, artistId);
        ArtistAlbumFragment fragment = new ArtistAlbumFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mArtistId = getArguments().getLong(ARTIST_ID);
        }
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_artist_album;
    }

    @Override
    protected void initInjector() {
        getFragmentComponent().inject(this);
    }

    @Override
    protected void initViews() {
        mRvArtistAlbum.setLayoutManager(new LinearLayoutManager(mActivity));
        mAdapter = new ArtistAlbumAdapter(R.layout.layout_song_item, mAlbumBeen);
        mRvArtistAlbum.setAdapter(mAdapter);
    }

    @Override
    protected void loadData() {
        mPresenter.setId(mArtistId);
        mPresenter.loadLocalAlbum();
    }

    @Override
    public void loadLocalAlbum(List<LocalAlbumBean> localAlbumBeen) {
        mAlbumBeen.clear();
        mAlbumBeen.addAll(localAlbumBeen);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void setEmptyView() {
    }

    @Override
    public void loadFailed(Throwable throwable) {
        throwable.printStackTrace();
    }
}
