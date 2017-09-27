package com.tcl.isport.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.tcl.isport.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by haoyi.pan on 17-9-23.
 */
public class JoinActivityActivity extends Activity implements AdapterView.OnItemClickListener {
    ////主界面-运动圈-活动-参加活动
    private ImageView back;
    private ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_activity_join);
        listView= (ListView) findViewById(R.id.listview_show_activity);
        SimpleAdapter simpleAdapter=new SimpleAdapter(this,getData(),R.layout.item_activity,new String[]{"a","b"},new int[]{R.id.item_activity_a,R.id.item_activity_b});
        listView.setAdapter(simpleAdapter);
        listView.setOnItemClickListener(this);
    }

    private List<Map<String,String>> getData() {
        List<Map<String,String>> list=new ArrayList<>();
        for (int i=0;i<8;i++){
            Map<String,String> map=new HashMap<>();
            map.put("a",(i+1)+"");
            map.put("b",(i+1)+"");
            list.add(map);
        }
        return list;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//        ListView lv= (ListView) parent;
//        Cursor data= (Cursor) lv.getItemAtPosition(position);
//        int _id=data.getInt(data.getColumnIndex("_id"));
    }
}
