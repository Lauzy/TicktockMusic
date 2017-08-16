package com.freedom.lauzy.ticktockmusic.ui.fragment;


import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.freedom.lauzy.model.LocalAlbumBean;
import com.freedom.lauzy.ticktockmusic.R;
import com.freedom.lauzy.ticktockmusic.base.BaseFragment;
import com.freedom.lauzy.ticktockmusic.contract.LocalAlbumContract;
import com.freedom.lauzy.ticktockmusic.presenter.LocalAlbumPresenter;
import com.freedom.lauzy.ticktockmusic.ui.adapter.AlbumAdapter;
import com.lauzy.freedom.librarys.view.decoration.GridItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class AlbumFragment extends BaseFragment<LocalAlbumPresenter> implements LocalAlbumContract.View {

    @BindView(R.id.rv_album)
    RecyclerView mRvAlbum;
    private List<LocalAlbumBean> mLocalAlbumBeen = new ArrayList<>();
    private AlbumAdapter mAdapter;

    public static AlbumFragment newInstance() {
        AlbumFragment fragment = new AlbumFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_album;
    }

    @Override
    protected void initInjector() {
        getFragmentComponent().inject(this);
    }

    @Override
    protected void initViews() {
        mRvAlbum.setLayoutManager(new GridLayoutManager(mActivity, 2));
    }

    @Override
    protected void loadData() {
        mAdapter = new AlbumAdapter(R.layout.layout_album_item, mLocalAlbumBeen);
        mRvAlbum.setAdapter(mAdapter);
        mRvAlbum.addItemDecoration(new GridItemDecoration.Builder(mActivity)
                .setIncludeEdge(false)
                .setSpace(10)
                .build());
        mPresenter.loadLocalAlbum();
        mAdapter.setOnItemClickListener((adapter, view, position) ->
                mNavigator.navigateToAlbumDetail(mActivity, mLocalAlbumBeen.get(position).id));
    }

    @Override
    public void loadLocalAlbum(List<LocalAlbumBean> localAlbumBeen) {
        mLocalAlbumBeen.clear();
        mLocalAlbumBeen.addAll(localAlbumBeen);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void setEmptyView() {

    }

    @Override
    public void loadFailed(Throwable throwable) {

    }
}
