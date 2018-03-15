package com.freedom.lauzy.interactor;

import com.freedom.lauzy.executor.PostExecutionThread;
import com.freedom.lauzy.executor.ThreadExecutor;
import com.freedom.lauzy.model.ArtistAvatar;
import com.freedom.lauzy.model.LocalArtistBean;
import com.freedom.lauzy.model.LocalSongBean;
import com.freedom.lauzy.repository.LocalSongRepository;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * Desc : 本地歌手数据
 * Author : Lauzy
 * Date : 2017/9/28
 * Blog : http://www.jianshu.com/u/e76853f863a9
 * Email : freedompaladin@gmail.com
 */
public class GetLocalArtistUseCase extends UseCase<List<LocalArtistBean>, Void> {

    private final LocalSongRepository mLocalSongRepository;

    @Inject
    GetLocalArtistUseCase(LocalSongRepository localSongRepository, ThreadExecutor threadExecutor,
                          PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        mLocalSongRepository = localSongRepository;
    }

    @Override
    Observable<List<LocalArtistBean>> buildUseCaseObservable(Void aVoid) {
        return mLocalSongRepository.getLocalArtistList();
    }

    public Observable<ArtistAvatar> getArtistAvatar(String method, String apiKey, String artistName, String format) {
        return mLocalSongRepository.getArtistAvatar(method, apiKey, artistName, format);
    }

    public Observable<List<LocalSongBean>> getArtistSongList(long id) {
        return mLocalSongRepository.getLocalArtistSongList(id);
    }
}
