package com.tb.baselib.adapter;

import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * @auther tb
 * @time 2018/2/9 下午5:32
 * @desc
 */
public class CommonLvViewHolder {
    private View mConvertView;
    /**
     * 现在对于int作为键的官方推荐用SparseArray替代HashMap
     * 用来缓存已经find的view，防止在滑动复用的时候，重复find，提升效率
     */
    private SparseArray<View> mViews;
    
    public CommonLvViewHolder(ViewGroup parent, int layoutId) {
        this.mConvertView = LayoutInflater.from(parent.getContext()).inflate(layoutId, parent, false);
        mConvertView.setTag(this);
        mViews = new SparseArray<>();
    }
    
    public static CommonLvViewHolder get(View mConvertView, ViewGroup parent, int layoutId) {
        if (mConvertView == null) {
            return new CommonLvViewHolder(parent, layoutId);
        }
        return (CommonLvViewHolder) mConvertView.getTag();
    }
    
    public View getConvertView() {
        return mConvertView;
    }
    
    /**
     * 通过viewId获取控件
     *
     * @param viewId
     * @return
     */
    public <T extends View> T getView(int viewId) {
        View view = mViews.get(viewId);
        if (view == null) {
            view = mConvertView.findViewById(viewId);
            mViews.put(viewId, view);
        }
        return (T) view;
    }
}
