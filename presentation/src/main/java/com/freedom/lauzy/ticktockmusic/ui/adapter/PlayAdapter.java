package com.freedom.lauzy.ticktockmusic.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.freedom.lauzy.ticktockmusic.R;
import com.freedom.lauzy.ticktockmusic.model.SongEntity;
import com.freedom.lauzy.ticktockmusic.service.MusicManager;
import com.lauzy.freedom.librarys.imageload.ImageConfig;
import com.lauzy.freedom.librarys.imageload.ImageLoader;
import com.lauzy.freedom.librarys.widght.CircleImageView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Desc : PlayViewAdapter
 * Author : Lauzy
 * Date : 2018/2/5
 * Blog : http://www.jianshu.com/u/e76853f863a9
 * Email : freedompaladin@gmail.com
 */
public class PlayAdapter extends RecyclerView.Adapter<PlayAdapter.PlayViewHolder> {

    private List<SongEntity> mSongEntities;
    private Context mContext;
    private int mAnimPosition;

    public PlayAdapter(Context context, List<SongEntity> songEntities) {
        mSongEntities = songEntities;
        mContext = context;
    }

    @Override
    public PlayViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new PlayViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_play_view, parent, false));
    }

    @Override
    public void onBindViewHolder(PlayViewHolder holder, int position) {
        CircleImageView cvPlayView = holder.mCvImageCover;
        SongEntity item = mSongEntities.get(position % mSongEntities.size());
        if (position % mSongEntities.size() == mAnimPosition % mSongEntities.size()) {
            if (item.isAnim()) {
                cvPlayView.start();
            } else {
                cvPlayView.pause();
            }
        } else {
            cvPlayView.setRotation(0);
            cvPlayView.stop();
        }
        ImageLoader.INSTANCE.display(mContext, new ImageConfig.Builder()
                .isRound(false)
                .url(item.albumCover)
                .cacheStrategy(ImageConfig.CACHE_ALL)
                .placeholder(R.drawable.ic_default)
                .into(cvPlayView)
                .build());
    }

    @Override
    public int getItemCount() {
        return Integer.MAX_VALUE;
    }

    public List<SongEntity> getData() {
        return mSongEntities;
    }

    /**
     * 开始旋转
     */
    public void startRotation() {
        mAnimPosition = getPlayPosition(MusicManager.getInstance().getCurPosition());
        mSongEntities.get(mAnimPosition % mSongEntities.size()).setAnim(true);
        notifyDataSetChanged();
    }

    /**
     * 暂停旋转
     */
    public void pauseRotation() {
        mAnimPosition = getPlayPosition(MusicManager.getInstance().getCurPosition());
        mSongEntities.get(mAnimPosition % mSongEntities.size()).setAnim(false);
        notifyDataSetChanged();
    }

    public int getPlayPosition(int curPos) {
        int halfMaxValue = Integer.MAX_VALUE / 2;
        int position = halfMaxValue % mSongEntities.size();
        //（总数-余数）（N倍数据长度）+当前位置=实际位置
        return halfMaxValue - position + curPos;
    }

    /**
     * 更新动画的View
     *
     * @param position 当前position
     */
    public void updateAnimView(int position) {
        if (mAnimPosition == position) {
            return;
        }
        mAnimPosition = position;
        mSongEntities.get(position % mSongEntities.size()).setAnim(true);
        notifyDataSetChanged();
    }

    public void setData(List<SongEntity> songData) {
        mSongEntities = songData;
    }

    class PlayViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.cv_music_cover)
        CircleImageView mCvImageCover;

        PlayViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
