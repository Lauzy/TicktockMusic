package com.freedom.lauzy.ticktockmusic.ui.fragment;


import android.os.Bundle;

import com.freedom.lauzy.ticktockmusic.R;
import com.freedom.lauzy.ticktockmusic.base.BaseFragment;

public class SingerFragment extends BaseFragment {

    public SingerFragment() {
    }

    public static SingerFragment newInstance() {
        SingerFragment fragment = new SingerFragment();
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
        return R.layout.fragment_singer;
    }

    @Override
    protected void initInjector() {
//        getFragmentComponent().inject(this);
    }

    @Override
    protected void initViews() {

    }

    @Override
    protected void loadData() {

    }

}
