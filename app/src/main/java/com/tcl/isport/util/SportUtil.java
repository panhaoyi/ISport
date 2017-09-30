package com.tcl.isport.util;

import com.avos.avoscloud.AVObject;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
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
                if (sportDataList.get(i).get("distance") != null){
                    totalDistance += Float.valueOf((String) sportDataList.get(i).get("distance"));
                }

            }
            DecimalFormat fnum = new DecimalFormat("#0.00");
            return fnum.format(totalDistance);
        } else {
            return null;
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

                return time + "秒";
            } else if (time < 3600) {
                long min = time / 60;
                long sec = time - min * 60;
                return min + "." + sec + "分";
            } else {
                long hour = time / 3600;
                time = time - hour * 3600;
                long min = time / 60;
                long sec = time - min * 60;
                return hour + "时" + min + "分" + sec + "秒";
            }
        } else {
            return null;
        }
    }
}
