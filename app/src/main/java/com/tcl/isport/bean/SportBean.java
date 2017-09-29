package com.tcl.isport.bean;

import java.util.Date;

/**
 * Created by lishui.lin on 17-9-29 10:48
 */

public class SportBean {

    public final String LEANCLOUD_TABLE_WALK = "Walk";
    public final String LEANCLOUD_TABLE_RUN = "Run";
    public final String LEANCLOUD_TABLE_RIDE = "Ride";

    private String objectId;
    //设置初始值，防止空指针异常
    private String distance = "";
    private long duration ;
    private int step = 0;
    private String speed = "";
    private String userId = "null";

    public String getLeancloudTableWalk() {
        return LEANCLOUD_TABLE_WALK;
    }

    public String getLeancloudTableRun() {
        return LEANCLOUD_TABLE_RUN;
    }

    public String getLeancloudTableRide() {
        return LEANCLOUD_TABLE_RIDE;
    }



    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public int getStep() {
        return step;
    }

    public void setStep(int step) {
        this.step = step;
    }

    public String getSpeed() {
        return speed;
    }

    public void setSpeed(String speed) {
        this.speed = speed;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }


}
