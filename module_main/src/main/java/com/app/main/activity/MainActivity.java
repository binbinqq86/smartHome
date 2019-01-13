package com.app.main.activity;


import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.app.main.R;
import com.app.main.fragment.DrawerFragment;
import com.app.main.fragment.HomeFragment;
import com.app.main.fragment.MyFragment;

public class MainActivity extends MainBaseActivity {
    
    private DrawerLayout drawerLayout;
    private FrameLayout container;
    private FrameLayout drawer;
    private ImageView ivHome, ivMy;
    private LinearLayout llHome, llMy;
    
    private DrawerFragment drawerFragment;
    private MyFragment myFragment;
    private HomeFragment homeFragment;
    private static final String TAG_HOME = "TAG_HOME";
    private static final String TAG_MY = "TAG_MY";
    private static final String TAG_DRAWER = "TAG_DRAWER";
    
    @Override
    protected int getContentLayoutID() {
        return R.layout.main_activity_main;
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
    protected boolean isLightStatusBar() {
        return super.isLightStatusBar();
    }
    
    @Override
    protected void initViewSeparate(View viewEmpty) {
    
    }
    
    @Override
    protected void initViews(View contentView, View titlebarView, Bundle savedInstanceState) {
        super.initViews(contentView, titlebarView, savedInstanceState);
        drawerLayout = findViewByID(R.id.drawer_layout);
        container = findViewByID(R.id.container);
        drawer = findViewByID(R.id.drawer);
        ivHome = findViewByID(R.id.iv_home);
        ivMy = findViewByID(R.id.iv_my);
        llHome = findViewByID(R.id.ll_home);
        llMy = findViewByID(R.id.ll_my);
        
        getSupportFragmentManager().beginTransaction()
                .add(R.id.container, homeFragment, TAG_HOME)
                .show(homeFragment)
                .commitAllowingStateLoss();
        getSupportFragmentManager().beginTransaction()
                .add(R.id.container, myFragment, TAG_MY)
                .hide(myFragment)
                .commitAllowingStateLoss();
        getSupportFragmentManager().beginTransaction()
                .add(R.id.drawer, drawerFragment, TAG_DRAWER)
                .show(drawerFragment)
                .commitAllowingStateLoss();
    }
    
    @Override
    protected void initListeners() {
        super.initListeners();
        llHome.setOnClickListener(noDoubleClickListener);
        llMy.setOnClickListener(noDoubleClickListener);
    }
    
    @Override
    protected void onNoDoubleClick(View v) {
        super.onNoDoubleClick(v);
        int id = v.getId();
        if (id == R.id.ll_home) {
            ivHome.setImageResource(R.drawable.main_ic_home_select);
            ivMy.setImageResource(R.drawable.main_ic_my_normal);
            getSupportFragmentManager().beginTransaction()
                    .hide(myFragment)
                    .show(homeFragment)
                    .commitAllowingStateLoss();
        } else if (id == R.id.ll_my) {
            ivHome.setImageResource(R.drawable.main_ic_home_normal);
            ivMy.setImageResource(R.drawable.main_ic_my_select);
            getSupportFragmentManager().beginTransaction()
                    .hide(homeFragment)
                    .show(myFragment)
                    .commitAllowingStateLoss();
        }
    }
    
    @Override
    protected void initVariables() {
        super.initVariables();
        drawerFragment = new DrawerFragment();
        myFragment = new MyFragment();
        homeFragment = new HomeFragment();
    }
    
    public boolean closeDrawer() {
        if (drawerLayout.isDrawerOpen(drawer) || drawerLayout.isDrawerVisible(drawer)) {
            drawerLayout.closeDrawer(drawer);
            return true;
        }
        return false;
    }
    
    public boolean openDrawer() {
        if (!drawerLayout.isDrawerOpen(drawer) && !drawerLayout.isDrawerVisible(drawer)) {
            drawerLayout.openDrawer(drawer);
            return true;
        }
        return false;
    }
    
    @Override
    public void onBackPressed() {
        if (!closeDrawer()) {
            super.onBackPressed();
        }
    }
}
