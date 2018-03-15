package com.freedom.lauzy.ticktockmusic.injection.component;

import android.content.Context;

import com.freedom.lauzy.executor.PostExecutionThread;
import com.freedom.lauzy.executor.ThreadExecutor;
import com.freedom.lauzy.repository.FavoriteRepository;
import com.freedom.lauzy.repository.FolderSongsRepository;
import com.freedom.lauzy.repository.LocalSongRepository;
import com.freedom.lauzy.repository.MusicFolderRepository;
import com.freedom.lauzy.repository.QueueRepository;
import com.freedom.lauzy.repository.RecentRepository;
import com.freedom.lauzy.repository.SongRepository;
import com.freedom.lauzy.ticktockmusic.injection.module.ApplicationModule;
import com.freedom.lauzy.ticktockmusic.injection.scope.ContextLife;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Desc : ApplicationComponent
 * Author : Lauzy
 * Date : 2017/7/3
 * Blog : http://www.jianshu.com/u/e76853f863a9
 * Email : freedompaladin@gmail.com
 */

@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {

    @ContextLife()
    Context getApplication();

    ThreadExecutor threadExecutor();

    PostExecutionThread postExecutionThread();

    SongRepository songRepository();

    LocalSongRepository localSongRepository();

    QueueRepository queueRepository();

    FavoriteRepository favoriteRepository();

    RecentRepository recentRepository();

    MusicFolderRepository musicFolderRepository();

    FolderSongsRepository folderSongsRepository();
}
