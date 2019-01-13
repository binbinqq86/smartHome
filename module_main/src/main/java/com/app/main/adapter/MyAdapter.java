package com.app.main.adapter;

import android.support.constraint.ConstraintLayout;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;


import com.app.main.R;
import com.app.main.webapi.bean.MyBean;
import com.tb.baselib.adapter.CommonLvAdapter;
import com.tb.baselib.adapter.CommonLvViewHolder;
import com.tb.baselib.adapter.CommonRvAdapter;
import com.tb.baselib.adapter.CommonRvViewHolder;
import com.tb.baselib.image.impl.GlideImageLoader;
import com.tb.baselib.listener.NoDoubleClickListener;
import com.tb.baselib.util.CommonUtils;
import com.tb.baselib.widget.BaseImageView;
import com.tb.baselib.widget.ListViewInScrollView;
import com.tb.baselib.widget.ToastUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @auther tb
 * @time 2018/2/11 下午3:49
 * @desc
 */
public class MyAdapter extends CommonRvAdapter<MyBean> {
    public static final int MyTypeHeader = 1;
    public static final int MyTypeCommon = 2;
    
    public interface OnClick {
        void onClick(View view);
    }
    
    private OnClick onClick;
    
    public void setOnClick(OnClick onClick) {
        this.onClick = onClick;
    }
    
    private NoDoubleClickListener noDoubleClickListener = new NoDoubleClickListener() {
        @Override
        public void onNoDoubleClick(View view) {
            if (onClick != null) {
                onClick.onClick(view);
            }
        }
    };
    private MultiTypeSupport<MyBean> mMultiTypeSupport = new MultiTypeSupport<MyBean>() {
        @Override
        public int getLayoutId(int itemType) {
            int layoutId = R.layout.main_empty;
            switch (itemType) {
                case MyTypeHeader:
                    layoutId = R.layout.main_layout_my_header;
                    break;
                case MyTypeCommon:
                    layoutId = R.layout.main_layout_my_common;
                    break;
            }
            return layoutId;
        }
        
        @Override
        public int getItemViewType(int position, MyBean homeBean) {
            int type = -1;
            switch (position) {
                case 0:
                    type = MyTypeHeader;
                    break;
                case 1:
                    type = MyTypeCommon;
                    break;
            }
            return type;
        }
    };
    
    public MyAdapter(List<MyBean> data) {
        super(-1, data);
        setmMultiTypeSupport(mMultiTypeSupport);
    }
    
    @Override
    public void convert(CommonRvViewHolder holder, MyBean homeBean) {
        if (holder.itemType == 0) {
            return;
        }
        int viewType = holder.itemType;
        if (MyTypeHeader == viewType) {
            dealHeader(holder);
        } else if (MyTypeCommon == viewType) {
            dealCommon(holder);
        } else {
        }
    }
    
    private void dealHeader(CommonRvViewHolder holder) {
        holder.getView(R.id.cl).setOnClickListener(noDoubleClickListener);
        holder.getView(R.id.tv_name);
        holder.getView(R.id.tv_phone);
        holder.getView(R.id.tv_nick);
        BaseImageView ivHeader=holder.getView(R.id.iv_header);
        ImageView ivQr=holder.getView(R.id.iv_qr);
        String url = "http://img.zcool.cn/community/0121cf5991a3110000002129b93880.jpg";
        GlideImageLoader.getInstance().displayImg(ivHeader.getContext(),ivHeader,url);
        String u2="https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=3354209541,1305424247&fm=27&gp=0.jpg";
        GlideImageLoader.getInstance().displayImg(ivQr.getContext(),ivQr,u2);
    }
    
    private void dealCommon(CommonRvViewHolder holder) {
        ListViewInScrollView lv = holder.getView(R.id.lv);
        //防止切换底部tab的时候抢占焦点
        lv.setFocusable(false);
        lv.setFocusableInTouchMode(false);
        final List<MyBean.CommonBean> list = new ArrayList<>();
        fill(list);
        lv.setAdapter(new CommonLvAdapter<MyBean.CommonBean>(list, R.layout.main_item_layout_my_common) {
            @Override
            public void convert(CommonLvViewHolder holder, final MyBean.CommonBean item) {
                ConstraintLayout cl=holder.getView(R.id.cl);
                AbsListView.LayoutParams lp= (AbsListView.LayoutParams) cl.getLayoutParams();
                ImageView iv = holder.getView(R.id.iv);
                TextView tvTitle = holder.getView(R.id.tv_title);
                View view = holder.getView(R.id.view);
                View separate = holder.getView(R.id.separate);
                iv.setImageResource(item.resId);
                tvTitle.setText(item.option);
                
                if (item.hasBottomLine) {
                    view.setVisibility(View.VISIBLE);
                } else {
                    view.setVisibility(View.GONE);
                }
                
                if (item.hasSeparate) {
                    lp.height= (int) CommonUtils.dp2Px(60);
                    cl.setLayoutParams(lp);
                    separate.setVisibility(View.VISIBLE);
                } else {
                    lp.height= (int) CommonUtils.dp2Px(50);
                    cl.setLayoutParams(lp);
                    separate.setVisibility(View.GONE);
                }
            }
        });
        lv.setListViewHeightBasedOnChildren(list.size());
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ToastUtils.showBottom("点击了" + list.get(position).option);
            }
        });
    }
    
    private void fill(List<MyBean.CommonBean> list) {
        list.add(new MyBean.CommonBean(true, false, R.drawable.main_ic_contact, "通讯录"));
        list.add(new MyBean.CommonBean(false, true, R.drawable.main_ic_msg_my, "消息"));
        
        list.add(new MyBean.CommonBean(true, false, R.drawable.main_ic_area, "区域"));
        list.add(new MyBean.CommonBean(true, false, R.drawable.main_ic_device, "设备"));
        list.add(new MyBean.CommonBean(false, true, R.drawable.main_ic_journal, "操作日志"));
        
        list.add(new MyBean.CommonBean(true, false, R.drawable.main_ic_scan, "扫一扫"));
        list.add(new MyBean.CommonBean(true, false, R.drawable.main_ic_mall, "商城"));
        list.add(new MyBean.CommonBean(true, false, R.drawable.main_ic_system, "系统演示"));
        list.add(new MyBean.CommonBean(true, false, R.drawable.main_ic_help, "问题帮助"));
        list.add(new MyBean.CommonBean(true, false, R.drawable.main_ic_feedback, "意见反馈"));
        list.add(new MyBean.CommonBean(false, true, R.drawable.main_ic_about, "关于我们"));
    }
}
