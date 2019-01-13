package com.app.main.fragment;

import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.TextView;


import com.app.main.R;
import com.app.main.activity.MainActivity;
import com.app.main.webapi.bean.DrawerBean;
import com.tb.baselib.adapter.CommonRvAdapter;
import com.tb.baselib.adapter.CommonRvViewHolder;
import com.tb.baselib.base.fragment.BasicFragment;
import com.tb.baselib.util.CommonUtils;
import com.tb.baselib.widget.ToastUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by TianBin on 2018/1/21 17:15.
 * Description :抽屉布局
 */

public class DrawerFragment extends BasicFragment {
    private ImageView mIvBack;
    private TextView mTvHomeName;
    private RecyclerView mRv;
    private List<DrawerBean> list = new ArrayList<>();
    private CommonRvAdapter<DrawerBean> adapter;
    private View view;
    @Override
    protected int getContentLayoutID() {
        return R.layout.main_fragment_drawer;
    }
    
    @Override
    protected void initViews(View contentView, Bundle savedInstanceState) {
        mIvBack = findViewByID(R.id.iv_back);
        mTvHomeName = findViewByID(R.id.tv_home_name);
        mRv = findViewByID(R.id.rv);
        view=findViewByID(R.id.status);
        view.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                ConstraintLayout.LayoutParams clp= (ConstraintLayout.LayoutParams) view.getLayoutParams();
                clp.height= CommonUtils.getStatusBarHeight();
                view.setLayoutParams(clp);
                view.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });
        
        fillData();
        mRv.setLayoutManager(new LinearLayoutManager(mContext));
        adapter = new CommonRvAdapter<DrawerBean>(R.layout.main_item_drawer, list) {
            @Override
            public void convert(CommonRvViewHolder holder, DrawerBean drawerBean) {
                TextView tv = holder.getView(R.id.tv);
                tv.setText(drawerBean.name);
                tv.setCompoundDrawablesWithIntrinsicBounds(drawerBean.resId, 0, 0, 0);
            }
        };
        mRv.setAdapter(adapter);
    }
    
    @Override
    protected void initListeners() {
        super.initListeners();
        adapter.setOnClickListener(new CommonRvAdapter.OnClickListener() {
            @Override
            public void onItemClickListener(View view, int position, Object o) {
                DrawerBean drawerBean = (DrawerBean) o;
                ToastUtils.showBottom(drawerBean.name + "===Click===" + position);
            }
            
            @Override
            public void onItemLongClickListener(View view, int position, Object o) {
                DrawerBean drawerBean = (DrawerBean) o;
                ToastUtils.showBottom(drawerBean.name + "===LongClick===" + position);
            }
        });
        mIvBack.setOnClickListener(noDoubleClickListener);
    }
    
    @Override
    protected void onNoDoubleClick(View v) {
        super.onNoDoubleClick(v);
        if(v.getId()==R.id.iv_back){
            ((MainActivity)getActivity()).closeDrawer();
        }
    }
    
    private void fillData() {
        DrawerBean bean1 = new DrawerBean();
        bean1.name = "客厅";
        bean1.resId = R.drawable.main_ic_sofa;
        DrawerBean bean2 = new DrawerBean();
        bean2.name = "厨房";
        bean2.resId = R.drawable.main_ic_kitchen;
        DrawerBean bean3 = new DrawerBean();
        bean3.name = "卧室";
        bean3.resId = R.drawable.main_ic_bed_drawer;
        
        list.add(bean1);
        list.add(bean2);
        list.add(bean3);
    }
}
