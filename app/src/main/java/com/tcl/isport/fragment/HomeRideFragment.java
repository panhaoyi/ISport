package com.tcl.isport.fragment;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.tcl.isport.iview.IHomeFragment;
import com.tcl.isport.presenter.HomeFragmentPresenter;
import com.tcl.isport.R;

import java.util.List;

/**
 * Created by user on 17-9-5.
 */
public class HomeRideFragment extends Fragment implements IHomeFragment {
    //首页骑行
    private View view;
    private HomeFragmentPresenter homeRideFragmentPresenter;
    private TextView mWeatherRideHome;
    private ImageView mWeatherIconRideHome;
    private TextView distance_ride_home,duration_ride_home, timescount_ride_home, speed_ride_home;
    private LineChart home_ride_history_chart;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home_ride, container, false);

        initView();
        homeRideFragmentPresenter=new HomeFragmentPresenter(this, this.getActivity());
        return view;
    }

    private void initView() {
        mWeatherRideHome = (TextView) view.findViewById(R.id.weather_ride_home);
        mWeatherIconRideHome = (ImageView) view.findViewById(R.id.weather_icon_ride_home);

        distance_ride_home = (TextView) view.findViewById(R.id.distance_ride_home);
        duration_ride_home = (TextView) view.findViewById(R.id.duration_ride_home);
        timescount_ride_home = (TextView) view.findViewById(R.id.timescount_ride_home);
        speed_ride_home = (TextView) view.findViewById(R.id.speed_ride_home);
        home_ride_history_chart = (LineChart) view.findViewById(R.id.home_ride_history_chart);
    }

    @Override
    public void setDistance(String distance) {
        distance_ride_home.setText(distance);
    }

    @Override
    public void setDuration(String duration) {
        duration_ride_home.setText(duration);
    }

    @Override
    public void setStep(String step) {

    }

    @Override
    public void setTimes(String times) {
        timescount_ride_home.setText(times);
    }

    @Override
    public void setSpeed(String speed) {
        speed_ride_home.setText(speed);
    }

    @Override
    public void setWeatherIcon(int resId) {

        mWeatherIconRideHome.setImageResource(resId);
    }

    @Override
    public void setWeather(String weather) {

        if (weather != null && !weather.equals("")) {
            mWeatherRideHome.setText(weather);
        }
    }

    @Override
    public void setHistory(int type) {
        homeRideFragmentPresenter.initHistoryChart(home_ride_history_chart, 3);
    }

    @Override
    public void onResume() {
        super.onResume();
        homeRideFragmentPresenter.getHomeSportData();
        homeRideFragmentPresenter.getHomeHistoryData();
    }
}
