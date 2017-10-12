package com.tcl.isport.iview;

/**
 * Created by haoyi.pan on 17-9-25.
 */
public interface IRegisterActivity {
    String getPhoneNumber();
    String getPassword();
    String getVerification();
    void failCheck();
    void successRegister();
    void failRegister();

}
