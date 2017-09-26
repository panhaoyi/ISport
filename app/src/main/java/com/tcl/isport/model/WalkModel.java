package com.tcl.isport.model;

import android.content.Context;
import com.tcl.isport.imodel.ISportModel;


/**
 * Created by haoyi.pan on 17-9-18.
 */
public class WalkModel implements ISportModel {

    String weather = "";
    private Context mContext = null;
    private boolean isFlag = false;
    //Walk数据模型接口实现
    public WalkModel() {}

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
