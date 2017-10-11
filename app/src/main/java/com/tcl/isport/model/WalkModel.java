package com.tcl.isport.model;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.FindCallback;
import com.avos.avoscloud.SaveCallback;
import com.tcl.isport.bean.Constant;
import com.tcl.isport.bean.SportBean;
import com.tcl.isport.imodel.ISportModel;
import com.tcl.isport.presenter.HistoryActivityPresenter;
import com.tcl.isport.presenter.HomeFragmentPresenter;
import com.tcl.isport.presenter.SportFragmentPresenter;
import com.tcl.isport.util.LocationUtil;
import com.tcl.isport.util.SportUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * Created by haoyi.pan on 17-9-18.
 */
public class WalkModel implements ISportModel {
    //Walk数据模型接口实现

    private SportFragmentPresenter sportFragmentPresenter;
    private HomeFragmentPresenter homeFragmentPresenter;
    private HistoryActivityPresenter historyActivityPresenter;
    public WalkModel() {

    }

    public WalkModel(HomeFragmentPresenter homeFragmentPresenter) {
        this.homeFragmentPresenter = homeFragmentPresenter;
    }

    public WalkModel(SportFragmentPresenter sportFragmentPresenter) {
        this.sportFragmentPresenter = sportFragmentPresenter;
    }

    public WalkModel(HistoryActivityPresenter historyActivityPresenter) {
        this.historyActivityPresenter = historyActivityPresenter;
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



  /*  added start by lishui.lin on 17-9-29*/

    @Override
    public String getWeather() {

        return "";
    }

    @Override
    public void saveSportData(final Context mContext, SportBean sportBean) {
        AVObject walk = new AVObject(sportBean.getLeancloudTableWalk());
        walk.put("distance", sportBean.getDistance());
        walk.put("duration", sportBean.getDuration());
        walk.put("speed", sportBean.getSpeed());
        walk.put("step", sportBean.getStep());
        walk.put("time", SportUtil.getNow());
        walk.put("userId", AVUser.getCurrentUser());
        walk.saveInBackground(new SaveCallback() {
            @Override
            public void done(AVException e) {
                if (e == null) {
                    //提示成功的Toast
                    Toast.makeText(mContext, "添加健走数据成功！", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(mContext, "添加健走数据失败！", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void findSportData() {
        AVQuery<AVObject> avQuery = new AVQuery<>(Constant.LEANCLOUD_TABLE_WALK);
        avQuery.whereEqualTo("userId", AVUser.getCurrentUser());
        avQuery.whereContains("time", SportUtil.getTodayDate());
        avQuery.selectKeys(Arrays.asList("distance", "duration"));
        avQuery.findInBackground(new FindCallback<AVObject>() {
            @Override
            public void done(List<AVObject> list, AVException e) {
                if (e == null) {
                    sportFragmentPresenter.setWalkData(list);
                    sportFragmentPresenter.doInWalk();

                } else {
                    e.printStackTrace();
                }

            }
        });
    }

    @Override
    public void findHomeSportData() {
        AVQuery<AVObject> avQuery = new AVQuery<>(Constant.LEANCLOUD_TABLE_WALK);
        avQuery.whereEqualTo("userId", AVUser.getCurrentUser());
        avQuery.selectKeys(Arrays.asList("distance", "duration", "step"));
        avQuery.findInBackground(new FindCallback<AVObject>() {
            @Override
            public void done(List<AVObject> list, AVException e) {
                if (e == null) {

                    homeFragmentPresenter.setWalkDataToHome(list);
                    homeFragmentPresenter.doInWalkToHome();

                } else {
                    e.printStackTrace();
                }

            }
        });
    }

    @Override
    public void showHomeHistoryData() {
        AVQuery<AVObject> avQuery = new AVQuery<>(Constant.LEANCLOUD_TABLE_WALK);
        avQuery.whereEqualTo("userId", AVUser.getCurrentUser());
        avQuery.selectKeys(Arrays.asList("distance", "time"));
        avQuery.orderByDescending("createdAt");
        avQuery.limit(5);
        avQuery.findInBackground(new FindCallback<AVObject>() {
            @Override
            public void done(List<AVObject> list, AVException e) {
                if (e == null) {

                    homeFragmentPresenter.setWalkDataToHome(list);
                    homeFragmentPresenter.doInWalkToHomeHistory();
                } else {
                    e.printStackTrace();
                }

            }
        });
    }

    @Override
    public void findTodaySportData() {
        AVQuery<AVObject> avQuery = new AVQuery<>(Constant.LEANCLOUD_TABLE_WALK);
        avQuery.whereEqualTo("userId", AVUser.getCurrentUser());
        avQuery.whereGreaterThan("createdAt", SportUtil.getToday());
        avQuery.selectKeys(Arrays.asList("distance", "duration","step"));
        avQuery.orderByAscending("createdAt");
        avQuery.findInBackground(new FindCallback<AVObject>() {
            @Override
            public void done(List<AVObject> list, AVException e) {
                if (e == null) {
                    historyActivityPresenter.setWalkData(list);
                    historyActivityPresenter.doInWalk();
                } else {
                    e.printStackTrace();
                }

            }
        });
    }

    @Override
    public void findWeekSportData() {
        AVQuery<AVObject> avQuery = new AVQuery<>(Constant.LEANCLOUD_TABLE_WALK);
        avQuery.whereEqualTo("userId", AVUser.getCurrentUser());
        avQuery.whereGreaterThan("createdAt", SportUtil.getWeek());
        avQuery.selectKeys(Arrays.asList("distance", "duration","step"));
        avQuery.orderByAscending("createdAt");
        avQuery.findInBackground(new FindCallback<AVObject>() {
            @Override
            public void done(List<AVObject> list, AVException e) {
                if (e == null) {
                    historyActivityPresenter.setWalkData(list);
                    historyActivityPresenter.doInWalk();
                } else {
                    e.printStackTrace();
                }

            }
        });
    }

    @Override
    public void findMonthSportData() {
        AVQuery<AVObject> avQuery = new AVQuery<>(Constant.LEANCLOUD_TABLE_WALK);
        avQuery.whereEqualTo("userId", AVUser.getCurrentUser());
        avQuery.whereGreaterThan("createdAt", SportUtil.getMonth());
        avQuery.selectKeys(Arrays.asList("distance", "duration","step"));
        avQuery.orderByAscending("createdAt");
        avQuery.findInBackground(new FindCallback<AVObject>() {
            @Override
            public void done(List<AVObject> list, AVException e) {
                if (e == null) {
                    historyActivityPresenter.setWalkData(list);
                    historyActivityPresenter.doInWalk();
                } else {
                    e.printStackTrace();
                }

            }
        });
    }

    public interface IWalkModel{
        void setWalkData(List<AVObject> lists);
        void doInWalk();

    }

    public interface IWalkModeToHome {
        void setWalkDataToHome(List<AVObject> lists);
        void doInWalkToHome();

        void doInWalkToHomeHistory();
    }

    /*  added end by lishui.lin on 17-9-29*/
}
