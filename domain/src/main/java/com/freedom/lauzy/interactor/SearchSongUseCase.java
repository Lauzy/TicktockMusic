package com.freedom.lauzy.interactor;

import com.freedom.lauzy.executor.PostExecutionThread;
import com.freedom.lauzy.executor.ThreadExecutor;
import com.freedom.lauzy.model.LocalSongBean;
import com.freedom.lauzy.repository.SearchSongRepository;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * Desc : 搜索
 * Author : Lauzy
 * Date : 2018/5/22
 * Blog : http://www.jianshu.com/u/e76853f863a9
 * Email : freedompaladin@gmail.com
 */
public class SearchSongUseCase extends UseCase<List<LocalSongBean>, String> {

    private SearchSongRepository mSearchSongRepository;

    @Inject
    SearchSongUseCase(ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread,
                      SearchSongRepository searchSongRepository) {
        super(threadExecutor, postExecutionThread);
        mSearchSongRepository = searchSongRepository;
    }

    @Override
    Observable<List<LocalSongBean>> buildUseCaseObservable(String s) {
        return mSearchSongRepository.searchSong(s);
    }
}
