package com.tcl.isport.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by haoyi.pan on 17-10-12.
 */
public class DateUtil {
    public static String compareDate(String date,int type){
        long day,hour,minute;
        date=date+":00";
        DateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date date1=dateFormat.parse(date);
            Date date2=new Date();
            dateFormat.format(date2);
            //倒计时(分钟)
            long countdown=(date1.getTime()-date2.getTime())/1000/60;
            day=countdown/60/24;
            hour=countdown%(60*24)/60;
            minute=countdown%60;
            if(countdown<0){
                return "活动已结束";
            }else if(type==1){
                return "倒计时: "+day+"天"+hour+"小时"+minute+"分";
            }else if(type==2){
                return "距离活动开始还有"+day+"天"+hour+"小时"+minute+"分";
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static boolean compareDate(String dateBefore,String dateAfter){
        dateBefore=dateBefore+":00";
        dateAfter=dateAfter+":00";
        DateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date date1=dateFormat.parse(dateBefore);
            Date date2=dateFormat.parse(dateAfter);

            if(date1.getTime()-date2.getTime()>=0){
                return false;
            }else{
                return true;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return false;
    }

}
