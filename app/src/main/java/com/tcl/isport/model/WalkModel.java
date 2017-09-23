package com.tcl.isport.model;

import com.tcl.isport.imodel.ISportModel;

/**
 * Created by haoyi.pan on 17-9-18.
 */
public class WalkModel implements ISportModel {
    //Walk数据模型接口实现

    @Override
    public String getDistance() {
        //从服务器获取数据
        return "0.00";
    }

    @Override
    public String getDuration() {
        return "0 h";
    }
}
