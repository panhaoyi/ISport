package com.tcl.isport.presenter;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.RequestMobileCodeCallback;
import com.tcl.isport.imodel.IUserModel;
import com.tcl.isport.iview.IRegisterActivity;
import com.tcl.isport.model.UserModel;

/**
 * Created by haoyi.pan on 17-9-25.
 */
public class RegisterPresenter {
    private IRegisterActivity iRegisterActivity;
    private IUserModel iUserModel;
    public RegisterPresenter(IRegisterActivity view){
        iRegisterActivity=view;
        iUserModel=new UserModel();
    }

    public void sendVerification(){
//        AVUser.requestMobilePhoneVerifyInBackground(iRegisterActivity.getPhoneNumber(), new RequestMobileCodeCallback() {
//            @Override
//            public void done(AVException e) {
//                if(e==null){
//                    //调用成功
//
//                }
//                else{
//                    //调用失败
//                }
//            }
//        });
    }

    public void register(){
        //iUserModel.addUser(iRegisterActivity.getPhoneNumber(),iRegisterActivity.getPassword(),iRegisterActivity.getVerification());
    }
}
