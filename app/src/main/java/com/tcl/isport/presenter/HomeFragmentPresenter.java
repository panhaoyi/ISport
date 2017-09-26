package com.tcl.isport.presenter;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
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

import static com.tcl.isport.util.LocationUtil.WEATHER_RECEIVER;

/**
 * Created by haoyi.pan on 17-9-19.
 */
public class HomeFragmentPresenter {
    //主界面-首页HomeFragment中三个Fragment共用的业务逻辑处理Presenter
    private ISportModel iSportModel;
    private IHomeFragment iHomeFragment;
    private String weather = "";

    private  LocationUtil locationUtil;
    //IHomeFragment view
    public HomeFragmentPresenter(IHomeFragment view, Context mContext){
        //构造器通过参数拿到view实例化view接口，根据view的类型初始化model
        this.iHomeFragment=view;
        if(view.getClass().getName().equals(HomeWalkFragment.class.getName())){
            iSportModel=new WalkModel(mContext);
            locationUtil = new LocationUtil(mContext);
            locationUtil.initLocatin();
           // initWeather(mContext);
//            weather=locationUtil.getWeatherData();
//            iHomeFragment.setWeather(weather);
//            iHomeFragment.setWeather(WeatherUtil.getWeather());

        }
        else if(view.getClass().getName().equals(HomeRunFragment.class.getName())){
            iSportModel=new RunModel();
        }
        else if(view.getClass().getName().equals(HomeRideFragment.class.getName())){
            iSportModel=new RideModel();
        }
    }

    public void initWeather(Context  mContext){
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(WEATHER_RECEIVER);
        mContext.registerReceiver(myReceiver,intentFilter);
    }

    BroadcastReceiver myReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            weather = intent.getStringExtra("weather");
            Log.e(LocationUtil.ISPORT_TAG, weather);
            iHomeFragment.setWeather(weather);
        }
    };




    //设置倒计时
//    CountDownTimer countDownTimer = new CountDownTimer(10000,2000) {
//        @Override
//        public void onTick(long millisUntilFinished) {
//            iSportModel.getWeather();
//        }
//
//        @Override
//        public void onFinish() {
//            iHomeFragment.setWeather(iSportModel.getWeather());
//        }
//    };



}
