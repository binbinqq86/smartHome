package com.app.main.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.app.main.R;
import com.app.main.activity.MainActivity;
import com.app.main.adapter.HomeAdapter;
import com.app.main.webapi.bean.HomeBean;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.tb.baselib.base.fragment.BasicFragment;
import com.tb.baselib.util.CommonUtils;
import com.tb.baselib.widget.DividerItemDecoration;
import com.tb.baselib.widget.ToastUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by TianBin on 2018/1/21 17:15.
 * Description :
 */

public class HomeFragment extends BasicFragment implements OnRefreshListener {
    private RecyclerView rv;
    private ImageView ivHome;
    private TextView tvTitle;
    private View fakeStatusBar;
    private LinearLayout llTop;
    private RelativeLayout rlTop;
    private View viewLine;
    private SmartRefreshLayout srl;
    private HomeAdapter adapter;
    private List<HomeBean> list = new ArrayList<>();
    private int mTotalDy;
    private float total;
    private float alpha;//透明度：0~255
    private HomeAdapter.OnClick onClick = new HomeAdapter.OnClick() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.cl:
                    ToastUtils.showBottom("===msg click===");
                    break;
            }
        }
    };
    
    @Override
    protected int getContentLayoutID() {
        return R.layout.main_fragment_home;
    }
    
    @Override
    protected void initViews(View contentView, Bundle savedInstanceState) {
        rv = findViewByID(R.id.rv);
        srl = findViewByID(R.id.srl);
        ivHome = findViewByID(R.id.iv_home_top);
        tvTitle = findViewByID(R.id.tv_title);
        fakeStatusBar = findViewByID(R.id.view);
        llTop = findViewByID(R.id.ll_top);
        rlTop = findViewByID(R.id.rl_top);
        viewLine = findViewByID(R.id.view_line);
        initRv();
        initSrl();
        initListener();
    }
    
    private void initListener() {
        fakeStatusBar.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                //4.4以下暂不考虑状态栏问题
                LinearLayout.LayoutParams clp = (LinearLayout.LayoutParams) fakeStatusBar.getLayoutParams();
                clp.height = CommonUtils.getStatusBarHeight();
                fakeStatusBar.setLayoutParams(clp);
                fakeStatusBar.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });
        ivHome.setOnClickListener(noDoubleClickListener);
        rv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }
            
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                mTotalDy += dy;
                updateTopBarBackgroundAlpha();
            }
        });
    }
    
    private void initSrl() {
        srl.setDisableContentWhenLoading(true);
        srl.setDisableContentWhenRefresh(true);
        srl.setEnableRefresh(true);
        srl.setEnableLoadMore(false);
        srl.setEnableAutoLoadMore(false);
        srl.setOnRefreshListener(this);
        srl.setEnableFooterTranslationContent(false);
        srl.setEnableNestedScroll(true);//为了解决跟自定义view的滑动冲突
    }
    
    private void initRv() {
        testData();
        rv.setLayoutManager(new LinearLayoutManager(mContext));
        rv.addItemDecoration(new DividerItemDecoration(mContext, mContext.getResources().getColor(R.color.main_color_EFEFEF), -1, 10));
        adapter = new HomeAdapter(list);
        adapter.setOnClick(onClick);
        rv.setAdapter(adapter);
    }
    
    private void testData() {
        for (int i = 0; i < 4; i++) {
            HomeBean hb = new HomeBean();
            list.add(hb);
        }
    }
    
    @Override
    public void onRefresh(RefreshLayout refreshLayout) {
        new Thread() {
            @Override
            public void run() {
                super.run();
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                srl.finishRefresh();
            }
        }.start();
    }
    
    @Override
    protected void onNoDoubleClick(View v) {
        super.onNoDoubleClick(v);
        switch (v.getId()) {
            case R.id.iv_home_top:
                ((MainActivity) getActivity()).openDrawer();
                break;
        }
    }
    
    @Override
    protected void initVariables() {
        super.initVariables();
        total = getResources().getDimension(R.dimen.baselib_dp56) + CommonUtils.getStatusBarHeight();
    }
    
    /**
     * 更新顶部bar透明度
     */
    private void updateTopBarBackgroundAlpha() {
        if (mTotalDy > total && alpha == 255) {
            return;
        }
        float scale = mTotalDy / total;
        
        alpha = 255 * scale;
        if (alpha > 255) {
            alpha = 255;
        }
        
        //#EF5227
        rlTop.setBackgroundColor(Color.argb((int) alpha, 239, 82, 39));
        fakeStatusBar.setBackgroundColor(Color.argb((int) alpha, 239, 82, 39));
//        if (scale >= 0.5) {
//            ivHome.setImageResource(R.drawable.main_ic_home_top_black);
//            tvTitle.setTextColor(Color.argb((int) alpha, 8, 8, 8));
//            viewLine.setVisibility(View.VISIBLE);
//        } else {
//            ivHome.setImageResource(R.drawable.main_ic_home_top);
//            tvTitle.setTextColor(Color.argb(255 - (int) alpha, 255, 255, 255));
//            viewLine.setVisibility(View.GONE);
//        }
    }
}
