package com.tcl.isport.Fragment;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tcl.isport.Bean.Constant;
import com.tcl.isport.IView.IHomeFragment;
import com.tcl.isport.R;
import com.tcl.isport.Util.StepService;

/**
 * Created by user on 17-9-4.
 */
public class HomeWalkFragment extends Fragment implements IHomeFragment {
    //首页健走
    private View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home_walk,container,false);

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
