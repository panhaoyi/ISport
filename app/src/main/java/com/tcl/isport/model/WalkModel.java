package com.tcl.isport.model;

<<<<<<< HEAD:app/src/main/java/com/tcl/isport/model/WalkModel.java
import com.tcl.isport.imodel.ISportModel;
=======
import com.tcl.isport.iModel.ISportModel;
>>>>>>> 97d18ceb2b153c6824083d9d5cb27c5bd8c1cb5b:app/src/main/java/com/tcl/isport/model/WalkModel.java

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