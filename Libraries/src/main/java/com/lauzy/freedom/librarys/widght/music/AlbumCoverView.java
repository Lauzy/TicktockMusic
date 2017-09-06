package com.lauzy.freedom.librarys.widght.music;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Shader;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Desc : 专辑封面
 * Author : Lauzy
 * Date : 2017/9/4
 * Blog : http://www.jianshu.com/u/e76853f863a9
 * Email : freedompaladin@gmail.com
 */
public class AlbumCoverView extends View {

    //    private Rect mRectForTest;
    private int mWidth;
    private int mHeight;
    private Paint mPaint;
    //    private Bitmap mBitmap;
    private int mRadius;
    private float mRotation;
    private Handler mHandler = new Handler();
    private boolean isPlaying;
    private Paint mBitmapPaint;
    private Bitmap mCircleBitmap;
    private int space = 20;

    public AlbumCoverView(Context context) {
        this(context, null);
    }

    public AlbumCoverView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AlbumCoverView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    public void setBitmap(Bitmap bitmap) {
//        mBitmap = bitmap;
        mCircleBitmap = circleBitmap(bitmap);

        invalidate();
    }

    private void init(Context context, AttributeSet attrs) {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(20);
        mPaint.setColor(Color.parseColor("#AB000000"));

        mBitmapPaint = new Paint();
        mBitmapPaint.setAntiAlias(true);
    }

    private Runnable mRotationRunnable = new Runnable() {
        @Override
        public void run() {
            mRotation += 0.5;
            mRotation = mRotation % 360;
            invalidate();
            mHandler.postDelayed(this, 20);
        }
    };

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mWidth = getMeasureSize(100, widthMeasureSpec);
        mHeight = getMeasureSize(100, heightMeasureSpec);
        mWidth = mHeight = Math.min(mWidth, mHeight);
        setMeasuredDimension(mWidth, mHeight);
    }

    private int getMeasureSize(int defaultSize, int measureSpec) {
        int measuredSize = defaultSize;
        int mode = MeasureSpec.getMode(measureSpec);
        int size = MeasureSpec.getSize(measureSpec);
        switch (mode) {
            case MeasureSpec.EXACTLY:
                measuredSize = size;
                break;
            case MeasureSpec.AT_MOST:
                measuredSize = size;
                break;
            case MeasureSpec.UNSPECIFIED:
                measuredSize = defaultSize;
                break;
        }
        return measuredSize;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;
//        mRectForTest = new Rect(0, 0, mWidth, mHeight);
        mRadius = mWidth / 2 - space;
//        mHandler.post(mRotationRunnable);
    }

    private Bitmap circleBitmap(Bitmap circleBitmap) {
        circleBitmap = Bitmap.createScaledBitmap(circleBitmap, mRadius * 2, mRadius * 2, false);
        Bitmap bitmap = Bitmap.createBitmap(mWidth, mHeight, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        canvas.drawCircle(mWidth / 2, mHeight / 2, mRadius, mBitmapPaint);
        BitmapShader shader = new BitmapShader(circleBitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        mBitmapPaint.setShader(shader);
        mBitmapPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(circleBitmap, space, space, mBitmapPaint);
        return bitmap;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
//        mPaint.setStrokeWidth(3);
//        canvas.drawRect(mRectForTest, mPaint);
        canvas.save();
        canvas.drawCircle(mWidth / 2, mHeight / 2, mRadius, mPaint);
        canvas.rotate(mRotation, mWidth / 2, mHeight / 2);
        if (mCircleBitmap != null) {
            canvas.drawBitmap(mCircleBitmap, 0, 0, null);
        }
        canvas.restore();
    }

    public void start() {
        if (isPlaying) {
            return;
        }
        isPlaying = true;
        mHandler.removeCallbacksAndMessages(null);
        mHandler.post(mRotationRunnable);
    }

    public void pause() {
        if (!isPlaying) {
            return;
        }
        isPlaying = false;
        mHandler.removeCallbacks(mRotationRunnable);
    }

    public boolean isPlaying() {
        return isPlaying;
    }
}
