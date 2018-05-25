package com.freedom.lauzy.ticktockmusic.ui.adapter;

import android.content.res.ColorStateList;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;

import com.bilibili.magicasakura.utils.ThemeUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.freedom.lauzy.ticktockmusic.R;
import com.freedom.lauzy.ticktockmusic.model.SongEntity;
import com.freedom.lauzy.ticktockmusic.model.mapper.LocalSongMapper;
import com.freedom.lauzy.ticktockmusic.service.MusicManager;
import com.freedom.lauzy.ticktockmusic.service.MusicUtil;
import com.freedom.lauzy.ticktockmusic.utils.HighlightFormatUtil;
import com.lauzy.freedom.librarys.imageload.ImageConfig;
import com.lauzy.freedom.librarys.imageload.ImageLoader;

import java.util.List;

/**
 * Desc : 搜索
 * Author : Lauzy
 * Date : 2017/8/8
 * Blog : http://www.jianshu.com/u/e76853f863a9
 * Email : freedompaladin@gmail.com
 */
public class SearchAdapter extends BaseQuickAdapter<SongEntity, BaseViewHolder> {

    private String mContent;

    public SearchAdapter(@LayoutRes int layoutResId, @Nullable List<SongEntity> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, SongEntity item) {
        ColorStateList stateList = ThemeUtils.getThemeColorStateList(mContext, R.color.color_tab);
        CharSequence titleResult = new HighlightFormatUtil(item.title,
                mContent, stateList.getDefaultColor()).fillColor().getResult();

        String singerAlbum = item.artistName + " 丨 " + item.albumName;
        helper.setText(R.id.txt_song_title, titleResult)
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
    }

    private void playSong(int position) {
        MusicManager.getInstance().playMusic(LocalSongMapper.transformLocal(mData),
                MusicUtil.getSongIds(mData), position);
    }

    public void setSearchContent(String content) {
        mContent = content;
    }
}