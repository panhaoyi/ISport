package com.tcl.isport.IModel;

/**
 * Created by haoyi.pan on 17-9-18.
 */
public interface ISportModel {
    //Walk,Run,Ride三张表的数据模型接口

    //获取里程
    public String getDistance();
    //获取用时
    public String getDuration();
}
