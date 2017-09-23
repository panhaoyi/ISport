package com.tcl.isport.presenter;

import com.tcl.isport.iModel.IUserModel;
import com.tcl.isport.iView.ILoginActivity;
import com.tcl.isport.model.UserModel;

/**
 * Created by haoyi.pan on 17-9-18.
 */
public class LoginPresenter {
    private ILoginActivity iLoginActivity;
    private IUserModel iUserModel;

    public LoginPresenter(ILoginActivity view){
        this.iLoginActivity=view;
        iUserModel=new UserModel();
    }

}
