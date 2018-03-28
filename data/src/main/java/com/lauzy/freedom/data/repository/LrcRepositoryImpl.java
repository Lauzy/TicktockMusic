package com.lauzy.freedom.data.repository;

import com.freedom.lauzy.model.LrcBean;
import com.freedom.lauzy.repository.LrcRepository;
import com.lauzy.freedom.data.entity.BaiduLrcEntity;
import com.lauzy.freedom.data.entity.OnLineLrcEntity;
import com.lauzy.freedom.data.entity.mapper.LrcMapper;
import com.lauzy.freedom.data.net.RetrofitHelper;
import com.lauzy.freedom.data.net.api.SongService;

import java.util.Collections;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;
import okhttp3.ResponseBody;

/**
 * Desc : 歌词
 * Author : Lauzy
 * Date : 2018/3/26
 * Blog : http://www.jianshu.com/u/e76853f863a9
 * Email : freedompaladin@gmail.com
 */
public class LrcRepositoryImpl implements LrcRepository {
    @Override
    public Observable<ResponseBody> getLrcData(String songName, String singer) {
        return RetrofitHelper.INSTANCE.createApi(SongService.class)
                .getOnLrcData(songName, singer)
                .map(new Function<OnLineLrcEntity, List<LrcBean>>() {
                    @Override
                    public List<LrcBean> apply(OnLineLrcEntity onLineLrcEntity) throws Exception {
                        if (onLineLrcEntity == null) {
                            return Collections.emptyList();
                        }
                        List<OnLineLrcEntity.ResultBean> result = onLineLrcEntity.result;
                        if (result == null || result.isEmpty()) {
                            return Collections.emptyList();
                        }
                        return LrcMapper.transform(result);
                    }
                }).flatMap(new Function<List<LrcBean>, ObservableSource<ResponseBody>>() {
                    @Override
                    public ObservableSource<ResponseBody> apply(List<LrcBean> lrcBeans) throws Exception {
                        if (lrcBeans == null || lrcBeans.isEmpty()) {
                            return null;
                        }
                        LrcBean lrcBean = lrcBeans.get(0);
                        if (lrcBean == null) {
                            return null;
                        }
                        return RetrofitHelper.INSTANCE.createApi(SongService.class)
                                .downloadLrcFile(lrcBean.lrc);
                    }
                });
    }

    @Override
    public Observable<String> getBaiduLrcData(String method, long songId) {
        return RetrofitHelper.INSTANCE.createApi(SongService.class)
                .getBaiduLrcData(method, songId)
                .map(new Function<BaiduLrcEntity, String>() {
                    @Override
                    public String apply(BaiduLrcEntity baiduLrcEntity) throws Exception {
                        if (baiduLrcEntity == null || baiduLrcEntity.lrcContent == null) {
                            return "";
                        }
                        return baiduLrcEntity.lrcContent;
                    }
                });
    }
}
