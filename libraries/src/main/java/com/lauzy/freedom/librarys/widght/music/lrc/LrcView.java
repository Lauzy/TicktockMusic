package com.lauzy.freedom.librarys.widght.music.lrc;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.OverScroller;

import com.lauzy.freedom.librarys.R;

import java.util.HashMap;
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
    private String mDefaultContent;
    private int mCurrentLine;
    private float mOffset;
    private float mLastMotionX;
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
    private Drawable mPlayDrawable;
    private boolean isShowTimeIndicator;
    private Rect mPlayRect;
    private Paint mIndicatorPaint;
    private Paint.FontMetricsInt mFontMetrics;
    private Rect mIndicateTimeBounds;
    private float mIndicatorLineWidth;
    private float mIndicatorTextSize;
    private int mCurrentIndicateLineTextColor;
    private int mIndicatorLineColor;
    private float mIndicatorMargin;
    private float mIconLineGap;
    private float mIconWidth;
    private float mIconHeight;
    private boolean isEnableShowIndicator = true;
    private int mIndicatorTextColor;

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
        mIndicatorLineWidth = typedArray.getDimension(R.styleable.LrcView_indicatorLineHeight, dp2px(context, 0.5f));
        mIndicatorTextSize = typedArray.getDimension(R.styleable.LrcView_indicatorTextSize, sp2px(context, 13));
        mIndicatorTextColor = typedArray.getColor(R.styleable.LrcView_indicatorTextColor, Color.GRAY);
        mCurrentIndicateLineTextColor = typedArray.getColor(R.styleable.LrcView_currentIndicateLrcColor, Color.GRAY);
        mIndicatorLineColor = typedArray.getColor(R.styleable.LrcView_indicatorLineColor, Color.GRAY);
        mIndicatorMargin = typedArray.getDimension(R.styleable.LrcView_indicatorStartEndMargin, dp2px(context, 5));
        mIconLineGap = typedArray.getDimension(R.styleable.LrcView_iconLineGap, dp2px(context, 3));
        mIconWidth = typedArray.getDimension(R.styleable.LrcView_playIconWidth, dp2px(context, 20));
        mIconHeight = typedArray.getDimension(R.styleable.LrcView_playIconHeight, dp2px(context, 20));
        mPlayDrawable = typedArray.getDrawable(R.styleable.LrcView_playIcon);
        mPlayDrawable = mPlayDrawable == null ? ContextCompat.getDrawable(context, R.drawable.play_icon) : mPlayDrawable;
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

        mIndicatorPaint = new Paint();
        mIndicatorPaint.setAntiAlias(true);
        mIndicatorPaint.setStrokeWidth(mIndicatorLineWidth);
        mIndicatorPaint.setColor(mIndicatorLineColor);
        mPlayRect = new Rect();
        mIndicatorPaint.setTextSize(mIndicatorTextSize);
        mFontMetrics = mIndicatorPaint.getFontMetricsInt();
        mIndicateTimeBounds = new Rect();
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if (changed) {
            mPlayRect.left = (int) mIndicatorMargin;
            mPlayRect.top = (int) (getHeight() / 2 - mIconHeight / 2);
            mPlayRect.right = (int) (mPlayRect.left + mIconWidth);
            mPlayRect.bottom = (int) (mPlayRect.top + mIconHeight);
            mPlayDrawable.setBounds(mPlayRect);
        }
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
        int indicatePosition = getIndicatePosition();
        mTextPaint.setTextSize(mLrcTextSize);
        mTextPaint.setTextAlign(Paint.Align.CENTER);
        float y = getLrcHeight() / 2 + mVerticalPadding;
        float x = getLrcWidth() / 2 + mHorizontalPadding;
        for (int i = 0; i < getLrcCount(); i++) {
            if (i > 0) {
                y += (getTextHeight(i - 1) + getTextHeight(i)) / 2 + mLrcLineSpaceHeight;
            }
            if (mCurrentLine == i) {
                mTextPaint.setColor(mCurrentPlayLineColor);
            } else if (indicatePosition == i && isShowTimeIndicator) {
                mTextPaint.setColor(mCurrentIndicateLineTextColor);
            } else {
                mTextPaint.setColor(mNormalColor);
            }
            drawLrc(canvas, x, y, i);
        }

        if (isShowTimeIndicator) {
            mPlayDrawable.draw(canvas);
            long time = mLrcData.get(indicatePosition).getTime();
            mIndicatorPaint.getTextBounds(LrcHelper.formatTime(time), 0,
                    (LrcHelper.formatTime(time)).length(), mIndicateTimeBounds);
            canvas.drawLine(mPlayRect.right + mIconLineGap, getHeight() / 2,
                    getWidth() - mIndicateTimeBounds.width() * 1.4f, getHeight() / 2, mIndicatorPaint);
            int baseX = (int) (getWidth() - mIndicateTimeBounds.width() * 1.2f);
            int baseline = getHeight() / 2 - (mFontMetrics.descent - mFontMetrics.ascent) / 2 - mFontMetrics.ascent;
            mIndicatorPaint.setColor(mIndicatorTextColor);
            canvas.drawText(LrcHelper.formatTime(time), baseX, baseline, mIndicatorPaint);
        }
    }

    private HashMap<String, StaticLayout> mLrcMap = new HashMap<>();

    private void drawLrc(Canvas canvas, float x, float y, int i) {
        String text = mLrcData.get(i).getText();
        StaticLayout staticLayout = mLrcMap.get(text);
        if (staticLayout == null) {
            mTextPaint.setTextSize(mLrcTextSize);
            staticLayout = new StaticLayout(text, mTextPaint, getLrcWidth(),
                    Layout.Alignment.ALIGN_NORMAL, 1f, 0f, false);
            mLrcMap.put(text, staticLayout);
        }
        canvas.save();
        canvas.translate(x, y - staticLayout.getHeight() / 2 - mOffset);
        staticLayout.draw(canvas);
        canvas.restore();
    }

    //中间空文字
    private void drawEmptyText(Canvas canvas) {
        mTextPaint.setTextAlign(Paint.Align.CENTER);
        mTextPaint.setColor(mNoLrcTextColor);
        mTextPaint.setTextSize(mNoLrcTextSize);
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
                postInvalidate();
                return;
            }
            ViewCompat.postOnAnimation(LrcView.this, mScrollRunnable);
        }
    }

    private int getUpdateTimeLinePosition(long time) {
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

    private Runnable mHideIndicatorRunnable = new Runnable() {
        @Override
        public void run() {
            isShowTimeIndicator = false;
            postInvalidate();
        }
    };

    private void scrollToPosition(int linePosition) {
        float scrollY = getItemOffsetY(linePosition);
        final ValueAnimator animator = ValueAnimator.ofFloat(mOffset, scrollY);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mOffset = (float) animation.getAnimatedValue();
                postInvalidate();
            }
        });
        animator.setDuration(300);
        animator.start();
    }

    public int getIndicatePosition() {
        int pos = 0;
        float min = Float.MAX_VALUE;
        //itemOffset 和 mOffset 最小即当前位置
        for (int i = 0; i < mLrcData.size(); i++) {
            float offsetY = getItemOffsetY(i);
            float abs = Math.abs(offsetY - mOffset);
            if (abs < min) {
                min = abs;
                pos = i;
            }
        }
        return pos;
    }

    private float getItemOffsetY(int linePosition) {
        float tempY = 0;
        for (int i = 1; i <= linePosition; i++) {
            tempY += (getTextHeight(i - 1) + getTextHeight(i)) / 2 + mLrcLineSpaceHeight;
        }
        return tempY;
    }

    private HashMap<String, StaticLayout> mStaticLayoutHashMap = new HashMap<>();

    private float getTextHeight(int linePosition) {
        String text = mLrcData.get(linePosition).getText();
        StaticLayout staticLayout = mStaticLayoutHashMap.get(text);
        if (staticLayout == null) {
            mTextPaint.setTextSize(mLrcTextSize);
            staticLayout = new StaticLayout(text, mTextPaint,
                    getLrcWidth(), Layout.Alignment.ALIGN_NORMAL, 1f, 0f, false);
            mStaticLayoutHashMap.put(text, staticLayout);
        }
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
                removeCallbacks(mHideIndicatorRunnable);
                if (!mOverScroller.isFinished()) {
                    mOverScroller.abortAnimation();
                }
                mLastMotionX = event.getX();
                mLastMotionY = event.getY();
                isUserScroll = true;
                isDragging = false;
                break;

            case MotionEvent.ACTION_MOVE:
                float moveY = event.getY() - mLastMotionY;
                if (Math.abs(moveY) > mScaledTouchSlop) {
                    isDragging = true;
                    isShowTimeIndicator = isEnableShowIndicator;
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
                        mOffset = Math.min(mOffset, maxHeight + getTextHeight(getLrcCount() - 1) + mLrcLineSpaceHeight);
                    }
                    mLastMotionY = event.getY();
                    postInvalidate();
                }
                break;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                if (!isDragging && (!isShowTimeIndicator || !onClickPlayButton(event))) {
                    performClick();
                }
                handleActionUp(event);
                break;
        }
