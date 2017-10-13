package com.tcl.isport.presenter;

import com.tcl.isport.imodel.IActivityModel;
import com.tcl.isport.iview.IJoinActivityFragment;
import com.tcl.isport.model.ActivityModel;

import java.util.Map;

/**
 * Created by haoyi.pan on 17-10-12.
 */
public class JoinActivityPresenter {
    private IActivityModel iActivityModel;
    private IJoinActivityFragment iJoinActivityFragment;
    public JoinActivityPresenter(IJoinActivityFragment view){
        iJoinActivityFragment=view;
        iActivityModel=new ActivityModel(this);
        try{
            iActivityModel.findJoinActivity();
        }catch (Exception e){

        }
    }

    public void setActivityData(Map<String, Object> map) {
        iJoinActivityFragment.addData(map);
    }

    public void refreshView() {
        //刷新view
        iJoinActivityFragment.refresh();
    }

    public void refreshData() {
        //刷新数据
        iActivityModel.findJoinActivity();
    }
}
