package com.tcl.isport.presenter;

import com.tcl.isport.activity.RideActivity;
import com.tcl.isport.activity.RunActivity;
import com.tcl.isport.activity.WalkActivity;
<<<<<<< HEAD:app/src/main/java/com/tcl/isport/presenter/SportActivityPresenter.java
import com.tcl.isport.imodel.ISportModel;
=======
>>>>>>> 97d18ceb2b153c6824083d9d5cb27c5bd8c1cb5b:app/src/main/java/com/tcl/isport/presenter/SportActivityPresenter.java
import com.tcl.isport.iView.ISportActivity;
import com.tcl.isport.model.RideModel;
import com.tcl.isport.model.RunModel;
import com.tcl.isport.model.WalkModel;

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
