package com.tb.baselib.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.tb.baselib.util.LogUtils;

/**
 * Created by : tb on 2017/11/7 上午11:18.
 * Description :ScrollView嵌套ListView
 */
public class ListViewInScrollView extends ListView {
    private int maxCount;
    private int height;
    
    public ListViewInScrollView(Context context) {
        super(context);
    }
    
    public ListViewInScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    
    public ListViewInScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    
    /**
     * 动态设置listview的高度
     *
     * @param maxPresentCount 最大展示条数
     */
    public void setListViewHeightBasedOnChildren(int maxPresentCount) {
        maxCount = maxPresentCount;
        ListAdapter listAdapter = getAdapter();
        if (listAdapter == null) {
            return;
        }
        if (maxPresentCount > listAdapter.getCount()) {
            maxPresentCount = listAdapter.getCount();
        }
        int totalHeight = 0;
        //最大显示高度，这里不去显示所有item
        for (int i = 0; i < maxPresentCount; i++) {
            View listItem = listAdapter.getView(i, null, this);
            int h = listItem.getLayoutParams().height;
            if (h == -1 || h == -2) {
                //说明是wrap或者match
                listItem.measure(0, 0);
                totalHeight += listItem.getMeasuredHeight();
            } else {
                totalHeight += h;
            }
        }
        
        ViewGroup.LayoutParams params = getLayoutParams();
        //最后再加上分割线的高度和padding高度，否则显示不完整。
        height = totalHeight + (getDividerHeight() * (listAdapter.getCount() - 1)) + getPaddingTop() + getPaddingBottom();
        params.height = height;
        setLayoutParams(params);
    }
    
    public int getLvHeight() {
        return height;
    }
    
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (maxCount >= getAdapter().getCount()) {
            //总数没有超过最大条数，则不去拦截滑动事件
            return super.dispatchTouchEvent(ev);
        }
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
}
