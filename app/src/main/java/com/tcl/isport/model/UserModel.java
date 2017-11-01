package com.tcl.isport.model;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVFile;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.FindCallback;
import com.avos.avoscloud.GetDataCallback;
import com.avos.avoscloud.LogInCallback;
import com.avos.avoscloud.RequestMobileCodeCallback;
import com.avos.avoscloud.SaveCallback;
import com.avos.avoscloud.SignUpCallback;
import com.avos.avoscloud.UpdatePasswordCallback;
import com.tcl.isport.imodel.IUserModel;
import com.tcl.isport.presenter.InformationActivityPresenter;
import com.tcl.isport.presenter.LoginPresenter;
import com.tcl.isport.presenter.RegisterPresenter;
import com.tcl.isport.util.LocationUtil;

import java.util.Arrays;
import java.util.List;

/**
 * Created by haoyi.pan on 17-9-18.
 */
public class UserModel implements IUserModel {

    private RegisterPresenter registerPresenter;
    private LoginPresenter loginPresenter;
    private InformationActivityPresenter informationActivityPresenter;

    public UserModel() {
    }

    public UserModel(RegisterPresenter registerPresenter) {
        this.registerPresenter = registerPresenter;
    }

    public UserModel(LoginPresenter loginPresenter) {
        this.loginPresenter = loginPresenter;
    }

    public UserModel(InformationActivityPresenter informationActivityPresenter){
        this.informationActivityPresenter=informationActivityPresenter;
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
        user.setUsername("用户"+phoneNumber);
        user.setMobilePhoneNumber(phoneNumber);
        user.setPassword(password);
        user.signUpInBackground(new SignUpCallback() {
            @Override
            public void done(AVException e) {
                if (e == null) {
                    registerPresenter.setRegisterState(true);
                } else {
                    registerPresenter.setRegisterState(false);
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
                    loginPresenter.setLoginState(10000);
                } else {
                    if (e.getCode() == 210) {
                        loginPresenter.setLoginState(210);
                    } else if (e.getCode() == 211) {
                        loginPresenter.setLoginState(211);
                    } else if (e.getCode() == 213) {
                        loginPresenter.setLoginState(213);
                    } else if (e.getCode() == 0) {
                        loginPresenter.setLoginState(0);
                    } else {
                        loginPresenter.setLoginState(10001);
                    }

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
    public void getVerification(String phoneNumber) {
        AVUser.requestPasswordResetBySmsCodeInBackground(phoneNumber, new RequestMobileCodeCallback() {
            @Override
            public void done(AVException e) {
                if (e == null) {
                    loginPresenter.setFindPwdVerification(true);
                } else {
                    loginPresenter.setFindPwdVerification(false);
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void resetPwd(String verification, String pwd) {
        AVUser.resetPasswordBySmsCodeInBackground(verification, pwd, new UpdatePasswordCallback() {
            @Override
            public void done(AVException e) {
                if (e == null) {
                    loginPresenter.setFindPwdResetPwd(true);
                } else {
                    loginPresenter.setFindPwdResetPwd(false);
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void updateUser(AVUser user) {
        user.saveInBackground(new SaveCallback() {
            @Override
            public void done(AVException e) {
                if (e==null){
//                    informationActivityPresenter.refreshView();
                }else{
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void getPhoto() {
        AVUser avUser=AVUser.getCurrentUser();
        AVFile avFile= (AVFile) avUser.get("photo");
        if (avFile == null) {
            return;
        }
        avFile.getDataInBackground(new GetDataCallback() {
            @Override
            public void done(byte[] bytes, AVException e) {
                if (e == null) {
                    Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);

                    informationActivityPresenter.setPhoto(bitmap);
//                    informationActivityPresenter.refreshView();
                } else {
                    e.printStackTrace();
                }
            }
        });
    }

    public interface IUserModel {
        //未验证手机号和密码注册
        void setCheckPhoneStatea(boolean phoneState);
        void doInCheckPhone();
        void setRegisterState(boolean registerState);
        void doInRegister();
    }

    public interface IUserModelLogin {
        void setLoginState(int loginState);
        void setLoginQuickState(boolean quickLoginState);
        void setLoginQuickVerification(boolean loginQuickVerification);

        void setFindPwdVerification(boolean findPwdVerification);

        void setFindPwdResetPwd(boolean findPwdResetPwd);
    }
}
