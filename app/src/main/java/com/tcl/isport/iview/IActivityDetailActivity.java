package com.tcl.isport.iview;

import com.tcl.isport.bean.ActivityBean;

/**
 * Created by haoyi.pan on 17-10-12.
 */
public interface IActivityDetailActivity {
    void setData(ActivityBean activityBean, int joinNumber);
    void setData(ActivityBean activityBean, int joinNumber, String objectId);
    void refreshView(int operationCode);
}
