package com.tcl.isport.util;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.os.CountDownTimer;
import android.util.Log;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by user on 17-8-24.
 */
public class StepDetector implements SensorEventListener {
    //当api<19时，自定义使用加速度传感器的计步器

    private final String TAG = "StepDetector";
    //alpha由t/(t+dT)计算得来,其中t是低通滤波器的时间常数,dT是事件报送频率
    private final double alpha = 0.8;
    private long perCalTime = 0;
    private final double minValue = 8.8;
    private final double maxValue = 10.5;
    private final double verminValue = 9.5;
    private final double vermaxValue = 10.0;
    private final double minTime = 150;
    private final double maxTime = 2000;
    //0准备计时,1计时中,2准备为正常计步计时,3正常计步中
    private int CountTimeState = 0;
    public static int CURRENT_STEP = 0;
    public static int TEMP_STEP = 0;
    private int lastStep = -1;
    //加速计的三个维度数值
    public static double[] gravity = new double[3];
    public static double[] linear_acceleration = new double[3];
    //用三个维度算出的平均值
    public static double average = 0;
    private Timer timer;
    //倒计时3秒,3秒内不会显示计步,用于屏蔽细微波动
    private long duration = 3000;
    private TimeDownCount time;

    OnSensorChangeListener onSensorChangeListener;

    public StepDetector(Context context) {
        super();
    }

    public interface OnSensorChangeListener {
        void onChange();
    }

    public void setOnSensorChangeListener(OnSensorChangeListener onSensorChangeListener) {
        this.onSensorChangeListener = onSensorChangeListener;
    }

    public OnSensorChangeListener getOnSensorChangeListener() {
        return onSensorChangeListener;
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        //传感器精度变化
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        //传感器值变化
        Sensor sensor = event.sensor;
        synchronized (this) {
            if (sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
                //加速度传感器
                //用低通滤波器分离出重力加速度
                gravity[0] = alpha * gravity[0] + (1 - alpha) * event.values[0];
                gravity[1] = alpha * gravity[1] + (1 - alpha) * event.values[1];
                gravity[2] = alpha * gravity[2] + (1 - alpha) * event.values[2];

                average = Math.sqrt(Math.pow(gravity[0], 2) + Math.pow(gravity[1], 2) + Math.pow(gravity[2], 2));
                if (average <= verminValue) {
                    if (average <= minValue) {
                        //当下降到波谷且满足计步范围时,获取当前系统时间
                        perCalTime = System.currentTimeMillis();
                    }
                } else if (average >= vermaxValue) {
                    if (average >= maxValue) {
                        //当上升到波峰且满足计步范围时,获取当前系统时间减去之前得到的波谷时间得到波峰波谷时间差
                        double betweentime = System.currentTimeMillis() - perCalTime;
                        if (betweentime >= minTime && betweentime < maxTime) {
                            //当波峰波谷时间差>=0.15s且<2s时,即满足计步时间差,则更新这次计步
                            perCalTime = 0;
                            if (CountTimeState == 0) {
                                //开启倒计时器
                                time = new TimeDownCount(duration, 1000);
                                time.start();
                                CountTimeState = 1;
                                Log.v(TAG, "开启计时器");
                            } else if (CountTimeState == 1) {
                                TEMP_STEP++;
                                Log.v(TAG, "计步中 TEMP_STEP:" + TEMP_STEP);
                            } else if (CountTimeState == 2) {
                                //创建一个Timer每2秒检测一次是否停止运动
                                //Timer可以创建一个单独的线程定时执行任务,在子类TimerTask写执行的任务
                                //通过Timer的schedule方法添加TimerTask并设定多久后开始以及间隔时间
                                timer = new Timer(true);
                                TimerTask task = new TimerTask() {
                                    @Override
                                    public void run() {
                                        if (lastStep == CURRENT_STEP) {
                                            //当前步数等于最后一次记录的步数时,停止计步
                                            timer.cancel();
                                            CountTimeState = 0;
                                            lastStep = -1;
                                            TEMP_STEP = 0;
                                            Log.v(TAG, "停止计步:" + CURRENT_STEP);
                                        } else {
                                            //否则将最后一次记录的步数改为当前步数
                                            lastStep = CURRENT_STEP;
                                        }
                                    }
                                };
                                //立刻开始,每2秒更新一次
                                timer.schedule(task, 0, 2000);
                                CountTimeState = 3;
                            } else if (CountTimeState == 3) {
                                CURRENT_STEP++;
                            }
                        }
                    }
                }
                if (onSensorChangeListener != null) {
                    onSensorChangeListener.onChange();
                }
            }
        }
    }

    //CountDownTimer是一个倒计时器,millisInFuture表示总时间,countDownInterval表示调用onTick的间隔时间
    class TimeDownCount extends CountDownTimer {

        public TimeDownCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            if (lastStep == TEMP_STEP) {
                Log.v(TAG, "计时停止");
                time.cancel();
                CountTimeState = 0;
                lastStep = -1;
                TEMP_STEP = 0;
            } else {
                lastStep = TEMP_STEP;
            }
        }

        @Override
        public void onFinish() {
            //如果计时器正常结束,则开始计步
            time.cancel();
            CURRENT_STEP += TEMP_STEP;
            lastStep = -1;
            CountTimeState = 2;
            Log.v(TAG, "计时器正常结束");
        }
    }
}
