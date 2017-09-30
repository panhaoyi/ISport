package com.tcl.isport.presenter;


import android.content.Context;

import com.avos.avoscloud.AVObject;
import com.tcl.isport.R;
import com.tcl.isport.fragment.HomeRideFragment;
import com.tcl.isport.fragment.HomeRunFragment;
import com.tcl.isport.fragment.HomeWalkFragment;
import com.tcl.isport.imodel.ISportModel;
import com.tcl.isport.iview.IHomeFragment;
import com.tcl.isport.model.RideModel;
import com.tcl.isport.model.RunModel;
import com.tcl.isport.model.WalkModel;
import com.tcl.isport.util.SportUtil;
import com.tcl.isport.util.WeatherUtil;

import java.util.List;

/**
 * Created by haoyi.pan on 17-9-19.
 */
public class HomeFragmentPresenter implements WeatherUtil.IWeather, WalkModel.IWalkModeToHome,
        RunModel.IRunModeToHome, RideModel.IRideModeToHome{
    //主界面-首页HomeFragment中三个Fragment共用的业务逻辑处理Presenter
    private ISportModel iSportModel;
    private IHomeFragment iHomeFragment;

    private List<AVObject> sportDataList;

    public HomeFragmentPresenter(IHomeFragment view, Context mContext){
        //构造器通过参数拿到view实例化view接口，根据view的类型初始化model
        this.iHomeFragment=view;
        WeatherUtil weatherUtil = new WeatherUtil(mContext, this);
        if(view instanceof HomeWalkFragment){
            iSportModel=new WalkModel(this);
            iHomeFragment.setWeather("连接中");
//            WeatherUtil weatherUtil = new WeatherUtil(mContext, this);
            weatherUtil.initLocatin();
        } else if(view instanceof HomeRunFragment){
            iSportModel=new RunModel(this);
            iHomeFragment.setWeather("连接中");
//            WeatherUtil weatherUtil = new WeatherUtil(mContext, this);
            weatherUtil.initLocatin();

        } else if(view instanceof HomeRideFragment){
            iSportModel=new RideModel(this);
            iHomeFragment.setWeather("连接中");
//            WeatherUtil weatherUtil = new WeatherUtil(mContext, this);
            weatherUtil.initLocatin();

        }
    }

    @Override
    public void setWeather(String weather) {
        if (weather !=null && !weather.equals("")){
            iHomeFragment.setWeather(weather);
            //对weather进行类型判断,然后更换图标
            if (weather.contains("晴")) {
                iHomeFragment.setWeatherIcon(R.drawable.ic_sun);
            }
            if (weather.contains("云") || weather.contains("阴")) {
                iHomeFragment.setWeatherIcon(R.drawable.ic_cloud);
            }
            if (weather.contains("雨")) {
                iHomeFragment.setWeatherIcon(R.drawable.ic_rain);
            }
            if (weather.contains("雪")) {
                iHomeFragment.setWeatherIcon(R.drawable.ic_snow);
            }
            if (weather.contains("雾") || weather.contains("霾")) {
                iHomeFragment.setWeatherIcon(R.drawable.ic_fog);
            }
            if (weather.contains("风")) {
                iHomeFragment.setWeatherIcon(R.drawable.ic_typhon);
            }
        }


    }

    public void getHomeSportData() {
        iSportModel.findHomeSportData();
    }

    private void refreshHomeData() {
        if (SportUtil.getTotalDistance(sportDataList) != null){
            iHomeFragment.setDistance(SportUtil.getTotalDistance(sportDataList));
        }
        if (SportUtil.getTotalTime(sportDataList) != null) {
            iHomeFragment.setDuration(SportUtil.getTotalTime(sportDataList));
        }

    }

    @Override
    public void setWalkDataToHome(List<AVObject> lists) {
        sportDataList = lists;
    }

    @Override
    public void doInWalkToHome() {
        refreshHomeData();
    }

    @Override
    public void setRunDataToHome(List<AVObject> lists) {
        sportDataList = lists;
    }

    @Override
    public void doInRunToHome() {
        refreshHomeData();
    }

    @Override
    public void setRideDataToHome(List<AVObject> lists) {
        sportDataList = lists;
    }

    @Override
    public void doInRideToHome() {
        refreshHomeData();
    }
}
