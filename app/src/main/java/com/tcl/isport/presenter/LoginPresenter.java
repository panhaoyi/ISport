package com.tcl.isport.presenter;

import com.tcl.isport.imodel.IUserModel;
import com.tcl.isport.iview.ILoginActivity;
import com.tcl.isport.iview.ILoginQuickActivity;
import com.tcl.isport.model.UserModel;

/**
 * Created by haoyi.pan on 17-9-18.
 */
public class LoginPresenter implements  UserModel.IUserModelLogin{
    private ILoginActivity iLoginActivity;
    private ILoginQuickActivity iLoginQuickActivity;
    private IUserModel iUserModel;

    public LoginPresenter(ILoginActivity view){
        this.iLoginActivity=view;
        this.iUserModel = new UserModel(this);
    }

    public LoginPresenter(ILoginQuickActivity iLoginQuickActivity) {
        this.iLoginQuickActivity = iLoginQuickActivity;
        this.iUserModel = new UserModel(this);
    }

    public void startLogin(String phoneNumber, String password) {
        iUserModel.loginUser(phoneNumber, password);
    }

    public void startLoginQuickVerification(String phoneNumber) {
        iUserModel.getQuickLoginVerification(phoneNumber);
    }

    public void statrtLoginQuick(String phoneNumber, String verification) {
        iUserModel.quickLoginUser(phoneNumber, verification);
    }
    @Override
    public void setLoginState(boolean loginState) {
        //回调结果处理结果
        if (loginState) {
            //登录成功，跳转到主页面
            iLoginActivity.successLogin();
        } else {
            //登录失败，提示信息
            iLoginActivity.failLogin();
        }
    }

    @Override
    public void setLoginQuickState(boolean quickLoginState) {
        //快速登录状态
        if (quickLoginState) {
            iLoginQuickActivity.successLogin();
        } else {
            iLoginQuickActivity.failLogin();
        }
    }

    @Override
    public void setLoginQuickVerification(boolean loginQuickVerification) {
        if (loginQuickVerification) {
            iLoginQuickActivity.successVerification();
        } else {
            iLoginQuickActivity.failVerification();
        }
    }
}
