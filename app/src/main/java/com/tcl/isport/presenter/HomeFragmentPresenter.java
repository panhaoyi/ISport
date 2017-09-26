package com.tcl.isport.presenter;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;

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
    private String weather = "";

    private  LocationUtil locationUtil;
    public HomeFragmentPresenter(IHomeFragment view, Context mContext){
        //构造器通过参数拿到view实例化view接口，根据view的类型初始化model
        this.iHomeFragment=view;
        if(view.getClass().getName().equals(HomeWalkFragment.class.getName())){
            iSportModel=new WalkModel(mContext);
            iHomeFragment.setWeather("获取中...");
            locationUtil = LocationUtil.getInstance(mContext, this);
            locationUtil.initLocatin();
        }
        else if(view.getClass().getName().equals(HomeRunFragment.class.getName())){
            iSportModel=new RunModel();
        }
        else if(view.getClass().getName().equals(HomeRideFragment.class.getName())){
            iSportModel=new RideModel();
        }
    }


    @Override
    public void setWeather(String weather) {
        iHomeFragment.setWeather(weather);
    }
}
