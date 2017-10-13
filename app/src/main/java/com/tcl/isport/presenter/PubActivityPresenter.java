package com.tcl.isport.presenter;

import com.avos.avoscloud.AVUser;
import com.tcl.isport.imodel.IActivityModel;
import com.tcl.isport.iview.IPubActivityFragment;
import com.tcl.isport.model.ActivityModel;

import java.util.Map;

/**
 * Created by haoyi.pan on 17-10-12.
 */
public class PubActivityPresenter {
    private IActivityModel iActivityModel;
    private IPubActivityFragment iPubActivityFragment;
    public PubActivityPresenter(IPubActivityFragment view){
        iPubActivityFragment=view;
        iActivityModel=new ActivityModel(this);
        try{
            iActivityModel.findPubActivity();
        }catch (Exception e){

        }
    }

    public void setActivityData(Map<String, Object> map) {
        iPubActivityFragment.addData(map);
    }

    public void refreshView() {
        //刷新view
        iPubActivityFragment.refresh();
    }

    public void refreshData() {
        //刷新数据
        iActivityModel.findPubActivity();
    }
}
