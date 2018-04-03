package com.freedom.lauzy.ticktockmusic.ui.adapter;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.freedom.lauzy.model.NetSongBean;
import com.freedom.lauzy.ticktockmusic.R;
import com.freedom.lauzy.ticktockmusic.model.SongEntity;
import com.freedom.lauzy.ticktockmusic.model.mapper.NetEntityMapper;
import com.freedom.lauzy.ticktockmusic.service.MusicManager;
import com.freedom.lauzy.ticktockmusic.service.MusicUtil;
import com.freedom.lauzy.ticktockmusic.utils.CheckNetwork;
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
                        .cacheStrategy(ImageConfig.CACHE_SOURCE)
                        .placeholder(R.drawable.ic_album_default)
                        .into(helper.getView(R.id.img_song_pic))
                        .build());

        helper.getView(R.id.layout_song_item).setOnClickListener(playListener(helper, item));
        helper.getView(R.id.img_item_menu).setVisibility(View.GONE);
    }

    private View.OnClickListener playListener(BaseViewHolder helper, NetSongBean item) {
        return v -> {
            SongEntity currentSong = MusicManager.getInstance().getCurrentSong();
            if (currentSong == null) {
                CheckNetwork.checkNetwork(mContext, () -> playSong(helper.getAdapterPosition()));
                return;
            }
            if (String.valueOf(currentSong.id).equals(item.songId)) {
                playSong(helper.getAdapterPosition());
                return;
            }
            CheckNetwork.checkNetwork(mContext, () -> playSong(helper.getAdapterPosition()));
        };
    }

    private void playSong(int position) {
        MusicManager.getInstance().playMusic(NetEntityMapper.transform(mData), MusicUtil
                .getSongIds(NetEntityMapper.transform(mData)), position);
    }
}
