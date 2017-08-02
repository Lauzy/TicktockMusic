package com.freedom.lauzy.ticktockmusic.module.ui.fragment;


import android.os.Bundle;

import com.freedom.lauzy.ticktockmusic.R;
import com.freedom.lauzy.ticktockmusic.base.BaseLazyFragment;
import com.freedom.lauzy.ticktockmusic.module.NetMusicPresenter;

/**
 * Desc :
 * Author : Lauzy
 * Date : 2017/8/2
 * Blog : http://www.jianshu.com/u/e76853f863a9
 * Email : freedompaladin@gmail.com
 */
public class NetMusicFragment extends BaseLazyFragment<NetMusicPresenter> {

    public static NetMusicFragment newInstance() {
        NetMusicFragment fragment = new NetMusicFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_net_music;
    }

    @Override
    protected void initInjector() {
        getFragmentComponent().inject(this);
    }

    @Override
    protected void initViews() {

    }

    @Override
    protected void loadData() {
        mPresenter.getData();
    }

}
