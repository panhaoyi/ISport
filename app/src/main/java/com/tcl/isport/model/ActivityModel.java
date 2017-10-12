package com.tcl.isport.model;

import android.app.Activity;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.FindCallback;
import com.avos.avoscloud.GetCallback;
import com.avos.avoscloud.SaveCallback;
import com.tcl.isport.bean.ActivityBean;
import com.tcl.isport.imodel.IActivityModel;
import com.tcl.isport.presenter.FindActivityPresenter;
import com.tcl.isport.presenter.NewActivityPresenter;

import java.util.Arrays;
import java.util.List;

/**
 * Created by haoyi.pan on 17-9-29.
 */
public class ActivityModel implements IActivityModel {
    private int numAll=0;
    private FindActivityPresenter findActivityPresenter;
    public ActivityModel(){

    }
    public ActivityModel(FindActivityPresenter findActivityPresenter){
        this.findActivityPresenter=findActivityPresenter;
    }
    @Override
    public void saveActivity(final Activity mActivity, ActivityBean activityBean) {
        AVObject activity=new AVObject("Activity");
        activity.put("theme",activityBean.getTheme());
        activity.put("intro",activityBean.getIntro());
        activity.put("content",activityBean.getContent());
        activity.put("number",activityBean.getNumber());
        activity.put("time",activityBean.getTime());
        activity.put("location",activityBean.getLocation());
        activity.put("deadline",activityBean.getDeadline());
        activity.put("cover",activityBean.getCover());
        activity.saveInBackground(new SaveCallback() {
            @Override
            public void done(AVException e) {
                if (e == null) {
                    //提示成功的Toast
                    Toast.makeText(mActivity, "发布成功！", Toast.LENGTH_SHORT).show();
                    mActivity.finish();
                } else {
                    Toast.makeText(mActivity, "发布失败！", Toast.LENGTH_SHORT).show();
                    mActivity.finish();
                }
            }
        });
    }

    @Override
    public void findAllActivity() {
        AVQuery<AVObject> query=new AVQuery<>("Activity");
        query.selectKeys(Arrays.asList("theme","time","number","cover"));
        query.findInBackground(new FindCallback<AVObject>(){
            @Override
            public void done(List<AVObject> list, AVException e) {
                if(e==null){
                    findActivityPresenter.setActivityData(list);
                    findActivityPresenter.refreshActivity();
                }else{
                    e.printStackTrace();
                }
            }
        });
        //每次查询10条结果
        query.limit(10);
        //跳过numAll条结果
        query.skip(numAll);
        //已查询结果数量
        numAll+=10;
    }

    @Override
    public void findPubActivity(String userId) {

    }

    @Override
    public void findJoinActivity(String userId) {

    }

    @Override
    public void updateActivity(ActivityBean activityBean) {

    }
}
