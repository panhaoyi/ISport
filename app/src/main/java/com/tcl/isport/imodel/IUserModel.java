package com.tcl.isport.imodel;

/**
 * Created by haoyi.pan on 17-9-18.
 */
public interface IUserModel {
    //User数据操作
    //注册
    public void addUser(String phoneNumber,String password,String verification);
    //登录
    public void loginUser();
    //更改个人信息,密码/昵称/个性签名/绑定手机/绑定邮箱
    public void updateUser();
}
