package com.tcl.isport.presenter;


import com.tcl.isport.imodel.IUserModel;
import com.tcl.isport.iview.IFindPasswordActivity;
import com.tcl.isport.iview.ILoginActivity;
import com.tcl.isport.iview.ILoginQuickActivity;
import com.tcl.isport.model.UserModel;

/**
 * Created by haoyi.pan on 17-9-18.
 */
public class LoginPresenter implements  UserModel.IUserModelLogin{
    private static final String TAG = "LoginPresenter";

    private ILoginActivity iLoginActivity;
    private ILoginQuickActivity iLoginQuickActivity;
    private IFindPasswordActivity iFindPasswordActivity;
    private IUserModel iUserModel;

    public LoginPresenter(ILoginActivity view){
        this.iLoginActivity=view;
        this.iUserModel = new UserModel(this);
    }

    public LoginPresenter(ILoginQuickActivity iLoginQuickActivity) {
        this.iLoginQuickActivity = iLoginQuickActivity;
        this.iUserModel = new UserModel(this);
    }

    public LoginPresenter(IFindPasswordActivity iFindPasswordActivity) {
        this.iFindPasswordActivity = iFindPasswordActivity;
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

    public void getResetVerification(String phoneNumber) {
        iUserModel.getVerification(phoneNumber);
    }

    public void resetPwd(String verification, String pwd) {
        iUserModel.resetPwd(verification, pwd);
    }

    @Override
    public void setLoginState(int loginState) {
        //回调结果处理结果
        if (10000 == loginState) {
            //登录成功，跳转到主页面
            iLoginActivity.successLogin();
        } else if (0 == loginState){
            //登录失败，网络异常
            iLoginActivity.failLogin(0);
        } else if (210 == loginState){
            //登录失败，用户名和密码不匹配
            iLoginActivity.failLogin(210);
        } else if (211 == loginState){
            //登录失败，找不到用户
            iLoginActivity.failLogin(211);
        } else if (213 == loginState){
            //登录失败，手机号码对应用户不存在
            iLoginActivity.failLogin(213);
        } else if (10001 == loginState){
            //登录失败，未知原因
            iLoginActivity.failLogin(10001);
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

    @Override
    public void setFindPwdVerification(boolean findPwdVerification) {
        if (!findPwdVerification) {
            iFindPasswordActivity.successVerificaton(false);
        }
    }

    @Override
    public void setFindPwdResetPwd(boolean findPwdResetPwd) {
        if (findPwdResetPwd) {
            iFindPasswordActivity.successReset(true);
        } else {
            iFindPasswordActivity.successReset(false);
        }
    }
}
