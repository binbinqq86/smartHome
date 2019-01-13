package com.example.test;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.tb.baselib.base.activity.BasicActivity;
import com.tb.router.constant.ARouterPath;

/**
 * Created by : tb on 2017/9/26 下午3:37.
 * Description :ARouter测试activity
 */
@Route(path = ARouterPath.TEST)
public class TestActivity extends BasicActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    
    @Override
    protected int getContentLayoutID() {
        return R.layout.test;
    }
    
//    @Override
//    protected void initVariables() {
//
//    }
    
    @Override
    protected void initViews(View contentView, View toolbarView, Bundle savedInstanceState) {
        titlebarTvLeft.setText(getString(R.string.test_app_name));
        titlebarTvLeft.setOnClickListener(noDoubleClickListener);
    }

    @Override
    protected void onNoDoubleClick(View v) {
        super.onNoDoubleClick(v);
        int i = v.getId();
        if (i == R.id.titlebar_tv_left) {
            finish();
        }
    }

    //    @Override
//    protected void initListeners() {
//
//    }
    
//    @Override
//    protected void loadData() {
//
//    }
//
//    @Override
//    public void showLoadingView() {
//
//    }
    
//    @Override
//    public void onSuccess(int responseCode, int requestCode, Object response) {
//
//    }
//
//    @Override
//    public void onFailure(int responseCode, int requestCode, String errMsg) {
//
//    }
}
