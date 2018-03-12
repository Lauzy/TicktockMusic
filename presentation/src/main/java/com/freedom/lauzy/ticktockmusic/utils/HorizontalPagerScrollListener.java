package com.freedom.lauzy.ticktockmusic.utils;

import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Desc : PagerSnapHelper、水平滑动RecyclerView监听
 * Author : Lauzy
 * Date : 2018/2/12
 * Blog : http://www.jianshu.com/u/e76853f863a9
 * Email : freedompaladin@gmail.com
 */
public abstract class HorizontalPagerScrollListener extends RecyclerView.OnScrollListener {
    private OrientationHelper mOrientationHelper;
    private int mLastPosition = -1;

    public void setLastPosition(int lastPosition) {
        mLastPosition = lastPosition;
    }

    @Override
    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        super.onScrollStateChanged(recyclerView, newState);
        if (newState != RecyclerView.SCROLL_STATE_IDLE) {
            return;
        }

        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        if (mOrientationHelper == null) {
            mOrientationHelper = OrientationHelper.createHorizontalHelper(layoutManager);
        }

        final int rvCenter;
        if (recyclerView.getClipToPadding()) {
            rvCenter = mOrientationHelper.getStartAfterPadding() + mOrientationHelper.getTotalSpace() / 2;
        } else {
            rvCenter = mOrientationHelper.getTotalSpace() / 2;
        }

        int minSpace = Integer.MAX_VALUE;
        View minCloseView = null;
        final int childCount = recyclerView.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childView = recyclerView.getChildAt(i);
            RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) childView.getLayoutParams();
            final int childStart = mOrientationHelper.getDecoratedStart(childView) + layoutParams.leftMargin;
            final int childWidth = mOrientationHelper.getDecoratedMeasurement(childView) - layoutParams.leftMargin - layoutParams.rightMargin;
            final int childCenter = childStart + childWidth / 2;
            final int space = Math.abs(childCenter - rvCenter);
            if (minSpace > space) {
                minSpace = space;
                minCloseView = childView;
            }
        }
        if (minCloseView != null) {
            int position = recyclerView.getChildViewHolder(minCloseView).getAdapterPosition();
            if (position == RecyclerView.NO_POSITION) {
                return;
            }

            if (mLastPosition != position) {
                onPageChange(position);
                mLastPosition = position;
            }
        }
    }

    protected abstract void onPageChange(int position);
}
