package com.freedom.lauzy.ticktockmusic.presenter;

import com.freedom.lauzy.interactor.CacheManagerUseCase;
import com.freedom.lauzy.ticktockmusic.base.BasePresenter;
import com.freedom.lauzy.ticktockmusic.contract.ArtistDetailContract;

import javax.inject.Inject;

/**
 * Desc : 歌手详情
 * Author : Lauzy
 * Date : 2018/4/3
 * Blog : http://www.jianshu.com/u/e76853f863a9
 * Email : freedompaladin@gmail.com
 */
public class ArtistDetailPresenter extends BasePresenter implements ArtistDetailContract.Presenter {

    CacheManagerUseCase mCacheManagerUseCase;

    @Inject
    public ArtistDetailPresenter(CacheManagerUseCase cacheManagerUseCase) {
        mCacheManagerUseCase = cacheManagerUseCase;
    }

    @Override
    public String getArtistAvatarUrl(String artistName) {
        return mCacheManagerUseCase.getArtistAvatar(artistName);
    }
}
