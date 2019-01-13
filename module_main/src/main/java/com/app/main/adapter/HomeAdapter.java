package com.app.main.adapter;

import android.support.constraint.ConstraintLayout;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;


import com.app.main.R;
import com.app.main.constant.HomeType;
import com.app.main.webapi.bean.HomeBean;
import com.app.main.webapi.bean.HomeSwitchBean;
import com.app.main.widget.VerticalSeekBar;
import com.tb.baselib.adapter.CommonLvAdapter;
import com.tb.baselib.adapter.CommonLvViewHolder;
import com.tb.baselib.adapter.CommonRvAdapter;
import com.tb.baselib.adapter.CommonRvViewHolder;
import com.tb.baselib.listener.NoDoubleClickListener;
import com.tb.baselib.util.CommonUtils;
import com.tb.baselib.widget.ListViewInScrollView;
import com.tb.baselib.widget.ToastUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by TianBin on 2018/2/9 9:09.
 * Description :
 */

public class HomeAdapter extends CommonRvAdapter<HomeBean> {
    
    public interface OnClick {
        void onClick(View view);
    }
    
    private OnClick onClick;
    
    public void setOnClick(OnClick onClick) {
        this.onClick = onClick;
    }
    
    private NoDoubleClickListener noDoubleClickListener = new NoDoubleClickListener() {
        @Override
        public void onNoDoubleClick(View view) {
            if (onClick != null) {
                onClick.onClick(view);
            }
        }
    };
    private MultiTypeSupport<HomeBean> mMultiTypeSupport = new MultiTypeSupport<HomeBean>() {
        @Override
        public int getLayoutId(int itemType) {
            int layoutId = R.layout.main_empty;
            switch (itemType) {
                case HomeType.HOME_TYPE_WEATHER:
                    layoutId = R.layout.main_layout_home_weather;
                    break;
                case HomeType.HOME_TYPE_MSG:
                    layoutId = R.layout.main_layout_home_msg;
                    break;
                case HomeType.HOME_TYPE_SWITCH:
                    layoutId = R.layout.main_layout_home_switch;
                    break;
                case HomeType.HOME_TYPE_BED:
                    layoutId = R.layout.main_layout_home_bed;
                    break;
            }
            return layoutId;
        }
        
        @Override
        public int getItemViewType(int position, HomeBean homeBean) {
            int type = -1;
            switch (position) {
                case 0:
                    type = HomeType.HOME_TYPE_WEATHER;
                    break;
                case 1:
                    type = HomeType.HOME_TYPE_MSG;
                    break;
                case 2:
                    type = HomeType.HOME_TYPE_SWITCH;
                    break;
                case 3:
                    type = HomeType.HOME_TYPE_BED;
                    break;
            }
            return type;
        }
    };
    
    public HomeAdapter(List<HomeBean> data) {
        super(-1, data);
        setmMultiTypeSupport(mMultiTypeSupport);
    }
    
    @Override
    public void convert(CommonRvViewHolder holder, HomeBean homeBean) {
        if (holder.itemType == 0) {
            return;
        }
        int viewType = holder.itemType;
        if (HomeType.HOME_TYPE_WEATHER == viewType) {
            dealWeather(holder);
        } else if (HomeType.HOME_TYPE_MSG == viewType) {
            dealMsg(holder);
        } else if (HomeType.HOME_TYPE_SWITCH == viewType) {
            dealSwitch(holder);
        } else if (HomeType.HOME_TYPE_BED == viewType) {
            dealBed(holder);
        } else {
        }
    }
    
