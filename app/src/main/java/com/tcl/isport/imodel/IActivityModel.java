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

    //所有活动列表
    void findAllActivity();

    //单条活动详情
    void findActivityById(String activityId);

    //我发布的活动列表
    void findPubActivity();

    //我加入的活动列表
    void findJoinActivity();

    //加入活动
    void joinActivity(String activityId);

    //退出活动
    void quitActivity(String joinActivityId);

    //活动延期
    void updateActivity(ActivityBean activityBean);

    //活动取消
    void deleteActivity(String activityId);

}
