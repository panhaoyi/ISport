package com.tcl.isport.imodel;

import android.content.Context;

import com.avos.avoscloud.AVObject;
import com.tcl.isport.bean.SportBean;

import java.util.List;

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

    /*leanCloud增删改查操作，使用SportBean类传递数据*/
    //增加一条运动数据操作
    void saveSportData(Context mContext, SportBean sportBean);

    void findSportData();

    //获取服务器数据到主页，
    void findHomeSportData();
}
