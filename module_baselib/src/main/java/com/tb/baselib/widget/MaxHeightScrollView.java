package com.tb.baselib.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;
import android.widget.ScrollView;

import com.tb.baselib.R;

/**
 * @auther tb
 * @time 2018/2/7 上午11:45
 * @desc 最大高度scrollview
 */
public class MaxHeightScrollView extends ScrollView {
    
    private int mMaxHeadHeight;
    
    public MaxHeightScrollView(Context context) {
        this(context, null);
    }
    
    public MaxHeightScrollView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }
    
    public MaxHeightScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        DisplayMetrics d = new DisplayMetrics();
        display.getMetrics(d);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.baselib_MaxHeightScrollView, defStyleAttr, 0);
        try {
            mMaxHeadHeight = a.getDimensionPixelSize(R.styleable.baselib_MaxHeightScrollView_baselib_max_height, d.heightPixels / 3);
        } finally {
            a.recycle();
        }
    }
    
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        try {
            heightMeasureSpec = MeasureSpec.makeMeasureSpec(mMaxHeadHeight, MeasureSpec.AT_MOST);
        } catch (Exception e) {
            e.printStackTrace();
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}