package com.app.main.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;


import com.app.main.R;

/**
 * @auther tb
 * @time 2018/3/5 下午4:45
 * @desc 控制升降床的seekBar
 */
public class VerticalSeekBar extends View {
    private static final String TAG = "VerticalSeekBar";
    private Paint paint, paintBorder, paintProgress;
    private float stokeWidth;
    private OnBarChangeListener mOnBarChangeListener;
    private int progress;
    private int max = 100;
    private Bitmap thumb;
    
    public interface OnBarChangeListener {
        void onStartTrackingTouch(VerticalSeekBar verticalSeekBar);
        
        void onStopTrackingTouch(VerticalSeekBar verticalSeekBar);
        
        void onProgressChanged(VerticalSeekBar verticalSeekBar, int progress);
    }
    
    public VerticalSeekBar(Context context) {
        this(context, null);
    }
    
    public VerticalSeekBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }
    
    public VerticalSeekBar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }
    
    private void init() {
        paintBorder = new Paint();
        paintBorder.setColor(getResources().getColor(R.color.main_color_BDBDBD));
        paintBorder.setStyle(Paint.Style.STROKE);
        paintBorder.setAntiAlias(true);
        stokeWidth = getResources().getDimensionPixelSize(R.dimen.baselib_dp1_5);
        paintBorder.setStrokeWidth(stokeWidth);
        
        paint = new Paint(paintBorder);
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.WHITE);
        
        paintProgress = new Paint(paint);
        
        thumb = BitmapFactory.decodeResource(getResources(), R.drawable.main_ic_bed_progress);
    }
    
    protected void onDraw(Canvas c) {
        //圆角半径
        float r = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 14, getResources().getDisplayMetrics());
        //背景的宽度
        float w = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 7, getResources().getDisplayMetrics());
        c.drawRoundRect(new RectF(getWidth() / 2f - w / 2f + stokeWidth / 2f, stokeWidth / 2f, getWidth() / 2f + w / 2f - stokeWidth / 2f, getHeight() - stokeWidth / 2f), r, r, paintBorder);
        c.drawRoundRect(new RectF(getWidth() / 2f - w / 2f + stokeWidth, stokeWidth, getWidth() / 2f + w / 2f - stokeWidth, getHeight() - stokeWidth), r - stokeWidth / 2f, r - stokeWidth / 2f, paint);
        
        float top = (getHeight() - thumb.getHeight()) * (max - progress) / max;
        c.drawBitmap(thumb, null, new RectF(0, top, getWidth(), top + thumb.getHeight()), paintProgress);
    }
    
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                getParent().requestDisallowInterceptTouchEvent(true);
                break;
            case MotionEvent.ACTION_UP:
            default:
                getParent().requestDisallowInterceptTouchEvent(false);
                break;
        }
        return super.dispatchTouchEvent(ev);
    }
    
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (mOnBarChangeListener != null) {
                    mOnBarChangeListener.onStartTrackingTouch(this);
                }
                break;
            case MotionEvent.ACTION_MOVE:
                calc(event);
                if (mOnBarChangeListener != null) {
                    mOnBarChangeListener.onProgressChanged(this, progress);
                }
                break;
            case MotionEvent.ACTION_UP:
            default:
                calc(event);
                if (mOnBarChangeListener != null) {
                    mOnBarChangeListener.onStopTrackingTouch(this);
                }
                break;
        }
        postInvalidate();
        return true;
    }
    
    private void calc(MotionEvent event) {
        float currY = event.getY();
        currY = currY > getHeight() - thumb.getHeight() ? getHeight() - thumb.getHeight() : currY;
        currY = currY < 0 ? 0 : currY;
        progress = max - (int) (max * currY / (getHeight() - thumb.getHeight()));
    }
    
    public void setOnBarChangeListener(OnBarChangeListener l) {
        mOnBarChangeListener = l;
    }
    
    public int getProgress() {
        return progress;
    }
    
    public void setProgress(int progress) {
        this.progress = progress;
    }
    
    public int getMax() {
        return max;
    }
    
    public void setMax(int max) {
        this.max = max;
    }
    
    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (thumb != null) {
            thumb.recycle();
            thumb = null;
        }
    }
}
