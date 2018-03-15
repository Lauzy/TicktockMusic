package com.freedom.lauzy.ticktockmusic.ui.adapter;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.PopupMenu;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
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
 * Desc : 文件夹音乐
 * Author : Lauzy
 * Date : 2018/3/15
 * Blog : http://www.jianshu.com/u/e76853f863a9
 * Email : freedompaladin@gmail.com
 */
public class FolderSongsAdapter extends BaseQuickAdapter<SongEntity, BaseViewHolder> {

    public FolderSongsAdapter(int layoutResId, @Nullable List<SongEntity> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, SongEntity item) {
        String singerAlbum = item.artistName + " 丨 " + item.albumName;
        helper.setText(R.id.txt_song_title, item.title)
                .setText(R.id.txt_song_singer, singerAlbum);
        ImageLoader.INSTANCE.display(mContext,
                new ImageConfig.Builder()
                        .url(item.albumCover)
                        .placeholder(R.drawable.ic_album_default)
                        .into(helper.getView(R.id.img_song_pic))
                        .build());

        helper.getView(R.id.layout_song_item).setOnClickListener(v ->
                MusicManager.getInstance().playMusic(LocalSongMapper.transformLocal(mData),
                        MusicUtil.getSongIds(mData), helper.getAdapterPosition()));
        helper.getView(R.id.img_item_menu).setOnClickListener(menuListener(helper, item));
    }

    private View.OnClickListener menuListener(BaseViewHolder helper, SongEntity songEntity) {
        return v -> {
            PopupMenu popupMenu = new PopupMenu(mContext, v);
            popupMenu.setOnMenuItemClickListener(item -> {
                switch (item.getItemId()) {
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
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {

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
}
