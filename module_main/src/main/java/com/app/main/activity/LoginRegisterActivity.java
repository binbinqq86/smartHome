package com.app.main.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.app.main.R;
import com.app.main.adapter.FragmentAdapter;
import com.app.main.fragment.LoginFrament;
import com.app.main.fragment.RegisterFragment;
import com.app.main.widget.Triangle;

import java.util.ArrayList;
import java.util.List;

/**
 * @auther tb
 * @time 2018/1/16 下午7:41
 * @desc 登录注册
 */
public class LoginRegisterActivity extends MainBaseActivity {

    private RadioGroup mRg;
    private RadioButton mRbLogin;
    private RadioButton mRbRegister;
    private Triangle mViewTriangle;
    private ViewPager mVp;
    private Fragment loginFragment;
    private Fragment registerFragment;
    private List<Fragment> fragmentList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getContentLayoutID() {
        return R.layout.main_activity_login;
    }

    @Override
    protected int getStatusBarColor() {
        return R.color.main_color_EF5227;
    }

    @Override
    protected boolean hasTitlebar() {
        return false;
    }

    @Override
    protected boolean isLightStatusBar() {
        return false;
    }

    @Override
    protected void initViewSeparate(View viewSeparate) {

    }

    @Override
    protected void initViews(View contentView, View titlebarView, Bundle savedInstanceState) {
        super.initViews(contentView,titlebarView,savedInstanceState);
        mRg = findViewByID(R.id.rg);
        mRbLogin = findViewByID(R.id.rb_login);
        mRbRegister = findViewByID(R.id.rb_register);
        mViewTriangle = findViewByID(R.id.view_triangle);
        mVp = findViewByID(R.id.vp);

        mVp.setAdapter(new FragmentAdapter(getSupportFragmentManager(), fragmentList));
    }

    @Override
    protected void initVariables() {
        super.initVariables();
        loginFragment = new LoginFrament();
        registerFragment = new RegisterFragment();
        fragmentList = new ArrayList<>();
        fragmentList.add(loginFragment);
        fragmentList.add(registerFragment);
    }

    @Override
    protected void initListeners() {
        super.initListeners();
        mRg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.rb_login) {
                    mVp.setCurrentItem(0);
                } else if (checkedId == R.id.rb_register) {
                    mVp.setCurrentItem(1);
                }
            }
        });
        mVp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                mViewTriangle.setTranslationX((position + positionOffset) * mViewTriangle.getWidth());
            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        mRbLogin.setChecked(true);
                        break;
                    case 1:
                        mRbRegister.setChecked(true);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }
}
