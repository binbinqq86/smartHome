package com.tb.baselib.base;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.support.multidex.MultiDex;
import android.util.Log;

import com.alibaba.android.arouter.launcher.ARouter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.DefaultRefreshFooterCreater;
import com.scwang.smartrefresh.layout.api.DefaultRefreshFooterCreator;
import com.scwang.smartrefresh.layout.api.DefaultRefreshHeaderCreater;
import com.scwang.smartrefresh.layout.api.DefaultRefreshHeaderCreator;
import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.tb.baselib.BuildConfig;
import com.tb.baselib.R;
import com.tb.baselib.util.LogUtils;
import com.tb.baselib.widget.ToastUtils;

/**
 * Created by : tb on 2017/9/20 下午3:40.
 * Description :
 */
public class BaseApplication extends Application {
    private static final String TAG = "BaseApplication";
    public static BaseApplication application;
    /**
     * 取包名最后一位
     */
    public static String LAST_PACKAGE_NAME;
    private static int mActivityCount = 0;
    
    //static 代码段可以防止内存泄露
    static {
        //设置全局的Header构建器
        SmartRefreshLayout.setDefaultRefreshHeaderCreator(new DefaultRefreshHeaderCreator() {
            @Override
            public RefreshHeader createRefreshHeader(Context context, RefreshLayout layout) {
                layout.setPrimaryColorsId(android.R.color.white);//全局设置主题颜色
                return new ClassicsHeader(context).setEnableLastTime(false);//指定为经典Header，默认是 贝塞尔雷达Header
            }
        });
        //设置全局的Footer构建器
        SmartRefreshLayout.setDefaultRefreshFooterCreator(new DefaultRefreshFooterCreator() {
            @Override
            public RefreshFooter createRefreshFooter(Context context, RefreshLayout layout) {
                //指定为经典Footer，默认是 BallPulseFooter
                return new ClassicsFooter(context).setDrawableSize(20);
            }
        });
    }
    
    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
        initComponent();
    }
    
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
    
    private void initComponent() {
        try {
            /**
             * 获取包名最后一位
             */
            String str[] = getPackageName().split("\\.");
            LAST_PACKAGE_NAME = str[str.length - 1];
            /**
             * 初始化日志
             */
            LogUtils.init();
            /**
             * 初始化toast
             */
            ToastUtils.init();
            /**
             * ARouter
             */
            if (BuildConfig.IS_DEBUG_MODE) {// 这两行必须写在init之前，否则这些配置在init过程中将无效
                ARouter.openLog();// 打印日志
                ARouter.openDebug();// 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
            }
            ARouter.init(this);// 尽可能早，推荐在Application中初始化
            /**
             * activity生命周期初始化
             */
            registerActivityLifecycleCallbacks();
            /**
             * 初始化数据库
             */
            initDB();
        } catch (Exception e) {
            LogUtils.e("BaseApplication======" + e.getMessage());
        }
    }
    
    private void registerActivityLifecycleCallbacks() {
        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
                LogUtils.v(">>>>>>>>>>>>onActivityCreated>>>>>>>>>>>>>>" + activity.toString());
            }
            
            @Override
            public void onActivityStarted(Activity activity) {
                if (mActivityCount == 0) {
                    LogUtils.d("TB_LIFECYCLE>>>>>>>>>>>>>>>>>>>切到前台>>>>>>>>>>>>>>>>" + activity.toString());
                }
                mActivityCount++;
                LogUtils.v(">>>>>>>>>>>>onActivityStarted>>>>>>>>>>>>>>" + activity.toString());
            }
            
            @Override
            public void onActivityResumed(Activity activity) {
                LogUtils.v(">>>>>>>>>>>>onActivityResumed>>>>>>>>>>>>>>" + activity.toString());
            }
            
            @Override
            public void onActivityPaused(Activity activity) {
                LogUtils.v(">>>>>>>>>>>>onActivityPaused>>>>>>>>>>>>>>" + activity.toString());
            }
            
            @Override
            public void onActivityStopped(Activity activity) {
                mActivityCount--;
                if (mActivityCount == 0) {
                    LogUtils.d("TB_LIFECYCLE>>>>>>>>>>>>>>>>>>>切到后台>>>>>>>>>>>>>>>>" + activity.toString());
                }
                LogUtils.v(">>>>>>>>>>>>onActivityStopped>>>>>>>>>>>>>>" + activity.toString());
            }
            
            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
                LogUtils.v(">>>>>>>>>>>>onActivitySaveInstanceState>>>>>>>>>>>>>>" + activity.toString());
            }
            
            @Override
            public void onActivityDestroyed(Activity activity) {
                LogUtils.v(">>>>>>>>>>>>onActivityDestroyed>>>>>>>>>>>>>>" + activity.toString());
            }
        });
    }
    
    /**
     * TODO 初始化全局数据库(统一采用同一个数据库)
     */
    private void initDB() {
    
    }
}
