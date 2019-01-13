package com.app.main.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.RadioButton;

/**
 * @auther tb
 * @time 2018/2/6 上午11:07
 * @desc
 */
@SuppressLint("AppCompatCustomView")
public class MyRadioButton extends RadioButton {
    public MyRadioButton(Context context) {
        super(context);
    }
    
    public MyRadioButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    
    public MyRadioButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Drawable[] drawables = getCompoundDrawables();
        Drawable drawable = drawables[0];
        int gravity = getGravity();
        int left = 0;
        if (gravity == Gravity.CENTER) {
            left = (int) (getWidth() - drawable.getIntrinsicWidth() - getPaint().measureText(getText().toString())) / 2;
        }
        drawable.setBounds(left, 0, left + drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
    }
}
