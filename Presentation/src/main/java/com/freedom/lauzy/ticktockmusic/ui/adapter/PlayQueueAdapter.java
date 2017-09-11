package com.freedom.lauzy.ticktockmusic.ui.adapter;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.freedom.lauzy.ticktockmusic.R;
import com.freedom.lauzy.ticktockmusic.model.SongEntity;
import com.freedom.lauzy.ticktockmusic.presenter.PlayQueuePresenter;
import com.freedom.lauzy.ticktockmusic.service.MusicManager;
import com.freedom.lauzy.ticktockmusic.service.MusicUtil;
import com.lauzy.freedom.librarys.widght.fonts.SubTextUtil;

import java.util.List;

/**
 * Desc : 播放队列Adapter
 * Author : Lauzy
 * Date : 2017/9/8
 * Blog : http://www.jianshu.com/u/e76853f863a9
 * Email : freedompaladin@gmail.com
 */
public class PlayQueueAdapter extends BaseQuickAdapter<SongEntity, BaseViewHolder> {

    private PlayQueuePresenter mPresenter;

    public void setQueuePresenter(PlayQueuePresenter presenter) {
        mPresenter = presenter;
    }

    public PlayQueueAdapter(@LayoutRes int layoutResId, @Nullable List<SongEntity> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, SongEntity item) {
        if (MusicManager.getInstance().getCurPosition() == helper.getAdapterPosition()) {
            helper.setTextColor(R.id.txt_queue_title, ContextCompat.getColor(mContext, R.color.blue));
        } else {
            helper.setTextColor(R.id.txt_queue_title, ContextCompat.getColor(mContext, R.color.gray_dark));
        }
        helper.setText(R.id.txt_queue_title, SubTextUtil.addEllipsis(item.title, 15))
                .setText(R.id.txt_queue_singer, item.artistName);
        helper.getView(R.id.img_queue_delete).setOnClickListener(deleteItemListener(helper, item));
        helper.getView(R.id.layout_queue_item).setOnClickListener(playListener(helper));
    }

    private View.OnClickListener playListener(BaseViewHolder helper) {
        return v -> MusicManager.getInstance().playLocalQueue(mData, MusicUtil.getSongIds(mData),
                helper.getAdapterPosition());
    }

    private View.OnClickListener deleteItemListener(BaseViewHolder helper, SongEntity item) {
        return v -> mPresenter.deleteQueueData(new String[]{String.valueOf(item.id)})
                .subscribe(integer -> {
                    MusicManager.getInstance().setMusicServiceData(MusicUtil.getSongIds(mData),
                            helper.getAdapterPosition());
                    mData.remove(helper.getAdapterPosition());
                    notifyItemRemoved(helper.getAdapterPosition());
                });
    }
}
