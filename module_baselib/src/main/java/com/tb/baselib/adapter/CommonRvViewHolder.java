package com.tb.baselib.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.HashMap;
import java.util.Map;

/**
 * @auther tb
 * @time 2018/2/6 下午1:47
 * @desc
 */
public class CommonRvViewHolder extends RecyclerView.ViewHolder {
    private View mConvertView;
    public int itemType;
    /**
     * 现在对于int作为键的官方推荐用SparseArray替代HashMap
     * 用来缓存已经find的view，防止在滑动复用的时候，重复find，提升效率
     */
    private SparseArray<View> mViews;
    
    public CommonRvViewHolder(View itemView, int itemType) {
        super(itemView);
        mConvertView = itemView;
        this.itemType = itemType;
        mViews = new SparseArray<>();
    }
    
    
    public static CommonRvViewHolder get(ViewGroup parent, int layoutId, int itemType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(layoutId, parent, false);
        CommonRvViewHolder holder = new CommonRvViewHolder(itemView, itemType);
        return holder;
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
