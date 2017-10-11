package com.tcl.isport.util;

import android.util.Log;

import com.avos.avoscloud.AVObject;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by lishui.lin on 17-9-30 10:10
 */

public class SportUtil {


    //实时时间，
    public synchronized static String getNow() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
        return sdf.format(new Date());
    }

    //获取今天凌晨的日期对象
    public synchronized static Date getToday() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        return calendar.getTime();
    }

    //获取这周周一凌晨的日期对象
    public synchronized static Date getWeek() {
        Calendar calendar = Calendar.getInstance();
        //使其日期减到周一
        while (calendar.get(Calendar.DAY_OF_WEEK) != Calendar.MONDAY) {
            calendar.add(Calendar.DATE, -1);
        }
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        return calendar.getTime();
    }

    //获取本月1号的日期对象
    public synchronized static Date getMonth() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DATE, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        return calendar.getTime();
    }

    //获取今天日期
    public synchronized static String getTodayDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
        return sdf.format(new Date());
    }

    //获取总距离
    public synchronized static String getTotalDistance(List<AVObject> sportDataList) {
        float totalDistance = 0f;
        if (sportDataList != null && !sportDataList.isEmpty()) {
            for (int i = 0; i < sportDataList.size(); i++) {
                if (sportDataList.get(i).get("distance") != null) {
                    totalDistance += Float.valueOf((String) sportDataList.get(i).get("distance"));
                }

            }
            DecimalFormat fnum = new DecimalFormat("#0.00");
            return fnum.format(totalDistance);
        } else {
            //错误值显示在用户界面
            return "0.00";
        }

    }

    //获取总时间
    public synchronized static String getTotalTime(List<AVObject> sportDataList) {

        if (sportDataList != null && !sportDataList.isEmpty()) {
            long time = 0L;
            for (int i = 0; i < sportDataList.size(); i++) {
                if (sportDataList.get(i).get("duration") != null) {
                    time += (Integer) sportDataList.get(i).get("duration");
                }
            }
            if (time < 60) {

                return "00:00:" + time;
            } else if (time < 3600) {
                long min = time / 60;
                long sec = time - min * 60;
                return "00:" + min + ":" + sec;
            } else {
                long hour = time / 3600;
                time = time - hour * 3600;
                long min = time / 60;
                long sec = time - min * 60;
                return hour + ":" + min + ":" + sec;
            }
//            if (time < 60) {
//
//                return time + " 秒";
//            } else if (time < 3600) {
//                long min = time / 60;
//                long sec = time - min * 60;
//                return min + "." + sec + " 分";
//            } else {
//                long hour = time / 3600;
//                time = time - hour * 3600;
//                long min = time / 60;
//                long sec = time - min * 60;
//                return hour + "时" + min + "分" + sec + "秒";
//            }
        } else {
            //错误值显示在用户界面
            return "0 秒";
        }
    }

    //获取总步数
    public synchronized static String getTotalStep(List<AVObject> sportDataList) {
        if (sportDataList != null && !sportDataList.isEmpty()) {
            int steps = 0;
            for (int i = 0; i < sportDataList.size(); i++) {
                steps += (Integer) sportDataList.get(i).get("step");
            }

            //当大于一千步时
            if (steps > 1000) {
                float ksteps = steps / 1000f;
                DecimalFormat fnum = new DecimalFormat("#0.00");
                return fnum.format(ksteps) + "k 步";
            }

            return String.valueOf(steps) + "步";
        } else {
            //错误值显示在用户界面
            return "0 步";
        }
    }

    //获取总步数
    public synchronized static int getTotalStepNum(List<AVObject> sportDataList) {
        if (sportDataList != null && !sportDataList.isEmpty()) {
            int steps = 0;
            for (int i = 0; i < sportDataList.size(); i++) {
                steps += (Integer) sportDataList.get(i).get("step");
            }

            return steps;
        } else {
            //错误值显示在用户界面
            return 0;
        }
    }
    //获取运动总次数
    public synchronized static String getTotalTimes(List<AVObject> sportDataList) {
        if (sportDataList != null && !sportDataList.isEmpty()) {
            return sportDataList.size() + " 次";
        } else {
            return "0 次";
        }
    }
    //获取运动总次数
    public synchronized static int getTotalTimesNum(List<AVObject> sportDataList) {
        if (sportDataList != null && !sportDataList.isEmpty()) {
            return sportDataList.size();
        } else {
            return 0;
        }
    }

    //换算成速度，km/h
    public synchronized static String getAverageSpeed(List<AVObject> sportDataList) {
        if (sportDataList != null && !sportDataList.isEmpty()) {
            float distances = Float.valueOf(getTotalDistance(sportDataList));
            long time = 0L;
            for (int i = 0; i < sportDataList.size(); i++) {
                if (sportDataList.get(i).get("duration") != null) {
                    time += (Integer) sportDataList.get(i).get("duration");
                }
            }
            double hour = time / 3600d;
            if (hour != 0) {
                double averageSpeed = distances / hour;
                DecimalFormat fnum = new DecimalFormat("#0.00");
                return fnum.format(averageSpeed) + " km/h";
            } else {
                return "0.0 km/h";
            }
        } else {
            return "0.0 km/h";
        }
    }

}
