package com.lauzy.freedom.data.repository;

import com.freedom.lauzy.model.SongListBean;
import com.freedom.lauzy.repository.SongRepository;
import com.lauzy.freedom.data.entity.MusicEntity;
import com.lauzy.freedom.data.entity.mapper.SongListMapper;
import com.lauzy.freedom.data.net.RetrofitHelper;
import com.lauzy.freedom.data.net.api.SongService;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;

/**
 * Desc :
 * Author : Lauzy
 * Date : 2017/7/10
 * Blog : http://www.jianshu.com/u/e76853f863a9
 * Email : freedompaladin@gmail.com
 */
@Singleton
public class SongRepositoryImpl implements SongRepository {

    @Inject
    SongRepositoryImpl() {
    }

    @Override
    public Observable<List<SongListBean>> getSongList(final String method, final int type,
                                                      final int offset, final int size) {

        return RetrofitHelper.INSTANCE.createApi(SongService.class)
                .getMusicData(method, type, offset, size)
                .map(new Function<MusicEntity, List<SongListBean>>() {
                    @Override
                    public List<SongListBean> apply(@NonNull MusicEntity musicEntity) throws Exception {
                        SongListMapper mapper = new SongListMapper();
                        return mapper.transform(musicEntity.song_list);
                    }
                });
    }

    @Override
    public Observable<List<SongListBean>> getCacheSongList() {
        return Observable.create(new ObservableOnSubscribe<List<SongListBean>>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<List<SongListBean>> e) throws Exception {
                List<SongListBean> listBeen = new ArrayList<>();
                for (int i = 0; i < 10; i++) {
                    SongListBean bean = new SongListBean();
                    bean.title = "loading";
                    bean.artistName = "loading";
                    listBeen.add(bean);
                }
                e.onNext(listBeen);
                e.onComplete();
            }
        });
    }
}
