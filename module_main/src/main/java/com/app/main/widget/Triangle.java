package com.app.main.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;


import com.app.main.R;
import com.tb.baselib.util.CommonUtils;

/**
 * @auther tb
 * @time 2018/1/17 上午10:47
 * @desc 登录注册下面的三角形
 */
public class Triangle extends View {
    private float width,height,tWidth;
    private Paint paint;
    private Path path;
    public Triangle(Context context) {
        super(context);
        init();
    }
    
    public Triangle(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }
    
    public Triangle(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }
    
    private void init(){
        paint=new Paint();
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL);
        paint.setStrokeJoin(Paint.Join.ROUND);// 笔刷图形样式
        paint.setStrokeCap(Paint.Cap.ROUND);// 设置画笔转弯的连接风格
        paint.setDither(true);//防抖动
//        paint.setStrokeWidth(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 3f, getResources().getDisplayMetrics()));
        
        path=new Path();
        width= CommonUtils.getScreenResolution()[0]/2f;
        tWidth=CommonUtils.dp2Px(24);
    }
    
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        setMeasuredDimension((int) width,heightSize);
    }
    
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        height=getMeasuredHeight();
    }
    
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //画白色背景
        RectF rect=new RectF(0,0,width,height);
        paint.setColor(getResources().getColor(R.color.main_color_FFFFFF));
        canvas.drawRect(rect,paint);
        
        //画三角形
        path.reset();
        path.moveTo((width-tWidth)/2f,0);
        path.lineTo((width+tWidth)/2f,0);
        path.lineTo(width/2f,height);
        path.close();
        paint.setColor(getResources().getColor(R.color.main_color_EF5227));
        canvas.drawPath(path,paint);
    }
}
