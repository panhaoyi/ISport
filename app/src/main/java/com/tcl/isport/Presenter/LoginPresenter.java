package com.tcl.isport.Presenter;

import com.tcl.isport.IModel.IUserModel;
import com.tcl.isport.IView.ILoginActivity;
import com.tcl.isport.Model.UserModel;

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
