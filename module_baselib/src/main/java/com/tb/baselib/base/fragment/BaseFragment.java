package com.tb.baselib.base.fragment;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.tb.baselib.listener.NoDoubleClickListener;
import com.tb.baselib.mvp.model.IBaseModel;
import com.tb.baselib.mvp.presenter.BasePresenterImpl;
import com.tb.baselib.mvp.view.IBaseView;
import com.tb.baselib.net.impl.OKHttpRequester;

/**
 * Created by : tb on 2017/9/20 下午4:33.
 * Description :fragment基类
 */
public abstract class BaseFragment extends Fragment implements IBaseView {
    protected BasePresenterImpl mBasePresenter;
    private IBaseModel iBaseModel;
    protected Context mContext;
    protected Context mApplicationContext;

    /**
     * 真实的Fragment布局
     */
    protected View contentView;
    /**
     * 真实的Fragment布局容器
     */
    protected LinearLayout rootView;
    protected NoDoubleClickListener noDoubleClickListener;
    protected LayoutInflater mLayoutInflater;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
        mApplicationContext = context.getApplicationContext();
        mLayoutInflater=LayoutInflater.from(context);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initVariables();
        initNoDoubleClickListener();
        iBaseModel = getIBaseModel();
        mBasePresenter = new BasePresenterImpl(this, iBaseModel);
        rootView=new LinearLayout(getContext());
        rootView.setLayoutParams(new LinearLayout.LayoutParams(-1,-1));
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        int contentLayoutID = getContentLayoutID();
        if (contentLayoutID > 0) {
            try {
                this.contentView = View.inflate(mContext, contentLayoutID, null);
//                this.contentView = LayoutInflater.from(this).inflate(this.contentLayoutID, rootView,false);
                if (contentView == null) {
//                    throw new NullPointerException("contentView must not be null,please invoke getContentLayoutID() first...");
                } else {
                    rootView.addView(contentView, 0, new LinearLayout.LayoutParams(-1, -1));
                }
            } catch (Resources.NotFoundException e) {
                e.printStackTrace();
            } catch (InflateException e) {
                e.printStackTrace();
            }
        }
        initViews(contentView, savedInstanceState);
        initListeners();
        loadData();
        return rootView;
    }

    private void initNoDoubleClickListener() {
        noDoubleClickListener = new NoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View view) {
                BaseFragment.this.onNoDoubleClick(view);
            }
        };
    }

    /**
     * 返回Fragment布局文件
     *
     * @return
     */
    protected abstract int getContentLayoutID();

    /**
     * 初始化变量，包括Intent带的数据和Activity内的变量
     */
    protected void initVariables() {
    }

    /**
     * 加载layout布局文件，初始化控件
     *
     * @param contentView
     * @param savedInstanceState
     */
    protected abstract void initViews(View contentView, Bundle savedInstanceState);

    /**
     * 为所有控件加上事件方法
     */
    protected void initListeners() {
    }

    /**
     * 向服务器请求具体数据
     */
    protected void loadData() {
    }


    protected void onNoDoubleClick(View v) {
    }

    /**
     * 设置网络请求框架，默认为okHttp3
     */
    protected IBaseModel getIBaseModel() {
        return OKHttpRequester.getInstance();
    }

    @Override
    public void onSuccess(int responseCode, int requestCode, Object response) {

    }

    @Override
    public void onFailure(int responseCode, int requestCode, String errMsg) {

    }

    @Override
    public void showLoadingView() {

    }

    @Override
    public void onDestroy() {
        if (mBasePresenter != null) {
            mBasePresenter.detachView();
            mBasePresenter = null;
        }
        super.onDestroy();
    }

    protected <T extends View> T findViewByID(View view, @IdRes int id) {
        return (T) view.findViewById(id);
    }

    protected <T extends View> T findViewByID(@IdRes int id) {
        return (T) contentView.findViewById(id);
    }
}
