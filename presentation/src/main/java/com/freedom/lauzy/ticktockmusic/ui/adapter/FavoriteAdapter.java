package com.freedom.lauzy.ticktockmusic.ui.adapter;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.PopupMenu;

import com.afollestad.materialdialogs.MaterialDialog;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.freedom.lauzy.model.SongType;
import com.freedom.lauzy.ticktockmusic.R;
import com.freedom.lauzy.ticktockmusic.model.SongEntity;
import com.freedom.lauzy.ticktockmusic.navigation.Navigator;
import com.freedom.lauzy.ticktockmusic.service.MusicManager;
import com.freedom.lauzy.ticktockmusic.service.MusicUtil;
import com.freedom.lauzy.ticktockmusic.utils.CheckNetwork;
import com.lauzy.freedom.librarys.common.IntentUtil;
import com.lauzy.freedom.librarys.imageload.ImageConfig;
import com.lauzy.freedom.librarys.imageload.ImageLoader;

import java.util.List;

/**
 * Desc : 我的喜欢Adapter
 * Author : Lauzy
 * Date : 2017/9/13
 * Blog : http://www.jianshu.com/u/e76853f863a9
 * Email : freedompaladin@gmail.com
 */
public class FavoriteAdapter extends BaseQuickAdapter<SongEntity, BaseViewHolder> {

    public FavoriteAdapter(@LayoutRes int layoutResId, @Nullable List<SongEntity> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, SongEntity item) {
        helper.setText(R.id.txt_song_title, item.title)
                .setText(R.id.txt_song_singer, item.artistName);
        ImageLoader.getInstance().display(mContext,
                new ImageConfig.Builder()
                        .url(item.albumCover)
                        .placeholder(R.drawable.ic_default)
                        .into(helper.getView(R.id.img_song_pic))
                        .build());
        helper.getView(R.id.layout_song_item).setOnClickListener(v -> {
            if (item.equals(MusicManager.getInstance().getCurrentSong())) {
                Navigator navigator = new Navigator();
                navigator.navigateToPlayActivity(mContext, helper.getView(R.id.img_song_pic));
            } else {
                if (item.type.equals(SongType.LOCAL)) {
                    MusicManager.getInstance().playMusic(mData, MusicUtil.getSongIds(mData), helper.getAdapterPosition());
                    return;
                }
                CheckNetwork.checkNetwork(mContext, () -> MusicManager.getInstance()
                        .playMusic(mData, MusicUtil.getSongIds(mData), helper.getAdapterPosition()));
            }
        });
        helper.getView(R.id.img_item_menu).setOnClickListener(menuListener(helper, item));
    }

    private View.OnClickListener menuListener(BaseViewHolder helper, SongEntity songEntity) {
        return v -> {
            PopupMenu popupMenu = new PopupMenu(mContext, v);
            popupMenu.setOnMenuItemClickListener(item -> {
                switch (item.getItemId()) {
                    case R.id.menu_item_play:
                        playSong(songEntity, helper.getAdapterPosition());
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
            if (songEntity.type.equals(SongType.NET)) {
                popupMenu.getMenu().findItem(R.id.menu_item_singer).setVisible(false);
                popupMenu.getMenu().findItem(R.id.menu_item_album).setVisible(false);
                popupMenu.getMenu().findItem(R.id.menu_item_share).setVisible(false);
            }
            popupMenu.show();
        };
    }

    private void playSong(SongEntity item, int position) {
        if (item.type.equals(SongType.LOCAL)) {
            MusicManager.getInstance().playMusic(mData, MusicUtil.getSongIds(mData), position);
            return;
        }
        if (MusicManager.getInstance().getCurrentSong() == null) {
            CheckNetwork.checkNetwork(mContext, () -> MusicManager.getInstance().playMusic(mData,
                    MusicUtil.getSongIds(mData), position));
            return;
        }
        if (item.id == MusicManager.getInstance().getCurrentSong().id) {
            MusicManager.getInstance().playMusic(mData, MusicUtil.getSongIds(mData), position);
            return;
        }

        CheckNetwork.checkNetwork(mContext, () -> MusicManager.getInstance().playMusic(mData,
                MusicUtil.getSongIds(mData), position));
    }

    private void deleteSong(BaseViewHolder helper, SongEntity songEntity) {
        new MaterialDialog.Builder(mContext)
                .content(R.string.delete_song)
                .positiveText(android.R.string.yes)
                .negativeText(android.R.string.cancel)
                .onPositive((dialog, which) -> {
                    if (mOnDeleteListener != null) {
                        mOnDeleteListener.deleteSong(songEntity, helper.getAdapterPosition());
                    }
                }).build().show();
    }

    private void gotoAlbumDetail(BaseViewHolder helper, SongEntity songEntity) {
        String transName = mContext.getString(R.string.img_transition) + helper.getAdapterPosition();
        helper.getView(R.id.img_song_pic).setTransitionName(transName);
        Navigator.navigateToAlbumDetail(mContext, helper.getView(R.id.img_song_pic),
                transName, songEntity.albumName, songEntity.albumId);
    }

    private void gotoSingerDetail(SongEntity songEntity) {
        Navigator.navigateToArtistDetail(mContext, null, null,
                songEntity.artistName, songEntity.artistId, 0, 0);
    }

    private OnDeleteListener mOnDeleteListener;

    public void setOnDeleteListener(OnDeleteListener onDeleteListener) {
        mOnDeleteListener = onDeleteListener;
    }

    public interface OnDeleteListener {
        void deleteSong(SongEntity songEntity, int position);
    }
}
