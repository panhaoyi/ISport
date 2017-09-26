package com.tcl.isport.iview;

import android.graphics.drawable.Drawable;

/**
 * Created by haoyi.pan on 17-9-18.
 */
public interface IHomeFragment {
    //主界面-首页里3个fragment的view接口，封装对3个fragment的view的操作
    public void setDistance(String distance);
    public void setDuration(String duration);
    public void setStep(String step);
    public void setTimes(String times);
    public void setSpeed(String speed);
    public void setWeatherIcon(int resId);
    public void setWeather(String weather);
    public void setHistory();
}
