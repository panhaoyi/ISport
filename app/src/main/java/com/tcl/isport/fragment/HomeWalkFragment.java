package com.tcl.isport.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.tcl.isport.iview.IHomeFragment;
import com.tcl.isport.presenter.HomeFragmentPresenter;
import com.tcl.isport.R;

/**
 * Created by user on 17-9-4.
 */
public class HomeWalkFragment extends Fragment implements IHomeFragment {
    //首页健走
    private View view;
    private HomeFragmentPresenter homeWalkFragmentPresenter;
    private TextView mWeatherWalkHome;
    private ImageView mWeatherIconWalkHome;
    private TextView distance_walk_home,duration_walk_home, step_walk_home, timescount_walk_home, speed_walk_home;
    //历史记录图表声明
    private LineChart home_walk_history_chart;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home_walk,container,false);
        //注意调用先后顺序，防止产生空指针
        initView();
        homeWalkFragmentPresenter=new HomeFragmentPresenter(this, this.getActivity());
        return view;
    }

    private void initView() {
        mWeatherWalkHome = (TextView) view.findViewById(R.id.weather_walk_home);
        mWeatherIconWalkHome = (ImageView) view.findViewById(R.id.weather_icon_walk_home);

        distance_walk_home = (TextView) view.findViewById(R.id.distance_walk_home);
        duration_walk_home = (TextView) view.findViewById(R.id.duration_walk_home);
        step_walk_home = (TextView) view.findViewById(R.id.step_walk_home);
        timescount_walk_home = (TextView) view.findViewById(R.id.timescount_walk_home);
        speed_walk_home = (TextView) view.findViewById(R.id.speed_walk_home);
        home_walk_history_chart = (LineChart) view.findViewById(R.id.home_walk_history_chart);
    }

    @Override
    public void setDistance(String distance) {
        distance_walk_home.setText(distance);
    }

    @Override
    public void setDuration(String duration) {
        duration_walk_home.setText(duration);
    }

    @Override
    public void setStep(String step) {
        step_walk_home.setText(step);
    }

    @Override
    public void setTimes(String times) {
        timescount_walk_home.setText(times);
    }

    @Override
    public void setSpeed(String speed) {
        speed_walk_home.setText(speed);
    }

    @Override
    public void setWeatherIcon(int resId) {
        mWeatherIconWalkHome.setImageResource(resId);

    }

    @Override
    public void setWeather(String weather) {
        if (weather != null && !weather.equals("")) {
            mWeatherWalkHome.setText(weather);
        }

    }

    @Override
    public void setHistory(int type) {
        homeWalkFragmentPresenter.initHistoryChart(home_walk_history_chart, 1);
    }

    @Override
    public void onResume() {
        super.onResume();
        homeWalkFragmentPresenter.getHomeSportData();
        homeWalkFragmentPresenter.getHomeHistoryData();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }

}
