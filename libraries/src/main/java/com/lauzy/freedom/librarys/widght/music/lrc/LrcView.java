package com.lauzy.freedom.librarys.widght.music.lrc;

import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.ColorInt;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.OverScroller;

import com.lauzy.freedom.librarys.R;

import java.util.List;

/**
 * Desc : 歌词
 * Author : Lauzy
 * Date : 2017/10/13
 * Blog : http://www.jianshu.com/u/e76853f863a9
 * Email : freedompaladin@gmail.com
 */
public class LrcView extends View {

    private static final String DEFAULT_CONTENT = "Empty";
    private List<Lrc> mLrcData;
    private TextPaint mTextPaint;
    //    private Rect mBounds;
    private String mDefaultContent;
    private int mCurrentLine;
    private float mOffset;
    private float mLastMotionY;
    private int mScaledTouchSlop;
    private OverScroller mOverScroller;
    private VelocityTracker mVelocityTracker;
    private int mMaximumFlingVelocity;
    private int mMinimumFlingVelocity;
    private float mLrcTextSize;
    private float mLrcLineSpaceHeight;
    private int mTouchDelay;
    private int mNormalColor;
    private int mCurrentPlayLineColor;
    private float mHorizontalPadding;
    private float mVerticalPadding;
    private float mNoLrcTextSize;
    private int mNoLrcTextColor;
    //是否拖拽中，否的话响应onClick事件
    private boolean isDragging;
    //用户开始操作
    private boolean isUserScroll;
    private boolean isAutoAdjustPosition = true;

    public LrcView(Context context) {
        this(context, null);
    }

