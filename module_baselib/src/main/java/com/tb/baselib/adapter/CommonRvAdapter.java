package com.tb.baselib.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.tb.baselib.util.LogUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @auther tb
 * @time 2018/2/6 下午1:57
 * @desc
 */
public abstract class CommonRvAdapter<T> extends RecyclerView.Adapter<CommonRvViewHolder> {
    protected int mLayoutId;
    protected List<T> mDatas = new ArrayList<>();
    protected OnClickListener onClickListener;
    private MultiTypeSupport<T> mMultiTypeSupport;
    private T item;
    
    public CommonRvAdapter(List<T> data, MultiTypeSupport<T> multiTypeSupport) {
        this(-1, data);
        this.mMultiTypeSupport = multiTypeSupport;
    }
    
    public CommonRvAdapter(int layoutId, List<T> datas) {
        mLayoutId = layoutId;
        mDatas.addAll(datas == null ? new ArrayList<T>() : datas);
    }
    
    public CommonRvAdapter(int layoutId) {
        this(layoutId, new ArrayList<T>());
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
    
    public void update(int position, T t) {
        mDatas.set(position, t);
        notifyItemChanged(position);
    }
    
    public void add(int position, T t) {
        mDatas.add(position, t);
        notifyItemInserted(position);
        //添加到末尾仍需更新
        notifyItemRangeChanged(position, mDatas.size() - position);
    }
    
    public void delete(int position) {
        mDatas.remove(position);
        notifyItemRemoved(position);
        if (position != mDatas.size()) {//删除最后一个不用重新绑定
            //需要调用如下方法更新bindview中的position
            //notifyDataSetChanged则不需要
            notifyItemRangeRemoved(position, mDatas.size() - position);
        }
    }
    
    public List<T> getDatas() {
        return mDatas;
    }
    
    public T getItem(int position) {
        return this.mDatas.get(position);
    }
    
    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }
    
    @Override
    public CommonRvViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mMultiTypeSupport != null) {
            mLayoutId = mMultiTypeSupport.getLayoutId(viewType);
        }
        return CommonRvViewHolder.get(parent, mLayoutId, viewType);
    }
    
    @Override
    public void onBindViewHolder(final CommonRvViewHolder holder, int position) {
        //position只会在调用的时候确定，后面数据改变不会更新，只能手动刷新(notifyItemRangeRemoved...)
        //getAdapterPosition获取的是数据集合中的位置
        //getLayoutPosition获取的布局刷新完成后的位置，跟getAdapterPosition会有一个相差16ms的时间差
//        LogUtils.d("onBindViewHolder: " + position + "#" + holder.getAdapterPosition() + "#" + holder.getLayoutPosition());
        final int pos = holder.getAdapterPosition() >= 0 ? holder.getAdapterPosition() : holder.getLayoutPosition();
        // 如果设置了回调，则设置点击事件
        try {
            item=mDatas.size()>0?mDatas.get(pos):null;
        }catch (Exception e){
            item=null;
            e.printStackTrace();
        }
        if (onClickListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickListener.onItemClickListener(holder.itemView, pos, item);
                }
            });
            
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    onClickListener.onItemLongClickListener(holder.itemView, pos, item);
                    return true;
                }
            });
        }
        convert(holder, item);
    }
    
    @Override
    public int getItemViewType(int position) {
//        LogUtils.d("getItemViewType>>>>>>>" + position);
        if (mMultiTypeSupport != null) {
            try {
                item=mDatas.size()>0?mDatas.get(position):null;
            }catch (Exception e){
                item=null;
                e.printStackTrace();
            }
            return mMultiTypeSupport.getItemViewType(position, item);
        }
        return super.getItemViewType(position);
    }
    
    @Override
    public int getItemCount() {
        return mDatas.size();
    }
    
    public abstract void convert(CommonRvViewHolder holder, T t);
    
    public void setmMultiTypeSupport(MultiTypeSupport<T> mMultiTypeSupport) {
        this.mMultiTypeSupport = mMultiTypeSupport;
    }
    
    public interface OnClickListener {
        void onItemClickListener(View view, int position, Object o);
        
        void onItemLongClickListener(View view, int position, Object o);
    }
    
    public interface MultiTypeSupport<T> {
        /**
         * 根据当前itemType返回对应布局
         *
         * @param itemType
         * @return
         */
        int getLayoutId(int itemType);
        
        /**
         * 根据当前的bean或者position返回item type
         *
         * @param position
         * @param t
         * @return
         */
        int getItemViewType(int position, T t);
    }
}
