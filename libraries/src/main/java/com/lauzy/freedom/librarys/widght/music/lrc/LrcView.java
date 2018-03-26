package com.lauzy.freedom.librarys.widght.music.lrc;

import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
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

import java.util.ArrayList;
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
    private Rect mBounds;
    private String mDefaultContent;
    private float mLineHeight;
    private int mCurrentLine;
    private float mOffset;
    private float mLastMotionY;
    private int mScaledTouchSlop;
    private OverScroller mOverScroller;
    private VelocityTracker mVelocityTracker;
    private int mMaximumFlingVelocity;
    private int mMinimumFlingVelocity;
    private boolean isUserScroll;
    private List<Integer> mMultiLineTextExtraHeights = new ArrayList<>();
    private int mSingleTextHeight;
    private int mMultiLineTextExtraHeight;
    private float mLrcTextSize;
    private float mLrcLineSpaceHeight;
    private int mTouchDelay;
    private int mNormalColor;
    private int mCurrentPlayLineColor;
    private float mHorizontalPadding;
    private float mVerticalPadding;
    private float mNoLrcTextSize;
    private int mNoLrcTextColor;

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
        measureLineHeight();
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
        mBounds = new Rect();
        mDefaultContent = DEFAULT_CONTENT;
    }

    private void measureLineHeight() {
        Rect lineBound = new Rect();
        mTextPaint.getTextBounds(mDefaultContent, 0, mDefaultContent.length(), lineBound);
        mSingleTextHeight = lineBound.height();
        mLineHeight = mSingleTextHeight + mLrcLineSpaceHeight;
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        setupLrcLineHeight();
    }

    private void setupLrcLineHeight() {
        if (mLrcData == null || mLrcData.size() == 0) {
            return;
        }
        for (Lrc lrcDatum : mLrcData) {
            StaticLayout staticLayout = new StaticLayout(lrcDatum.getText(), mTextPaint,
                    getLrcWidth(), Layout.Alignment.ALIGN_NORMAL, 1f,
                    0f, false);
            if (staticLayout.getLineCount() > 1) {
                mMultiLineTextExtraHeight = mMultiLineTextExtraHeight + (staticLayout.getLineCount() - 1) * mSingleTextHeight;
            }
            mMultiLineTextExtraHeights.add(mMultiLineTextExtraHeight);
        }
    }

    private int getLrcWidth() {
        return (int) (getMeasuredWidth() - mHorizontalPadding * 2);
    }

    private int getLrcHeight() {
        return (int) (getMeasuredHeight() - mVerticalPadding * 2);
    }

    private boolean isLrcEmpty() {
        return mLrcData == null || getLrcCount() == 0;
    }

    private int getLrcCount() {
        return mLrcData.size();
    }

    public void setLrcData(List<Lrc> lrcData) {
        resetView();
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
        for (int i = 0; i < getLrcCount(); i++) {
            float x = getLrcWidth() * 0.5f + mHorizontalPadding;
            float y;
            if (i > 0) {
                y = getLrcHeight() * 0.5f + (i - 0.5f) * mLineHeight - mOffset + mMultiLineTextExtraHeights.get(i - 1);
            } else {
                y = getLrcHeight() * 0.5f + (i - 0.5f) * mLineHeight - mOffset;
            }
            if (y < 0) {
                continue;
            }
            if (y > getLrcHeight()) {
                break;
            }
            if (mCurrentLine == i) {
                mTextPaint.setColor(mCurrentPlayLineColor);
            } else {
                mTextPaint.setColor(mNormalColor);
            }
            @SuppressLint("DrawAllocation")
            StaticLayout staticLayout = new StaticLayout(mLrcData.get(i).getText(), mTextPaint,
                    getLrcWidth(), Layout.Alignment.ALIGN_NORMAL, 1f, 0f, true);
            canvas.save();
            canvas.translate(x, y + mVerticalPadding);
            staticLayout.draw(canvas);
            canvas.restore();
//            canvas.drawText(mLrcData.get(i).getText(), x, y, mTextPaint);
        }
    }

    //中间空文字
    private void drawEmptyText(Canvas canvas) {
        mTextPaint.setTextAlign(Paint.Align.LEFT);
        mTextPaint.setColor(mNoLrcTextColor);
        mTextPaint.setTextSize(mNoLrcTextSize);
        mTextPaint.getTextBounds(mDefaultContent, 0, mDefaultContent.length(), mBounds);
        Paint.FontMetricsInt fontMetrics = mTextPaint.getFontMetricsInt();
        int baseline = (int) ((getLrcHeight() - fontMetrics.bottom + fontMetrics.top) / 2 - fontMetrics.top + mVerticalPadding);
        int baseX = (int) ((getLrcWidth() - mBounds.width()) / 2 + mHorizontalPadding);
        canvas.drawText(mDefaultContent, baseX, baseline, mTextPaint);
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

        float scrollY = linePosition != getLrcCount() ? linePosition * mLineHeight
                + mMultiLineTextExtraHeights.get(linePosition) : getTotalLrcHeight();
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

    public float getTotalLrcHeight() {
        return getLrcCount() * mLineHeight + mMultiLineTextExtraHeights.get(getLrcCount() - 1) - mLrcLineSpaceHeight;
    }

    private boolean overScrolled() {
        return mOffset > getTotalLrcHeight() || mOffset < 0;
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
                performClick();
                if (!mOverScroller.isFinished()) {
                    mOverScroller.abortAnimation();
                }
                mLastMotionY = event.getY();
                isUserScroll = true;
                break;

            case MotionEvent.ACTION_MOVE:
                float moveY = event.getY() - mLastMotionY;
                if (Math.abs(moveY) > mScaledTouchSlop) {
//                    double exp = Math.exp(-event.getY() / mLastMotionY);
//                    moveY = (float) (exp * moveY);
                    mOffset -= moveY;
                    if (mOffset < 0) {
                        mOffset = Math.max(mOffset, -mLineHeight);
                    }
                    float maxHeight = getTotalLrcHeight();
                    if (mOffset > maxHeight) {
                        mOffset = Math.min(mOffset, maxHeight + mLineHeight);
                    }
                    invalidate();
                    mLastMotionY = event.getY();
                }
                break;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                handleActionUp();
                break;
        }
        return true;
    }

    private void handleActionUp() {
        if (overScrolled() && mOffset < 0) {
            scrollToPosition(0);
            ViewCompat.postOnAnimationDelayed(LrcView.this, mScrollRunnable, mTouchDelay);
            return;
        }

        if (overScrolled() && mOffset > getTotalLrcHeight()) {
            scrollToPosition(getLrcCount());
            ViewCompat.postOnAnimationDelayed(LrcView.this, mScrollRunnable, mTouchDelay);
            return;
        }

        mVelocityTracker.computeCurrentVelocity(1000, mMaximumFlingVelocity);
        float YVelocity = mVelocityTracker.getYVelocity();
        float absYVelocity = Math.abs(YVelocity);
        if (absYVelocity > mMinimumFlingVelocity) {
            mOverScroller.fling(0, (int) mOffset, 0, (int) (-YVelocity), 0,
                    0, 0, (int) getTotalLrcHeight(), 0, (int) mLineHeight);
            invalidate();
        }
        releaseVelocityTracker();
        ViewCompat.postOnAnimationDelayed(LrcView.this, mScrollRunnable, mTouchDelay);
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

    private void resetView() {
        if (mLrcData != null) {
            mLrcData.clear();
        }
        if (mMultiLineTextExtraHeights != null) {
            mMultiLineTextExtraHeights.clear();
        }
        mCurrentLine = 0;
        mOffset = 0;
        isUserScroll = false;
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
