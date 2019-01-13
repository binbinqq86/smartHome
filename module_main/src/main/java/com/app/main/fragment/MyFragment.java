package com.app.main.fragment;

import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.TextView;


import com.app.main.R;
import com.app.main.adapter.MyAdapter;
import com.app.main.webapi.bean.MyBean;
import com.tb.baselib.base.fragment.BasicFragment;
import com.tb.baselib.util.CommonUtils;
import com.tb.baselib.widget.ToastUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by TianBin on 2018/1/21 17:15.
 * Description :
 */

public class MyFragment extends BasicFragment {
    private TextView tvSet;
    private RecyclerView rv;
    private MyAdapter adapter;
    private View view;
    private List<MyBean> list = new ArrayList<>();
    
    private MyAdapter.OnClick onClick = new MyAdapter.OnClick() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.cl:
                    ToastUtils.showBottom("个人中心");
                    break;
            }
        }
    };
    
    @Override
    protected int getContentLayoutID() {
        return R.layout.main_fragment_my;
    }
    
    @Override
    protected void initViews(View contentView, Bundle savedInstanceState) {
        tvSet = findViewByID(R.id.tv_set);
        rv = findViewByID(R.id.rv);
        view = findViewByID(R.id.view);
        view.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                ConstraintLayout.LayoutParams clp = (ConstraintLayout.LayoutParams) view.getLayoutParams();
                clp.height = CommonUtils.getStatusBarHeight();
                view.setLayoutParams(clp);
                view.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });
        initRv();
    }
    
    @Override
    protected void initListeners() {
        super.initListeners();
        tvSet.setOnClickListener(noDoubleClickListener);
    }
    
    @Override
    protected void onNoDoubleClick(View v) {
        super.onNoDoubleClick(v);
        int id = v.getId();
        switch (id) {
            case R.id.tv_set:
                ToastUtils.showBottom("设置");
                break;
        }
    }
    
    private void initRv() {
        fillHeader();
        fillCommon();
        rv.setLayoutManager(new LinearLayoutManager(mContext));
        adapter = new MyAdapter(list);
        adapter.setOnClick(onClick);
        rv.setAdapter(adapter);
    }
    
    private void fillHeader() {
        MyBean myBean=new MyBean();
        myBean.headerBean=new MyBean.HeaderBean();
        list.add(myBean);
    }
    
    private void fillCommon() {
        MyBean myBean=new MyBean();
        list.add(myBean);
    }
}
