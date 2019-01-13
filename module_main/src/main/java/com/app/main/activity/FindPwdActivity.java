package com.app.main.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.app.main.R;

/**
 * @auther tb
 * @time 2018/1/20 下午2:09
 * @desc 找回密码
 */
public class FindPwdActivity extends MainBaseActivity {

    private EditText etPhone;
    private EditText etCode;
    private TextView tvGetCode;
    private EditText etPwd;
    private Button btOk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getContentLayoutID() {
        return R.layout.main_activity_find_pwd;
    }

    @Override
    protected void initViews(View contentView, View titlebarView, Bundle savedInstanceState) {
        super.initViews(contentView, titlebarView, savedInstanceState);
        titlebarTvCenter.setText(R.string.main_string_find_pwd);

        etPhone = findViewByID(R.id.et_phone);
        etCode = findViewByID(R.id.et_code);
        tvGetCode = findViewByID(R.id.tv_get_code);
        etPwd = findViewByID(R.id.et_pwd);
        btOk = findViewByID(R.id.bt_ok);
    }

}
