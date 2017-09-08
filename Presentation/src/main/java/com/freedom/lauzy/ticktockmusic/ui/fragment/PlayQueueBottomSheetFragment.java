package com.freedom.lauzy.ticktockmusic.ui.fragment;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.freedom.lauzy.ticktockmusic.R;
import com.freedom.lauzy.ticktockmusic.TicktockApplication;
import com.freedom.lauzy.ticktockmusic.contract.PlayQueueContract;
import com.freedom.lauzy.ticktockmusic.injection.component.DaggerFragmentComponent;
import com.freedom.lauzy.ticktockmusic.injection.module.FragmentModule;
import com.freedom.lauzy.ticktockmusic.model.SongEntity;
import com.freedom.lauzy.ticktockmusic.presenter.PlayQueuePresenter;
import com.freedom.lauzy.ticktockmusic.service.MusicManager;
import com.lauzy.freedom.librarys.common.ScreenUtils;

import java.util.List;

import javax.inject.Inject;

/**
 * Desc :
 * Author : Lauzy
 * Date : 2017/9/8
 * Blog : http://www.jianshu.com/u/e76853f863a9
 * Email : freedompaladin@gmail.com
 */
public class PlayQueueBottomSheetFragment extends BottomSheetDialogFragment
        implements View.OnClickListener, PlayQueueContract.View {

    @Inject
    PlayQueuePresenter mQueuePresenter;
    private Activity mActivity;
    private RecyclerView mRvPlayQueue;

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
        BottomSheetDialog dialog = (BottomSheetDialog) super.onCreateDialog(savedInstanceState);
        View view = View.inflate(getContext(), R.layout.layout_play_queue, null);
        setUpBottomView(view);
        dialog.setContentView(view);
        return dialog;
    }

    private void setUpBottomView(View view) {
        setViewSize(view);
        mQueuePresenter.loadQueueData(MusicManager.getInstance().getCurIds());
        mRvPlayQueue = (RecyclerView) view.findViewById(R.id.rv_play_queue);
        TextView txtPlayMode = (TextView) view.findViewById(R.id.txt_play_mode);
        ImageView imgMode = (ImageView) view.findViewById(R.id.img_queue_mode);
        view.findViewById(R.id.img_delete_queue).setOnClickListener(this);
    }

    private void setViewSize(View view) {
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.WRAP_CONTENT);
        params.height = ScreenUtils.getScreenHeight(mActivity.getApplicationContext()) / 2;
        view.setLayoutParams(params);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_delete_queue:
                break;
        }
    }

    @Override
    public void loadQueueData(List<SongEntity> songEntities) {

    }

    @Override
    public void emptyView() {

    }

    @Override
    public void deleteAllQueueData() {

    }

    @Override
    public void deleteQueueItem() {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mQueuePresenter.detachView();
    }
}
