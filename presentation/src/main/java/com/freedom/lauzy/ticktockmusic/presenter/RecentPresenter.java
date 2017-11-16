package com.freedom.lauzy.ticktockmusic.presenter;

import com.freedom.lauzy.interactor.RecentSongUseCase;
import com.freedom.lauzy.model.RecentSongBean;
import com.freedom.lauzy.ticktockmusic.base.BaseRxPresenter;
import com.freedom.lauzy.ticktockmusic.function.DefaultDisposableObserver;
import com.freedom.lauzy.ticktockmusic.contract.RecentContract;
import com.freedom.lauzy.ticktockmusic.function.RxHelper;
import com.freedom.lauzy.ticktockmusic.model.SongEntity;
import com.freedom.lauzy.ticktockmusic.model.mapper.RecentMapper;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.annotations.NonNull;

/**
 * Desc : 最近播放Presenter
 * Author : Lauzy
 * Date : 2017/9/15
 * Blog : http://www.jianshu.com/u/e76853f863a9
 * Email : freedompaladin@gmail.com
 */
public class RecentPresenter extends BaseRxPresenter<RecentContract.View>
        implements RecentContract.Presenter {

    private RecentSongUseCase mRecentSongUseCase;
    private RecentMapper mRecentMapper;

    @Inject
    RecentPresenter(RecentSongUseCase recentSongUseCase, RecentMapper recentMapper) {
        mRecentSongUseCase = recentSongUseCase;
        mRecentMapper = recentMapper;
    }

    @Override
    public void loadRecentSongs() {
        mRecentSongUseCase.getRecentSongs()
                .compose(RxHelper.ioMain())
                .subscribeWith(new DefaultDisposableObserver<List<RecentSongBean>>() {
                    @Override
                    public void onNext(@NonNull List<RecentSongBean> recentSongBeen) {
                        super.onNext(recentSongBeen);
                        List<SongEntity> songEntities = mRecentMapper.transform(recentSongBeen);
                        if (songEntities != null && songEntities.size() != 0) {
                            if (getView() != null) getView().getRecentSongs(songEntities);
                        } else {
                            if (getView() != null) getView().emptyView();
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        super.onError(e);
                        getView().emptyView();
                    }
                });
    }

    @Override
    public void clearRecentSongs() {
        mRecentSongUseCase.clearRecentSongs()
                .compose(RxHelper.ioMain())
                .subscribe(integer -> getView().clearAllRecentSongs());
    }
}
