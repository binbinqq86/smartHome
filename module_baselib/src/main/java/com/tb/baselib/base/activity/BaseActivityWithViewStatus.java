package com.tb.baselib.base.activity;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tb.baselib.R;
import com.tb.baselib.widget.CustomProgressDialog;

/**
 * Created by : tb on 2017/9/19 下午5:10.
 * Description :Activity基类——带加载状态
 * rootView只能最多包含2个child:0-content,1-status
 */
public abstract class BaseActivityWithViewStatus extends BaseActivity {
    public static final int count = 2;

    /**
     * 显示加载空页面
     *
     * @param emptyView 传空则显示默认页面
     */
    protected void showLoadEmptyView(View emptyView) {
        if (rootView.getChildCount() == count) {
            rootView.removeViewAt(1);
        }
        rootView.getChildAt(0).setVisibility(View.GONE);
        if (emptyView == null) {
            emptyView = mLayoutInflater.inflate(R.layout.baselib_layout_loading_empty, rootView, false);
        }
        rootView.addView(emptyView, 1);
    }

    /**
     * 显示加载空页面
     *
     * @param imgId     空页面图片资源id
     * @param emptyText 空页面文案
     */
    protected void showLoadEmptyView(int imgId, String emptyText) {
        if (rootView.getChildCount() == count) {
            rootView.removeViewAt(1);
        }
        rootView.getChildAt(0).setVisibility(View.GONE);
        View emptyView = mLayoutInflater.inflate(R.layout.baselib_layout_loading_empty, rootView, false);
        if (imgId > 0) {
            try {
                ((ImageView) emptyView.findViewById(R.id.baselib_iv_loading_empty)).setImageResource(imgId);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (!TextUtils.isEmpty(emptyText)) {
            ((TextView) emptyView.findViewById(R.id.baselib_tv_loading_empty)).setText(emptyText);
        }
        rootView.addView(emptyView, 1);
    }

    /**
     * 显示加载失败页面
     *
     * @param errorView 传空则显示默认页面
     */
    protected void showLoadErrorView(View errorView) {
        if (rootView.getChildCount() == count) {
            rootView.removeViewAt(1);
        }
        rootView.getChildAt(0).setVisibility(View.GONE);
        if (errorView == null) {
            errorView = mLayoutInflater.inflate(R.layout.baselib_layout_loading_error, rootView, false);
        }
        rootView.addView(errorView, 1);
    }

    /**
     * 显示加载失败页面
     *
     * @param imgId           失败页面图片资源id
     * @param errText         失败页面文案
     * @param btText          按钮文案
     * @param btBg            按钮背景
     * @param onClickListener 按钮点击事件
     */
    protected void showLoadErrorView(int imgId, String errText, String btText, int btBg, View.OnClickListener onClickListener) {
        if (rootView.getChildCount() == count) {
            rootView.removeViewAt(1);
        }
        rootView.getChildAt(0).setVisibility(View.GONE);
        View errorView = mLayoutInflater.inflate(R.layout.baselib_layout_loading_error, rootView, false);

        if (imgId > 0) {
            try {
                ((ImageView) errorView.findViewById(R.id.baselib_iv_loading_error)).setImageResource(imgId);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (!TextUtils.isEmpty(errText)) {
            ((TextView) errorView.findViewById(R.id.baselib_tv_loading_error)).setText(errText);
        }
        if (btBg > 0) {
            try {
                ((Button) errorView.findViewById(R.id.baselib_bt_loading_retry)).setBackgroundResource(btBg);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (!TextUtils.isEmpty(btText)) {
            ((Button) errorView.findViewById(R.id.baselib_bt_loading_retry)).setText(btText);
        }
        if (onClickListener != null) {
            ((Button) errorView.findViewById(R.id.baselib_bt_loading_retry)).setOnClickListener(onClickListener);
        }
        rootView.addView(errorView, 1);
    }

    /**
     * 显示加载中页面
     *
     * @param loadingView 传空则显示默认页面
     */
    protected void showLoadingView(View loadingView) {
        if (rootView.getChildCount() == count) {
            rootView.removeViewAt(1);
        }
        rootView.getChildAt(0).setVisibility(View.GONE);
        if (loadingView == null) {
            loadingView = mLayoutInflater.inflate(R.layout.baselib_layout_loading, rootView, false);
        }
        rootView.addView(loadingView, 1);
    }

    /**
     * 显示加载中页面
     *
     * @param loadingText 加载文案，空则为默认值
     */
    protected void showLoadingView(String loadingText) {
        if (rootView.getChildCount() == count) {
            rootView.removeViewAt(1);
        }
        rootView.getChildAt(0).setVisibility(View.GONE);
        View loadingView = mLayoutInflater.inflate(R.layout.baselib_layout_loading, rootView, false);

        if (!TextUtils.isEmpty(loadingText)) {
            ((TextView) loadingView.findViewById(R.id.baselib_tv_loading)).setText(loadingText);
        }
        rootView.addView(loadingView, 1);
    }

    /**
     * 显示内容页面
     */
    protected void showContentView() {
        if (rootView.getChildCount() == count) {
            rootView.removeViewAt(1);
        }
        rootView.getChildAt(0).setVisibility(View.VISIBLE);
    }

    /**
     * 显示自定义view
     *
     * @param selfView
     */
    protected void showSelfView(View selfView) {
        if (selfView == null) {
            return;
        }
        if (rootView.getChildCount() == count) {
            rootView.removeViewAt(1);
        }
        rootView.getChildAt(0).setVisibility(View.GONE);
        rootView.addView(selfView, 1, new LinearLayout.LayoutParams(-1, -1));
    }

    /**
     * 显示自定义view
     *
     * @param selfView
     */
    protected void showSelfView(int selfView) {
        if (selfView <= 0) {
            return;
        }
        if (rootView.getChildCount() == count) {
            rootView.removeViewAt(1);
        }
        rootView.getChildAt(0).setVisibility(View.GONE);
        rootView.addView(mLayoutInflater.inflate(selfView, rootView, false), 1);
    }

    /**
     * 显示加载框
     */
    protected void showLoadingDialog() {
        CustomProgressDialog.show(mActivityContext, false, false, null);
    }

    /**
     * 显示加载框
     *
     * @param canCancel
     * @param canCancelOnTouchOutside
     * @param text                    不传递则显示默认"加载中..."字样
     */
    protected void showLoadingDialog(boolean canCancel, boolean canCancelOnTouchOutside, String text) {
        CustomProgressDialog.show(mActivityContext, canCancel, canCancelOnTouchOutside, text);
    }

    /**
     * 关闭加载框
     */
    protected void hideLoadingDialog() {
        if (!isFinishing()) {
            CustomProgressDialog.close();
        }
    }
}
