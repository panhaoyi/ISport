package com.tcl.isport.presenter;

import com.tcl.isport.bean.ActivityBean;
import com.tcl.isport.imodel.IActivityModel;
import com.tcl.isport.iview.IActivityDetailActivity;
import com.tcl.isport.model.ActivityModel;

/**
 * Created by haoyi.pan on 17-10-12.
 */
public class ActivityDetailPresenter {
    private IActivityModel iActivityModel;
    private IActivityDetailActivity iActivityDetailActivity;
    public ActivityDetailPresenter(IActivityDetailActivity view,String activityId){
        iActivityDetailActivity=view;
        iActivityModel=new ActivityModel(this);
        iActivityModel.findActivityById(activityId);
    }

    //活动详细信息数据
    public void setActivityData(ActivityBean activityBean,int joinNumber) {
        iActivityDetailActivity.setData(activityBean,joinNumber);
    }

    //当前用户参与的活动详细信息
    public void setActivityData(ActivityBean activityBean,int joinNumber, String objectId) {
        iActivityDetailActivity.setData(activityBean,joinNumber,objectId);
    }

    //活动延期
    public void postpone(ActivityBean activityBrean){
        iActivityModel.updateActivity(activityBrean);
    }

    //加入活动
    public void joinActivity(String activityId){
        iActivityModel.joinActivity(activityId);
    }

    //刷新界面及数据
    public void refreshView(int operationCode) {
        iActivityDetailActivity.refreshView(operationCode);
    }

    public void quitActivity(String joinActivityId) {
        iActivityModel.quitActivity(joinActivityId);
    }

    public void cancelActivity(String activityId){
        iActivityModel.deleteActivity(activityId);
    }

//    public void refreshActivity(){
//
//    }
}
