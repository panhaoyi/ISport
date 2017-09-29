package com.tcl.isport.model;

import android.content.Context;
import android.util.Log;
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
import com.tcl.isport.util.LocationUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * Created by haoyi.pan on 17-9-18.
 */
public class WalkModel implements ISportModel {
    //Walk数据模型接口实现

    private SportFragmentPresenter sportFragmentPresenter;
    public WalkModel() {

    }

    public WalkModel(SportFragmentPresenter sportFragmentPresenter) {
        this.sportFragmentPresenter = sportFragmentPresenter;
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
        walk.put("userId", sportBean.getUserId());
        walk.saveInBackground(new SaveCallback() {
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
        AVQuery<AVObject> avQuery = new AVQuery<>(Constant.LEANCLOUD_TABLE_WALK);
//        avQuery.selectKeys(Arrays.asList("distance", "duration"));
        avQuery.findInBackground(new FindCallback<AVObject>() {
            @Override
            public void done(List<AVObject> list, AVException e) {
                if (e == null) {
                    sportBeanList.addAll(list);
                    sportFragmentPresenter.setWalkData(sportBeanList);
                    sportFragmentPresenter.doInWalk();

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

    /*  added end by lishui.lin on 17-9-29*/
}
