package com.tcl.isport.Fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tcl.isport.Adapter.FragmentAdapter;
import com.tcl.isport.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 17-9-4.
 */
public class HomeFragment extends Fragment implements View.OnClickListener,ViewPager.OnPageChangeListener {
    //主界面-首页
    private View view;
    private TextView item_walk, item_run, item_ride;
    private LinearLayout walk,run,ride;
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
        lf = new ArrayList<>();
        lf.add(walkFragment);
        lf.add(runFragment);
        lf.add(rideFragment);
        item_walk = (TextView) view.findViewById(R.id.item_walk);
        item_run = (TextView) view.findViewById(R.id.item_run);
        item_ride = (TextView) view.findViewById(R.id.item_ride);
        item_walk.setOnClickListener(this);
        item_run.setOnClickListener(this);
        item_ride.setOnClickListener(this);
        walk= (LinearLayout) view.findViewById(R.id.selected_walk_home);
        run= (LinearLayout) view.findViewById(R.id.selected_run_home);
        ride= (LinearLayout) view.findViewById(R.id.selected_ride_home);
        fragmentAdapter = new FragmentAdapter(getFragmentManager(), lf);
        viewPager.setOffscreenPageLimit(3);
        viewPager.setAdapter(fragmentAdapter);
        viewPager.setCurrentItem(0);
        viewPager.addOnPageChangeListener(this);
        return view;
    }

    public void changeColor(int position) {
        //标识选中fragment
        switch (position) {
            case 0:
                item_walk.setTextColor(Color.parseColor("#ffffff"));
                item_walk.setTextSize(16);
                walk.setVisibility(View.VISIBLE);
                item_run.setTextColor(Color.parseColor("#9b9b9b"));
                item_run.setTextSize(14);
                run.setVisibility(View.GONE);
                item_ride.setTextColor(Color.parseColor("#9b9b9b"));
                item_ride.setTextSize(14);
                ride.setVisibility(View.GONE);
                break;
            case 1:
                item_walk.setTextColor(Color.parseColor("#9b9b9b"));
                item_walk.setTextSize(14);
                walk.setVisibility(View.GONE);
                item_run.setTextColor(Color.parseColor("#ffffff"));
                item_run.setTextSize(16);
                run.setVisibility(View.VISIBLE);
                item_ride.setTextColor(Color.parseColor("#9b9b9b"));
                item_ride.setTextSize(14);
                ride.setVisibility(View.GONE);
                break;
            case 2:
                item_walk.setTextColor(Color.parseColor("#9b9b9b"));
                item_walk.setTextSize(14);
                walk.setVisibility(View.GONE);
                item_run.setTextColor(Color.parseColor("#9b9b9b"));
                item_run.setTextSize(14);
                run.setVisibility(View.GONE);
                item_ride.setTextColor(Color.parseColor("#ffffff"));
                item_ride.setTextSize(16);
                ride.setVisibility(View.VISIBLE);
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
}
