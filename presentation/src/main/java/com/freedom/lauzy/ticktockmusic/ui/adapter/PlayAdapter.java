package com.freedom.lauzy.ticktockmusic.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.freedom.lauzy.ticktockmusic.R;
import com.freedom.lauzy.ticktockmusic.model.SongEntity;
import com.freedom.lauzy.ticktockmusic.service.MusicManager;
import com.lauzy.freedom.librarys.imageload.ImageConfig;
import com.lauzy.freedom.librarys.imageload.ImageLoader;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Desc : RvPlayAdapter
 * Author : Lauzy
 * Date : 2018/3/12
 * Blog : http://www.jianshu.com/u/e76853f863a9
 * Email : freedompaladin@gmail.com
 */
public class PlayAdapter extends RecyclerView.Adapter<PlayAdapter.PlayViewHolder> {

    private Context mContext;

    public PlayAdapter(Context context) {
        mContext = context;
    }

    @Override
    public PlayViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new PlayViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_play_view, parent, false));
    }

    @Override
    public void onBindViewHolder(PlayViewHolder holder, int position) {
        ImageView cvPlayView = holder.mIvCover;
        SongEntity songEntity = MusicManager.getInstance().getSongData().get(position);
        ImageLoader.INSTANCE.display(mContext, new ImageConfig.Builder()
                .isRound(false)
                .url(songEntity.albumCover)
                .cacheStrategy(ImageConfig.CACHE_ALL)
                .placeholder(R.drawable.ic_default)
                .into(cvPlayView)
                .build());
    }

    @Override
    public int getItemCount() {
        return Integer.MAX_VALUE;
    }

    class PlayViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_play)
        ImageView mIvCover;

        PlayViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}