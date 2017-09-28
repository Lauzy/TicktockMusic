package com.freedom.lauzy.ticktockmusic.ui.adapter;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.freedom.lauzy.model.LocalArtistBean;
import com.freedom.lauzy.ticktockmusic.R;

import java.util.List;

/**
 * Desc : 本地歌手列表Adapter
 * Author : Lauzy
 * Date : 2017/9/28
 * Blog : http://www.jianshu.com/u/e76853f863a9
 * Email : freedompaladin@gmail.com
 */
public class ArtistAdapter extends BaseQuickAdapter<LocalArtistBean, BaseViewHolder> {

    public ArtistAdapter(@LayoutRes int layoutResId, @Nullable List<LocalArtistBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, LocalArtistBean item) {
        String albumNum = mContext.getResources().getString(item.albumsNum == 1
                ? R.string.album_number : R.string.album_numbers, item.albumsNum);
        String songsNum = mContext.getResources().getString(item.songsNum == 1
                ? R.string.song_number : R.string.song_numbers, item.songsNum);
        String detail = albumNum + " | " + songsNum;
        helper.setText(R.id.txt_singer_name, item.artistName)
                .setText(R.id.txt_singer_album_num, detail);
        ((ImageView) helper.getView(R.id.img_singer_pic)).setImageResource(R.drawable.ic_default);
        if (mAvatarListener != null) {
            mAvatarListener.setArtistAvatar(item, helper.getView(R.id.img_singer_pic));
        }
    }

    private ArtistAvatarListener mAvatarListener;

    public void setAvatarListener(ArtistAvatarListener avatarListener) {
        mAvatarListener = avatarListener;
    }

    public interface ArtistAvatarListener {
        void setArtistAvatar(LocalArtistBean artistBean, ImageView imageView);
    }
}
