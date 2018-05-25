package com.freedom.lauzy.ticktockmusic.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import com.freedom.lauzy.ticktockmusic.R;
import com.freedom.lauzy.ticktockmusic.base.BaseActivity;
import com.freedom.lauzy.ticktockmusic.contract.SearchContract;
import com.freedom.lauzy.ticktockmusic.model.SongEntity;
import com.freedom.lauzy.ticktockmusic.presenter.SearchPresenter;
import com.freedom.lauzy.ticktockmusic.service.MusicManager;
import com.freedom.lauzy.ticktockmusic.ui.adapter.SearchAdapter;
import com.lauzy.freedom.librarys.widght.TickToolbar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Desc : 搜索
 * Author : Lauzy
 * Date : 2018/5/22
 * Blog : http://www.jianshu.com/u/e76853f863a9
 * Email : freedompaladin@gmail.com
 */
public class SearchActivity extends BaseActivity<SearchPresenter> implements SearchContract.View {

    @BindView(R.id.et_search)
    EditText mEtSearch;
    @BindView(R.id.toolbar_common)
    TickToolbar mToolbarCommon;
    @BindView(R.id.rv_search)
    RecyclerView mRvSearch;
    private SearchAdapter mAdapter;
    private List<SongEntity> mSongEntities = new ArrayList<>();

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_search;
    }

    public static Intent newIntent(Context context) {
        return new Intent(context, SearchActivity.class);
    }

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected void initViews() {
        showBackIcon();
        mEtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String content = s.toString();
                mAdapter.setSearchContent(content);
                if (content.length() > 0) {
                    mPresenter.searchSongs(content);
                } else {
                    mSongEntities.clear();
                    mAdapter.isUseEmpty(false);
                    mAdapter.notifyDataSetChanged();
                }
            }
        });
        mRvSearch.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new SearchAdapter(R.layout.layout_search_item, mSongEntities);
        mRvSearch.setAdapter(mAdapter);
    }

    @Override
    protected void loadData() {
        MusicManager.getInstance().addPlayQueueListener(() -> mAdapter.notifyDataSetChanged());
    }

    @Override
    public void onSearchSuccess(List<SongEntity> songEntities) {
        mSongEntities.clear();
        mSongEntities.addAll(songEntities);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onSearchFailed() {

    }

    @Override
    public void setEmptyView() {
        mAdapter.isUseEmpty(true);
        mSongEntities.clear();
        mAdapter.notifyDataSetChanged();
        mAdapter.setEmptyView(R.layout.layout_empty, mRvSearch);
    }
}
