package com.tcl.isport.model;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.CountDownTimer;
import android.util.Log;

import com.tcl.isport.bean.MessageEvent;
import com.tcl.isport.imodel.ISportModel;
import com.tcl.isport.util.LocationUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Created by haoyi.pan on 17-9-18.
 */
public class WalkModel implements ISportModel {

    String weather = "";
    private Context mContext = null;
    private boolean isFlag = false;
    //Walk数据模型接口实现
    public WalkModel() {}
    //通过presenter传递context
    public WalkModel(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public String getDistance() {
        //从服务器获取数据
        return "0.00";
    }

    @Override
    public String getDuration() {
        return "0 h";
    }


    /*
    * modify by lishui.lin on 17-9-23
    * */
    @Override
    public String getWeather() {

        return weather;
    }

}
