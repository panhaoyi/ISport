package com.tcl.isport.util;

/**
 * Created by lishui.lin on 17-9-26 16:14
 */

public class TimeCounter {

    //多线程，不能为静态内部单例模式
    public TimeCounter() {

    }

//    public static  TimeCounter getInstance() {
//        return TimeCounterHolder.sTimeCounter;
//    }
//
//    private static class  TimeCounterHolder {
//        private static final TimeCounter sTimeCounter = new TimeCounter();
//    }


    private CountingThread countingThread ;
    //程序创建获取现有时间
    private long timeStart = System.currentTimeMillis();
    //程序一开始是暂停
    private long pauseStart = timeStart;
    //程序暂停总的时间
    private long pauseCount = 0;
    //计时的总时间
    private long time = 0;

    private boolean isRun = true;

    private boolean isTimeRun = true;

    public void startTime() {

        if (isTimeRun) {
            countingThread = new CountingThread();
            countingThread.start();
            isTimeRun = false;
        }
        if (countingThread.isStopped) {
            pauseCount += (System.currentTimeMillis() - pauseStart);
            countingThread.isStopped = false;
        }

    }

    public void pauseTime() {

        if (!countingThread.isStopped) {
            pauseStart = System.currentTimeMillis();
            countingThread.isStopped = true;
        }
    }

    public void stopTime() {

        if (!isTimeRun) {
            //清零时间
            pauseStart = timeStart;
            pauseCount = 0;
            countingThread.isStopped = true;
            countingThread = null;
            isRun = false;
            isTimeRun = true;
        }

    }

    public String getTime() {
        return format(time);
    }

    //将毫秒数格式化、
    private String format(long duration) {

        int hour, minute, second, milli;

        milli = (int) (duration % 1000);
        duration = duration / 1000;

        second = (int) (duration % 60);
        duration = duration / 60;

        minute = (int) (duration % 60);
        duration = duration / 60;

        hour = (int) (duration % 60);

        return String.format("%02d:%02d:%02d", hour, minute, second);
    }

    private class CountingThread extends Thread {

        public boolean isStopped = true;

        public CountingThread() {
            //设置为守护线程
           setDaemon(true);
        }
        @Override
        public void run() {
            super.run();
            while (isRun) {
                if (!isStopped){
                    time = System.currentTimeMillis() - timeStart - pauseCount;
                }
                try {
                    sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }


        }
    }
}
