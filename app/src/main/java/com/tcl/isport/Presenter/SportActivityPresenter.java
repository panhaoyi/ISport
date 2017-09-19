package com.tcl.isport.Presenter;

import com.tcl.isport.Activity.RideActivity;
import com.tcl.isport.Activity.RunActivity;
import com.tcl.isport.Activity.WalkActivity;
import com.tcl.isport.IModel.ISportModel;
import com.tcl.isport.IView.ISportActivity;
import com.tcl.isport.Model.RideModel;
import com.tcl.isport.Model.RunModel;
import com.tcl.isport.Model.WalkModel;

/**
 * Created by haoyi.pan on 17-9-18.
 */
public class SportActivityPresenter {
    //WalkActivity,RunActivity,RideActivity业务逻辑
    private ISportModel iSportModel;
    private ISportActivity iSportActivity;

    public SportActivityPresenter(ISportActivity view){
        //构造器通过参数拿到view实例化view接口，根据view的类型初始化model
        this.iSportActivity=view;
        if(view.getClass().getName().equals(WalkActivity.class.getName())){
            iSportModel=new WalkModel();
        }
        else if(view.getClass().getName().equals(RunActivity.class.getName())){
            iSportModel=new RunModel();
        }
        else if(view.getClass().getName().equals(RideActivity.class.getName())){
            iSportModel=new RideModel();
        }
    }

}
