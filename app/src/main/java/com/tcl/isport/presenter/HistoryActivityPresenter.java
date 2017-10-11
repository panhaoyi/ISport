package com.tcl.isport.presenter;

import android.graphics.Color;

import com.avos.avoscloud.AVObject;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.tcl.isport.imodel.ISportModel;
import com.tcl.isport.iview.IHistoryActivity;
import com.tcl.isport.model.RideModel;
import com.tcl.isport.model.RunModel;
import com.tcl.isport.model.WalkModel;
import com.tcl.isport.util.SportUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lishui.lin on 17-10-10 09:51
 */

public class HistoryActivityPresenter implements WalkModel.IWalkModel {

    private ISportModel iSportModel;
    private IHistoryActivity iHistoryActivity;
    //历史记录
    private List<AVObject> historyDataList;
    //表格数据
    List<Entry> entries = new ArrayList<>();
    private LineChart mineHistory;
    private int dateType;
    private int sportType;

    //type:   1 = 健走； 2 = 跑步； 3 = 骑行
    public HistoryActivityPresenter(IHistoryActivity iHistoryActivity, int sportType) {
        this.iHistoryActivity = iHistoryActivity;
        this.sportType = sportType;
        //构造器通过参数拿到view实例化view接口，根据view的类型初始化model
        if(sportType == 1){
            iSportModel=new WalkModel(this);
        }
        else if(sportType == 2){
            iSportModel=new RunModel(this);
        }
        else if(sportType == 3){
            iSportModel=new RideModel(this);
        }
    }

    public void getMineHistoryChart(LineChart mineHistory, int dateType) {
        //获取数据,dateTyep = 1为日，dateTyep = 2为周，dateTyep = 3为月
        this.dateType = dateType;
        this.mineHistory = mineHistory;
        if (dateType == 1) {
            iSportModel.findTodaySportData();
        }
        if (dateType == 2) {
            iSportModel.findWeekSportData();
        }
        if (dateType == 3) {
            iSportModel.findMonthSportData();
        }

    }
    
    private void initHistoryChart(LineChart mineHistory) {
        //清空上一次的图表
        mineHistory.clear();

        if (entries == null || entries.isEmpty()) {
            mineHistory.setNoDataText("无有效运动数据或网络连接异常");
            return;
        }


        mineHistory.setTouchEnabled(true);
        mineHistory.setScaleEnabled(false);
        mineHistory.setDragEnabled(true);

        XAxis xAxis = mineHistory.getXAxis();
        xAxis.setDrawGridLines(false);
//        xAxis.setDrawLabels(false);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setTextColor(Color.WHITE);
        xAxis.setEnabled(true);

        YAxis leftYAxis = mineHistory.getAxisLeft();
//        leftYAxis.setDrawLabels(false);
        leftYAxis.setTextColor(Color.WHITE);
        leftYAxis.setEnabled(true);

        YAxis rightYAxis = mineHistory.getAxisRight();
        rightYAxis.setEnabled(false);

        //数据源
        LineDataSet dataSet = new LineDataSet(entries, "mine history");
        dataSet.setDrawIcons(false);
        dataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        dataSet.setDrawFilled(true);


        dataSet.setFillAlpha(58);
        dataSet.setFillColor(Color.rgb(171, 117, 243));  //设置fill区域的颜色
        dataSet.setColor(Color.rgb(232, 190, 68)); //设置数据线的颜色

        //数据模型
        LineData lineData = new LineData(dataSet);
        lineData.setValueTextColor(Color.WHITE);
        lineData.setValueTextSize(11f);

        //禁用legend
        Legend legend = mineHistory.getLegend();
        legend.setEnabled(false);

        mineHistory.setData(lineData);
        mineHistory.animateY(2000);
        if (entries.size() < 3) {
            mineHistory.setVisibleXRange(1,3);
        } else {
            mineHistory.setVisibleXRange(1,6);
        }

        mineHistory.getDescription().setEnabled(false);
        mineHistory.invalidate();


    }

    @Override
    public void setWalkData(List<AVObject> lists) {
        this.historyDataList = lists;
    }

    @Override
    public void doInWalk() {
        refreshDayUi();
        disposeData();
        //绘制图表
        initHistoryChart(mineHistory);

    }

    private void refreshDayUi() {
        if (historyDataList == null || historyDataList.isEmpty()) {
            if (sportType == 3) {
                iHistoryActivity.setSteps("0");
            } else {
                iHistoryActivity.setSteps("0");
                iHistoryActivity.setDistance("0");
            }
            iHistoryActivity.setTimes(0);
            iHistoryActivity.setDuration("00:00:00");
            iHistoryActivity.setAvgSpeed("0");
            return;
        }
        if (sportType == 3) {
            iHistoryActivity.setSteps(SportUtil.getTotalDistance(historyDataList));
        } else {
            iHistoryActivity.setSteps(String.valueOf(SportUtil.getTotalStepNum(historyDataList)));
            iHistoryActivity.setDistance(SportUtil.getTotalDistance(historyDataList));
        }
        iHistoryActivity.setTimes(SportUtil.getTotalTimesNum(historyDataList));
        iHistoryActivity.setDuration(SportUtil.getTotalTime(historyDataList));
        iHistoryActivity.setAvgSpeed(SportUtil.getAverageSpeed(historyDataList));

    }


    private void disposeData() {

        entries.clear();
        if (historyDataList == null || historyDataList.isEmpty()) {
            return;
        }
        int length = historyDataList.size();
        for (int i = 0; i < length; i++) {
            entries.add(new Entry(i +1 , Float.valueOf((String) historyDataList.get(i).get("distance"))));
        }
    }
}
