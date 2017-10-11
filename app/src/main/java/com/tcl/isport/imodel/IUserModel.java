package com.tcl.isport.imodel;

/**
 * Created by haoyi.pan on 17-9-18.
 */
public interface IUserModel {
    //User数据操作
    //注册
    void addUser(String phoneNumber,String password,String verification);
    void checkUser(String phoneNumber);
    void registerUser(String phoneNumber,String password);
    //登录
    void loginUser(String phoneNumber,String password);
    //更改个人信息,密码/昵称/个性签名/绑定手机/绑定邮箱
    void updateUser();
}
