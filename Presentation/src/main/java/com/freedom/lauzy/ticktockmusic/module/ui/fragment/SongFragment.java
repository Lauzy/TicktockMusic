package com.freedom.lauzy.ticktockmusic.module.ui.fragment;


import android.os.Bundle;
import android.support.v4.widget.NestedScrollView;

import com.freedom.lauzy.ticktockmusic.R;
import com.freedom.lauzy.ticktockmusic.base.BaseFragment;

import butterknife.BindView;

public class SongFragment extends BaseFragment {


    @BindView(R.id.nsv)
    NestedScrollView mRvSong;

    public static SongFragment newInstance() {
        SongFragment fragment = new SongFragment();
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
        return R.layout.fragment_song;
    }

    @Override
    protected void initInjector() {

    }

    @Override
    protected void initViews() {
    }

    @Override
    protected void loadData() {

    }
}