//        return isDragging || super.onTouchEvent(event);
        return true;
    }

    private void handleActionUp(MotionEvent event) {
        if (isEnableShowIndicator) {
            ViewCompat.postOnAnimationDelayed(LrcView.this, mHideIndicatorRunnable, mTouchDelay);
        }
        if (isShowTimeIndicator && mPlayRect != null && onClickPlayButton(event)) {
            isShowTimeIndicator = false;
            postInvalidate();
            if (mOnPlayIndicatorLineListener != null) {
                mOnPlayIndicatorLineListener.onPlay(mLrcData.get(getIndicatePosition()).getTime(),
                        mLrcData.get(getIndicatePosition()).getText());
            }
        }
        if (overScrolled() && mOffset < 0) {
            scrollToPosition(0);
            if (isAutoAdjustPosition) {
                ViewCompat.postOnAnimationDelayed(LrcView.this, mScrollRunnable, mTouchDelay);
            }
            return;
        }

        if (overScrolled() && mOffset > getItemOffsetY(getLrcCount() - 1)) {
            scrollToPosition(getLrcCount() - 1);
            if (isAutoAdjustPosition) {
                ViewCompat.postOnAnimationDelayed(LrcView.this, mScrollRunnable, mTouchDelay);
            }
            return;
        }

        mVelocityTracker.computeCurrentVelocity(1000, mMaximumFlingVelocity);
        float YVelocity = mVelocityTracker.getYVelocity();
        float absYVelocity = Math.abs(YVelocity);
        if (absYVelocity > mMinimumFlingVelocity) {
            mOverScroller.fling(0, (int) mOffset, 0, (int) (-YVelocity), 0,
                    0, 0, (int) getItemOffsetY(getLrcCount() - 1),
                    0, (int) getTextHeight(0));
            postInvalidate();
        }
        releaseVelocityTracker();
        if (isAutoAdjustPosition) {
            ViewCompat.postOnAnimationDelayed(LrcView.this, mScrollRunnable, mTouchDelay);
        }
    }

    private boolean onClickPlayButton(MotionEvent event) {
        float left = mPlayRect.left;
        float right = mPlayRect.right;
        float top = mPlayRect.top;
        float bottom = mPlayRect.bottom;
        float x = event.getX();
        float y = event.getY();
        return mLastMotionX > left && mLastMotionX < right && mLastMotionY > top
                && mLastMotionY < bottom && x > left && x < right && y > top && y < bottom;
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if (mOverScroller.computeScrollOffset()) {
            mOffset = mOverScroller.getCurrY();
            postInvalidate();
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
        mLrcMap.clear();
        mStaticLayoutHashMap.clear();
        mCurrentLine = 0;
        mOffset = 0;
        isUserScroll = false;
        isDragging = false;
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
        postInvalidate();
    }

    public void resume() {
        isAutoAdjustPosition = true;
        ViewCompat.postOnAnimationDelayed(LrcView.this, mScrollRunnable, mTouchDelay);
        postInvalidate();
    }


    /*------------------Config-------------------*/

    private OnPlayIndicatorLineListener mOnPlayIndicatorLineListener;

    public void setOnPlayIndicatorLineListener(OnPlayIndicatorLineListener onPlayIndicatorLineListener) {
        mOnPlayIndicatorLineListener = onPlayIndicatorLineListener;
    }

    public interface OnPlayIndicatorLineListener {
        void onPlay(long time, String content);
    }


    public void setDefaultContent(String defaultContent) {
        mDefaultContent = defaultContent;
        postInvalidate();
    }

    public void setLrcTextSize(float lrcTextSize) {
        mLrcTextSize = lrcTextSize;
        postInvalidate();
    }

    public void setLrcLineSpaceHeight(float lrcLineSpaceHeight) {
        mLrcLineSpaceHeight = lrcLineSpaceHeight;
        postInvalidate();
    }

    public void setTouchDelay(int touchDelay) {
        mTouchDelay = touchDelay;
        postInvalidate();
    }

    public void setNormalColor(@ColorInt int normalColor) {
        mNormalColor = normalColor;
        postInvalidate();
    }

    public void setCurrentPlayLineColor(@ColorInt int currentPlayLineColor) {
        mCurrentPlayLineColor = currentPlayLineColor;
        postInvalidate();
    }

    public float getHorizontalPadding() {
        return mHorizontalPadding;
    }

    public void setHorizontalPadding(float horizontalPadding) {
        mHorizontalPadding = horizontalPadding;
        postInvalidate();
    }

    public float getVerticalPadding() {
        return mVerticalPadding;
    }

    public void setVerticalPadding(float verticalPadding) {
        mVerticalPadding = verticalPadding;
        postInvalidate();
    }

    public void setNoLrcTextSize(float noLrcTextSize) {
        mNoLrcTextSize = noLrcTextSize;
        postInvalidate();
    }

    public void setNoLrcTextColor(@ColorInt int noLrcTextColor) {
        mNoLrcTextColor = noLrcTextColor;
        postInvalidate();
    }

    public void setIndicatorLineWidth(float indicatorLineWidth) {
        mIndicatorLineWidth = indicatorLineWidth;
        postInvalidate();
    }

    public void setIndicatorTextSize(float indicatorTextSize) {
//        mIndicatorTextSize = indicatorTextSize;
        mIndicatorPaint.setTextSize(indicatorTextSize);
        postInvalidate();
    }

    public void setCurrentIndicateLineTextColor(int currentIndicateLineTextColor) {
        mCurrentIndicateLineTextColor = currentIndicateLineTextColor;
        postInvalidate();
    }

    public void setIndicatorLineColor(int indicatorLineColor) {
        mIndicatorLineColor = indicatorLineColor;
        postInvalidate();
    }

    public void setIndicatorMargin(float indicatorMargin) {
        mIndicatorMargin = indicatorMargin;
        postInvalidate();
    }

    public void setIconLineGap(float iconLineGap) {
        mIconLineGap = iconLineGap;
        postInvalidate();
    }

    public void setIconWidth(float iconWidth) {
        mIconWidth = iconWidth;
        postInvalidate();
    }

    public void setIconHeight(float iconHeight) {
        mIconHeight = iconHeight;
        postInvalidate();
    }

    public void setEnableShowIndicator(boolean enableShowIndicator) {
        isEnableShowIndicator = enableShowIndicator;
        postInvalidate();
    }

    public Drawable getPlayDrawable() {
        return mPlayDrawable;
    }

    public void setPlayDrawable(Drawable playDrawable) {
        mPlayDrawable = playDrawable;
        mPlayDrawable.setBounds(mPlayRect);
        postInvalidate();
    }

    public void setIndicatorTextColor(int indicatorTextColor) {
        mIndicatorTextColor = indicatorTextColor;
        postInvalidate();
    }
}
