package com.tb.baselib.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * @auther tb
 * @time 2018/2/9 下午5:39
 * @desc
 */
public abstract class CommonLvAdapter<T> extends BaseAdapter {
    protected List<T> mDatas = new ArrayList<>();
    protected int mLayoutId;
    private T item;
    
    public CommonLvAdapter(List<T> list, int layoutId) {
        this.mDatas.addAll(list == null ? new ArrayList<T>() : list);
        this.mLayoutId = layoutId;
    }
    
    public void setDatas(List<T> mDatas) {
        this.mDatas.addAll(mDatas == null ? new ArrayList<T>() : mDatas);
        notifyDataSetChanged();
    }
    
    /**
     * @param mDatas
     * @param isRefresh 是否是下拉刷新
     */
    public void setDatas(List<T> mDatas, boolean isRefresh) {
        if (isRefresh) {
            this.mDatas.clear();
        }
        this.mDatas.addAll(mDatas == null ? new ArrayList<T>() : mDatas);
        notifyDataSetChanged();
    }
    
    public void clearDatas() {
        this.mDatas.clear();
        notifyDataSetChanged();
    }
    
    public List<T> getDatas() {
        return mDatas;
    }
    
    @Override
    public int getCount() {
        return mDatas.size();
    }
    
    @Override
    public T getItem(int position) {
        return mDatas.get(position);
    }
    
    @Override
    public long getItemId(int position) {
        return position;
    }
    
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        CommonLvViewHolder viewHolder = CommonLvViewHolder.get(convertView, parent, mLayoutId);
        try {
            item=mDatas.size()>0?mDatas.get(position):null;
        }catch (Exception e){
            item=null;
            e.printStackTrace();
        }
        convert(viewHolder, item);
        return viewHolder.getConvertView();
    }
    
    public abstract void convert(CommonLvViewHolder holder, T item);
}
