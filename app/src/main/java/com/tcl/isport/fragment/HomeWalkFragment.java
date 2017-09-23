package com.tcl.isport.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home_walk,container,false);
        homeWalkFragmentPresenter=new HomeFragmentPresenter(this);
        return view;
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
    public void setWeatherIcon() {

    }

    @Override
    public void setWeather(String weather) {

    }

    @Override
    public void setHistory() {

    }
}