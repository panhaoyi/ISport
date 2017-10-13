package com.tcl.isport.presenter;

import android.content.Context;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.RequestMobileCodeCallback;
import com.tcl.isport.activity.RegisterActivity;
import com.tcl.isport.imodel.IUserModel;
import com.tcl.isport.iview.IRegisterActivity;
import com.tcl.isport.model.UserModel;

/**
 * Created by haoyi.pan on 17-9-25.
 */
public class RegisterPresenter implements UserModel.IUserModel {
    private IRegisterActivity iRegisterActivity;
    private IUserModel iUserModel;
    private boolean registerState;
    private boolean phoneState = false;

    public RegisterPresenter(IRegisterActivity view){
        iRegisterActivity=view;
        iUserModel=new UserModel(this);
    }

    public void registerUser(String phoneNumber, String password) {
        iUserModel.registerUser(phoneNumber, password);
    }

    public void checkUser(String phoneNumber){
        iUserModel.checkUser(phoneNumber);
    }

    @Override
    public void setCheckPhoneStatea(boolean phoneState) {
        this.phoneState = phoneState;
    }

    @Override
    public void doInCheckPhone() {
        if (phoneState) {
            //手机号码被占用
            iRegisterActivity.failCheck();
        }
    }
    @Override
    public void setRegisterState(boolean registerState) {
        this.registerState = registerState;
    }

    @Override
    public void doInRegister() {
        if (registerState) {
            iRegisterActivity.successRegister();
        } else {
            iRegisterActivity.failRegister();
        }
    }
}
