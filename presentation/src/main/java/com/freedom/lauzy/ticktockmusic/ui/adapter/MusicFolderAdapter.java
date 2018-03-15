package com.freedom.lauzy.ticktockmusic.ui.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.freedom.lauzy.model.Folder;
import com.freedom.lauzy.ticktockmusic.R;

import java.util.List;

/**
 * Desc : 文件夹Adapter
 * Author : Lauzy
 * Date : 2018/3/15
 * Blog : http://www.jianshu.com/u/e76853f863a9
 * Email : freedompaladin@gmail.com
 */
public class MusicFolderAdapter extends BaseQuickAdapter<Folder, BaseViewHolder> {

    public MusicFolderAdapter(int layoutResId, @Nullable List<Folder> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Folder item) {
        helper.setText(R.id.tv_file_name, item.folderName)
                .setText(R.id.tv_desc, item.folderPath);
        helper.getView(R.id.ll_folder_item).setOnClickListener(v -> {
            if (mOnFolderClickListener != null) {
                Folder folder = mData.get(helper.getAdapterPosition());
                mOnFolderClickListener.onFolderClick(folder.folderPath, folder.folderName);
            }
        });
    }

    private OnFolderClickListener mOnFolderClickListener;

    public void setOnFolderClickListener(OnFolderClickListener onFolderClickListener) {
        mOnFolderClickListener = onFolderClickListener;
    }

    public interface OnFolderClickListener {
        void onFolderClick(String folderPath, String folderName);
    }
}
