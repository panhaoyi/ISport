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

import com.tcl.isport.Activity.MainActivity;
import com.tcl.isport.Adapter.FragmentAdapter;
import com.tcl.isport.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 17-9-4.
 */
public class SportFragment extends Fragment implements View.OnClickListener,ViewPager.OnPageChangeListener {
    //主界面-运动
    private View view;
    private TextView item_walk_sport, item_run_sport, item_ride_sport;
    private LinearLayout walk,run,ride;
    private SportWalkFragment walkFragment;
    private SportRunFragment runFragment;
    private SportRideFragment rideFragment;
    private List<Fragment> lf;
    private ViewPager viewPager;
    private FragmentAdapter fragmentAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_sport, container, false);
        viewPager = (ViewPager) view.findViewById(R.id.viewpager_sport);
        walkFragment = new SportWalkFragment();
        runFragment = new SportRunFragment();
        rideFragment = new SportRideFragment();
        lf = new ArrayList<>();
        lf.add(walkFragment);
        lf.add(runFragment);
        lf.add(rideFragment);
        item_walk_sport = (TextView) view.findViewById(R.id.item_walk_sport);
        item_run_sport = (TextView) view.findViewById(R.id.item_run_sport);
        item_ride_sport = (TextView) view.findViewById(R.id.item_ride_sport);
        item_walk_sport.setOnClickListener(this);
        item_run_sport.setOnClickListener(this);
        item_ride_sport.setOnClickListener(this);
        walk= (LinearLayout) view.findViewById(R.id.selected_walk_sport);
        run= (LinearLayout) view.findViewById(R.id.selected_run_sport);
        ride= (LinearLayout) view.findViewById(R.id.selected_ride_sport);
        fragmentAdapter = new FragmentAdapter(getFragmentManager(), lf);
        viewPager.setOffscreenPageLimit(3);
        viewPager.setAdapter(fragmentAdapter);
        viewPager.setCurrentItem(0);
        viewPager.addOnPageChangeListener(this);
        return view;
    }

    public void changeColor(int position) {
        //标识当前选中的fragment
        switch (position) {
            case 0:
                item_walk_sport.setTextColor(Color.parseColor("#ffffff"));
                item_walk_sport.setTextSize(16);
                walk.setVisibility(View.VISIBLE);
                item_run_sport.setTextColor(Color.parseColor("#9b9b9b"));
                item_run_sport.setTextSize(14);
                run.setVisibility(View.GONE);
                item_ride_sport.setTextColor(Color.parseColor("#9b9b9b"));
                item_ride_sport.setTextSize(14);
                ride.setVisibility(View.GONE);
                break;
            case 1:
                item_walk_sport.setTextColor(Color.parseColor("#9b9b9b"));
                item_walk_sport.setTextSize(14);
                walk.setVisibility(View.GONE);
                item_run_sport.setTextColor(Color.parseColor("#ffffff"));
                item_run_sport.setTextSize(16);
                run.setVisibility(View.VISIBLE);
                item_ride_sport.setTextColor(Color.parseColor("#9b9b9b"));
                item_ride_sport.setTextSize(14);
                ride.setVisibility(View.GONE);
                break;
            case 2:
                item_walk_sport.setTextColor(Color.parseColor("#9b9b9b"));
                item_walk_sport.setTextSize(14);
                walk.setVisibility(View.GONE);
                item_run_sport.setTextColor(Color.parseColor("#9b9b9b"));
                item_run_sport.setTextSize(14);
                run.setVisibility(View.GONE);
                item_ride_sport.setTextColor(Color.parseColor("#ffffff"));
                item_ride_sport.setTextSize(16);
                ride.setVisibility(View.VISIBLE);
            default:
                break;
        }
    }

    @Override
    public void onClick(View v) {
        //点击切换fragment
        switch (v.getId()) {
            case R.id.item_walk_sport:
                viewPager.setCurrentItem(0);
                changeColor(0);
                break;
            case R.id.item_run_sport:
                viewPager.setCurrentItem(1);
                changeColor(1);
                break;
            case R.id.item_ride_sport:
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
