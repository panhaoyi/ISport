package com.tcl.isport.iview;

import android.graphics.Bitmap;

/**
 * Created by haoyi.pan on 17-9-29.
 */
public interface IInformationActivity {
    //初始化个人资料
    void setPhoto(Bitmap bitmap);

    void setName(String name);
    void setSex(String sex);
    void setBirth(String birth);
    void setCity(String city);
    void setSignature(String signature);

    void refresh();
}
