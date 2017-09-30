package com.tcl.isport.fragment;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.tcl.isport.iview.IHomeFragment;
import com.tcl.isport.presenter.HomeFragmentPresenter;
import com.tcl.isport.R;
import com.tcl.isport.util.LocationUtil;

/**
 * Created by user on 17-9-5.
 */
public class HomeRunFragment extends Fragment implements IHomeFragment {
    //首页跑步
    private View view;
    private HomeFragmentPresenter homeRunFragmentPresenter;
    private TextView mWeatherRunHome;
    private ImageView mWeatherIconRunHome;
    private TextView distance_run_home, duration_run_home, step_run_home, timescount_run_home, speed_run_home;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home_run, container, false);
        initView();
        homeRunFragmentPresenter = new HomeFragmentPresenter(this, this.getActivity());
        return view;
    }

    private void initView() {
        mWeatherRunHome = (TextView) view.findViewById(R.id.weather_run_home);
        mWeatherIconRunHome = (ImageView) view.findViewById(R.id.weather_icon_run_home);

        distance_run_home = (TextView) view.findViewById(R.id.distance_run_home);
        duration_run_home = (TextView) view.findViewById(R.id.duration_run_home);
        step_run_home = (TextView) view.findViewById(R.id.step_run_home);
        timescount_run_home = (TextView) view.findViewById(R.id.timescount_run_home);
        speed_run_home = (TextView) view.findViewById(R.id.speed_run_home);
    }

    @Override
    public void setDistance(String distance) {
        distance_run_home.setText(distance);
    }

    @Override
    public void setDuration(String duration) {
        duration_run_home.setText(duration);
    }

    @Override
    public void setStep(String step) {
        step_run_home.setText(step);
    }

    @Override
    public void setTimes(String times) {
        timescount_run_home.setText(times);
    }

    @Override
    public void setSpeed(String speed) {
        speed_run_home.setText(speed);
    }

    @Override
    public void setWeatherIcon(int resId) {

        mWeatherIconRunHome.setImageResource(resId);
    }

    @Override
    public void setWeather(String weather) {

        if (weather != null && !weather.equals("")) {
            mWeatherRunHome.setText(weather);
        }
    }

    @Override
    public void setHistory() {

    }

    @Override
    public void onResume() {
        super.onResume();
        homeRunFragmentPresenter.getHomeSportData();
    }
}
