package com.tb.baselib.base.activity;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.annotation.StringRes;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tb.baselib.R;
import com.tb.baselib.listener.NoDoubleClickListener;
import com.tb.baselib.mvp.model.IBaseModel;
import com.tb.baselib.mvp.presenter.BasePresenterImpl;
import com.tb.baselib.mvp.view.IBaseView;
import com.tb.baselib.net.impl.OKHttpRequester;
import com.tb.baselib.util.CommonUtils;
import com.tb.baselib.util.LogUtils;

/**
 * Created by : tb on 2017/9/19 下午5:10.
 * Description :Activity基类
 */
public abstract class BaseActivity extends AppCompatActivity implements IBaseView {
    protected BasePresenterImpl mBasePresenter;
    protected Context mActivityContext;
    protected Context mApplicationContext;
    /**
     * 真实的Activity布局
     */
    private int contentLayoutID;
    /**
     * 真实的Activity布局
     */
    protected View contentView;
    /**
     * 真实的Activity布局容器
     */
    protected LinearLayout rootView;
    /**
     * 默认使用titlebar作为标题栏，如不满足可自定义
     */
    protected ViewGroup titlebar;
    /**
     * 自定义的标题栏视图
     */
    private View selfTitlebar;

    private IBaseModel iBaseModel;
    protected NoDoubleClickListener noDoubleClickListener;

    protected TextView titlebarTvLeft;
    protected TextView titlebarTvCenter;
    protected TextView titlebarTvCenterSub;
    protected TextView titlebarTvRight;
    protected ImageView titlebarIvLeft;
    protected ImageView titlebarIvRight;

