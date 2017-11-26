package com.freedom.lauzy.ticktockmusic.ui.fragment;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.freedom.lauzy.ticktockmusic.R;
import com.freedom.lauzy.ticktockmusic.base.BaseFragment;
import com.lauzy.freedom.librarys.widght.TickToolbar;

import butterknife.BindView;

/**
 * Desc : 文件夹
 * Author : Lauzy
 * Date : 2017/11/26
 * Blog : http://www.jianshu.com/u/e76853f863a9
 * Email : freedompaladin@gmail.com
 */
public class FileFolderFragment extends BaseFragment {
    @BindView(R.id.toolbar_common)
    TickToolbar mToolbarCommon;
    @BindView(R.id.rv_file_folder)
    RecyclerView mRvFileFolder;

    public static FileFolderFragment newInstance() {
        Bundle args = new Bundle();
        FileFolderFragment fragment = new FileFolderFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_file_folder;
    }

    @Override
    protected void initViews() {
        super.initViews();
        setToolbarPadding();
        setDrawerSync();
        mToolbarCommon.setTitle(R.string.drawer_file_folder);
    }
}
