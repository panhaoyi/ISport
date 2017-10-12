package com.tcl.isport.model;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.FindCallback;
import com.avos.avoscloud.LogInCallback;
import com.avos.avoscloud.RequestMobileCodeCallback;
import com.avos.avoscloud.SignUpCallback;
import com.avos.avoscloud.ops.BaseOp;
import com.tcl.isport.imodel.IUserModel;
import com.tcl.isport.presenter.LoginPresenter;
import com.tcl.isport.presenter.RegisterPresenter;

import java.util.Arrays;
import java.util.List;

/**
 * Created by haoyi.pan on 17-9-18.
 */
public class UserModel implements IUserModel {

    private RegisterPresenter registerPresenter;
    private LoginPresenter loginPresenter;

    public UserModel() {
    }

    public UserModel(RegisterPresenter registerPresenter) {
        this.registerPresenter = registerPresenter;
    }

    public UserModel(LoginPresenter loginPresenter) {
        this.loginPresenter = loginPresenter;
    }

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
    public void checkUser(final String phoneNumber) {
        AVQuery<AVUser> userAVUser = new AVQuery<>("_User");
        userAVUser.whereEqualTo("mobilePhoneNumber", phoneNumber);
        userAVUser.selectKeys(Arrays.asList("mobilePhoneNumber"));
        userAVUser.findInBackground(new FindCallback<AVUser>() {
            @Override
            public void done(List<AVUser> list, AVException e) {
                if (e == null) {
                    if (list.size() > 0) {
                        registerPresenter.setCheckPhoneStatea(true);

                    } else {
                        registerPresenter.setCheckPhoneStatea(false);
                    }
                }
                registerPresenter.doInCheckPhone();
            }
        });
    }

    @Override
    public void registerUser(String phoneNumber, String password) {
        AVUser user = new AVUser();
        user.setUsername(phoneNumber);
        user.setMobilePhoneNumber(phoneNumber);
        user.setPassword(password);
        user.signUpInBackground(new SignUpCallback() {
            @Override
            public void done(AVException e) {
                if (e == null) {
                    registerPresenter.setRegisterState(true);
                } else {
                    registerPresenter.setRegisterState(false);
//                    if (e.getCode() == AVException.INVALID_PHONE_NUMBER) {
//
//                    }
                }
                registerPresenter.doInRegister();
            }
        });
    }

    @Override
    public void loginUser(String phoneNumber,String password) {
        AVUser.loginByMobilePhoneNumberInBackground(phoneNumber, password, new LogInCallback<AVUser>() {
            @Override
            public void done(AVUser avUser, AVException e) {
                if (e == null) {
                    //登录成功
                    loginPresenter.setLoginState(true);
                } else {
                    loginPresenter.setLoginState(false);
                }
            }
        });
    }

    @Override
    public void getQuickLoginVerification(String phoneNumber) {
        AVUser.requestLoginSmsCodeInBackground(phoneNumber, new RequestMobileCodeCallback() {
            @Override
            public void done(AVException e) {
                if (e == null) {
                    loginPresenter.setLoginQuickVerification(true);
                } else {
                    loginPresenter.setLoginQuickVerification(false);
                }
            }
        });
    }

    @Override
    public void quickLoginUser(String phoneNumber, String verification) {
        AVUser.signUpOrLoginByMobilePhoneInBackground(phoneNumber, verification, new LogInCallback<AVUser>() {
            @Override
            public void done(AVUser avUser, AVException e) {
                if (e == null) {
                    //快速登录成功
                    loginPresenter.setLoginQuickState(true);
                } else {
                    loginPresenter.setLoginQuickState(false);
                }
            }
        });
    }

    @Override
    public void updateUser() {

    }

    public interface IUserModel {
        void setCheckPhoneStatea(boolean phoneState);
        void doInCheckPhone();
        void setRegisterState(boolean registerState);
        void doInRegister();
    }

    public interface IUserModelLogin {
        void setLoginState(boolean loginState);
        void setLoginQuickState(boolean quickLoginState);
        void setLoginQuickVerification(boolean loginQuickVerification);
    }
}
