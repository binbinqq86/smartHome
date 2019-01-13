package com.app.main.activity;

import android.os.Bundle;
import android.view.View;

import com.app.main.R;
import com.tb.baselib.base.activity.BasicActivity;

/**
 * Created by TianBin on 2018/1/20 18:02.
 * Description :
 */

public abstract class MainBaseActivity extends BasicActivity {
    @Override
    protected void initViews(View contentView, View titlebarView, Bundle savedInstanceState) {
        titlebarIvLeft.setImageResource(R.drawable.main_ic_titlebar_back);
        titlebarIvLeft.setOnClickListener(noDoubleClickListener);
    }

    @Override
    protected void onNoDoubleClick(View v) {
        super.onNoDoubleClick(v);
        if (v.getId() == R.id.titlebar_iv_back) {
            finish();
        }
    }

    @Override
    protected void initViewSeparate(View viewEmpty) {
        super.initViewSeparate(viewEmpty);
        viewEmpty.getLayoutParams().height = getResources().getDimensionPixelSize(R.dimen.baselib_dp10);
        viewEmpty.setBackgroundColor(getResources().getColor(R.color.main_color_EFEFEF));
    }

}
