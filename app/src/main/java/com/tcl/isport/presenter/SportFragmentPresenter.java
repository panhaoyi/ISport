package com.tcl.isport.presenter;

import com.avos.avoscloud.AVObject;
import com.tcl.isport.fragment.SportRideFragment;
import com.tcl.isport.fragment.SportRunFragment;
import com.tcl.isport.fragment.SportWalkFragment;
import com.tcl.isport.imodel.ISportModel;
import com.tcl.isport.iview.ISportFragment;
import com.tcl.isport.model.RideModel;
import com.tcl.isport.model.RunModel;
import com.tcl.isport.model.WalkModel;
import com.tcl.isport.util.LocationUtil;
import com.tcl.isport.util.SportUtil;

import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.List;

/**
 * Created by haoyi.pan on 17-9-18.
 */
public class SportFragmentPresenter implements WalkModel.IWalkModel, RunModel.IRunModel, RideModel.IRideModel{
    //主界面-运动SportFragment中三个Fragment共用的业务逻辑处理Presenter

    private ISportFragment iSportFragment;
    private ISportModel iSportModel;

    private List<AVObject> sportDataList;

    public SportFragmentPresenter(ISportFragment view) {
        //带参数构造器，通过参数拿到调用的view作为接口的实例
        this.iSportFragment = view;
        //对应view的Walk/Run/Ride类型，初始化Model实例
        if (view instanceof SportWalkFragment) {
            iSportModel = new WalkModel(this);
        } else if (view instanceof SportRunFragment) {
            iSportModel = new RunModel(this);
        } else if (view instanceof SportRideFragment) {
            iSportModel = new RideModel(this);
        }
    }

    public void loadData() {
        //加载fragment页面显示的里程，用时数据
        iSportFragment.setDistance(iSportModel.getDistance());
        iSportFragment.setDuration(iSportModel.getDuration());
    }

    /*added start by lishui.lin in 2017-09-29*/

    public void getSportData() {
        iSportModel.findSportData();
    }

    public void refreshData() {
        if (SportUtil.getTotalDistance(sportDataList) != null){
            iSportFragment.setDistance(SportUtil.getTotalDistance(sportDataList));
        }
        if (SportUtil.getTotalTime(sportDataList) != null) {
            iSportFragment.setDuration(SportUtil.getTotalTime(sportDataList));
        }

    }

    @Override
    public void setWalkData(List<AVObject> lists) {
        sportDataList = lists;
    }

    @Override
    public void doInWalk() {
        refreshData();
    }

    @Override
    public void setRunData(List<AVObject> lists) {
        sportDataList = lists;
    }

    @Override
    public void doInRun() {
        refreshData();
    }

    @Override
    public void setRideData(List<AVObject> lists) {
        sportDataList = lists;
    }

    @Override
    public void doInRide() {
        refreshData();
    }

    /*added end by lishui.lin in 2017-09-29*/


}
