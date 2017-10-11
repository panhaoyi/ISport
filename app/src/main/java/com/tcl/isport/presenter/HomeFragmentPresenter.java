package com.tcl.isport.presenter;


import android.content.Context;
import android.graphics.Color;
import android.util.Log;

import com.avos.avoscloud.AVObject;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
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
import com.tcl.isport.util.SportUtil;
import com.tcl.isport.util.WeatherUtil;

import java.text.DecimalFormat;
import java.util.ArrayList;
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

    //图表初始化
    public void initHistoryChart(LineChart homeHistory, int type) {
        List<Entry> entries = new ArrayList<>();
        int length = sportDataList.size();
        for (int i = 0; i < length; i++) {
            entries.add(new Entry(i +1 , Float.valueOf((String) sportDataList.get(length - 1 - i).get("distance"))));
        }

        //禁用所有图表可能的触摸交互
        homeHistory.setTouchEnabled(false);
        XAxis xAxis = homeHistory.getXAxis();
        xAxis.setDrawGridLines(false);
//        xAxis.setDrawLabels(false);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setLabelCount(5);
        xAxis.setTextColor(Color.WHITE);
        xAxis.setEnabled(false);

        YAxis leftYAxis = homeHistory.getAxisLeft();
        leftYAxis.setTextColor(Color.WHITE);
//        leftYAxis.setDrawLabels(false);
        leftYAxis.setEnabled(false);

        YAxis rightYAxis = homeHistory.getAxisRight();
        rightYAxis.setEnabled(false);

        if (!entries.isEmpty()) {
            //数据源
            LineDataSet dataSet = new LineDataSet(entries, "walk history");
            dataSet.setDrawIcons(false);
            dataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);
            dataSet.setDrawFilled(true);

            if (type == 1) {
                dataSet.setFillAlpha(58);
                dataSet.setFillColor(Color.rgb(171, 117, 243));  //设置fill区域的颜色
                dataSet.setColor(Color.rgb(232, 190, 68)); //设置数据线的颜色
            }
            if (type == 2) {
                dataSet.setFillAlpha(58);
                dataSet.setFillColor(Color.rgb(126, 140, 234));  //设置fill区域的颜色
                dataSet.setColor(Color.rgb(74, 234, 252)); //设置数据线的颜色
            }
            if (type == 3) {
                dataSet.setFillAlpha(58);
                dataSet.setFillColor(Color.rgb(120, 185, 239));  //设置fill区域的颜色
                dataSet.setColor(Color.rgb(110, 223, 102)); //设置数据线的颜色
            }

            //数据模型
            LineData lineData = new LineData(dataSet);
            lineData.setValueTextColor(Color.WHITE);
            lineData.setValueTextSize(10f);

            //禁用legend
            Legend legend = homeHistory.getLegend();
            legend.setEnabled(false);

            homeHistory.setData(lineData);
            homeHistory.animateY(2500);
            homeHistory.getDescription().setEnabled(false);
            homeHistory.invalidate();
        } else {
            homeHistory.setNoDataText("无有效运动数据或网络连接异常");
        }

    }

    public void getHomeSportData() {
        iSportModel.findHomeSportData();
    }

    //更新主页模块的骑行信息
    private void refreshHomeRideData() {
        if (SportUtil.getTotalDistance(sportDataList) != null){
            iHomeFragment.setDistance(SportUtil.getTotalDistance(sportDataList));
        }
        if (SportUtil.getTotalTime(sportDataList) != null) {
            iHomeFragment.setDuration(SportUtil.getTotalTime(sportDataList));
        }

        iHomeFragment.setTimes(SportUtil.getTotalTimes(sportDataList));
        iHomeFragment.setSpeed(SportUtil.getAverageSpeed(sportDataList));

    }
    //更新主页模块的健走和跑步信息
    private void refreshHomeWalkRunData() {
        if (SportUtil.getTotalDistance(sportDataList) != null){
            iHomeFragment.setDistance(SportUtil.getTotalDistance(sportDataList));
        }
        if (SportUtil.getTotalTime(sportDataList) != null) {
            iHomeFragment.setDuration(SportUtil.getTotalTime(sportDataList));
        }
        iHomeFragment.setStep(SportUtil.getTotalStep(sportDataList));
        iHomeFragment.setTimes(SportUtil.getTotalTimes(sportDataList));
        iHomeFragment.setSpeed(SportUtil.getAverageSpeed(sportDataList));

    }

    //更新历史记录
    private void refreshHomeHistoryData(int type) {
        iHomeFragment.setHistory(type);
    }
    
    public void getHomeHistoryData() {
        iSportModel.showHomeHistoryData();
    }

    @Override
    public void setWalkDataToHome(List<AVObject> lists) {
        sportDataList = lists;
    }

    @Override
    public void doInWalkToHome() {
        refreshHomeWalkRunData();
    }

    @Override
    public void doInWalkToHomeHistory() {
        refreshHomeHistoryData(1);
    }

    @Override
    public void setRunDataToHome(List<AVObject> lists) {
        sportDataList = lists;
    }

    @Override
    public void doInRunToHome() {
        refreshHomeWalkRunData();
    }

    @Override
    public void doInRunToHomeHistory() {
        refreshHomeHistoryData(2);
    }

    @Override
    public void setRideDataToHome(List<AVObject> lists) {
        sportDataList = lists;
    }

    @Override
    public void doInRideToHome() {
        refreshHomeRideData();
    }

    @Override
    public void doInRideToHomeHistory() {
        refreshHomeHistoryData(3);
    }
}
