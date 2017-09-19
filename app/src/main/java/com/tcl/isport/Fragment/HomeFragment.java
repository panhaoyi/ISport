package com.tcl.isport.Fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tcl.isport.Adapter.FragmentAdapter;
import com.tcl.isport.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 17-9-4.
 */
public class HomeFragment extends Fragment implements View.OnClickListener {
    //主界面-首页
    private View view;
    private TextView item_walk, item_run, item_ride;
    private HomeWalkFragment walkFragment;
    private HomeRunFragment runFragment;
    private HomeRideFragment rideFragment;
    private List<Fragment> lf;
    private ViewPager viewPager;
    private FragmentAdapter fragmentAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, container, false);
        viewPager = (ViewPager) view.findViewById(R.id.viewpager_home);
        walkFragment = new HomeWalkFragment();
        runFragment = new HomeRunFragment();
        rideFragment = new HomeRideFragment();
        lf = new ArrayList<Fragment>();
        lf.add(walkFragment);
        lf.add(runFragment);
        lf.add(rideFragment);
        item_walk = (TextView) view.findViewById(R.id.item_walk);
        item_run = (TextView) view.findViewById(R.id.item_run);
        item_ride = (TextView) view.findViewById(R.id.item_ride);
        item_walk.setOnClickListener(this);
        item_run.setOnClickListener(this);
        item_ride.setOnClickListener(this);
        fragmentAdapter = new FragmentAdapter(getFragmentManager(), lf);
        viewPager.setOffscreenPageLimit(3);
        viewPager.setAdapter(fragmentAdapter);
        viewPager.setCurrentItem(0);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            //滑动切换fragment
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                changeColor(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        return view;
    }

    public void changeColor(int position) {
        //标识选中fragment
        switch (position) {
            case 0:
                item_walk.setTextColor(Color.parseColor("#000000"));
                item_walk.setBackgroundResource(R.drawable.border_bottom);
                item_run.setTextColor(Color.parseColor("#5d5d5d"));
                item_run.setBackground(null);
                item_ride.setTextColor(Color.parseColor("#5d5d5d"));
                item_ride.setBackground(null);
                break;
            case 1:
                item_walk.setTextColor(Color.parseColor("#5d5d5d"));
                item_walk.setBackground(null);
                item_run.setTextColor(Color.parseColor("#000000"));
                item_run.setBackgroundResource(R.drawable.border_bottom);
                item_ride.setTextColor(Color.parseColor("#5d5d5d"));
                item_ride.setBackground(null);
                break;
            case 2:
                item_walk.setTextColor(Color.parseColor("#5d5d5d"));
                item_walk.setBackground(null);
                item_run.setTextColor(Color.parseColor("#5d5d5d"));
                item_run.setBackground(null);
                item_ride.setTextColor(Color.parseColor("#000000"));
                item_ride.setBackgroundResource(R.drawable.border_bottom);
                break;
            default:
                break;
        }
    }

    @Override
    public void onClick(View v) {
        //点击切换fragment
        switch (v.getId()) {
            case R.id.item_walk:
                viewPager.setCurrentItem(0);
                changeColor(0);
                break;
            case R.id.item_run:
                viewPager.setCurrentItem(1);
                changeColor(1);
                break;
            case R.id.item_ride:
                viewPager.setCurrentItem(2);
                changeColor(2);
                break;
            default:
                break;
        }
    }
}
