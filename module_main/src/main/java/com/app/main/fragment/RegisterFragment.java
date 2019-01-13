package com.app.main.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.app.main.R;
import com.tb.baselib.base.fragment.BasicFragment;

/**
 * @auther tb
 * @time 2018/1/17 下午2:04
 * @desc
 */
public class RegisterFragment extends BasicFragment {
    private EditText mEtPhone;
    private LinearLayout mLlVerifyCode;
    private EditText mEtCode;
    private TextView mTvGetCode;
    private EditText mEtPwd;
    private Button mBtRegister;
    private CheckBox mCbRegisterProtocol;
    private TextView mTvRegisterProtocol;

    @Override
    protected int getContentLayoutID() {
        return R.layout.main_fragment_register;
    }

    @Override
    protected void initViews(View contentView, Bundle savedInstanceState) {
        mEtPhone = findViewByID(R.id.et_phone);
        mLlVerifyCode = findViewByID(R.id.ll_verify_code);
        mEtCode = findViewByID(R.id.et_code);
        mTvGetCode = findViewByID(R.id.tv_get_code);
        mEtPwd = findViewByID(R.id.et_pwd);
        mBtRegister = findViewByID(R.id.bt_register);
        mCbRegisterProtocol = findViewByID(R.id.cb_register_protocol);
        mTvRegisterProtocol = findViewByID(R.id.tv_register_protocol);
    }
}
