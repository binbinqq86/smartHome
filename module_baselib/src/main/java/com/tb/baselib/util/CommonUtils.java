package com.tb.baselib.util;

import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.view.WindowManager;

import com.tb.baselib.R;
import com.tb.baselib.base.BaseApplication;

/**
 * Created by : tb on 2017/9/20 下午3:36.
 * Description :通用的工具类，无法归类的都写到这里
 */
public class CommonUtils {
    /**
     * dp转px
     *
     * @param dp
     * @return
     */
    public static float dp2Px(float dp) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, BaseApplication.application.getResources().getDisplayMetrics());
    }

    /**
     * 获取statusBar，可以用来设置背景渐变色
     * @param activity
     * @return
     */
    public static View getStatusBarView(Activity activity) {
        int identifier = activity.getResources().getIdentifier("statusBarBackground", "id", "android");
        return activity.getWindow().findViewById(identifier);
    }

    /**
     * 获取系统状态栏高度
     * @return
     */
    public static int getStatusBarHeight() {
        int result = 0;
        int resourceId = BaseApplication.application.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = BaseApplication.application.getResources().getDimensionPixelSize(resourceId);
        }
        LogUtils.d("======getStatusBarHeight===="+result);
        if (result == 0) {
            result = (int) Math.ceil(20 * BaseApplication.application.getResources().getDisplayMetrics().density);
            LogUtils.d("======getStatusBarHeight@@@====="+result);
        }
        return result;
    }
    
    /**
     * 获取屏幕宽高
     * @return 返回一个宽高组成的int[]数组
     */
    public static int[] getScreenResolution(){
        DisplayMetrics dm=BaseApplication.application.getResources().getDisplayMetrics();
        int w=dm.widthPixels;
        int h=dm.heightPixels;
        LogUtils.d("=====getScreenWidth====="+w+"@"+h);
        return new int[]{w,h};
    }

    /**
     * 获取屏幕真实的宽高
     * @param activity
     * @return
     */
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public static Point getScreenRealSize(Activity activity){
        Point point=new Point();
        activity.getWindowManager().getDefaultDisplay().getRealSize(point);
        LogUtils.d("=======getScreenRealSize=========="+point.x+"$$"+point.y);
        return point;
    }

}
