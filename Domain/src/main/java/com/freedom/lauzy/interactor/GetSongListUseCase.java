package com.freedom.lauzy.interactor;

import com.freedom.lauzy.executor.PostExecutionThread;
import com.freedom.lauzy.executor.ThreadExecutor;
import com.freedom.lauzy.model.NetSongBean;
import com.freedom.lauzy.repository.SongRepository;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * Desc : 音乐数据列表用例
 * Author : Lauzy
 * Date : 2017/7/6
 * Blog : http://www.jianshu.com/u/e76853f863a9
 * Email : freedompaladin@gmail.com
 */
public class GetSongListUseCase extends UseCase<List<NetSongBean>, GetSongListUseCase.Params> {

    private final SongRepository mSongRepository;

    @Inject
    GetSongListUseCase(SongRepository songRepository, ThreadExecutor threadExecutor,
                       PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        mSongRepository = songRepository;
    }


    @Override
    Observable<List<NetSongBean>> buildUseCaseObservable(GetSongListUseCase.Params params) {
        return mSongRepository.getSongList(params.method, params.type, params.offset, params.size);
    }

    public Observable<List<NetSongBean>> buildCacheObservable(int param) {
        return mSongRepository.getCacheSongList(param);
    }

    public static final class Params {
        private String method;
        private int type;
        private int offset;
        private int size;

        public Params(String method, int type, int offset, int size) {
            this.method = method;
            this.type = type;
            this.offset = offset;
            this.size = size;
        }

        public static Params forSongList(String method, int type, int offset, int size) {
            return new Params(method, type, offset, size);
        }
    }
}
