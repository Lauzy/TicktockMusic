package com.freedom.lauzy.interactor;

import com.freedom.lauzy.executor.PostExecutionThread;
import com.freedom.lauzy.executor.ThreadExecutor;
import com.freedom.lauzy.model.LrcBean;
import com.freedom.lauzy.repository.LrcRepository;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * Desc : 歌词
 * Author : Lauzy
 * Date : 2018/3/26
 * Blog : http://www.jianshu.com/u/e76853f863a9
 * Email : freedompaladin@gmail.com
 */
public class LrcUseCase extends UseCase<List<LrcBean>, LrcUseCase.Param> {

    private final LrcRepository mLrcRepository;

    @Inject
    LrcUseCase(ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread,
               LrcRepository lrcRepository) {
        super(threadExecutor, postExecutionThread);
        mLrcRepository = lrcRepository;
    }

    @Override
    Observable<List<LrcBean>> buildUseCaseObservable(Param param) {
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
