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

    //获取最近五条历史运动记录到主页
    void showHomeHistoryData();

    //获取当天的所有次数的详细历史记录到我的模块中的历史图表里
    void findTodaySportData();

    //获取本周的所有历史运动数据
    void findWeekSportData();

    //获取本月所有的历史运动数据
    void findMonthSportData();
}