    protected LayoutInflater mLayoutInflater;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStatusbar(getWindow());//在setContentView之前调用
        mActivityContext = this;
        mApplicationContext = getApplicationContext();
        mLayoutInflater=LayoutInflater.from(this);
        iBaseModel = getIBaseModel();
        mBasePresenter = new BasePresenterImpl(this, iBaseModel);
        initVariables();
        initNoDoubleClickListener();
        setContentView(R.layout.baselib_base_layout);
        setUpTitlebar();
        setUpContentView();
        initViews(this.contentView, this.selfTitlebar, savedInstanceState);
        initViewSeparate(findViewById(R.id.view_empty));
        initListeners();
        loadData();
    }

    /**
     * 用于分隔标题栏和内容的视图
     *
     * @param viewEmpty 可以是线、留白、阴影等各种形式，默认为空实现
     */
    protected void initViewSeparate(View viewEmpty) {

    }

    /**
     * 设置具体的Activity的View
     */
    private void setUpContentView() {
        rootView = (LinearLayout) findViewById(R.id.root_content);
        if (hasStatusBar()) {
            //设置留出系统状态栏间距
//            ((View) rootView.getParent()).setFitsSystemWindows(true);

            //或者在setContentView()之后
            ViewGroup mContentView = (ViewGroup) getWindow().getDecorView().findViewById(Window.ID_ANDROID_CONTENT);
            View mChildView = mContentView.getChildAt(0);
            if (mChildView != null) {
                //注意不是设置 ContentView 的 FitsSystemWindows, 而是设置 ContentView 的第一个子 View . 预留出系统 View 的空间.
                mChildView.setFitsSystemWindows(true);
            }

        }
        this.contentLayoutID = getContentLayoutID();
        if (contentLayoutID > 0) {
            try {
                this.contentView = View.inflate(this, contentLayoutID, null);
//                this.contentView = LayoutInflater.from(this).inflate(this.contentLayoutID, rootView,false);
                if (contentView == null) {
//                    throw new NullPointerException("contentView must not be null,please invoke getContentLayoutID() first...");
                } else {
                    rootView.addView(contentView, 0, new LinearLayout.LayoutParams(-1, -1));
                }
            } catch (Resources.NotFoundException e) {
                e.printStackTrace();
            } catch (InflateException e) {
                e.printStackTrace();
            }
        }
//        setRootViewMarginBottom();
    }

    /**
     * 设置titlebar相关
     */
    private void setUpTitlebar() {
        titlebar = (ViewGroup) findViewById(R.id.rl_root_titlebar);
        titlebar.setVisibility(hasTitlebar() ? View.VISIBLE : View.GONE);
        initTitlebar();
        int toobarLayoutId = getTitlebarSelfViewID();
        if (toobarLayoutId > 0) {
            try {
                this.selfTitlebar = View.inflate(this, toobarLayoutId, null);
                if (selfTitlebar != null) {
                    titlebar.removeAllViews();
                    titlebar.addView(selfTitlebar, new LinearLayout.LayoutParams(-1, -1));
                }
            } catch (Resources.NotFoundException e) {
                e.printStackTrace();
            } catch (InflateException e) {
                e.printStackTrace();
            }
        }
    }

    private void initNoDoubleClickListener() {
        noDoubleClickListener = new NoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View view) {
                BaseActivity.this.onNoDoubleClick(view);
            }
        };
    }

    /**
     * 改变状态栏文字颜色为深色（6.0及以上手机生效）
     *
     * @return
     */
    protected boolean isLightStatusBar() {
        return true;
    }

    /**
     * 设置自定义假状态栏的颜色
     *
     * @return 返回状态栏颜色的资源id (e.g. {@code android.R.color.black})
     * 0代表默认黑色
     */
    protected int getStatusBarColor() {
        return 0;
    }

    /**
     * 是否有系统状态栏（没有则内容填充至顶部状态栏区域）
     *
     * @return
     */
    protected boolean hasStatusBar() {
        return true;
    }

    /**
     * 返回titlebar的颜色的资源id (e.g. {@code android.R.color.black})
     *
     * @return 0代表默认白色
     */
    protected int getTitlebarColor() {
        return 0;
    }

    /**
     * 返回Activity布局文件
     *
     * @return
     */
    protected abstract int getContentLayoutID();

    /**
     * 设置自定义的标题栏
     *
     * @return
     */
    protected int getTitlebarSelfViewID() {
        return -1;
    }

    /**
     * 初始化titlebar
     */
    private void initTitlebar() {
        titlebarTvLeft = (TextView) findViewById(R.id.titlebar_tv_left);
        titlebarTvCenter = (TextView) findViewById(R.id.titlebar_tv_center_title);
        titlebarTvCenterSub = (TextView) findViewById(R.id.titlebar_tv_center_sub_title);
        titlebarTvRight = (TextView) findViewById(R.id.titlebar_tv_menu);
        titlebarIvLeft = (ImageView) findViewById(R.id.titlebar_iv_back);
        titlebarIvRight = (ImageView) findViewById(R.id.titlebar_iv_menu);
        int colorResId = getTitlebarColor();
        if (colorResId > 0) {
            titlebar.setBackgroundColor(getResources().getColor(colorResId));
        } else {
            titlebar.setBackgroundColor(getResources().getColor(R.color.baselib_color_FFFFFF));
        }
    }

    /**
     * 是否显示titlebar,默认显示
     */
    protected boolean hasTitlebar() {
        return true;
    }

    /**
     * 初始化变量，包括Intent带的数据和Activity内的变量
     */
    protected void initVariables() {
    }

    /**
     * 加载layout布局文件，初始化控件
     *
     * @param contentView
     * @param titlebarView
     * @param savedInstanceState
     */
    protected abstract void initViews(View contentView, View titlebarView, Bundle savedInstanceState);

    /**
     * 为所有控件加上事件方法
     */
    protected void initListeners() {
    }

    /**
     * 向服务器请求具体数据
     */
    protected void loadData() {
    }

    protected void onNoDoubleClick(View v) {
    }

    /**
     * 设置网络请求框架，默认为okHttp3
     *
     * @return
     */
    protected IBaseModel getIBaseModel() {
        return OKHttpRequester.getInstance();
    }

    @Override
    public void onSuccess(int responseCode, int requestCode, Object response) {

    }

    @Override
    public void onFailure(int responseCode, int requestCode, String errMsg) {

    }

    @Override
    public void showLoadingView() {

    }

    @Override
    protected void onDestroy() {
        if (mBasePresenter != null) {
            mBasePresenter.detachView();
            mBasePresenter = null;
        }
        super.onDestroy();
    }

    protected <T extends View> T findViewByID(View view, @IdRes int id) {
        return (T) view.findViewById(id);
    }

    protected <T extends View> T findViewByID(@IdRes int id) {
        return (T) contentView.findViewById(id);
    }

    private void setStatusbar(Window window) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            //5.0
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS//去除半透明效果
                    /*| WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION*/);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    /*| View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION*/);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            if (hasStatusBar()) {
                if (isLightStatusBar() && Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    //6.0及以上才有效果
                    getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
                } else {
                    window.setStatusBarColor(getStatusBarColor() > 0 ? getResources().getColor(getStatusBarColor()) : getResources().getColor(R.color.baselib_color_A5A5A5));
                }
            } else {
                window.setStatusBarColor(Color.TRANSPARENT);
            }
