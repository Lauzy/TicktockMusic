package com.freedom.lauzy.ticktockmusic.ui.fragment;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.transition.TransitionInflater;
import android.widget.ImageView;

import com.freedom.lauzy.ticktockmusic.R;
import com.freedom.lauzy.ticktockmusic.base.BaseFragment;
import com.freedom.lauzy.ticktockmusic.contract.LocalMusicContract;
import com.freedom.lauzy.ticktockmusic.model.SongEntity;
import com.freedom.lauzy.ticktockmusic.presenter.LocalMusicPresenter;
import com.freedom.lauzy.ticktockmusic.ui.adapter.AlbumDetailAdapter;
import com.lauzy.freedom.librarys.imageload.ImageConfig;
import com.lauzy.freedom.librarys.imageload.ImageLoader;
import com.lauzy.freedom.librarys.widght.TickToolbar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

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
    @BindView(R.id.toolbar_common)
    TickToolbar mToolbarCommon;
    @BindView(R.id.rv_album_detail)
    RecyclerView mRvAlbumDetail;
    @BindView(R.id.img_album)
    ImageView mImgAlbum;
    private static final String ALBUM_ID = "_id";
    private static final String ALBUM_COVER = "album_cover";
    private static final String ALBUM_TRANS_NAME = "trans_name";
    private List<SongEntity> mLocalSongBeen = new ArrayList<>();
    private AlbumDetailAdapter mAdapter;
    private long mAlbumId;
    private Uri mAlbumCover;
    private String mTransName;

    public static AlbumDetailFragment newInstance(Uri url, String transName, long id) {
        AlbumDetailFragment albumDetailFragment = new AlbumDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putLong(ALBUM_ID, id);
        bundle.putParcelable(ALBUM_COVER, url);
        bundle.putString(ALBUM_TRANS_NAME, transName);
        albumDetailFragment.setArguments(bundle);
        return albumDetailFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setSharedElementEnterTransition(TransitionInflater.from(getContext())
//                .inflateTransition(android.R.transition.move));
        if (getArguments() != null) {
            mAlbumId = getArguments().getLong(ALBUM_ID);
            mAlbumCover = getArguments().getParcelable(ALBUM_COVER);
            mTransName = getArguments().getString(ALBUM_TRANS_NAME);
        }
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
        ActionBar actionBar = ((AppCompatActivity) mActivity).getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        setToolbarPadding();
        mImgAlbum.setTransitionName(mTransName);
        mRvAlbumDetail.setLayoutManager(new LinearLayoutManager(mActivity));
        mAdapter = new AlbumDetailAdapter(R.layout.layout_song_item, mLocalSongBeen);
        mRvAlbumDetail.setAdapter(mAdapter);
    }

    @Override
    protected void loadData() {
        ImageLoader.INSTANCE.display(mActivity, new ImageConfig.Builder()
                .url(mAlbumCover).isRound(false)
                .placeholder(R.drawable.ic_default)
                .into(mImgAlbum).build());
        mPresenter.setId(mAlbumId);
        mPresenter.loadLocalSong();
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
}
