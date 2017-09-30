package com.tcl.isport.iview;

/**
 * Created by haoyi.pan on 17-9-18.
 */
public interface ISportActivity {
    //开始运动view接口，封装WalkActivity,RunActivity,RideActivity的view操作
    void setDistance(String distance);
    void setSpeed(String speed);
    void setDuration(String duration);
    void setStep(int step);


    String getDistance();
    long getDuration();
    String getSpeed();
    int getStep();
}
