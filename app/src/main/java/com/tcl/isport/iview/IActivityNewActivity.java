package com.tcl.isport.iview;

/**
 * Created by haoyi.pan on 17-9-29.
 */
public interface IActivityNewActivity {
    //获取新增活动各属性值
    String getActivityTheme();
    String getIntro();
    String getContent();
    String getNumber();
    String getTime();
    String getLocation();
    String getDeadline();
    byte[] getCover();
}
