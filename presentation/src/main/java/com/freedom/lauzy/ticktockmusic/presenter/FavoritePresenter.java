package com.freedom.lauzy.ticktockmusic.presenter;

import com.freedom.lauzy.interactor.FavoriteSongUseCase;
import com.freedom.lauzy.model.FavoriteSongBean;
import com.freedom.lauzy.ticktockmusic.base.BaseRxPresenter;
import com.freedom.lauzy.ticktockmusic.contract.FavoriteContract;
import com.freedom.lauzy.ticktockmusic.function.DefaultDisposableObserver;
import com.freedom.lauzy.ticktockmusic.function.RxHelper;
import com.freedom.lauzy.ticktockmusic.model.SongEntity;
import com.freedom.lauzy.ticktockmusic.model.mapper.FavoriteMapper;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.annotations.NonNull;

/**
 * Desc : 我的喜欢Presenter
 * Author : Lauzy
 * Date : 2017/9/13
 * Blog : http://www.jianshu.com/u/e76853f863a9
 * Email : freedompaladin@gmail.com
 */
public class FavoritePresenter extends BaseRxPresenter<FavoriteContract.View>
        implements FavoriteContract.Presenter {

    private FavoriteSongUseCase mFavoriteSongUseCase;
    private FavoriteMapper mFavoriteMapper;

    @Inject
    FavoritePresenter(FavoriteSongUseCase favoriteSongUseCase, FavoriteMapper favoriteMapper) {
        mFavoriteSongUseCase = favoriteSongUseCase;
        mFavoriteMapper = favoriteMapper;
    }

    @Override
    public void loadFavoriteSongs() {
        mFavoriteSongUseCase.favoriteSongObservable()
                .compose(RxHelper.ioMain())
                .subscribeWith(new DefaultDisposableObserver<List<FavoriteSongBean>>() {
                    @Override
                    public void onNext(@NonNull List<FavoriteSongBean> favoriteSongBeen) {
                        super.onNext(favoriteSongBeen);
                        if (getView() == null) {
                            return;
                        }
                        List<SongEntity> songEntities = mFavoriteMapper.transform(favoriteSongBeen);
                        if (songEntities != null && !songEntities.isEmpty()) {
                            getView().getFavoriteSongs(songEntities);
                        } else {
                            getView().emptyView();
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        super.onError(e);
                        if (getView() == null) {
                            return;
                        }
                        getView().emptyView();
                    }
                });
    }

    @Override
    public void clearFavoriteSongs() {
        mFavoriteSongUseCase.clearFavoriteSongs().compose(RxHelper.ioMain())
                .subscribe(integer -> {
                    if (getView()==null) {
                        return;
                    }
                    getView().clearSongs();
                });
    }
}
