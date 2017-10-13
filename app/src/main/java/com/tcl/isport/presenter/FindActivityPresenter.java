package com.tcl.isport.presenter;

import com.tcl.isport.imodel.IActivityModel;
import com.tcl.isport.iview.IFindActivityFragment;
import com.tcl.isport.model.ActivityModel;

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

    public void setActivityData(Map<String, Object> map){
        iFindActivityFragment.addData(map);
//        final List<Map<String,Object>> data=new ArrayList<>();
//        Map<String,Object> map;
//        for(AVObject avObject:list){
//            map=new HashMap<>();
//            map.put("objectId",avObject.getObjectId());
//            map.put("theme",avObject.get("theme").toString());
//            String date=avObject.get("time").toString();
//            map.put("countdown",compareDate(date));
//            map.put("number","人数规模: "+avObject.get("number").toString());
//            AVFile avFile= (AVFile) avObject.get("cover");
//            final Map<String, Object> finalMap = map;
//            avFile.getDataInBackground(new GetDataCallback() {
//                @Override
//                public void done(byte[] bytes, AVException e) {
//                    if (e==null) {
//                        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
//                        finalMap.put("picture", bitmap);
//                        iFindActivityFragment.addData(finalMap);
//                        refreshActivity();
//                    }else {
//                        e.printStackTrace();
//                    }
//                }
//            });
//        }
    }

    public void refreshView(){
        //刷新view
        iFindActivityFragment.refresh();
    }

}
