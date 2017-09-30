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
 * Created by user on 17-9-4.
 */
public class HomeWalkFragment extends Fragment implements IHomeFragment {
    //首页健走
    private View view;
    private HomeFragmentPresenter homeWalkFragmentPresenter;
    private TextView mWeatherWalkHome;
    private ImageView mWeatherIconWalkHome;
    private TextView distance_walk_home,duration_walk_home;

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

    }

    @Override
    public void setTimes(String times) {

    }

    @Override
    public void setSpeed(String speed) {

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
    public void setHistory() {

    }

    @Override
    public void onResume() {
        super.onResume();
        homeWalkFragmentPresenter.getHomeSportData();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

}
