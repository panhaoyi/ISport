package com.tcl.isport.presenter;

import com.tcl.isport.imodel.IUserModel;
import com.tcl.isport.iview.ILoginActivity;
import com.tcl.isport.model.UserModel;

/**
 * Created by haoyi.pan on 17-9-18.
 */
public class LoginPresenter implements  UserModel.IUserModelLogin{
    private ILoginActivity iLoginActivity;
    private IUserModel iUserModel;

    public LoginPresenter(ILoginActivity view){
        this.iLoginActivity=view;
        iUserModel=new UserModel(this);
    }

    public void startLogin(String phoneNumber,String password) {
        iUserModel.loginUser(phoneNumber, password);
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
}
