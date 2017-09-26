package com.tcl.isport.imodel;

/**
 * Created by haoyi.pan on 17-9-18.
 */
public interface ISportModel {
    //Walk,Run,Ride三张表的数据模型接口

    //获取里程
    String getDistance();
    //获取用时
    String getDuration();

    /*
    * modify by lishui.lin on 17-9-23
    * */
    String getWeather();
}
