package com.freedom.lauzy.ticktockmusic.ui.fragment;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.freedom.lauzy.ticktockmusic.R;
import com.freedom.lauzy.ticktockmusic.TicktockApplication;
import com.freedom.lauzy.ticktockmusic.contract.PlayQueueContract;
import com.freedom.lauzy.ticktockmusic.event.DeleteQueueItemEvent;
import com.freedom.lauzy.ticktockmusic.event.PlayModeEvent;
import com.freedom.lauzy.ticktockmusic.function.RxBus;
import com.freedom.lauzy.ticktockmusic.injection.component.DaggerFragmentComponent;
import com.freedom.lauzy.ticktockmusic.injection.module.FragmentModule;
import com.freedom.lauzy.ticktockmusic.model.SongEntity;
import com.freedom.lauzy.ticktockmusic.presenter.PlayQueuePresenter;
import com.freedom.lauzy.ticktockmusic.service.MusicManager;
import com.freedom.lauzy.ticktockmusic.service.MusicService;
import com.freedom.lauzy.ticktockmusic.service.MusicUtil;
import com.freedom.lauzy.ticktockmusic.ui.adapter.PlayQueueAdapter;
import com.freedom.lauzy.ticktockmusic.utils.SharePrefHelper;
import com.lauzy.freedom.librarys.common.ScreenUtils;
import com.lauzy.freedom.librarys.widght.TickBottomSheetDialog;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Desc : 播放队列
 * Author : Lauzy
 * Date : 2017/9/8
 * Blog : http://www.jianshu.com/u/e76853f863a9
 * Email : freedompaladin@gmail.com
 */
public class PlayQueueBottomSheetFragment extends BottomSheetDialogFragment implements
        View.OnClickListener, PlayQueueContract.View, PlayQueueAdapter.QueueItemListener {

    private static final String TAG = "QueueBottomSheet";
    @Inject
    PlayQueuePresenter mQueuePresenter;
    private Activity mActivity;
    private PlayQueueAdapter mAdapter;
    private TextView mTxtPlayMode;
    private ImageView mImgMode;
    private List<SongEntity> mSongEntities = new ArrayList<>();
    private static final int DELETE_DELAY = 150; //删除后更新Item视图延迟时间

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof Activity) {
            mActivity = (Activity) context;
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initInjection();
    }

    private void initInjection() {
        DaggerFragmentComponent.builder()
                .applicationComponent(TicktockApplication.getInstance().getApplicationComponent())
                .fragmentModule(new FragmentModule(this))
                .build().inject(this);
    }

    @Override
    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        mQueuePresenter.attachView(this);
