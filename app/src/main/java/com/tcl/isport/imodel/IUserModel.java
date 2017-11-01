package com.tcl.isport.imodel;

import com.avos.avoscloud.AVUser;

/**
 * Created by haoyi.pan on 17-9-18.
 */
public interface IUserModel {
    //User数据操作
    //注册
    void checkUser(String phoneNumber);
    void registerUser(String phoneNumber,String password);
    //登录
    void loginUser(String phoneNumber,String password);
    //获取快速登录验证码
    void getQuickLoginVerification(String phoneNumber);
    //进行快速登录判断
    void quickLoginUser(String phoneNumber, String verification);
    void getVerification(String phoneNumber);
    void resetPwd(String verification, String pwd);
    //更改个人信息,密码/昵称/个性签名/绑定手机/绑定邮箱
    void updateUser(AVUser user);

    void getPhoto();
}
