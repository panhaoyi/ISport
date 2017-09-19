package com.tcl.isport.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 17-9-14.
 */
public class FragmentAdapter extends FragmentPagerAdapter {
    //ViewPager的适配器
    List<Fragment> lf = new ArrayList<Fragment>();

    public FragmentAdapter(FragmentManager fm, List<Fragment> lf) {
        super(fm);
        this.lf = lf;
    }

    @Override
    public Fragment getItem(int position) {
        return lf.get(position);
    }

    @Override
    public int getCount() {
        return lf.size();
    }
}