//            window.setNavigationBarColor(Color.TRANSPARENT);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //4.4
            //透明状态栏
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //透明导航栏
//            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);

            if (hasStatusBar()) {
                ViewGroup decorViewGroup = (ViewGroup) window.getDecorView();//DecorView继承FrameLayout
                View statusBarView = new View(window.getContext());
                int statusBarHeight = CommonUtils.getStatusBarHeight();
                FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, statusBarHeight);
                params.gravity = Gravity.TOP;
                statusBarView.setLayoutParams(params);
                statusBarView.setBackgroundColor(getStatusBarColor() > 0 ? getResources().getColor(getStatusBarColor()) : getResources().getColor(R.color.baselib_color_A5A5A5));
                decorViewGroup.addView(statusBarView);
            }
        }
    }


    /**
     * 处理软键盘把布局顶上去的问题
     */
    private void setRootViewMarginBottom() {
        final View decorView = getWindow().getDecorView();
        decorView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
            @Override
            public void onGlobalLayout() {
                Rect rect = new Rect();
                decorView.getWindowVisibleDisplayFrame(rect);
                //这里获取的是真实的高度
//                int screenHeight = CommonUtils.getScreenRealSize(BaseActivity.this).y;
                int screenHeight = CommonUtils.getScreenResolution()[1];
                int heightDifference = screenHeight - rect.bottom;//计算软键盘占有的高度  = 屏幕高度 - 视图可见高度
                LogUtils.d("setRootViewMarginBottom====rect.bottom===" + rect.bottom + "*******" + heightDifference);
                /**
                 * 排除掉导航栏高度，这里有两种情况：1、浮动状态栏。(如小米mix)2、底部状态栏(如android虚拟机Nexus5)
                 * 注：getScreenResolution获取的都是不包含导航栏高度的分辨率，rect.bottom指的是可以放内容的区域的最底部坐标
                 * 第一种情况：getScreenResolution：1920  getScreenRealSize：2040  rect.bottom:2040
                 * 第二种情况：getScreenResolution：1776  getScreenRealSize：1920  rect.bottom:1776
                 * 所以下面的判断需要两个条件都加上，如果后续有其他情况再进行补充
                 *
                 * （有个偷懒的方案：heightDifference>70dp，因为键盘高度肯定大于这个值，但是导航栏高度肯定小于这个值）
                 * （或者直接用getScreenResolution的高度减去rect.bottom，判断是否大于0）
                 */
                if (heightDifference > 0 /*&& rect.bottom != CommonUtils.getScreenResolution()[1]*/) {
                    //说明键盘弹出
                    rootView.setPadding(0, 0, 0, heightDifference);
                } else {
                    //说明键盘隐藏
                    rootView.setPadding(0, 0, 0, 0);
                }
            }
        });
    }

    /**
     * 将整个Window内的View都打印出来
     * @param viewGroup
     */
    private void printChildView(ViewGroup viewGroup) {
        LogUtils.d("printView-ViewGroup===" + viewGroup.getClass().getSimpleName() + "的子View和数量:" + viewGroup.getChildCount());
        for (int i = 0; i < viewGroup.getChildCount(); i++) {
            String simpleName = viewGroup.getChildAt(i).getClass().getSimpleName();
            LogUtils.d("printView-ChildView===" + simpleName);
        }
        for (int i = 0; i < viewGroup.getChildCount(); i++) {
            if (viewGroup.getChildAt(i) instanceof ViewGroup) {
                printChildView((ViewGroup) viewGroup.getChildAt(i));
            }
        }
    }
}
