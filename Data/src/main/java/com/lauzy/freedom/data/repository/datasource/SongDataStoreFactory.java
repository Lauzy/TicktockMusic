/**
 * Copyright (C) 2015 Fernando Cejas Open Source Project
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.lauzy.freedom.data.repository.datasource;

import android.content.Context;
import android.support.annotation.NonNull;

import com.lauzy.freedom.data.entity.mapper.SongListMapper;
import com.lauzy.freedom.data.net.RetrofitHelper;
import com.lauzy.freedom.data.net.api.SongService;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Desc : 歌曲数据实现类（7.11暂不考虑磁盘缓存，待加入数据库）
 * Author : Lauzy
 * Date : 2017/7/11
 * Blog : http://www.jianshu.com/u/e76853f863a9
 * Email : freedompaladin@gmail.com
 */
@Singleton
public class SongDataStoreFactory {

    private final Context mContext;

    @Inject
    SongDataStoreFactory(@NonNull Context context) {
        mContext = context.getApplicationContext();
    }

    public SongDataStore create(String method, int type, int offset, int size) {
        return createCloudDataStore(method, type, offset, size);
    }

    public SongDataStore createCloudDataStore(String method, int type, int offset, int size) {
        SongListMapper listMapper = new SongListMapper();
        RetrofitHelper retrofitHelper = new RetrofitHelper(mContext);
        SongService songService = retrofitHelper.createApi(SongService.class);
//        return songService.getSongList(method, type, offset, size);
        return null;
    }
}
