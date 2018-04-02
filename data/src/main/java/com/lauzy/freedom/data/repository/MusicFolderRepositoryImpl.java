package com.lauzy.freedom.data.repository;

import android.content.Context;

import com.freedom.lauzy.model.Folder;
import com.freedom.lauzy.repository.MusicFolderRepository;
import com.lauzy.freedom.data.local.loader.LocalFolderLoader;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * Desc : 音乐文件夹数据实现类
 * Author : Lauzy
 * Date : 2018/3/15
 * Blog : http://www.jianshu.com/u/e76853f863a9
 * Email : freedompaladin@gmail.com
 */
public class MusicFolderRepositoryImpl implements MusicFolderRepository {

    private Context mContext;

    @Inject
    public MusicFolderRepositoryImpl(Context context) {
        mContext = context;
    }

    @Override
    public Observable<List<Folder>> getMusicFolders() {
        return Observable.just(LocalFolderLoader.getMusicFolders(mContext));
    }
}
