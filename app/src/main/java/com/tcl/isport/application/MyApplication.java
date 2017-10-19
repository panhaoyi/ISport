package com.tcl.isport.application;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;

import com.avos.avoscloud.AVAnalytics;
import com.avos.avoscloud.AVOSCloud;
import com.tcl.isport.R;
import com.tcl.isport.presenter.FriendFragmentPresenter;

import cn.leancloud.chatkit.LCChatKit;

/**
 * Created by haoyi.pan on 17-9-25.
 */
public class MyApplication extends Application {
//    private static final String APP_ID="L170AceMRxUFJVxa9cjQoBc1-gzGzoHsz";
//    private static final String APP_KEY="YLJjFIgV2Q1QPSwEKDtmxfAv";

    //Add by lishui.lin  for test leanCloud in sport section
    private static final String APP_ID="jOUCLfbGMIsMi1UGXwYGfBLV-gzGzoHsz";
    private static final String APP_KEY="doOKhSNbWlpFczb6z7pkvcQA";

    public static int statusBarHeight = -1;

    @Override
    public void onCreate() {
        super.onCreate();
        //初始化leancloud
//        AVOSCloud.initialize(this,"L170AceMRxUFJVxa9cjQoBc1-gzGzoHsz","YLJjFIgV2Q1QPSwEKDtmxfAv");
        //此函数用于设置用户体系
//        LCChatKit.getInstance().setProfileProvider(FriendFragmentPresenter.getInstance());
        LCChatKit.getInstance().init(getApplicationContext(), APP_ID, APP_KEY);
//        AVOSCloud.initialize(this,APP_ID,APP_KEY);
        //开启leancloud debug log
        AVOSCloud.setDebugLogEnabled(true);
        AVAnalytics.enableCrashReport(this, true);

        //获取状态栏高度
        //获取status_bar_height资源的ID
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            //根据资源ID获取响应的尺寸值
            statusBarHeight = getResources().getDimensionPixelSize(resourceId);
        }
    }
    public static void hide(Activity activity,int resourceId){

        //隐藏虚拟按键
        activity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                |View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                |View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                |View.SYSTEM_UI_FLAG_IMMERSIVE);
        //设置布局marginTop为状态栏高度
        FrameLayout.LayoutParams layoutParams;
        layoutParams= (FrameLayout.LayoutParams) activity.findViewById(resourceId).getLayoutParams();
        layoutParams.setMargins(0, MyApplication.statusBarHeight,0,0);
        activity.findViewById(resourceId).setLayoutParams(layoutParams);
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
}
