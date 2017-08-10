package com.freedom.lauzy.ticktockmusic.ui.adapter;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.freedom.lauzy.model.LocalSongBean;
import com.freedom.lauzy.ticktockmusic.R;
import com.lauzy.freedom.librarys.imageload.ImageConfig;
import com.lauzy.freedom.librarys.imageload.ImageLoader;

import java.util.List;

/**
 * Desc : LocalSongAdapter
 * Author : Lauzy
 * Date : 2017/8/8
 * Blog : http://www.jianshu.com/u/e76853f863a9
 * Email : freedompaladin@gmail.com
 */
public class LocalSongAdapter extends BaseQuickAdapter<LocalSongBean, BaseViewHolder> {

    public LocalSongAdapter(@LayoutRes int layoutResId, @Nullable List<LocalSongBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, LocalSongBean item) {
        String singerAlbum = item.artistName + " 丨 " + item.albumName;
        helper.setText(R.id.txt_song_title, item.title)
                .setText(R.id.txt_song_singer, singerAlbum);
        ImageLoader.INSTANCE.display(mContext,
                new ImageConfig.Builder()
                        .url(item.albumCover)
                        .placeholder(R.drawable.ic_default)
                        .into(helper.getView(R.id.img_song_pic))
                        .build());
    }
}
