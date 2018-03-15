package com.freedom.lauzy.ticktockmusic.ui.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.freedom.lauzy.model.Folder;
import com.freedom.lauzy.ticktockmusic.R;
import com.freedom.lauzy.ticktockmusic.base.BaseFragment;
import com.freedom.lauzy.ticktockmusic.contract.MusicFolderContract;
import com.freedom.lauzy.ticktockmusic.presenter.MusicFolderPresenter;
import com.freedom.lauzy.ticktockmusic.ui.adapter.MusicFolderAdapter;
import com.lauzy.freedom.librarys.widght.TickToolbar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Desc : 文件夹
 * Author : Lauzy
 * Date : 2017/11/26
 * Blog : http://www.jianshu.com/u/e76853f863a9
 * Email : freedompaladin@gmail.com
 */
public class MusicFolderFragment extends BaseFragment<MusicFolderPresenter>
        implements MusicFolderContract.View {

    @BindView(R.id.toolbar_common)
    TickToolbar mToolbarCommon;
    @BindView(R.id.rv_file_folder)
    RecyclerView mRvFileFolder;
    private List<Folder> mFolders = new ArrayList<>();
    private MusicFolderAdapter mAdapter;

    public static MusicFolderFragment newInstance() {
        Bundle args = new Bundle();
        MusicFolderFragment fragment = new MusicFolderFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_file_folder;
    }

    @Override
    protected void initInjector() {
        super.initInjector();
        getFragmentComponent().inject(this);
    }

    @Override
    protected void initViews() {
        super.initViews();
        setToolbarPadding();
        setDrawerSync();
        mToolbarCommon.setTitle(R.string.drawer_file_folder);
        setupRecyclerView();
    }

    private void setupRecyclerView() {
        mRvFileFolder.setLayoutManager(new LinearLayoutManager(mActivity));
        mAdapter = new MusicFolderAdapter(R.layout.layout_folder_item, mFolders);
        mRvFileFolder.setAdapter(mAdapter);
        mAdapter.setOnFolderClickListener((folderPath, folderName) ->
                mNavigator.navigateToFolderSongs(mActivity, folderPath, folderName));
    }

    @Override
    protected void loadData() {
        super.loadData();
        mPresenter.loadFolders();
    }

    @Override
    public void onLoadFoldersSuccess(List<Folder> folders) {
        mFolders.clear();
        mFolders.addAll(folders);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void setEmptyView() {

    }

    @Override
    public void loadFailed(Throwable throwable) {

    }
}
