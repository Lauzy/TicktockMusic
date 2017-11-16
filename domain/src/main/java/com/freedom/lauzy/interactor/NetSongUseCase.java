package com.freedom.lauzy.interactor;

import com.freedom.lauzy.executor.PostExecutionThread;
import com.freedom.lauzy.executor.ThreadExecutor;
import com.freedom.lauzy.model.NetSongEntity;
import com.freedom.lauzy.repository.NetSongRepository;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * Desc : 获取网络歌曲的播放资源用例
 * Author : Lauzy
 * Date : 2017/9/17
 * Blog : http://www.jianshu.com/u/e76853f863a9
 * Email : freedompaladin@gmail.com
 */
public class NetSongUseCase extends UseCase<NetSongEntity, NetSongUseCase.Params> {

    private final NetSongRepository mNetSongRepository;

    @Inject
    public NetSongUseCase(NetSongRepository netSongRepository, ThreadExecutor threadExecutor,
                   PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        mNetSongRepository = netSongRepository;
    }

    @Override
    Observable<NetSongEntity> buildUseCaseObservable(Params params) {
        return mNetSongRepository.getNetSongData(params.method, params.songId);
    }

    public static final class Params {
        private String method;
        private long songId;

        public Params(String method, long songId) {
            this.method = method;
            this.songId = songId;
        }
    }
}
