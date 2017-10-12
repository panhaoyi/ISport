package com.tcl.isport.presenter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVFile;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.GetDataCallback;
import com.tcl.isport.imodel.IActivityModel;
import com.tcl.isport.iview.IFindActivityFragment;
import com.tcl.isport.model.ActivityModel;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by haoyi.pan on 17-10-11.
 */
public class FindActivityPresenter {
    private IActivityModel iActivityModel;
    private IFindActivityFragment iFindActivityFragment;
    public FindActivityPresenter(IFindActivityFragment view){
        iFindActivityFragment=view;
        iActivityModel=new ActivityModel(this);
        iActivityModel.findAllActivity();
    }

    public void refreshData(){
        iActivityModel.findAllActivity();
    }

    public void setActivityData(List<AVObject> list){
        final List<Map<String,Object>> data=new ArrayList<>();
        Map<String,Object> map;
        for(AVObject avObject:list){
            map=new HashMap<>();

            map.put("theme",avObject.get("theme").toString());
            String date=avObject.get("time").toString();
            map.put("countdown",compareDate(date));
            map.put("number","人数规模: "+avObject.get("number").toString());
            AVFile avFile= (AVFile) avObject.get("cover");
            final Map<String, Object> finalMap = map;
            avFile.getDataInBackground(new GetDataCallback() {
                @Override
                public void done(byte[] bytes, AVException e) {
                    Bitmap bitmap=BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                    finalMap.put("picture",bitmap);
                    iFindActivityFragment.addData(finalMap);
                    refreshActivity();
                }
            });
        }
    }

    public String compareDate(String date){
        long day,hour,minute;
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
            }else{
                return "倒计时: "+day+"天"+hour+"小时"+minute+"分";
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
    }

    public void refreshActivity(){
        iFindActivityFragment.refresh();
    }

}
