package com.tcl.isport.imodel;

import android.app.Activity;
import android.content.Context;

import com.tcl.isport.bean.ActivityBean;

/**
 * Created by haoyi.pan on 17-9-29.
 */
public interface IActivityModel {
    //发布活动
    void saveActivity(Activity mActivity, ActivityBean activityBean);
    //所有活动
    void findAllActivity();
    //我发布的
    void findPubActivity(String userId);
    //我加入的
    void findJoinActivity(String userId);
    //活动延期
    void updateActivity(ActivityBean activityBean);
}
