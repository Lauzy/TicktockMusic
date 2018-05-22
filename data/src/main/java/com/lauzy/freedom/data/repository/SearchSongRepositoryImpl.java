package com.lauzy.freedom.data.repository;

import android.content.Context;

import com.freedom.lauzy.model.LocalSongBean;
import com.freedom.lauzy.repository.SearchSongRepository;
import com.lauzy.freedom.data.local.loader.LocalSongLoader;

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
public class SearchSongRepositoryImpl implements SearchSongRepository {

    private Context mContext;

    @Inject
    public SearchSongRepositoryImpl(Context context) {
        mContext = context;
    }

    @Override
    public Observable<List<LocalSongBean>> searchSong(String songName) {
        return Observable.just(LocalSongLoader.getSearchSongList(mContext, songName));
    }
}
