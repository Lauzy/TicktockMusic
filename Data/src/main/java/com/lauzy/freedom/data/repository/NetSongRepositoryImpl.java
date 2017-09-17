package com.lauzy.freedom.data.repository;

import com.freedom.lauzy.model.NetSongEntity;
import com.freedom.lauzy.repository.NetSongRepository;
import com.lauzy.freedom.data.entity.OnlineSongEntity;
import com.lauzy.freedom.data.entity.mapper.NetSongMapper;
import com.lauzy.freedom.data.net.RetrofitHelper;
import com.lauzy.freedom.data.net.api.SongService;

import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;

/**
 * Desc : 获取单个歌曲信息（包括播放链接）
 * Author : Lauzy
 * Date : 2017/9/17
 * Blog : http://www.jianshu.com/u/e76853f863a9
 * Email : freedompaladin@gmail.com
 */
public class NetSongRepositoryImpl implements NetSongRepository {
    @Override
    public Observable<NetSongEntity> getNetSongData(String method, long songId) {
        return RetrofitHelper.INSTANCE.createApi(SongService.class)
                .getOnlineSongData(method, songId)
                .map(new Function<OnlineSongEntity, NetSongEntity>() {
                    @Override
                    public NetSongEntity apply(@NonNull OnlineSongEntity songEntity) throws Exception {
                        NetSongMapper netSongMapper = new NetSongMapper();
                        return netSongMapper.transform(songEntity);
                    }
                });
    }
}
