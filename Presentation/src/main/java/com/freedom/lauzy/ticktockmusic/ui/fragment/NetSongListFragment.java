package com.freedom.lauzy.ticktockmusic.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.freedom.lauzy.model.SongListBean;
import com.freedom.lauzy.ticktockmusic.R;
import com.freedom.lauzy.ticktockmusic.base.BaseLazyFragment;
import com.freedom.lauzy.ticktockmusic.contract.NetMusicContract;
import com.freedom.lauzy.ticktockmusic.event.PaletteEvent;
import com.freedom.lauzy.ticktockmusic.function.RxBus;
import com.freedom.lauzy.ticktockmusic.presenter.NetMusicPresenter;
import com.freedom.lauzy.ticktockmusic.ui.adapter.NetSongAdapter;
import com.lauzy.freedom.librarys.widght.TickSwipeRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.reactivex.disposables.Disposable;

/**
 * Desc : Net Song
 * Author : Lauzy
 * Date : 2017/8/8
 * Blog : http://www.jianshu.com/u/e76853f863a9
 * Email : freedompaladin@gmail.com
 */
public class NetSongListFragment extends BaseLazyFragment<NetMusicPresenter>
        implements NetMusicContract.View {
    @BindView(R.id.rv_net_song)
    RecyclerView mRvNetSong;
    @BindView(R.id.srl_net_song)
    TickSwipeRefreshLayout mSrlNetSong;
    private List<SongListBean> mSongListBeen = new ArrayList<>();
    private NetSongAdapter mAdapter;
    private static final String TYPE = "type";
    private int mType;

    public static NetSongListFragment newInstance(int type) {
        NetSongListFragment songListFragment = new NetSongListFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(TYPE, type);
        songListFragment.setArguments(bundle);
        return songListFragment;
    }


    @Override
    protected int getLayoutRes() {
        return R.layout.layout_net_music;
    }

    @Override
    protected void initInjector() {
        getFragmentComponent().inject(this);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Disposable disposable = RxBus.INSTANCE.doDefaultSubscribe(PaletteEvent.class,
                paletteEvent -> mSrlNetSong.setColorSchemeColors(paletteEvent.getColor()));
        RxBus.INSTANCE.addDisposable(this, disposable);
        if (getArguments() != null) {
            mType = getArguments().getInt(TYPE);
        }
    }

    @Override
    protected void initViews() {
        initRecyclerView();
    }

    private void initRecyclerView() {
        mSrlNetSong.setRefreshing(true);
        mSrlNetSong.setOnRefreshListener(() -> mPresenter.loadNetMusicList());
        mRvNetSong.setLayoutManager(new LinearLayoutManager(mActivity));
        mAdapter = new NetSongAdapter(R.layout.layout_song_item, mSongListBeen);
        mRvNetSong.setAdapter(mAdapter);
    }

    @Override
    protected void loadData() {
        mPresenter.setType(mType);
        mPresenter.loadNetMusicList();
        mAdapter.setOnLoadMoreListener(() -> mPresenter.loadMoreNetMusicList(), mRvNetSong);
    }

    @Override
    public void loadCacheData(List<SongListBean> songListBeen) {
        mAdapter.setNewData(songListBeen);
    }

    @Override
    public void loadSuccess(List<SongListBean> songListBeen) {
        /*List<SongListBean> cacheData = mAdapter.getData();
        Observable.create((ObservableOnSubscribe<List<SongListBean>>) e -> {
            e.onNext(songListBeen);
            e.onComplete();
        }).compose(RxHelper.ioMain()).subscribe(data -> {

        });
        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(null);
        diffResult.dispatchUpdatesTo(mAdapter);*/
        mAdapter.setNewData(songListBeen);
        mSrlNetSong.setRefreshing(false);
    }

    @Override
    public void setEmptyView() {
        mSrlNetSong.setRefreshing(false);
    }

    @Override
    public void loadFail(Throwable throwable) {
        mSrlNetSong.setRefreshing(false);
    }

    @Override
    public void loadMoreSuccess(List<SongListBean> songListBeen) {
        mAdapter.addData(songListBeen);
        mAdapter.loadMoreComplete();
    }

    @Override
    public void loadMoreEnd() {
        mAdapter.loadMoreEnd();
    }

    @Override
    public void loadMoreFail(Throwable throwable) {
        mAdapter.loadMoreFail();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        RxBus.INSTANCE.dispose(this);
    }
}
