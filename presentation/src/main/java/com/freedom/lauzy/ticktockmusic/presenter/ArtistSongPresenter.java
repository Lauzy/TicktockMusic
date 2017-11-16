package com.freedom.lauzy.ticktockmusic.presenter;

import com.freedom.lauzy.interactor.GetLocalArtistUseCase;
import com.freedom.lauzy.model.LocalSongBean;
import com.freedom.lauzy.ticktockmusic.base.BaseRxPresenter;
import com.freedom.lauzy.ticktockmusic.function.DefaultDisposableObserver;
import com.freedom.lauzy.ticktockmusic.contract.ArtistSongContract;
import com.freedom.lauzy.ticktockmusic.function.RxHelper;
import com.freedom.lauzy.ticktockmusic.model.mapper.LocalSongMapper;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.annotations.NonNull;

/**
 * Desc : 歌手音乐Presenter
 * Author : Lauzy
 * Date : 2017/9/29
 * Blog : http://www.jianshu.com/u/e76853f863a9
 * Email : freedompaladin@gmail.com
 */
public class ArtistSongPresenter extends BaseRxPresenter<ArtistSongContract.View>
        implements ArtistSongContract.Presenter {

    private GetLocalArtistUseCase mArtistUseCase;
    private LocalSongMapper mLocalSongMapper;

    @Inject
    public ArtistSongPresenter(GetLocalArtistUseCase artistUseCase, LocalSongMapper localSongMapper) {
        mArtistUseCase = artistUseCase;
        mLocalSongMapper = localSongMapper;
    }

    @Override
    public void loadArtistSongs(long id) {
        mArtistUseCase.getArtistSongList(id)
                .compose(RxHelper.ioMain())
                .subscribeWith(new DefaultDisposableObserver<List<LocalSongBean>>() {
                    @Override
                    public void onNext(@NonNull List<LocalSongBean> localSongBeen) {
                        super.onNext(localSongBeen);
                        if (localSongBeen != null && localSongBeen.size() != 0) {
                            getView().loadArtistSongsResult(mLocalSongMapper.transform(localSongBeen));
                        } else {
                            getView().emptyView();
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        super.onError(e);
                        getView().loadFailed(e);
                    }
                });
    }
}
