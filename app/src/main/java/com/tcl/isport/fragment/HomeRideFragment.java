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

import com.tcl.isport.iview.IHomeFragment;
import com.tcl.isport.presenter.HomeFragmentPresenter;
import com.tcl.isport.R;

/**
 * Created by user on 17-9-5.
 */
public class HomeRideFragment extends Fragment implements IHomeFragment {
    //首页骑行
    private View view;
    private HomeFragmentPresenter homeRideFragmentPresenter;
    private TextView mWeatherRideHome;
    private ImageView mWeatherIconRideHome;

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

        mWeatherIconRideHome.setImageResource(resId);
    }

    @Override
    public void setWeather(String weather) {

        if (weather != null && !weather.equals("")) {
            mWeatherRideHome.setText(weather);
        }
    }

    @Override
    public void setHistory() {

    }
}
