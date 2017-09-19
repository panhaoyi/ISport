package com.tcl.isport.Presenter;

import android.view.View;

import com.tcl.isport.Fragment.SportRideFragment;
import com.tcl.isport.Fragment.SportRunFragment;
import com.tcl.isport.Fragment.SportWalkFragment;
import com.tcl.isport.IModel.ISportModel;
import com.tcl.isport.IView.ISportFragment;
import com.tcl.isport.Model.RideModel;
import com.tcl.isport.Model.RunModel;
import com.tcl.isport.Model.WalkModel;

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
