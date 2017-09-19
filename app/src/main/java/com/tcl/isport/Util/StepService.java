package com.tcl.isport.Util;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.PowerManager;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.tcl.isport.Activity.MainActivity;
import com.tcl.isport.Bean.Constant;
import com.tcl.isport.Bean.StepData;
import com.tcl.isport.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by user on 17-8-10.
 */
public class StepService extends Service implements SensorEventListener {
    //计步器服务
    final String TAG = "StepService";
    private static int duration = 30000;
    private static String CURRENTDATE = "";
    private SensorManager sensorManager;
    private StepDetector stepDetector;
    private NotificationManager nm;
    private NotificationCompat.Builder builder;
    private Messenger messenger = new Messenger(new MessengerHandler());
    private BroadcastReceiver mBatInfoReceiver;
    private PowerManager.WakeLock mWakeLock;
    private TimeCount time;

    private static int i = 0;
    private static int stepSensor = -1;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return messenger.getBinder();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        CURRENTDATE = getTodayDate();
        //注册开屏关屏等广播
        initBroadcastReceiver();
        new Thread(new Runnable() {
            @Override
            public void run() {
                //启动计步检测器
                startStepDetector();
            }

        }).start();
        //开启计时器
        startTimeCount();

        initTodayData();
        updateNotification("今日步数:" + StepDetector.CURRENT_STEP + "步");
    }


    @Override
    public void onDestroy() {
        stopForeground(true);
        DbUtil.closeDb();
        unregisterReceiver(mBatInfoReceiver);
        Intent intent = new Intent(this, StepService.class);
        startService(intent);
        super.onDestroy();
    }

    private String getTodayDate() {
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(date);
    }

    //初始化广播
    private void initBroadcastReceiver() {
        //定义意图过滤器
        final IntentFilter filter = new IntentFilter();
        //屏幕灭屏广播
        filter.addAction(Intent.ACTION_SCREEN_OFF);
        //日期变化
        filter.addAction(Intent.ACTION_TIME_CHANGED);
        //关闭广播
        filter.addAction(Intent.ACTION_SHUTDOWN);
        //屏幕高亮广播
        filter.addAction(Intent.ACTION_SCREEN_ON);
        //屏幕解锁广播
        filter.addAction(Intent.ACTION_USER_PRESENT);
        //长按电源键弹出关机对话框或者锁屏时广播
        //有时候权限高系统对话框会覆盖在锁屏界面或关机对话框上，所以监听这个广播，当收到时隐藏自己的对话
        filter.addAction(Intent.ACTION_CLOSE_SYSTEM_DIALOGS);
        BroadcastReceiver mBatInfoReceiver = new BroadcastReceiver() {

            @Override
            public void onReceive(Context context, Intent intent) {
                String action = intent.getAction();
                switch (action) {
                    case Intent.ACTION_SCREEN_ON:
                        Log.v(TAG, "screen on");
                        break;
                    case Intent.ACTION_SCREEN_OFF:
                        Log.v(TAG, "screen off");
                        save();
                        //改为60s存储一次
                        duration = 60000;
                        break;
                    case Intent.ACTION_USER_PRESENT:
                        Log.v(TAG, "screen unlock");
                        save();
                        //30s
                        duration = 30000;
                        break;
                    case Intent.ACTION_CLOSE_SYSTEM_DIALOGS:
                        Log.v(TAG, "receive Intent.ACTION_CLOSE_SYSTEM_DIALOGS 出现系统对话框");
                        save();
                        break;
                    case Intent.ACTION_SHUTDOWN:
                        Log.v(TAG, "reveive ACTION_SHUTDOWN");
                        save();
                        break;
                    case Intent.ACTION_TIME_CHANGED:
                        Log.v(TAG, "receive ACTION_TIME_CHANGED");
                        initTodayData();
                        break;
                    default:
                        break;
                }
            }
        };
        registerReceiver(mBatInfoReceiver, filter);
    }

    private void save() {
        //保存数据
        int tempStep = StepDetector.CURRENT_STEP;
        List<StepData> list = DbUtil.getQueryByDate(CURRENTDATE);
        if (list.size() == 0 || list.isEmpty()) {
            StepData data = new StepData();
            data.setToday(CURRENTDATE);
            data.setStep(tempStep + "");
            DbUtil.insert(data);
        } else if (list.size() == 1) {
            StepData data = list.get(0);
            data.setStep(tempStep + "");
            DbUtil.update(data);
        } else {
            Log.v(TAG, "error in save()");
        }
    }

    private void startTimeCount() {
        time = new TimeCount(duration, 1000);
        time.start();
    }

    //根据不同API开启Google内置计步器或加速度传感器
    private void startStepDetector() {
        //重置sensorManager和stepDetector
        if (sensorManager != null && stepDetector != null) {
            sensorManager.unregisterListener(stepDetector);
            sensorManager = null;
            stepDetector = null;
        }
        //为了当手机黑屏后仍保持CPU运行从而服务能持续运行,取得休眠锁
        getLock(this);
        sensorManager = (SensorManager) this.getSystemService(SENSOR_SERVICE);
        int VERSION_CODES = Build.VERSION.SDK_INT;
        //API>19使用Google内置计步器
        if (VERSION_CODES > 19) {
            addCountStepListener();
        } else {
            addBasePedoListener();
        }
    }

    synchronized private PowerManager.WakeLock getLock(Context context) {
        if (mWakeLock != null) {
            if (mWakeLock.isHeld()) {
                mWakeLock.release();
            }
            mWakeLock = null;
        }
        if (mWakeLock == null) {
            PowerManager mgr = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
            mWakeLock = mgr.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, StepService.class.getName());
            mWakeLock.setReferenceCounted(true);
            Calendar c = Calendar.getInstance();
            c.setTimeInMillis(System.currentTimeMillis());
            int hour = c.get(Calendar.HOUR_OF_DAY);
            if (hour >= 23 || hour <= 6) {
                mWakeLock.acquire(5000);
            } else {
                mWakeLock.acquire(300000);
            }
        }
        return mWakeLock;
    }

    private void addCountStepListener() {
        //API>19,首先拿到两种传感器,TYPE_STEP_COUNTER计步传感器用于记录本次开机后的步数,重启手机将重置
        //TYPE_STEP_DETECTOR步行检测传感器,每走一步触发一次事件
        Sensor detectorSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR);
