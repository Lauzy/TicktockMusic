package com.freedom.lauzy.ticktockmusic.ui.adapter;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.PopupMenu;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.freedom.lauzy.model.QueueSongBean;
import com.freedom.lauzy.ticktockmusic.R;
import com.freedom.lauzy.ticktockmusic.model.SongEntity;
import com.freedom.lauzy.ticktockmusic.model.mapper.LocalSongMapper;
import com.freedom.lauzy.ticktockmusic.navigation.Navigator;
import com.freedom.lauzy.ticktockmusic.service.MusicManager;
import com.freedom.lauzy.ticktockmusic.service.MusicUtil;
import com.lauzy.freedom.data.database.PlayQueueDao;
import com.lauzy.freedom.librarys.common.LogUtil;
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
        ImageLoader.INSTANCE.display(mContext,
                new ImageConfig.Builder()
                        .url(item.albumCover)
                        .placeholder(R.drawable.ic_album_default)
                        .into(helper.getView(R.id.img_song_pic))
                        .build());
        helper.getView(R.id.layout_song_item).setOnClickListener(v ->
                MusicManager.getInstance().playLocalQueue(LocalSongMapper.transformLocal(mData),
                        MusicUtil.getSongIds(mData), helper.getAdapterPosition()));
        helper.getView(R.id.img_item_menu).setOnClickListener(menuListener(helper, item));
    }

    private View.OnClickListener menuListener(BaseViewHolder helper, SongEntity songEntity) {
        return v -> {
            PopupMenu popupMenu = new PopupMenu(mContext, v);
            popupMenu.setOnMenuItemClickListener(item -> {
                switch (item.getItemId()) {
                    case R.id.menu_item_play_next:
                        //TODO 插入数组，再获取队列（判断是否为空）
                        String[] curIds = MusicManager.getInstance().getCurIds();
                        String[] newIds = insertArr(curIds, MusicManager.getInstance().getCurPosition() + 1, String.valueOf(songEntity.id));
                        List<QueueSongBean> songBeen = PlayQueueDao.getInstance(mContext).queryQueue(newIds);
                        for (QueueSongBean queueSongBean : songBeen) {
                            LogUtil.e("EEEEE", queueSongBean.title);
                        }
                        break;
                    case R.id.menu_item_singer:
                        break;
                    case R.id.menu_item_album:
                        gotoAlbumDetail(helper, songEntity);
                        break;
                    case R.id.menu_item_share:
                        break;
                    case R.id.menu_item_delete:
                        break;
                }
                return false;
            });
            popupMenu.inflate(R.menu.menu_play_list_item);
            popupMenu.show();
        };
    }

    private void gotoAlbumDetail(BaseViewHolder helper, SongEntity songEntity) {
        String transName = mContext.getString(R.string.img_transition) + helper.getAdapterPosition();
        helper.getView(R.id.img_song_pic).setTransitionName(transName);
        Navigator.navigateToAlbumDetail(mContext, helper.getView(R.id.img_song_pic),
                transName, songEntity.albumName, songEntity.albumId);
    }

    public String[] insertArr(String[] arr, int index, String value) {
        String[] newArr = new String[arr.length + 1];
//        for (int i = 0; i < arr.length; i++) {
//            newArr[i] = arr[i];
//        }
//        for (int i = newArr.length - 1; i > index; i--) {
//            newArr[i] = newArr[i - 1];
//        }

        System.arraycopy(arr, 0, newArr, 0, arr.length);
        System.arraycopy(newArr, index, newArr, index + 1, newArr.length - 1 - index);
        newArr[index] = value;
        return newArr;
    }
}