package com.freedom.lauzy.ticktockmusic.ui.adapter;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.freedom.lauzy.model.SongListBean;
import com.freedom.lauzy.ticktockmusic.R;

import java.util.List;

/**
 * Desc :
 * Author : Lauzy
 * Date : 2017/8/8
 * Blog : http://www.jianshu.com/u/e76853f863a9
 * Email : freedompaladin@gmail.com
 */
public class NetSongAdapter extends BaseQuickAdapter<SongListBean, BaseViewHolder> {

    public NetSongAdapter(@LayoutRes int layoutResId, @Nullable List<SongListBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, SongListBean item) {
        helper.setText(R.id.txt_song_title, item.title)
                .setText(R.id.txt_song_singer, item.artistName);
    }
}
