package com.freedom.lauzy.ticktockmusic.ui.adapter;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.freedom.lauzy.ticktockmusic.R;
import com.freedom.lauzy.ticktockmusic.model.SongEntity;
import com.freedom.lauzy.ticktockmusic.navigation.Navigator;
import com.freedom.lauzy.ticktockmusic.service.MusicManager;
import com.lauzy.freedom.librarys.imageload.ImageConfig;
import com.lauzy.freedom.librarys.imageload.ImageLoader;

import java.util.List;

/**
 * Desc : 最近播放Adapter
 * Author : Lauzy
 * Date : 2017/9/15
 * Blog : http://www.jianshu.com/u/e76853f863a9
 * Email : freedompaladin@gmail.com
 */
public class RecentAdapter extends BaseQuickAdapter<SongEntity, BaseViewHolder> {

    public RecentAdapter(@LayoutRes int layoutResId, @Nullable List<SongEntity> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, SongEntity item) {
        helper.setText(R.id.txt_song_title, item.title)
                .setText(R.id.txt_song_singer, item.artistName);
        ImageLoader.INSTANCE.display(mContext,
                new ImageConfig.Builder()
                        .url(item.albumCover)
                        .isRound(false)
                        .cacheStrategy(ImageConfig.CACHE_SOURCE)
                        .placeholder(R.drawable.ic_default)
                        .into(helper.getView(R.id.img_song_pic))
                        .build());

        helper.getView(R.id.layout_song_item).setOnClickListener(v -> {
            if (item.equals(MusicManager.getInstance().getCurrentSong())) {
                Navigator navigator = new Navigator();
                navigator.navigateToPlayActivity(mContext, helper.getView(R.id.img_song_pic));
            } else {
                if (mRecentPlayListener != null) {
                    mRecentPlayListener.playRecent(item, helper.getAdapterPosition());
                }
            }
        });
    }

    private RecentPlayListener mRecentPlayListener;

    public void setRecentPlayListener(RecentPlayListener recentPlayListener) {
        mRecentPlayListener = recentPlayListener;
    }

    public interface RecentPlayListener {
        void playRecent(SongEntity entity, int position);
    }
}
