package com.tcl.isport.model;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVFile;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.DeleteCallback;
import com.avos.avoscloud.FindCallback;
import com.avos.avoscloud.GetCallback;
import com.avos.avoscloud.GetDataCallback;
import com.avos.avoscloud.SaveCallback;
import com.tcl.isport.bean.ActivityBean;
import com.tcl.isport.imodel.IActivityModel;
import com.tcl.isport.presenter.ActivityDetailPresenter;
import com.tcl.isport.presenter.FindActivityPresenter;
import com.tcl.isport.presenter.JoinActivityPresenter;
import com.tcl.isport.presenter.NewActivityPresenter;
import com.tcl.isport.presenter.PubActivityPresenter;
import com.tcl.isport.util.DateUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by haoyi.pan on 17-9-29.
 */
public class ActivityModel implements IActivityModel {
    private int numAll = 0;
    private FindActivityPresenter findActivityPresenter;
    private ActivityDetailPresenter activityDetailPresenter;
    private PubActivityPresenter pubActivityPresenter;
    private JoinActivityPresenter joinActivityPresenter;

    public ActivityModel() {

    }

    public ActivityModel(FindActivityPresenter findActivityPresenter) {
        this.findActivityPresenter = findActivityPresenter;
    }

    public ActivityModel(ActivityDetailPresenter activityDetailPresenter) {
        this.activityDetailPresenter = activityDetailPresenter;
    }

    public ActivityModel(PubActivityPresenter pubActivityPresenter) {
        this.pubActivityPresenter = pubActivityPresenter;
    }

    public ActivityModel(JoinActivityPresenter joinActivityPresenter) {
        this.joinActivityPresenter = joinActivityPresenter;
    }

    @Override
    public void saveActivity(final Activity mActivity, ActivityBean activityBean) {
        //发布活动
        AVObject activity = new AVObject("Activity");
        activity.put("theme", activityBean.getTheme());
        activity.put("intro", activityBean.getIntro());
        activity.put("content", activityBean.getContent());
        activity.put("number", activityBean.getNumber());
        activity.put("time", activityBean.getTime());
        activity.put("location", activityBean.getLocation());
        activity.put("deadline", activityBean.getDeadline());
        activity.put("cover", new AVFile("cover", activityBean.getCover()));
        activity.put("userId", AVUser.getCurrentUser());
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
        //所有活动列表
        AVQuery<AVObject> query = new AVQuery<>("Activity");
        query.selectKeys(Arrays.asList("theme", "time", "number", "cover"));
        query.findInBackground(new FindCallback<AVObject>() {
            @Override
            public void done(List<AVObject> list, AVException e) {
                if (e == null) {
                    final List<Map<String, Object>> data = new ArrayList<>();
                    Map<String, Object> map;
                    for (AVObject avObject : list) {
                        map = new HashMap<>();
                        map.put("objectId", avObject.getObjectId());
                        map.put("theme", avObject.get("theme").toString());
                        String date = avObject.get("time").toString();
                        map.put("countdown", DateUtil.compareDate(date, 1));
                        map.put("number", "人数规模: " + avObject.get("number").toString());
                        AVFile avFile = (AVFile) avObject.get("cover");
                        final Map<String, Object> finalMap = map;
                        avFile.getDataInBackground(new GetDataCallback() {
                            @Override
                            public void done(byte[] bytes, AVException e) {
                                if (e == null) {
                                    Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                                    finalMap.put("picture", bitmap);
                                    findActivityPresenter.setActivityData(finalMap);
                                    findActivityPresenter.refreshView();
                                } else {
                                    e.printStackTrace();
                                }
                            }
                        });
                    }
//                    findActivityPresenter.setActivityData(list);
//                    findActivityPresenter.refreshActivity();
                } else {
                    e.printStackTrace();
                }
            }
        });
        //每次查询10条结果
        query.limit(10);
        //跳过numAll条结果
        query.skip(numAll);
        //已查询结果数量
        numAll += 10;
    }

