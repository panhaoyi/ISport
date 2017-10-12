package com.tcl.isport.bean;

import com.avos.avoscloud.AVFile;

/**
 * Created by haoyi.pan on 17-9-30.
 */
public class ActivityBean {
    private String objectId;
    //设置初始值，防止空指针异常
    private String theme = "";
    private String intro = "";
    private String content = "";
    private String number = "";
    private String time="";
    private String location="";
    private String deadline="";
    private AVFile cover;
    private String userId = "null";

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDeadline() {
        return deadline;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }

    public AVFile getCover() {
        return cover;
    }

    public void setCover(AVFile cover) {
        this.cover = cover;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
