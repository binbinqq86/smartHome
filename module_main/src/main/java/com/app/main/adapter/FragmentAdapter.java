package com.app.main.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.View;

import com.tb.baselib.util.LogUtils;

import java.util.List;

/**
 * @auther tb
 * @time 2018/1/17 下午2:30
 * @desc
 */
public class FragmentAdapter<T extends Fragment> extends FragmentPagerAdapter {
    private List<T> fragmentList;
    
    public FragmentAdapter(FragmentManager fm, List<T> fragmentList) {
        super(fm);
        this.fragmentList = fragmentList;
    }
    
    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }
    
    @Override
    public int getCount() {
        return fragmentList == null ? 0 : fragmentList.size();
    }
    
}
