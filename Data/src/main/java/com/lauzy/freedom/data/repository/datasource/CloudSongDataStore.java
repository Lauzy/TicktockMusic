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

import com.lauzy.freedom.data.entity.SongListEntity;
import com.lauzy.freedom.data.net.api.SongService;

import java.util.List;

import io.reactivex.Observable;

class CloudSongDataStore implements SongDataStore {

    private final SongService mService;

    CloudSongDataStore(SongService songService) {
        mService = songService;
    }

    @Override
    public Observable<List<SongListEntity>> getSongListEntities(String method, int type, int offset, int size) {
        return null;
    }
}
