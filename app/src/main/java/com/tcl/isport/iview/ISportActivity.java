package com.tcl.isport.iview;

/**
 * Created by haoyi.pan on 17-9-18.
 */
public interface ISportActivity {
    //开始运动view接口，封装WalkActivity,RunActivity,RideActivity的view操作
    public void setDistance(String distance);
    public void setSpeed(String speed);
    public void setDuration(String duration);
}