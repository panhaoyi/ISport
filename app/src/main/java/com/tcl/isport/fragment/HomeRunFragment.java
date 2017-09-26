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
    private HomeFragmentPresenter homeWalkFragmentPresenter;
    private TextView mWeatherRunHome;
    private ImageView mWeatherIconRunHome;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home_run, container, false);
        initView();
        homeWalkFragmentPresenter=new HomeFragmentPresenter(this, this.getActivity());
        return view;
    }

    private void initView() {
        mWeatherRunHome = (TextView) view.findViewById(R.id.weather_run_home);
        mWeatherIconRunHome = (ImageView) view.findViewById(R.id.weather_icon_run_home);
    }

    @Override
    public void setDistance(String distance) {

    }

    @Override
    public void setDuration(String duration) {

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
}
