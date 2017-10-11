package com.tcl.isport.util;

import android.text.TextUtils;

/**
 * Created by lishui.lin on 17-10-11 10:32
 */

public class UserUtil {
    //判断是否时有效的手机号码
    public synchronized static boolean checkValidPhoneNumber(String phoneNumber) {
        boolean isValid = false;

        String telRegex = "[1][358]\\d{9}";
        if (TextUtils.isEmpty(phoneNumber)) {
            isValid = false;
        } else {
            isValid = phoneNumber.matches(telRegex);
        }

        return isValid;
    }
}
