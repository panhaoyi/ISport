package com.tcl.isport.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.avos.avoscloud.AVObject;
import com.tcl.isport.R;
import com.tcl.isport.activity.ActivityDetailActivity;
import com.tcl.isport.activity.ActivityNewActivity;
import com.tcl.isport.iview.IFindActivityFragment;
import com.tcl.isport.presenter.FindActivityPresenter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by haoyi.pan on 17-9-22.
 */
public class FindActivityFragment extends Fragment implements IFindActivityFragment,OnClickListener,AdapterView.OnItemClickListener {
    //主界面-运动圈-活动
    private View view;
    private ImageView add_activity;
    private ListView listView;
    private SimpleAdapter simpleAdapter;
    private List<Map<String,Object>> data;
    private FindActivityPresenter findActivityPresenter;
    private boolean isFirst=true;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        findActivityPresenter=new FindActivityPresenter(this);
        data=new ArrayList<>();
        view=inflater.inflate(R.layout.fragment_find_activity,container,false);
        add_activity= (ImageView) view.findViewById(R.id.add_activity);
        add_activity.setOnClickListener(this);

        listView= (ListView) view.findViewById(R.id.listview_find_activity);
//        simpleAdapter=new SimpleAdapter(this.getContext(),data,R.layout.item_activity
//                ,new String[]{"picture","theme","countdown","number"}
//                ,new int[]{R.id.picture_item_activity,R.id.theme_item_activity,R.id.countdown_item_activity,R.id.number_item_activity});
//        listView.setAdapter(simpleAdapter);
        listView.setOnItemClickListener(this);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (isFirst!=true) {
            data = new ArrayList<>();
            findActivityPresenter.refreshData();
        }else{
            isFirst=false;
        }
    }

    @Override
    public void refresh(){
        simpleAdapter=new SimpleAdapter(this.getContext(),data,R.layout.item_activity
                ,new String[]{"picture","theme","countdown","number"}
                ,new int[]{R.id.picture_item_activity,R.id.theme_item_activity,R.id.countdown_item_activity,R.id.number_item_activity});
        simpleAdapter.setViewBinder(new SimpleAdapter.ViewBinder() {

            @Override
            public boolean setViewValue(View view, Object data,
                                        String textRepresentation) {
                if (view instanceof ImageView && data instanceof Bitmap) {
                    ImageView iv = (ImageView) view;
                    iv.setImageBitmap((Bitmap) data);
                    return true;
                }
                return false;
            }
        });

        listView.setAdapter(simpleAdapter);
    }

    @Override
    public void addData(Map<String,Object> map) {
//        List<Map<String,Object>> data=new ArrayList<>();
//        Map<String,Object> map;
//        map=new HashMap();
//        map.put("picture",R.drawable.activity1);
//        map.put("theme","2017红花湖马拉松");
//        map.put("countdown","倒计时: 0天8时47分");
//        map.put("number","报名人数: 58");
//        list.add(map);
//        map=new HashMap();
//        map.put("picture",R.drawable.activity2);
//        map.put("theme","骑行千里");
//        map.put("countdown","倒计时: 12天8时47分");
//        map.put("number","报名人数: 18");
//        list.add(map);
//        map=new HashMap();
//        map.put("picture",R.drawable.activity3);
//        map.put("theme","高榜山徒步");
//        map.put("countdown","倒计时: 6天8时47分");
//        map.put("number","报名人数: 28");
//        list.add(map);
//        map=new HashMap();
//        map.put("picture",R.drawable.activity4);
//        map.put("theme","环市区骑行");
//        map.put("countdown","倒计时: 7天12时47分");
//        map.put("number","报名人数: 22");
//        list.add(map);
        data.add(map);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.add_activity:
                //跳转发布活动
                Intent intent =new Intent(getActivity(), ActivityNewActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent=new Intent(getActivity(), ActivityDetailActivity.class);
        intent.putExtra("objectId",data.get(position).get("objectId").toString());
        startActivity(intent);
    }
}