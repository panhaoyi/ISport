package com.tcl.isport.presenter;


import android.content.Context;

import com.tcl.isport.R;
import com.tcl.isport.fragment.HomeRideFragment;
import com.tcl.isport.fragment.HomeRunFragment;
import com.tcl.isport.fragment.HomeWalkFragment;
import com.tcl.isport.imodel.ISportModel;
import com.tcl.isport.iview.IHomeFragment;
import com.tcl.isport.model.RideModel;
import com.tcl.isport.model.RunModel;
import com.tcl.isport.model.WalkModel;
import com.tcl.isport.util.LocationUtil;
/**
 * Created by haoyi.pan on 17-9-19.
 */
public class HomeFragmentPresenter implements LocationUtil.IWeather{
    //主界面-首页HomeFragment中三个Fragment共用的业务逻辑处理Presenter
    private ISportModel iSportModel;
    private IHomeFragment iHomeFragment;

    private  LocationUtil locationUtil;

    public HomeFragmentPresenter(IHomeFragment view, Context mContext){
        //构造器通过参数拿到view实例化view接口，根据view的类型初始化model
        this.iHomeFragment=view;
        locationUtil = LocationUtil.getInstance(mContext, this);
        locationUtil.initLocatin();
        if(view instanceof HomeWalkFragment){
            iSportModel=new WalkModel();
            iHomeFragment.setWeather("连接中");
            locationUtil.initLocatin();
        }
        if(view instanceof HomeRunFragment){
            iSportModel=new RunModel();
            iHomeFragment.setWeather("连接中");

        }
        if(view instanceof HomeRideFragment){
            iSportModel=new RideModel();
            iHomeFragment.setWeather("连接中");

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
}
