package com.freedom.lauzy.ticktockmusic.ui.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.freedom.lauzy.ticktockmusic.R;
import com.freedom.lauzy.ticktockmusic.model.SongEntity;
import com.lauzy.freedom.librarys.imageload.ImageConfig;
import com.lauzy.freedom.librarys.imageload.ImageLoader;
import com.lauzy.freedom.librarys.widght.CircleImageView;

import java.util.List;

/**
 * Desc : PlayViewAdapter
 * Author : Lauzy
 * Date : 2018/2/5
 * Blog : http://www.jianshu.com/u/e76853f863a9
 * Email : freedompaladin@gmail.com
 */
public class PlayAdapter extends BaseQuickAdapter<SongEntity, BaseViewHolder> {

    public PlayAdapter(int layoutResId, @Nullable List<SongEntity> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, SongEntity item) {
        CircleImageView cvPlayView = helper.getView(R.id.cv_music_cover);
        if (item.isStop()) {
            cvPlayView.setRotation(0);
        }
        if (item.isAnim()) {
            cvPlayView.start();
        } else {
            cvPlayView.pause();
        }
        ImageLoader.INSTANCE.display(mContext, new ImageConfig.Builder()
                .isRound(false)
                .url(item.albumCover)
                .cacheStrategy(ImageConfig.CACHE_ALL)
                .placeholder(R.drawable.ic_default)
                .into(cvPlayView)
                .build());
    }
}
