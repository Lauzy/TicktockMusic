package com.freedom.lauzy.interactor;

import com.freedom.lauzy.executor.PostExecutionThread;
import com.freedom.lauzy.executor.ThreadExecutor;
import com.freedom.lauzy.repository.LrcRepository;

import javax.inject.Inject;

import io.reactivex.Observable;
import okhttp3.ResponseBody;

/**
 * Desc : 歌词
 * Author : Lauzy
 * Date : 2018/3/26
 * Blog : http://www.jianshu.com/u/e76853f863a9
 * Email : freedompaladin@gmail.com
 */
public class LrcUseCase extends UseCase<ResponseBody, LrcUseCase.Param> {

    private final LrcRepository mLrcRepository;

    @Inject
    LrcUseCase(ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread,
               LrcRepository lrcRepository) {
        super(threadExecutor, postExecutionThread);
        mLrcRepository = lrcRepository;
    }

    @Override
    Observable<ResponseBody> buildUseCaseObservable(Param param) {
        return mLrcRepository.getLrcData(param.getSongName(), param.getSinger());
    }

    public static final class Param {

        private String songName;
        private String singer;

        public Param(String songName, String singer) {
            this.songName = songName;
            this.singer = singer;
        }

        public String getSongName() {
            return songName;
        }

        public void setSongName(String songName) {
            this.songName = songName;
        }

        public String getSinger() {
            return singer;
        }

        public void setSinger(String singer) {
            this.singer = singer;
        }
    }
}