//        Sensor countSensor=sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
        Sensor countSensor = null;
        if (countSensor != null) {
            stepSensor = 0;
            Log.v(TAG, "countSensor");
            sensorManager.registerListener(StepService.this, countSensor, SensorManager.SENSOR_DELAY_UI);
        } else if (detectorSensor != null) {
            stepSensor = 1;
            Log.v("base", "detector");
            sensorManager.registerListener(StepService.this, detectorSensor, SensorManager.SENSOR_DELAY_UI);
        } else {
            Log.v("xf", "Count sensor not available!");
            addBasePedoListener();
        }
    }

    private void addBasePedoListener() {
        stepDetector = new StepDetector(this);
        //获得加速度传感器
        Sensor sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        //注册传感器,SensorEventListener,Sensor,更新速率
        sensorManager.registerListener(stepDetector, sensor, SensorManager.SENSOR_DELAY_UI);
        stepDetector.setOnSensorChangeListener(new StepDetector.OnSensorChangeListener() {
            @Override
            public void onChange() {
                updateNotification("今日步数: " + StepDetector.CURRENT_STEP + " 步");
            }
        });
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //从数据库中初始化今日步数，并更新通知栏
        initTodayData();
        updateNotification("今日步数: " + StepDetector.CURRENT_STEP + " 步");
        return START_STICKY;
    }

    //获取当天数据,若为空,步数为0,若存在则获取数据库中的步数
    private void initTodayData() {
        DbUtil.createDb(this);
        List<StepData> list = DbUtil.getQueryByDate(CURRENTDATE);
        if (list.size() == 0 || list.isEmpty()) {
            stepDetector.CURRENT_STEP = 0;
        } else if (list.size() == 1) {
            stepDetector.CURRENT_STEP = Integer.parseInt(list.get(0).getStep());
        } else {
            Log.v(TAG, "error in initTodayData()");
        }
    }

    //更新通知
    private void updateNotification(String content) {
        builder = new NotificationCompat.Builder(this);
        builder.setPriority(Notification.PRIORITY_MIN);
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, new Intent(this, MainActivity.class), 0);
        builder.setContentIntent(contentIntent);
        builder.setSmallIcon(R.mipmap.ic_launcher);
        builder.setTicker("Pedometer");
        builder.setContentTitle("Pedometer");
        //设置不可清除
        builder.setOngoing(true);
        builder.setContentText(content);
        Notification notification = builder.build();
        startForeground(0, notification);
        nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        nm.notify(R.string.app_name, notification);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (stepSensor == 0) {
            StepDetector.CURRENT_STEP = (int) event.values[0];
        } else if (stepSensor == 1) {
            StepDetector.CURRENT_STEP++;
        }
        updateNotification("今日步数: " + StepDetector.CURRENT_STEP + " 步");
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    //与MainActivity通讯
    class MessengerHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case Constant.MSG_FROM_CLIENT:
                    try {
                        Messenger messenger = msg.replyTo;
                        Message replyMsg = Message.obtain(null, Constant.MSG_FROM_SERVER);
                        Bundle bundle = new Bundle();
                        bundle.putInt("step", StepDetector.CURRENT_STEP);
                        replyMsg.setData(bundle);
                        //发送要返回的消息
                        messenger.send(replyMsg);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                    break;
                default:
                    super.handleMessage(msg);
            }
        }
    }

    private class TimeCount extends CountDownTimer {

        public TimeCount(long millisInFuture, long countDownInteval) {
            super(millisInFuture, countDownInteval);
        }

        @Override
        public void onTick(long millisUntilFinished) {

        }

        @Override
        public void onFinish() {
            //如果计时器正常结束,则保存一次步数
            time.cancel();
            save();
            startTimeCount();
        }
    }
}
