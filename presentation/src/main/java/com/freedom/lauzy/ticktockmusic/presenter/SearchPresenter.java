package com.freedom.lauzy.ticktockmusic.presenter;

import com.freedom.lauzy.interactor.SearchSongUseCase;
import com.freedom.lauzy.ticktockmusic.base.BaseRxPresenter;
import com.freedom.lauzy.ticktockmusic.contract.SearchContract;
import com.freedom.lauzy.ticktockmusic.function.DefaultDisposableObserver;
import com.freedom.lauzy.ticktockmusic.model.SongEntity;
import com.freedom.lauzy.ticktockmusic.model.mapper.LocalSongMapper;

import java.util.List;

import javax.inject.Inject;

/**
 * Desc : 搜索
 * Author : Lauzy
 * Date : 2018/5/22
 * Blog : http://www.jianshu.com/u/e76853f863a9
 * Email : freedompaladin@gmail.com
 */
public class SearchPresenter extends BaseRxPresenter<SearchContract.View>
        implements SearchContract.Presenter {

    private SearchSongUseCase mSearchSongUseCase;
    private LocalSongMapper mLocalSongMapper;

    @Inject
    public SearchPresenter(SearchSongUseCase searchSongUseCase, LocalSongMapper localSongMapper) {
        mSearchSongUseCase = searchSongUseCase;
        mLocalSongMapper = localSongMapper;
    }

    @Override
    public void searchSongs(String searchContent) {
        mSearchSongUseCase.buildObservable(searchContent)
                .map(localSongBeans -> mLocalSongMapper.transform(localSongBeans))
                .subscribe(new DefaultDisposableObserver<List<SongEntity>>() {

                    @Override
                    public void onNext(List<SongEntity> songEntities) {
                        super.onNext(songEntities);
                        if (getView() == null) {
                            return;
                        }
                        if (songEntities != null && songEntities.size() != 0) {
                            getView().onSearchSuccess(songEntities);
                            return;
                        }
                        getView().setEmptyView();
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        if (getView() == null) {
                            return;
                        }
                        getView().onSearchFailed();
                    }
                });
    }
}
