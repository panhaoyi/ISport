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
import com.tcl.isport.presenter.SportFragmentPresenter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by haoyi.pan on 17-9-18.
 */
public class RideModel implements ISportModel {
    //Ride数据模型接口实现

    private SportFragmentPresenter sportFragmentPresenter;
    public RideModel() {

    }

    public RideModel(SportFragmentPresenter sportFragmentPresenter) {
        this.sportFragmentPresenter = sportFragmentPresenter;
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

        AVObject ride = new AVObject(sportBean.getLeancloudTableRide());
        ride.put("distance", sportBean.getDistance());
        ride.put("duration", sportBean.getDuration());
        ride.put("speed", sportBean.getSpeed());
        ride.put("step", sportBean.getStep());
        ride.put("userId", sportBean.getUserId());
        ride.saveInBackground(new SaveCallback() {
            @Override
            public void done(AVException e) {
                if (e == null) {
                    //提示成功的Toast
                    Toast.makeText(mContext, "添加数据成功！", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(mContext, "添加数据失败！", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void findSportData() {
        final List<AVObject> sportBeanList = new ArrayList<>();
        AVQuery<AVObject> avQuery = new AVQuery<>(Constant.LEANCLOUD_TABLE_RIDE);
//        avQuery.selectKeys(Arrays.asList("distance", "duration"));
        avQuery.findInBackground(new FindCallback<AVObject>() {
            @Override
            public void done(List<AVObject> list, AVException e) {
                if (e == null) {
                    sportBeanList.addAll(list);
                    sportFragmentPresenter.setRideData(sportBeanList);
                    sportFragmentPresenter.doInRide();

                } else {
                    e.printStackTrace();
                }

            }
        });
    }
    public interface IRideModel{
        void setRideData(List<AVObject> lists);
        void doInRide();
    }

     /*  added end by lishui.lin on 17-9-29*/


}
