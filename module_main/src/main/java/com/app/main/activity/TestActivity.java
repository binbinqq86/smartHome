package com.app.main.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.main.R;
import com.app.main.webapi.api.Apis;
import com.app.main.webapi.bean.TestBean;
import com.tb.baselib.constant.BaseConstant;
import com.tb.baselib.image.impl.GlideImageLoader;
import com.tb.baselib.manager.PermissionMgr;
import com.tb.baselib.widget.ToastUtils;
import com.tb.router.ActivityLauncher;

/**
 * @auther tb
 * @time 2017/11/13 下午12:00
 * @desc
 */
public class TestActivity extends MainBaseActivity {
    private static final String TAG = "TestActivity";
    ImageView iv;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getContentLayoutID() {
        return R.layout.main_activity_test;
    }

    @Override
    protected int getStatusBarColor() {
        return R.color.main_color_EF5227;
    }

    @Override
    protected boolean isLightStatusBar() {
        return false;
    }

    //    @Override
//    protected int getStatusBarColor() {
//        return R.color.main_color_FFFFFF;
//    }

//    @Override
//    protected boolean hasStatusBar() {
//        return false;
//    }

    @Override
    protected void initViews(View contentView, View toolbarView, Bundle savedInstanceState) {
        textView = (TextView) contentView.findViewById(R.id.textView);
        iv = (ImageView) contentView.findViewById(R.id.iv);
        titlebarTvCenter.setText(getString(R.string.app_name));
    }

    @Override
    protected void initListeners() {
        //默认点击finish，此处可以重写事件
        titlebarIvLeft.setImageResource(R.mipmap.main_ic_launcher_round);
        titlebarIvLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ToastUtils.showCenter("ARouter test");
                ActivityLauncher.test(TestActivity.this);
            }
        });
        textView.setOnClickListener(noDoubleClickListener);
    }

    @Override
    protected void onNoDoubleClick(View v) {
        super.onNoDoubleClick(v);
        switch (v.getId()) {
            case R.id.textView:
                ToastUtils.showBottom(BaseConstant.BASE_API_URL);
                PermissionMgr.checkCallPhonePermission(TestActivity.this, new PermissionMgr.PermissionGrantListener() {
                    @SuppressLint("MissingPermission")
                    @Override
                    public void permissionHasGranted(String permission) {
                        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:17085347782"));
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        TestActivity.this.startActivity(intent);
                    }
                });
                break;
        }
    }

//    @Override
//    protected IBaseModel getIBaseModel() {
//        //采用retrofit封装请求，默认直接使用okhttp
//        return RetrofitRequester.getInstance();
//    }

    @Override
    protected void loadData() {
        Apis.test(mBasePresenter);
//        Apis.test1(mBasePresenter);
    }

    @Override
    public void showLoadingView() {
        showLoadingView("loading");
    }

    @Override
    public void onSuccess(int responseCode, int requestCode, final Object response) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //此处也可以显示其他页面，比如空页面等。。。
//        showLoadEmptyView(R.mipmap.main_ic_launcher,"empty");
                showContentView();
//        TextView tv=new TextView(this);
//        tv.setBackgroundColor(Color.YELLOW);
//        tv.setText("aaaaa");
//        tv.setTextSize(33);
//        showSelfView(tv);

//        ArrayList<TestBean> bean= (ArrayList<TestBean>) response;
//        textView.setText(bean.get(0).getName()+"\n"+bean.get(0).getJson());
                if (response instanceof TestBean) {
                    TestBean bean = (TestBean) response;
                    textView.setText(bean.getName() + "\n" + bean.getJson());
                }
                imageTest();
            }
        },3000);
    }

    @Override
    public void onFailure(int responseCode, int requestCode, final String errMsg) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                showLoadErrorView(R.mipmap.main_ic_launcher, errMsg, "retry", 0, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ToastUtils.showBottom("retry click");
                        loadData();
                    }
                });
            }
        },3000);
    }

    @SuppressLint("ResourceType")
    private void imageTest() {
//        Log.e(TAG, "imageTest: " + (iv == null));
        String url = "https://ss0.bdstatic.com/5aV1bjqh_Q23odCf/static/superman/img/logo/logo_white_fe6da1ec.png";
        GlideImageLoader.getInstance().displayImg(mActivityContext,iv,url,R.color.main_color_95D1FF,R.color.main_color_060606);
    }
}