    @Override
    public void findActivityById(String activityId) {
        //通过活动id查询活动详情
        final AVQuery<AVObject> query = new AVQuery<>("Activity");
        query.include("cover");
        query.getInBackground(activityId, new GetCallback<AVObject>() {
            @Override
            public void done(final AVObject avObject, AVException e) {
                if (e == null) {
                    final ActivityBean activityBean = new ActivityBean();
                    activityBean.setObjectId(avObject.getObjectId());
                    activityBean.setTheme(avObject.get("theme").toString());
                    activityBean.setIntro(avObject.get("intro").toString());
                    activityBean.setContent(avObject.get("content").toString());
                    activityBean.setTime(avObject.get("time").toString());
                    activityBean.setDeadline(avObject.get("deadline").toString());
                    activityBean.setLocation(avObject.get("location").toString());
                    activityBean.setNumber(avObject.get("number").toString());
                    AVUser avUser = (AVUser) avObject.get("userId");
                    activityBean.setUserId(avUser.getObjectId());
                    AVFile avFile = (AVFile) avObject.get("cover");
                    avFile.getDataInBackground(new GetDataCallback() {
                        @Override
                        public void done(byte[] bytes, AVException e) {
                            if (e == null) {
                                activityBean.setCover(bytes);

                                    AVQuery<AVObject> avQuery1=new AVQuery<>("JoinActivity");
                                    avQuery1.whereEqualTo("activityId",avObject);
                                    avQuery1.include("userId");
                                    avQuery1.findInBackground(new FindCallback<AVObject>() {
                                        @Override
                                        public void done(List<AVObject> list, AVException e) {
                                            if (e==null) {
                                                //获得参加该活动的人数,包括发布者
                                                int joinNumber = list.size() + 1;
                                                if (activityBean.getUserId().equals(AVUser.getCurrentUser().getObjectId())) {
                                                    //活动发布者为当前用户时
                                                    activityDetailPresenter.setActivityData(activityBean,joinNumber);
                                                }
                                                else{
                                                    //活动发布者不是当前用户时,查找JoinActivity是否存在当前用户参加该活动的信息
                                                    for(AVObject joinActivity:list){
                                                        AVUser user= (AVUser) joinActivity.get("userId");
                                                        if (user.equals(AVUser.getCurrentUser())){
                                                            //查询到数据,当前用户参加了该活动,返回参加的id以便于退出活动
                                                            activityDetailPresenter.setActivityData(activityBean,joinNumber, joinActivity.getObjectId());
                                                            break;
                                                        }
                                                    }
                                                    //for循环遍历JoinActivity没找到当前用户的信息
                                                    activityDetailPresenter.setActivityData(activityBean,joinNumber);
                                                }


                                            }else{
                                                e.printStackTrace();
                                            }
                                        }
                                    });

                            } else {
                                e.printStackTrace();
                            }
                        }
                    });
                } else {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void findPubActivity() {
        //我发布的活动
        AVQuery<AVObject> query = new AVQuery<>("Activity");
        query.selectKeys(Arrays.asList("theme", "time", "number", "cover"));
        query.whereEqualTo("userId", AVUser.getCurrentUser());
        query.findInBackground(new FindCallback<AVObject>() {
            @Override
            public void done(List<AVObject> list, AVException e) {
                if (e == null) {
                    Map<String, Object> map;
                    for (AVObject avObject : list) {
                        if (list.size()==0){
                            joinActivityPresenter.refreshView();
                        }
                        map = new HashMap<>();
                        map.put("objectId", avObject.getObjectId());
                        map.put("theme", avObject.get("theme").toString());
                        String date = avObject.get("time").toString();
                        map.put("countdown", DateUtil.compareDate(date, 1));
                        map.put("number", "人数规模: " + avObject.get("number").toString());
                        AVFile avFile = (AVFile) avObject.get("cover");
                        final Map<String, Object> finalMap = map;
                        avFile.getDataInBackground(new GetDataCallback() {
                            @Override
                            public void done(byte[] bytes, AVException e) {
                                if (e == null) {
                                    Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                                    finalMap.put("picture", bitmap);
                                    pubActivityPresenter.setActivityData(finalMap);
                                    pubActivityPresenter.refreshView();
                                } else {
                                    e.printStackTrace();
                                }
                            }
                        });
                    }
//                    findActivityPresenter.setActivityData(list);
//                    findActivityPresenter.refreshActivity();
                } else {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void findJoinActivity() {
        //我加入的活动
        AVQuery<AVObject> query=new AVQuery<>("JoinActivity");
        query.whereEqualTo("userId",AVUser.getCurrentUser());
        query.include("activityId");
        query.findInBackground(new FindCallback<AVObject>() {
            @Override
            public void done(List<AVObject> list, AVException e) {
                if (e == null) {
                    Map<String, Object> map;
                    if (list.size()==0){
                        joinActivityPresenter.refreshView();
                    }
                    for (AVObject avObject : list) {
                        AVObject activity= (AVObject) avObject.get("activityId");
                        map = new HashMap<>();
                        map.put("objectId", activity.getObjectId());
                        map.put("theme", activity.get("theme").toString());
                        String date = activity.get("time").toString();
                        map.put("countdown", DateUtil.compareDate(date, 1));
                        map.put("number", "人数规模: " + activity.get("number").toString());
                        AVFile avFile = (AVFile) activity.get("cover");
                        final Map<String, Object> finalMap = map;
                        avFile.getDataInBackground(new GetDataCallback() {
                            @Override
                            public void done(byte[] bytes, AVException e) {
                                if (e == null) {
                                    Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                                    finalMap.put("picture", bitmap);
                                    joinActivityPresenter.setActivityData(finalMap);
                                    joinActivityPresenter.refreshView();
                                } else {
                                    e.printStackTrace();
                                }
                            }
                        });
                    }
//                    findActivityPresenter.setActivityData(list);
//                    findActivityPresenter.refreshActivity();
                } else {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void joinActivity(String activityId){
        AVObject joinActivity=new AVObject("JoinActivity");
        AVObject activity=AVObject.createWithoutData("Activity",activityId);
        joinActivity.put("activityId",activity);
        joinActivity.put("userId",AVUser.getCurrentUser());
        joinActivity.saveInBackground(new SaveCallback() {
            @Override
            public void done(AVException e) {
                if (e==null){
                    activityDetailPresenter.refreshView(2);
                }else{
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void quitActivity(String joinActivityId) {
        AVObject joinActivity=AVObject.createWithoutData("JoinActivity",joinActivityId);
        joinActivity.deleteInBackground(new DeleteCallback() {
            @Override
            public void done(AVException e) {
                if (e==null){
                    activityDetailPresenter.refreshView(3);
                }else{
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void updateActivity(ActivityBean activityBean) {
        final AVObject activity = AVObject.createWithoutData("Activity", activityBean.getObjectId());
        activity.put("time", activityBean.getTime());
        activity.saveInBackground(new SaveCallback() {
            @Override
            public void done(AVException e) {
                if (e == null) {
                    activityDetailPresenter.refreshView(1);
                } else {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void deleteActivity(String activityId) {
        AVObject activity = AVObject.createWithoutData("Activity",activityId);
        activity.put("status","cancel");
        activity.saveInBackground(new SaveCallback() {
            @Override
            public void done(AVException e) {
                if (e==null){

                }else{
                    e.printStackTrace();
                }
            }
        });
//        activity.deleteInBackground(new DeleteCallback() {
//            @Override
//            public void done(AVException e) {
//                if (e==null){
//
//                }else{
//                    e.printStackTrace();
//                }
//            }
//        });
    }
}
