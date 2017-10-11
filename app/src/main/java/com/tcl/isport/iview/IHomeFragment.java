package com.tcl.isport.iview;

import android.graphics.drawable.Drawable;

import com.github.mikephil.charting.data.Entry;

import java.util.List;

/**
 * Created by haoyi.pan on 17-9-18.
 */
public interface IHomeFragment {
    //主界面-首页里3个fragment的view接口，封装对3个fragment的view的操作
    void setDistance(String distance);
    void setDuration(String duration);
    void setStep(String step);
    void setTimes(String times);
    void setSpeed(String speed);
    void setWeatherIcon(int resId);
    void setWeather(String weather);
    void setHistory(int type);
}
