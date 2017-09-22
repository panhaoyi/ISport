package com.tcl.isport.Activity;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.widget.TextView;

import com.tcl.isport.Bean.Constant;
import com.tcl.isport.R;
import com.tcl.isport.Service.StepService;

/**
 * Created by haoyi.pan on 17-9-18.
 */
public class StepCountActivity extends Activity implements Handler.Callback {
    private int TIME_INTERVAL = 0;
    public static TextView step;
    private Handler delayHandler;
    private Messenger mGetReplyMessenger = new Messenger(new Handler(this)), messenger;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_stepcount);
        step = (TextView) findViewById(R.id.step_stepcount);
        delayHandler = new Handler(this);
    }

    @Override
    public void onStart() {
        super.onStart();
        //在onStart()中开启服务使程序即使退到后台再到前台时也能开启服务
        setupService();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
       unbindService(conn);
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
        onBackPressed();
    }

    //开启StepService服务
    private void setupService() {
        Intent intent = new Intent(this, StepService.class);
        bindService(intent, conn, Context.BIND_AUTO_CREATE);
        startService(intent);
    }

    //开启服务使用bind形式而有ServiceConnection接收回调
    ServiceConnection conn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            try {
                messenger = new Messenger(service);
                Message msg = Message.obtain(null, Constant.MSG_FROM_CLIENT);
                msg.replyTo = mGetReplyMessenger;
                //发送消息
                messenger.send(msg);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    @Override
    public boolean handleMessage(Message msg) {
        switch (msg.what) {
            case Constant.MSG_FROM_SERVER:
                //更新
                step.setText(msg.getData().getInt("step") + " 步");
                delayHandler.sendEmptyMessageDelayed(Constant.REQUEST_SERVER, TIME_INTERVAL);
                break;
            case Constant.REQUEST_SERVER:
                try {
                    Message msg1 = Message.obtain(null, Constant.MSG_FROM_CLIENT);
                    msg1.replyTo = mGetReplyMessenger;
                    messenger.send(msg1);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                break;
        }
        return false;
    }
}
