package com.tb.baselib.listener;

import android.view.View;

import com.tb.baselib.util.LogUtils;

import java.util.Calendar;

public abstract class NoDoubleClickListener implements View.OnClickListener {
    //两次点击事件的最小事件间隔,间隔内的点击不反应
    public static final int MIN_CLICK_DELAY_TIME = 1000;
    //最后一次的按钮点击时刻
    private long mLastClickTime = 0;
    //最后一次点击的按钮
    //为了解决同一个activity中连续点击不同按钮不能触发的bug，
    //因为间隔时间不够，所以需要加上viewId和clickTime两重判断
    private int mLastClickViewId = -1;
    
    /**
     * 有效点击事件需要执行的逻辑
     *
     * @param view
     */
    public abstract void onNoDoubleClick(View view);
    
    @Override
    public void onClick(View view) {
        long currentTime = Calendar.getInstance().getTimeInMillis();
        if (mLastClickViewId == -1) {
            //第一次点击，无需任何判断
            onNoDoubleClick(view);
        } else {
            if (mLastClickViewId == view.getId()) {
                //非第一次点击：对同一个view进行点击，需要去重判断
                if (currentTime - mLastClickTime > MIN_CLICK_DELAY_TIME) {
                    onNoDoubleClick(view);
                }
            } else {
                //非第一次点击：操作的是不同的view，则不需要有效点击事件的检测
                onNoDoubleClick(view);
            }
        }
        mLastClickViewId = view.getId();
        //mLastClickTime放到里面，则在快速点击的时候，每隔#MIN_CLICK_DELAY_TIME#执行一次
        mLastClickTime = currentTime;//距离上次点击的时间必须相差*MIN_CLICK_DELAY_TIME*（防止连续快速点击）
    }
}