package com.tcl.isport.presenter;

import android.app.Activity;
import android.content.Context;

import com.avos.avoscloud.AVFile;
import com.tcl.isport.bean.ActivityBean;
import com.tcl.isport.imodel.IActivityModel;
import com.tcl.isport.iview.IActivityNewActivity;
import com.tcl.isport.model.ActivityModel;

/**
 * Created by haoyi.pan on 17-10-11.
 */
public class NewActivityPresenter {
    private IActivityModel iActivityModel;
    private IActivityNewActivity iActivityNewActivity;
    public NewActivityPresenter(IActivityNewActivity view){
        iActivityNewActivity=view;
        iActivityModel=new ActivityModel();
    }

    public boolean pubActivity(Activity activity){
        ActivityBean activityBean=new ActivityBean();
        activityBean.setTheme(iActivityNewActivity.getActivityTheme());
        activityBean.setIntro(iActivityNewActivity.getIntro());
        activityBean.setContent(iActivityNewActivity.getContent());
        activityBean.setNumber(iActivityNewActivity.getNumber());
        activityBean.setTime(iActivityNewActivity.getTime());
        activityBean.setLocation(iActivityNewActivity.getLocation());
        activityBean.setDeadline(iActivityNewActivity.getDeadline());
        activityBean.setCover(new AVFile("cover",iActivityNewActivity.getCover()));
        iActivityModel.saveActivity(activity,activityBean);
        return false;
    }
}