//        BottomSheetDialog dialog = (BottomSheetDialog) super.onCreateDialog(savedInstanceState);
        BottomSheetDialog dialog = new TickBottomSheetDialog(mActivity, getTheme());
        View view = View.inflate(getContext(), R.layout.layout_play_queue, null);
        setUpBottomView(view);
        dialog.setContentView(view);
        return dialog;
    }

    private void setUpBottomView(View view) {
        setViewSize(view);
        setUpRv(view);
        mTxtPlayMode = (TextView) view.findViewById(R.id.txt_play_mode);
        mImgMode = (ImageView) view.findViewById(R.id.img_queue_mode);
        setModeView();
        view.findViewById(R.id.img_clear_queue).setOnClickListener(this);
        mImgMode.setOnClickListener(this);
        MusicManager.getInstance().setPlayQueueListener(() -> mAdapter.notifyDataSetChanged());
    }

    private void setUpRv(View view) {
        RecyclerView rvPlayQueue = (RecyclerView) view.findViewById(R.id.rv_play_queue);
        rvPlayQueue.setLayoutManager(new LinearLayoutManager(mActivity));
        ((SimpleItemAnimator) rvPlayQueue.getItemAnimator()).setSupportsChangeAnimations(false);
        mAdapter = new PlayQueueAdapter(R.layout.layout_play_queue_item, mSongEntities);
        rvPlayQueue.setAdapter(mAdapter);
        mAdapter.setDeleteQueueItemListener(this);
        mQueuePresenter.loadQueueData(MusicManager.getInstance().getCurIds());
    }

    private void setModeView() {
        switch (SharePrefHelper.getRepeatMode(mActivity.getApplicationContext())) {
            case MusicService.REPEAT_SINGLE_MODE:
                mImgMode.setImageResource(R.drawable.ic_repeat_one_black);
                mTxtPlayMode.setText(R.string.repeat_single_mode);
                break;
            case MusicService.REPEAT_ALL_MODE:
                mImgMode.setImageResource(R.drawable.ic_repeat_black);
                mTxtPlayMode.setText(R.string.repeat_loop_mode);
                break;
            case MusicService.REPEAT_RANDOM_MODE:
                mImgMode.setImageResource(R.drawable.ic_shuffle_black);
                mTxtPlayMode.setText(R.string.repeat_shuffle_mode);
                break;
        }
    }

    /**
     * 设置 bottomSheet 的宽高
     *
     * @param view fragmentView
     */
    private void setViewSize(View view) {
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.WRAP_CONTENT);
        params.height = ScreenUtils.getScreenHeight(mActivity.getApplicationContext()) * 4 / 7;
        view.setLayoutParams(params);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_clear_queue:
                mQueuePresenter.deleteAllQueueData(MusicManager.getInstance().getCurIds());
                break;
            case R.id.img_queue_mode:
                switchMode();
                break;
        }
    }

    private void switchMode() {
        switch (SharePrefHelper.getRepeatMode(mActivity.getApplicationContext())) {
            case MusicService.REPEAT_SINGLE_MODE:
                SharePrefHelper.setRepeatMode(mActivity.getApplicationContext(), MusicService.REPEAT_RANDOM_MODE);
                mImgMode.setImageResource(R.drawable.ic_shuffle_black);
                mTxtPlayMode.setText(R.string.repeat_shuffle_mode);
                break;
            case MusicService.REPEAT_ALL_MODE:
                SharePrefHelper.setRepeatMode(mActivity.getApplicationContext(), MusicService.REPEAT_SINGLE_MODE);
                mImgMode.setImageResource(R.drawable.ic_repeat_one_black);
                mTxtPlayMode.setText(R.string.repeat_single_mode);
                break;
            case MusicService.REPEAT_RANDOM_MODE:
                SharePrefHelper.setRepeatMode(mActivity.getApplicationContext(), MusicService.REPEAT_ALL_MODE);
                mImgMode.setImageResource(R.drawable.ic_repeat_black);
                mTxtPlayMode.setText(R.string.repeat_loop_mode);
                break;
        }
        RxBus.INSTANCE.post(new PlayModeEvent());
    }

    @Override
    public void loadQueueData(List<SongEntity> songEntities) {
        Log.d(TAG, "size is " + songEntities.size());
        mSongEntities.clear();
        mSongEntities.addAll(songEntities);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void deleteItem(int position) {
        MusicManager.getInstance().setMusicServiceData(MusicUtil.getSongIds(mSongEntities), position);
        mSongEntities.remove(position);
        mAdapter.notifyItemRemoved(position);
        new Handler().postDelayed(() -> {
            mAdapter.notifyItemChanged(MusicManager.getInstance().getCurPosition());
            RxBus.INSTANCE.post(new DeleteQueueItemEvent());
            if (mSongEntities == null || mSongEntities.size() == 0) {
                MusicManager.getInstance().clearPlayData();
                mActivity.finish();
            }
        }, DELETE_DELAY);
    }

    @Override
    public void emptyView() {

    }

    @Override
    public void playNewSong(int position) {
        MusicManager.getInstance().playLocalQueue(mSongEntities,
                MusicUtil.getSongIds(mSongEntities), position);
        //设置回调接口，确保播放后刷新Adapter(setPlayQueueListener())
//        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void deleteQueueItem(int position, SongEntity entity) {
        mQueuePresenter.deleteQueueData(new String[]{String.valueOf(entity.id)}, position, entity);
    }

    @Override
    public void deleteAllQueueData() {
        mSongEntities.clear();
        mAdapter.notifyDataSetChanged();
        MusicManager.getInstance().clearPlayData();
        mActivity.finish();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mQueuePresenter.detachView();
    }
}
