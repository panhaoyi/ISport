package com.tcl.isport.model;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVMobilePhoneVerifyCallback;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.SaveCallback;
import com.avos.avoscloud.SignUpCallback;
import com.tcl.isport.imodel.IUserModel;

/**
 * Created by haoyi.pan on 17-9-18.
 */
public class UserModel implements IUserModel {
    @Override
    public void addUser(String phoneNumber,String password,String verification) {
        final AVUser user=new AVUser();
        user.setMobilePhoneNumber(phoneNumber);
        user.setPassword(password);
        //验证验证码
//        AVUser.verifyMobilePhoneInBackground(verification, new AVMobilePhoneVerifyCallback() {
//            @Override
//            public void done(AVException e) {
//                if(e==null){
//                    //验证码验证成功
//                    user.saveInBackground(new SaveCallback() {
//                        @Override
//                        public void done(AVException e) {
//                            if(e==null){
//
//                            }
//                        }
//                    });
//                }
//                else{
//                    //验证失败
//                }
//            }
//        });
    }

    @Override
    public void loginUser() {

    }

    @Override
    public void updateUser() {

    }
}
