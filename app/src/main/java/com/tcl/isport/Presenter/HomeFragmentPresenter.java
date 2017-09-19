package com.tcl.isport.Presenter;

import com.tcl.isport.Fragment.HomeRideFragment;
import com.tcl.isport.Fragment.HomeRunFragment;
import com.tcl.isport.Fragment.HomeWalkFragment;
import com.tcl.isport.IModel.ISportModel;
import com.tcl.isport.IView.IHomeFragment;
import com.tcl.isport.Model.RideModel;
import com.tcl.isport.Model.RunModel;
import com.tcl.isport.Model.WalkModel;

/**
 * Created by haoyi.pan on 17-9-19.
 */
public class HomeFragmentPresenter {
    //主界面-首页HomeFragment中三个Fragment共用的业务逻辑处理Presenter
    private ISportModel iSportModel;
    private IHomeFragment iHomeFragment;

    public HomeFragmentPresenter(IHomeFragment view){
        //构造器通过参数拿到view实例化view接口，根据view的类型初始化model
        this.iHomeFragment=view;
        if(view.getClass().getName().equals(HomeWalkFragment.class.getName())){
            iSportModel=new WalkModel();
        }
        else if(view.getClass().getName().equals(HomeRunFragment.class.getName())){
            iSportModel=new RunModel();
        }
        else if(view.getClass().getName().equals(HomeRideFragment.class.getName())){
            iSportModel=new RideModel();
        }
    }

}
