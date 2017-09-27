package com.tcl.isport.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.tcl.isport.R;

/**
 * Created by haoyi.pan on 17-9-27.
 */
public class MyArrayAdapter extends ArrayAdapter<String> {
    private Activity activity;

    public MyArrayAdapter(Activity activity, Context context, int resource, String[] objects) {
        super(context, resource, objects);
        this.activity = activity;
    }


    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            //设置spinner展开的Item布局
            convertView = activity.getLayoutInflater().inflate(R.layout.spinner_item, parent, false);
        }
        TextView spinnerText = (TextView) convertView.findViewById(R.id.spinner_textView);
        spinnerText.setText(getItem(position));
        return convertView;
    }
}
