package com.tcl.isport.Model;

import com.tcl.isport.IModel.ISportModel;

/**
 * Created by haoyi.pan on 17-9-18.
 */
public class RunModel implements ISportModel {
    //Run数据模型接口实现

    @Override
    public String getDistance() {
        return "0.00";
    }

    @Override
    public String getDuration() {
        return "0 h";
    }
}
