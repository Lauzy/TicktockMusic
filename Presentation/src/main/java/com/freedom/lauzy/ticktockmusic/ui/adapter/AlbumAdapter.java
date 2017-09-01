package com.freedom.lauzy.ticktockmusic.ui.adapter;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.freedom.lauzy.model.LocalAlbumBean;
import com.freedom.lauzy.ticktockmusic.R;
import com.freedom.lauzy.ticktockmusic.navigation.Navigator;
import com.lauzy.freedom.data.local.LocalUtil;
import com.lauzy.freedom.librarys.imageload.ImageConfig;
import com.lauzy.freedom.librarys.imageload.ImageLoader;

import java.util.List;

/**
 * Desc : Album Adapter
 * Author : Lauzy
 * Date : 2017/8/10
 * Blog : http://www.jianshu.com/u/e76853f863a9
 * Email : freedompaladin@gmail.com
 */
public class AlbumAdapter extends BaseQuickAdapter<LocalAlbumBean, BaseViewHolder> {
    public AlbumAdapter(@LayoutRes int layoutResId, @Nullable List<LocalAlbumBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, LocalAlbumBean item) {
        String songsNum = mContext.getResources().getString(R.string.song_num, item.songsNum);
        helper.setText(R.id.txt_singer, item.artistName)
                .setText(R.id.txt_album_name, item.albumName)
                .setText(R.id.txt_song_num, songsNum);

        ImageLoader.INSTANCE.display(mContext, new ImageConfig.Builder()
                .url(LocalUtil.getCoverUri(item.id))
                .placeholder(R.drawable.ic_default)
                .into(helper.getView(R.id.img_album))
                .build());

        String transName = mContext.getString(R.string.img_transition) + helper.getAdapterPosition();
        helper.getView(R.id.img_album).setTransitionName(transName);
        helper.getView(R.id.cv_category).setOnClickListener(v ->
                Navigator.navigateToAlbumDetail(mContext, helper.getView(R.id.img_album),
                        transName, item.albumName, item.id)
        );
    }
}
