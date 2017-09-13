package com.freedom.lauzy.interactor;

import com.freedom.lauzy.executor.PostExecutionThread;
import com.freedom.lauzy.executor.ThreadExecutor;
import com.freedom.lauzy.model.FavoriteSongBean;
import com.freedom.lauzy.repository.FavoriteRepository;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * Desc : 我的喜欢逻辑用例
 * Author : Lauzy
 * Date : 2017/9/12
 * Blog : http://www.jianshu.com/u/e76853f863a9
 * Email : freedompaladin@gmail.com
 */
public class FavoriteSongUseCase extends UseCase<Long, FavoriteSongBean> {

    private final FavoriteRepository mFavoriteRepository;

    @Inject
    FavoriteSongUseCase(FavoriteRepository favoriteRepository, ThreadExecutor threadExecutor,
                        PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        mFavoriteRepository = favoriteRepository;
    }

    @Override
    Observable<Long> buildUseCaseObservable(FavoriteSongBean favoriteSongBean) {
        return mFavoriteRepository.addFavoriteSong(favoriteSongBean);
    }

    public Observable<Long> deleteFavoriteSong(long songId) {
        return mFavoriteRepository.deleteFavoriteSong(songId);
    }

    public Observable<List<FavoriteSongBean>> favoriteSongObservable() {
        return mFavoriteRepository.getFavoriteSongs();
    }

    public Observable<Integer> clearFavoriteSongs() {
        return mFavoriteRepository.clearFavoriteSongs();
    }

    public Observable<Boolean> isFavoriteSong(long songId) {
        return mFavoriteRepository.isFavoriteSong(songId);
    }
}
