package com.freedom.lauzy.ticktockmusic.ui.adapter;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.freedom.lauzy.model.NetSongBean;
import com.freedom.lauzy.ticktockmusic.R;
import com.freedom.lauzy.ticktockmusic.model.mapper.NetEntityMapper;
import com.freedom.lauzy.ticktockmusic.service.MusicManager;
import com.freedom.lauzy.ticktockmusic.service.MusicUtil;
import com.lauzy.freedom.librarys.imageload.ImageConfig;
import com.lauzy.freedom.librarys.imageload.ImageLoader;

import java.util.List;

/**
 * Desc : NetSongAdapter
 * Author : Lauzy
 * Date : 2017/8/8
 * Blog : http://www.jianshu.com/u/e76853f863a9
 * Email : freedompaladin@gmail.com
 */
public class NetSongAdapter extends BaseQuickAdapter<NetSongBean, BaseViewHolder> {

    public NetSongAdapter(@LayoutRes int layoutResId, @Nullable List<NetSongBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, NetSongBean item) {
        helper.setText(R.id.txt_song_title, item.title)
                .setText(R.id.txt_song_singer, item.artistName);
        ImageLoader.INSTANCE.display(mContext,
                new ImageConfig.Builder()
                        .url(item.imgUrl)
                        .placeholder(R.drawable.ic_album_default)
                        .into(helper.getView(R.id.img_song_pic))
                        .build());
//        helper.getView(R.id.layout_song_item).setOnClickListener(v -> MusicManager.getInstance()
//                .playNetQueue(mData, MusicUtil.getNetSongIds(mData), helper.getAdapterPosition()));
        helper.getView(R.id.layout_song_item).setOnClickListener(v -> MusicManager.getInstance()
                .playLocalQueue(NetEntityMapper.transform(mData), MusicUtil.getSongIds(NetEntityMapper.transform(mData)),
                        helper.getAdapterPosition()));
    }

}
