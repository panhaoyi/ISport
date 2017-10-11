package com.tcl.isport.model;

import android.content.Context;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.FindCallback;
import com.avos.avoscloud.SaveCallback;
import com.tcl.isport.bean.Constant;
import com.tcl.isport.bean.SportBean;
import com.tcl.isport.imodel.ISportModel;
import com.tcl.isport.presenter.HistoryActivityPresenter;
import com.tcl.isport.presenter.HomeFragmentPresenter;
import com.tcl.isport.presenter.SportFragmentPresenter;
import com.tcl.isport.util.SportUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by haoyi.pan on 17-9-18.
 */
public class RunModel implements ISportModel {
    //Run数据模型接口实现

    private SportFragmentPresenter sportFragmentPresenter;
    private HomeFragmentPresenter homeFragmentPresenter;
    private HistoryActivityPresenter historyActivityPresenter;
    public RunModel() {

    }
    public RunModel(HomeFragmentPresenter homeFragmentPresenter) {
        this.homeFragmentPresenter = homeFragmentPresenter;
    }

    public RunModel(SportFragmentPresenter sportFragmentPresenter) {
        this.sportFragmentPresenter = sportFragmentPresenter;
    }

    public RunModel(HistoryActivityPresenter historyActivityPresenter) {
        this.historyActivityPresenter = historyActivityPresenter;
    }

    @Override
    public String getDistance() {
        return "0.00";
    }

    @Override
    public String getDuration() {
        return "0 h";
    }


    @Override
    public String getWeather() {
        return null;
    }

    /*  added start by lishui.lin on 17-9-29*/
    @Override
    public void saveSportData(final Context mContext, SportBean sportBean) {
        AVObject run = new AVObject(sportBean.getLeancloudTableRun());
        run.put("distance", sportBean.getDistance());
        run.put("duration", sportBean.getDuration());
        run.put("speed", sportBean.getSpeed());
        run.put("step", sportBean.getStep());
        run.put("time", SportUtil.getNow());
        run.put("userId", sportBean.getUserId());
        run.saveInBackground(new SaveCallback() {
            @Override
            public void done(AVException e) {
                if (e == null) {
                    //提示成功的Toast
                    Toast.makeText(mContext, "添加跑步数据成功！", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(mContext, "添加跑步数据失败！", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void findSportData() {
        final List<AVObject> sportBeanList = new ArrayList<>();
        AVQuery<AVObject> avQuery = new AVQuery<>(Constant.LEANCLOUD_TABLE_RUN);
        avQuery.whereContains("time", SportUtil.getTodayDate());
        avQuery.selectKeys(Arrays.asList("distance", "duration"));
        avQuery.findInBackground(new FindCallback<AVObject>() {
            @Override
            public void done(List<AVObject> list, AVException e) {
                if (e == null) {
                    sportBeanList.addAll(list);
                    sportFragmentPresenter.setRunData(sportBeanList);
                    sportFragmentPresenter.doInRun();

                } else {
                    e.printStackTrace();
                }

            }
        });
    }

    @Override
    public void findHomeSportData() {
        final List<AVObject> sportBeanList = new ArrayList<>();
        AVQuery<AVObject> avQuery = new AVQuery<>(Constant.LEANCLOUD_TABLE_RUN);
        avQuery.selectKeys(Arrays.asList("distance", "duration", "step"));
        avQuery.findInBackground(new FindCallback<AVObject>() {
            @Override
            public void done(List<AVObject> list, AVException e) {
                if (e == null) {
                    sportBeanList.addAll(list);
                    homeFragmentPresenter.setRunDataToHome(sportBeanList);
                    homeFragmentPresenter.doInRunToHome();

                } else {
                    e.printStackTrace();
                }

            }
        });
    }

    @Override
    public void showHomeHistoryData() {
        final List<AVObject> sportBeanList = new ArrayList<>();
        AVQuery<AVObject> avQuery = new AVQuery<>(Constant.LEANCLOUD_TABLE_RUN);
        avQuery.selectKeys(Arrays.asList("distance", "time"));
        avQuery.orderByDescending("createdAt");
        avQuery.limit(5);
        avQuery.findInBackground(new FindCallback<AVObject>() {
            @Override
            public void done(List<AVObject> list, AVException e) {
                if (e == null) {
                    sportBeanList.addAll(list);
                    homeFragmentPresenter.setRunDataToHome(sportBeanList);
                    homeFragmentPresenter.doInWalkToHomeHistory();
                } else {
                    e.printStackTrace();
                }

            }
        });
    }

    @Override
    public void findTodaySportData() {
        AVQuery<AVObject> avQuery = new AVQuery<>(Constant.LEANCLOUD_TABLE_RUN);
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
        AVQuery<AVObject> avQuery = new AVQuery<>(Constant.LEANCLOUD_TABLE_RUN);
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
        AVQuery<AVObject> avQuery = new AVQuery<>(Constant.LEANCLOUD_TABLE_RUN);
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

    public interface IRunModel{
        void setRunData(List<AVObject> lists);
        void doInRun();
    }

    public interface IRunModeToHome {
        void setRunDataToHome(List<AVObject> lists);
        void doInRunToHome();

        void doInRunToHomeHistory();
    }
    /*  added end by lishui.lin on 17-9-29*/
}
