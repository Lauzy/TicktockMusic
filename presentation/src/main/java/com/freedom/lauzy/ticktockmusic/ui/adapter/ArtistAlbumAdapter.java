package com.freedom.lauzy.ticktockmusic.ui.adapter;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.freedom.lauzy.model.LocalAlbumBean;
import com.freedom.lauzy.ticktockmusic.R;
import com.freedom.lauzy.ticktockmusic.navigation.Navigator;
import com.lauzy.freedom.librarys.imageload.ImageConfig;
import com.lauzy.freedom.librarys.imageload.ImageLoader;

import java.util.List;

/**
 * Desc : 歌手专辑Adapter
 * Author : Lauzy
 * Date : 2017/9/29
 * Blog : http://www.jianshu.com/u/e76853f863a9
 * Email : freedompaladin@gmail.com
 */
public class ArtistAlbumAdapter extends BaseQuickAdapter<LocalAlbumBean, BaseViewHolder> {

    public ArtistAlbumAdapter(@LayoutRes int layoutResId, @Nullable List<LocalAlbumBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, LocalAlbumBean item) {
        String songNum = mContext.getResources().getString(item.songsNum == 1 ?
                R.string.song_numbers : R.string.song_number, item.songsNum);
        helper.setText(R.id.txt_song_title, item.albumName)
                .setText(R.id.txt_song_singer, songNum);
        helper.getView(R.id.img_item_menu).setVisibility(View.GONE);
        ImageLoader.getInstance().display(mContext, new ImageConfig.Builder()
                .url(item.albumCover)
                .placeholder(R.drawable.ic_default)
                .into(helper.getView(R.id.img_song_pic))
                .build());
        helper.getView(R.id.layout_song_item).setOnClickListener(v -> {
                    String transName = mContext.getString(R.string.img_transition) + item.id;
                    helper.getView(R.id.img_song_pic).setTransitionName(transName);
                    Navigator.navigateToAlbumDetail(mContext, helper.getView(R.id.img_song_pic),
                            transName, item.albumName, item.id);
                }
        );
    }
}