    public LrcView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LrcView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.LrcView);
        mLrcTextSize = typedArray.getDimension(R.styleable.LrcView_lrcTextSize, sp2px(context, 15));
        mLrcLineSpaceHeight = typedArray.getDimension(R.styleable.LrcView_lrcLineSpaceSize, dp2px(context, 20));
        mTouchDelay = typedArray.getInt(R.styleable.LrcView_lrcTouchDelay, 2500);
        mNormalColor = typedArray.getColor(R.styleable.LrcView_lrcNormalTextColor, Color.GRAY);
        mCurrentPlayLineColor = typedArray.getColor(R.styleable.LrcView_lrcCurrentTextColor, Color.BLUE);
        mHorizontalPadding = typedArray.getDimension(R.styleable.LrcView_lrcHorizontalPadding, 0f);
        mVerticalPadding = typedArray.getDimension(R.styleable.LrcView_lrcVerticalPadding, 0f);
        mNoLrcTextSize = typedArray.getDimension(R.styleable.LrcView_noLrcTextSize, dp2px(context, 20));
        mNoLrcTextColor = typedArray.getColor(R.styleable.LrcView_noLrcTextColor, Color.BLACK);
        typedArray.recycle();

        setupConfigs(context);
    }

    private void setupConfigs(Context context) {
        mScaledTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        mMaximumFlingVelocity = ViewConfiguration.get(context).getScaledMaximumFlingVelocity();
        mMinimumFlingVelocity = ViewConfiguration.get(context).getScaledMinimumFlingVelocity();
        mOverScroller = new OverScroller(context);

        mTextPaint = new TextPaint();
        mTextPaint.setAntiAlias(true);
        mTextPaint.setTextAlign(Paint.Align.CENTER);
        mTextPaint.setTextSize(mLrcTextSize);
        mDefaultContent = DEFAULT_CONTENT;
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    private int getLrcWidth() {
        return (int) (getWidth() - mHorizontalPadding * 2);
    }

    private int getLrcHeight() {
        return (int) (getHeight() - mVerticalPadding * 2);
    }

    private boolean isLrcEmpty() {
        return mLrcData == null || getLrcCount() == 0;
    }

    private int getLrcCount() {
        return mLrcData.size();
    }

    public void setLrcData(List<Lrc> lrcData) {
        resetView(DEFAULT_CONTENT);
        mLrcData = lrcData;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (isLrcEmpty()) {
            drawEmptyText(canvas);
            return;
        }
        mTextPaint.setTextSize(mLrcTextSize);
        mTextPaint.setTextAlign(Paint.Align.CENTER);
        float y = getLrcHeight() / 2;
        float x = getLrcWidth() * 0.5f + mHorizontalPadding;
        for (int i = 0; i < getLrcCount(); i++) {
            if (i > 0) {
                y += (getTextHeight(i - 1) + getTextHeight(i)) / 2 + mLrcLineSpaceHeight;
            }
            if (mCurrentLine == i) {
                mTextPaint.setColor(mCurrentPlayLineColor);
            } else {
                mTextPaint.setColor(mNormalColor);
            }
            @SuppressLint("DrawAllocation")
            StaticLayout staticLayout = new StaticLayout(mLrcData.get(i).getText(), mTextPaint,
                    getLrcWidth(), Layout.Alignment.ALIGN_NORMAL, 1f, 0f, false);
            canvas.save();
            canvas.translate(x, y + mVerticalPadding - staticLayout.getHeight() / 2 - mOffset);
            staticLayout.draw(canvas);
            canvas.restore();
//            canvas.drawText(mLrcData.get(i).getText(), x, y, mTextPaint);
        }
    }

    //中间空文字
    private void drawEmptyText(Canvas canvas) {
        mTextPaint.setTextAlign(Paint.Align.CENTER);
        mTextPaint.setColor(mNoLrcTextColor);
        mTextPaint.setTextSize(mNoLrcTextSize);
//        Paint.FontMetricsInt fontMetrics = mTextPaint.getFontMetricsInt();
//        int baseline = (int) ((getLrcHeight() - fontMetrics.bottom + fontMetrics.top) / 2 - fontMetrics.top + mVerticalPadding);
//        int baseX = (int) ((getLrcWidth() - mBounds.width()) / 2 + mHorizontalPadding);
//        canvas.drawText(mDefaultContent, baseX, baseline, mTextPaint);
        canvas.save();
        StaticLayout staticLayout = new StaticLayout(mDefaultContent, mTextPaint,
                getLrcWidth(), Layout.Alignment.ALIGN_NORMAL, 1f, 0f, false);
        canvas.translate(getLrcWidth() / 2 + mHorizontalPadding, getLrcHeight() / 2 + mVerticalPadding);
        staticLayout.draw(canvas);
        canvas.restore();
    }

    public void updateTime(long time) {
        if (isLrcEmpty()) {
            return;
        }
        int linePosition = getUpdateTimeLinePosition(time);
        if (mCurrentLine != linePosition) {
            mCurrentLine = linePosition;
            if (isUserScroll) {
                invalidate();
                return;
            }
            ViewCompat.postOnAnimation(LrcView.this, mScrollRunnable);
//            scrollToPosition(linePosition);
        }
    }

    public int getUpdateTimeLinePosition(long time) {
        int linePos = 0;
        for (int i = 0; i < getLrcCount(); i++) {
            Lrc lrc = mLrcData.get(i);
            if (time >= lrc.getTime()) {
                if (i == getLrcCount() - 1) {
                    linePos = getLrcCount() - 1;
                } else if (time < mLrcData.get(i + 1).getTime()) {
                    linePos = i;
                    break;
                }
            }
        }
        return linePos;
    }

    private Runnable mScrollRunnable = new Runnable() {
        @Override
        public void run() {
            isUserScroll = false;
            scrollToPosition(mCurrentLine);
        }
    };

    private void scrollToPosition(int linePosition) {
//        float scrollY = linePosition != getLrcCount() - 1 ? getItemOffsetY(linePosition) : getTotalLrcHeight();
        float scrollY = getItemOffsetY(linePosition);
        final ValueAnimator animator = ValueAnimator.ofFloat(mOffset, scrollY);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mOffset = (float) animation.getAnimatedValue();
                invalidate();
            }
        });
        animator.setDuration(300);
        animator.start();
    }

    private float getItemOffsetY(int linePosition) {
        float tempY = 0;
        for (int i = 0; i < linePosition; i++) {
            if (i > 0) {
                tempY += (getTextHeight(i - 1) + getTextHeight(i)) / 2 + mLrcLineSpaceHeight;
            } else {
                tempY = getTextHeight(0) + mLrcLineSpaceHeight;
            }
        }
        return tempY;
    }

    private float getTextHeight(int linePosition) {
        StaticLayout staticLayout = new StaticLayout(mLrcData.get(linePosition).getText(), mTextPaint,
                getLrcWidth(), Layout.Alignment.ALIGN_NORMAL, 1f, 0f, false);
        return staticLayout.getHeight();
    }

    private boolean overScrolled() {
        return mOffset > getItemOffsetY(getLrcCount() - 1) || mOffset < 0;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (isLrcEmpty()) {
            return super.onTouchEvent(event);
        }
        if (mVelocityTracker == null) {
            mVelocityTracker = VelocityTracker.obtain();
        }
        mVelocityTracker.addMovement(event);
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                removeCallbacks(mScrollRunnable);
                if (!mOverScroller.isFinished()) {
                    mOverScroller.abortAnimation();
                }
                mLastMotionY = event.getY();
                isUserScroll = true;
                isDragging = false;
                break;

            case MotionEvent.ACTION_MOVE:
                float moveY = event.getY() - mLastMotionY;
                if (Math.abs(moveY) > mScaledTouchSlop) {
                    isDragging = true;
                }
                if (isDragging) {
//                    double exp = Math.exp(-event.getY() / mLastMotionY);
//                    moveY = (float) (exp * moveY);
                    mOffset -= moveY;
                    if (mOffset < 0) {
                        mOffset = Math.max(mOffset, -getTextHeight(0) - mLrcLineSpaceHeight);
                    }
                    float maxHeight = getItemOffsetY(getLrcCount() - 1);
                    if (mOffset > maxHeight) {
                        mOffset = Math.min(mOffset, maxHeight + getTextHeight(0) + mLrcLineSpaceHeight);
                    }
                    mLastMotionY = event.getY();
                    invalidate();
                }
                break;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                if (!isDragging) {
                    performClick();
                }
                handleActionUp();
                break;
        }
