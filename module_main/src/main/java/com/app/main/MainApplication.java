package com.app.main;


import com.app.main.webapi.api.ServerUrls;
import com.tb.baselib.base.BaseApplication;

/**
 * Created by : tb on 2017/9/20 下午3:32.
 * Description :
 */
public class MainApplication extends BaseApplication {
    @Override
    public void onCreate() {
        super.onCreate();
        init();
    }

    private void init() {
        /**
         * 初始化服务器API前缀
         */
        ServerUrls.getInstance();
    }
}