    private void dealBed(CommonRvViewHolder holder) {
        final ImageView ivBed = holder.getView(R.id.iv_bed);
        VerticalSeekBar vsb = holder.getView(R.id.sb);
        vsb.setOnBarChangeListener(new VerticalSeekBar.OnBarChangeListener() {
            @Override
            public void onProgressChanged(VerticalSeekBar seekBar, int progress) {
                setBed(seekBar, ivBed, false);
            }
            
            @Override
            public void onStartTrackingTouch(VerticalSeekBar seekBar) {
            
            }
            
            @Override
            public void onStopTrackingTouch(VerticalSeekBar seekBar) {
                setBed(seekBar, ivBed, true);
                
            }
        });
    }
    
    /**
     *
     * @param seekBar
     * @param ivBed
     * @param isFinish 是否拖动完成，完成后才请求服务器
     */
    private void setBed(VerticalSeekBar seekBar, ImageView ivBed, boolean isFinish) {
        //dp147
        int total = ivBed.getContext().getResources().getDimensionPixelOffset(R.dimen.baselib_dp147);
        FrameLayout.LayoutParams flp = (FrameLayout.LayoutParams) ivBed.getLayoutParams();
        flp.bottomMargin = total * seekBar.getProgress() / seekBar.getMax();
        ivBed.setLayoutParams(flp);
    }
    
    private void dealSwitch(CommonRvViewHolder holder) {
        ListViewInScrollView lv = holder.getView(R.id.lv);
        //防止切换底部tab的时候抢占焦点
        lv.setFocusable(false);
        lv.setFocusableInTouchMode(false);
        List<HomeSwitchBean> list = new ArrayList<>();
        HomeSwitchBean hsb1 = new HomeSwitchBean();
        hsb1.imgResId = R.drawable.main_ic_light;
        hsb1.title = "走廊吊灯";
        hsb1.subTitle = "18:00-22:00开启";
        hsb1.isChecked = false;
        
        HomeSwitchBean hsb2 = new HomeSwitchBean();
        hsb2.imgResId = R.drawable.main_ic_curtain;
        hsb2.title = "客厅窗帘";
        hsb2.subTitle = "6:00-18:00开启";
        hsb2.isChecked = true;
        
        HomeSwitchBean hsb3 = new HomeSwitchBean();
        hsb3.imgResId = R.drawable.main_ic_led;
        hsb3.title = "客厅灯带";
        hsb3.subTitle = "6:00-18:00开启";
        hsb3.isChecked = false;
        list.add(hsb1);
        list.add(hsb2);
        list.add(hsb3);
        lv.setAdapter(new CommonLvAdapter<HomeSwitchBean>(list, R.layout.main_item_layout_home_switch) {
            @Override
            public void convert(CommonLvViewHolder holder, final HomeSwitchBean item) {
                ImageView iv = holder.getView(R.id.iv);
                TextView tvTitle = holder.getView(R.id.tv_title);
                TextView tvSubTitle = holder.getView(R.id.tv_sub_title);
                CheckBox cb = holder.getView(R.id.cb_right);
                iv.setImageResource(item.imgResId);
                tvTitle.setText(item.title);
                tvSubTitle.setText(item.subTitle);
                //TODO 此处listview不能滚动，所以暂不考虑错位复用的问题
                cb.setChecked(item.isChecked);
                cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (isChecked) {
                            ToastUtils.showBottom("===checked===" + item.title);
                        } else {
                            ToastUtils.showBottom("===unChecked===" + item.title);
                        }
                    }
                });
            }
        });
        lv.setListViewHeightBasedOnChildren(list.size());
    }
    
    private void dealMsg(CommonRvViewHolder holder) {
        View view = holder.getView(R.id.cl);
        view.setOnClickListener(noDoubleClickListener);
    }
    
    private void dealWeather(CommonRvViewHolder holder) {
        final View view = holder.getView(R.id.view);
        view.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                //4.4以下暂不考虑状态栏问题
                ConstraintLayout.LayoutParams clp = (ConstraintLayout.LayoutParams) view.getLayoutParams();
                clp.height = CommonUtils.getStatusBarHeight() + (int) CommonUtils.dp2Px(56);
                view.setLayoutParams(clp);
                view.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });
    }
}
