package com.app.main.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


import com.app.main.R;
import com.app.main.activity.FindPwdActivity;
import com.app.main.activity.MainActivity;
import com.tb.baselib.base.fragment.BasicFragment;

/**
 * @auther tb
 * @time 2018/1/17 上午10:38
 * @desc
 */
public class LoginFrament extends BasicFragment {
    private EditText mEtUsername;
    private EditText mEtUserpwd;
    private Button mBtLogin;
    private TextView mTvForgetPwd;

    @Override
    protected int getContentLayoutID() {
        return R.layout.main_fragment_login;
    }

    @Override
    protected void initViews(View contentView, Bundle savedInstanceState) {
        mEtUsername = findViewByID(R.id.et_username);
        mEtUserpwd = findViewByID(R.id.et_userpwd);
        mBtLogin = findViewByID(R.id.bt_login);
        mTvForgetPwd = findViewByID(R.id.tv_forget_pwd);
    }

    @Override
    protected void initListeners() {
        super.initListeners();
        mTvForgetPwd.setOnClickListener(noDoubleClickListener);
        mBtLogin.setOnClickListener(noDoubleClickListener);
    }

    @Override
    protected void onNoDoubleClick(View v) {
        super.onNoDoubleClick(v);
        int id = v.getId();
        if (id == R.id.tv_forget_pwd) {
            startActivity(new Intent(getContext(), FindPwdActivity.class));
        }else if(id==R.id.bt_login){
            startActivity(new Intent(getContext(), MainActivity.class));
        }
    }

}
