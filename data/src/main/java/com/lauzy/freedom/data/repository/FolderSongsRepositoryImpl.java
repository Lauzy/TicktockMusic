package com.lauzy.freedom.data.repository;

import android.content.Context;

import com.freedom.lauzy.model.LocalSongBean;
import com.freedom.lauzy.repository.FolderSongsRepository;
import com.lauzy.freedom.data.local.loader.LocalSongLoader;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * Desc : 文件夹音乐
 * Author : Lauzy
 * Date : 2018/3/15
 * Blog : http://www.jianshu.com/u/e76853f863a9
 * Email : freedompaladin@gmail.com
 */
public class FolderSongsRepositoryImpl implements FolderSongsRepository {

    private Context mContext;

    @Inject
    public FolderSongsRepositoryImpl(Context context) {
        mContext = context;
    }

    @Override
    public Observable<List<LocalSongBean>> getFolderSongs(String folderPath) {
        return Observable.just(LocalSongLoader.getLocalFolderSongList(mContext, folderPath));
    }
}