//        return isDragging || super.onTouchEvent(event);
        return true;
    }

    private void handleActionUp() {
        if (overScrolled() && mOffset < 0) {
            scrollToPosition(0);
            ViewCompat.postOnAnimationDelayed(LrcView.this, mScrollRunnable, mTouchDelay);
            return;
        }

        if (overScrolled() && mOffset > getItemOffsetY(getLrcCount() - 1)) {
            scrollToPosition(getLrcCount() - 1);
            ViewCompat.postOnAnimationDelayed(LrcView.this, mScrollRunnable, mTouchDelay);
            return;
        }

        mVelocityTracker.computeCurrentVelocity(1000, mMaximumFlingVelocity);
        float YVelocity = mVelocityTracker.getYVelocity();
        float absYVelocity = Math.abs(YVelocity);
        if (absYVelocity > mMinimumFlingVelocity) {
            mOverScroller.fling(0, (int) mOffset, 0, (int) (-YVelocity), 0,
                    0, 0, (int) getItemOffsetY(getLrcCount() - 1), 0, (int) getTextHeight(0));
            invalidate();
        }
        releaseVelocityTracker();
        if (isAutoAdjustPosition) {
            ViewCompat.postOnAnimationDelayed(LrcView.this, mScrollRunnable, mTouchDelay);
        }
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if (mOverScroller.computeScrollOffset()) {
            mOffset = mOverScroller.getCurrY();
            invalidate();
        }
    }

    private void releaseVelocityTracker() {
        if (null != mVelocityTracker) {
            mVelocityTracker.clear();
            mVelocityTracker.recycle();
            mVelocityTracker = null;
        }
    }

    public void resetView(String defaultContent) {
        if (mLrcData != null) {
            mLrcData.clear();
        }
        mCurrentLine = 0;
        mOffset = 0;
        isUserScroll = false;
        mDefaultContent = defaultContent;
        removeCallbacks(mScrollRunnable);
        invalidate();
    }

    @Override
    public boolean performClick() {
        return super.performClick();
    }

    public int dp2px(Context context, float dpVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                dpVal, context.getResources().getDisplayMetrics());
    }

    public int sp2px(Context context, float spVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,
                spVal, context.getResources().getDisplayMetrics());
    }

    public void pause() {
        isAutoAdjustPosition = false;
        invalidate();
    }

    public void resume() {
        isAutoAdjustPosition = true;
        ViewCompat.postOnAnimationDelayed(LrcView.this, mScrollRunnable, mTouchDelay);
        invalidate();
    }


    /*------------------Config-------------------*/

    public void setDefaultContent(String defaultContent) {
        mDefaultContent = defaultContent;
        invalidate();
    }

    public void setLrcTextSize(float lrcTextSize) {
        mLrcTextSize = lrcTextSize;
        invalidate();
    }

    public void setLrcLineSpaceHeight(float lrcLineSpaceHeight) {
        mLrcLineSpaceHeight = lrcLineSpaceHeight;
        invalidate();
    }

    public void setTouchDelay(int touchDelay) {
        mTouchDelay = touchDelay;
        invalidate();
    }

    public void setNormalColor(@ColorInt int normalColor) {
        mNormalColor = normalColor;
        invalidate();
    }

    public void setCurrentPlayLineColor(@ColorInt int currentPlayLineColor) {
        mCurrentPlayLineColor = currentPlayLineColor;
        invalidate();
    }

    public float getHorizontalPadding() {
        return mHorizontalPadding;
    }

    public void setHorizontalPadding(float horizontalPadding) {
        mHorizontalPadding = horizontalPadding;
        invalidate();
    }

    public float getVerticalPadding() {
        return mVerticalPadding;
    }

    public void setVerticalPadding(float verticalPadding) {
        mVerticalPadding = verticalPadding;
        invalidate();
    }

    public void setNoLrcTextSize(float noLrcTextSize) {
        mNoLrcTextSize = noLrcTextSize;
        invalidate();
    }

    public void setNoLrcTextColor(@ColorInt int noLrcTextColor) {
        mNoLrcTextColor = noLrcTextColor;
        invalidate();
    }
}
