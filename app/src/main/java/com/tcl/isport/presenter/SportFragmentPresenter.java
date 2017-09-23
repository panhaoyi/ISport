package com.tcl.isport.presenter;

import com.tcl.isport.fragment.SportRideFragment;
import com.tcl.isport.fragment.SportRunFragment;
import com.tcl.isport.fragment.SportWalkFragment;
<<<<<<< HEAD:app/src/main/java/com/tcl/isport/presenter/SportFragmentPresenter.java
import com.tcl.isport.imodel.ISportModel;
=======
>>>>>>> 97d18ceb2b153c6824083d9d5cb27c5bd8c1cb5b:app/src/main/java/com/tcl/isport/presenter/SportFragmentPresenter.java
import com.tcl.isport.iview.ISportFragment;
import com.tcl.isport.model.RideModel;
import com.tcl.isport.model.RunModel;
import com.tcl.isport.model.WalkModel;

/**
 * Created by haoyi.pan on 17-9-18.
 */
public class SportFragmentPresenter {
    //主界面-运动SportFragment中三个Fragment共用的业务逻辑处理Presenter

    private ISportFragment iSportFragment;
    private ISportModel iSportModel;

    public SportFragmentPresenter(ISportFragment view){
        //带参数构造器，通过参数拿到调用的view作为接口的实例
        this.iSportFragment=view;
        //对应view的Walk/Run/Ride类型，初始化Model实例
        if(view.getClass().getName().equals(SportWalkFragment.class.getName())){
            iSportModel=new WalkModel();
        }
        else if(view.getClass().getName().equals(SportRunFragment.class.getName())){
            iSportModel=new RunModel();
        }
        else if(view.getClass().getName().equals(SportRideFragment.class.getName())){
            iSportModel=new RideModel();
        }
    }

    public void loadData(){
        //加载fragment页面显示的里程，用时数据
        iSportFragment.setDistance(iSportModel.getDistance());
        iSportFragment.setDuration(iSportModel.getDuration());
    }
}
