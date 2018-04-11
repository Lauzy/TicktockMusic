package com.freedom.lauzy.ticktockmusic.ui.adapter;

import android.content.res.ColorStateList;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.PopupMenu;

import com.afollestad.materialdialogs.MaterialDialog;
import com.bilibili.magicasakura.utils.ThemeUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.freedom.lauzy.ticktockmusic.R;
import com.freedom.lauzy.ticktockmusic.model.SongEntity;
import com.freedom.lauzy.ticktockmusic.model.mapper.LocalSongMapper;
import com.freedom.lauzy.ticktockmusic.navigation.Navigator;
import com.freedom.lauzy.ticktockmusic.service.MusicManager;
import com.freedom.lauzy.ticktockmusic.service.MusicUtil;
import com.lauzy.freedom.librarys.common.IntentUtil;
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
public class LocalSongAdapter extends BaseQuickAdapter<SongEntity, BaseViewHolder> {

    public LocalSongAdapter(@LayoutRes int layoutResId, @Nullable List<SongEntity> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, SongEntity item) {
        String singerAlbum = item.artistName + " 丨 " + item.albumName;
        helper.setText(R.id.txt_song_title, item.title)
                .setText(R.id.txt_song_singer, singerAlbum);

        SongEntity currentSong = MusicManager.getInstance().getCurrentSong();
        if (currentSong != null && currentSong.equals(item)) {
            ColorStateList csl = ThemeUtils.getThemeColorStateList(mContext, R.color.color_tab);
            helper.setTextColor(R.id.txt_song_title, csl.getDefaultColor());
            helper.setTextColor(R.id.txt_song_singer, csl.getDefaultColor());
        } else {
            helper.setTextColor(R.id.txt_song_title, ContextCompat.getColor(mContext, R.color.txt_black));
            helper.setTextColor(R.id.txt_song_singer, ContextCompat.getColor(mContext, R.color.gray_dark));
        }

        ImageLoader.getInstance().display(mContext,
                new ImageConfig.Builder()
                        .url(item.albumCover)
                        .isRound(false)
                        .placeholder(R.drawable.ic_album_default)
                        .into(helper.getView(R.id.img_song_pic))
                        .build());
        helper.getView(R.id.layout_song_item).setOnClickListener(v -> playSong(helper.getAdapterPosition()));
        helper.getView(R.id.img_item_menu).setOnClickListener(menuListener(helper, item));

    }

    private void playSong(int position) {
        MusicManager.getInstance().playMusic(LocalSongMapper.transformLocal(mData),
                MusicUtil.getSongIds(mData), position);
    }

    private View.OnClickListener menuListener(BaseViewHolder helper, SongEntity songEntity) {
        return v -> {
            PopupMenu popupMenu = new PopupMenu(mContext, v);
            popupMenu.setOnMenuItemClickListener(item -> {
                switch (item.getItemId()) {
                    case R.id.menu_item_play:
                        playSong(helper.getAdapterPosition());
                        break;
                    case R.id.menu_item_singer:
                        gotoSingerDetail(songEntity);
                        break;
                    case R.id.menu_item_album:
                        gotoAlbumDetail(helper, songEntity);
                        break;
                    case R.id.menu_item_share:
                        IntentUtil.shareFile(mContext, songEntity.path);
                        break;
                    case R.id.menu_item_delete:
                        deleteSong(helper, songEntity);
                        break;
                }
                return false;
            });
            popupMenu.inflate(R.menu.menu_play_list_item);
            popupMenu.show();
        };
    }

    private void deleteSong(BaseViewHolder helper, SongEntity songEntity) {
        new MaterialDialog.Builder(mContext)
                .content(R.string.delete_song)
                .positiveText(android.R.string.yes)
                .negativeText(android.R.string.cancel)
                .onPositive((dialog, which) -> {
                    if (mOnDeleteSongListener != null) {
                        mOnDeleteSongListener.onDelete(helper.getAdapterPosition(), songEntity);
                    }
                }).build().show();
    }

    private void gotoSingerDetail(SongEntity songEntity) {
        Navigator.navigateToArtistDetail(mContext, null, null,
                songEntity.artistName, songEntity.artistId, 0, 0);
    }

    private void gotoAlbumDetail(BaseViewHolder helper, SongEntity songEntity) {
        String transName = mContext.getString(R.string.img_transition) + helper.getAdapterPosition();
        helper.getView(R.id.img_song_pic).setTransitionName(transName);
        Navigator.navigateToAlbumDetail(mContext, helper.getView(R.id.img_song_pic),
                transName, songEntity.albumName, songEntity.albumId);
    }

    private OnDeleteSongListener mOnDeleteSongListener;

    public void setOnDeleteSongListener(OnDeleteSongListener onDeleteSongListener) {
        mOnDeleteSongListener = onDeleteSongListener;
    }

    public interface OnDeleteSongListener {
        void onDelete(int position, SongEntity songEntity);
    }
}