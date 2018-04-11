package com.freedom.lauzy.ticktockmusic.ui.adapter;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.freedom.lauzy.ticktockmusic.R;
import com.freedom.lauzy.ticktockmusic.model.SongEntity;
import com.freedom.lauzy.ticktockmusic.model.mapper.LocalSongMapper;
import com.freedom.lauzy.ticktockmusic.service.MusicManager;
import com.freedom.lauzy.ticktockmusic.service.MusicUtil;
import com.lauzy.freedom.librarys.common.IntentUtil;
import com.lauzy.freedom.librarys.imageload.ImageConfig;
import com.lauzy.freedom.librarys.imageload.ImageLoader;

import java.util.List;

/**
 * Desc : 歌手音乐列表Adapter
 * Author : Lauzy
 * Date : 2017/9/29
 * Blog : http://www.jianshu.com/u/e76853f863a9
 * Email : freedompaladin@gmail.com
 */
public class ArtistSongAdapter extends BaseQuickAdapter<SongEntity, BaseViewHolder> {

    public ArtistSongAdapter(@LayoutRes int layoutResId, @Nullable List<SongEntity> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, SongEntity item) {
        String singerAlbum = item.artistName + "  |  " + item.albumName;
        helper.setText(R.id.txt_song_title, item.title)
                .setText(R.id.txt_song_singer, singerAlbum);
        ImageLoader.getInstance().display(mContext, new ImageConfig.Builder()
                .url(item.albumCover)
                .placeholder(R.drawable.ic_default)
                .into(helper.getView(R.id.img_song_pic))
                .build());
        helper.getView(R.id.layout_song_item).setOnClickListener(v ->
                MusicManager.getInstance().playMusic(LocalSongMapper.transformLocal(mData),
                        MusicUtil.getSongIds(mData), helper.getAdapterPosition()));
        helper.getView(R.id.img_item_menu).setOnClickListener(menuListener(helper, item));
    }

    private View.OnClickListener menuListener(BaseViewHolder helper, SongEntity entity) {
        return (View v) -> {
            PopupMenu popupMenu = new PopupMenu(mContext, v);
            popupMenu.setOnMenuItemClickListener((MenuItem item) -> {
                switch (item.getItemId()) {
                    case R.id.menu_item_play:
                        MusicManager.getInstance().playMusic(LocalSongMapper.transformLocal(mData),
                                MusicUtil.getSongIds(mData), helper.getAdapterPosition());
                        break;
                    case R.id.menu_item_share:
                        IntentUtil.shareFile(mContext, entity.path);
                        break;
                }
                return false;
            });
            popupMenu.inflate(R.menu.menu_album_singer_item);
            popupMenu.show();
        };
    }
}