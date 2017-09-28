package com.tcl.isport.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.tcl.isport.R;
import com.tcl.isport.activity.ActivityDetailActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by haoyi.pan on 17-9-28.
 */
public class PubActivityFragment extends Fragment implements AdapterView.OnItemClickListener {
    //主界面-我-活动管理-我发布的
    private View view;
    private ListView listView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_activity_pub,container,false);
        listView= (ListView) view.findViewById(R.id.listview_pub);
        SimpleAdapter simpleAdapter=new SimpleAdapter(this.getContext(),getData(),R.layout.item_activity
                ,new String[]{"picture","theme","countdown","number"}
                ,new int[]{R.id.picture_item_activity,R.id.theme_item_activity,R.id.countdown_item_activity,R.id.number_item_activity});
        listView.setAdapter(simpleAdapter);
        listView.setOnItemClickListener(this);
        return view;
    }
    private List<Map<String,Object>> getData() {
        List<Map<String,Object>> list=new ArrayList<>();
        Map<String,Object> map;
        map=new HashMap();
        map.put("picture",R.drawable.activity1);
        map.put("theme","2017红花湖马拉松");
        map.put("countdown","倒计时: 0天8时47分");
        map.put("number","报名人数: 58");
        list.add(map);
        map=new HashMap();
        map.put("picture",R.drawable.activity2);
        map.put("theme","骑行千里");
        map.put("countdown","倒计时: 12天8时47分");
        map.put("number","报名人数: 18");
        list.add(map);
        map=new HashMap();
        map.put("picture",R.drawable.activity3);
        map.put("theme","高榜山徒步");
        map.put("countdown","倒计时: 6天8时47分");
        map.put("number","报名人数: 28");
        list.add(map);
        map=new HashMap();
        map.put("picture",R.drawable.activity4);
        map.put("theme","环市区骑行");
        map.put("countdown","倒计时: 7天12时47分");
        map.put("number","报名人数: 22");
        list.add(map);

        return list;
    }
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent=new Intent(getActivity(), ActivityDetailActivity.class);
        startActivity(intent);
    }
}