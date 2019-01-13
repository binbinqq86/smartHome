package com.app.main.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;

import com.app.main.R;

public class SplashActivity extends MainBaseActivity {
    public static final int DELAY = 2 * 1000;
    private ImageView ivBg;
    
    @Override
    protected int getContentLayoutID() {
        return R.layout.main_activity_splash;
    }
    
    @Override
    protected boolean hasStatusBar() {
        return false;
    }
    
    @Override
    protected boolean hasTitlebar() {
        return false;
    }
    
    @Override
    protected void initViewSeparate(View viewSeparate) {
    
    }
    
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            //全屏
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
        super.onCreate(savedInstanceState);
    }
    
    @Override
    public void onBackPressed() {
        //屏蔽返回按键
    }
    
    @Override
    protected void initViews(View contentView, View titlebarView, Bundle savedInstanceState) {
        super.initViews(contentView, titlebarView, savedInstanceState);
        ivBg = findViewByID(R.id.iv_splash);
        ivBg.setBackgroundResource(R.drawable.main_ic_splash);
        
        startAnim();
    }
    
    private void startAnim() {
        ScaleAnimation scaleAnimation = new ScaleAnimation(1, 1.05f, 1, 1.05f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        scaleAnimation.setDuration(DELAY);
        scaleAnimation.setFillAfter(true);
        scaleAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            
            }
            
            @Override
            public void onAnimationEnd(Animation animation) {
                startActivity(new Intent(mActivityContext, LoginRegisterActivity.class));
                finish();
            }
            
            @Override
            public void onAnimationRepeat(Animation animation) {
            
            }
        });
        ivBg.startAnimation(scaleAnimation);
    }
}
