package com.tcl.isport.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.tcl.isport.R;
import com.tcl.isport.activity.NewTopicActivity;

/**
 * Created by haoyi.pan on 17-9-22.
 */
public class FindTopicFragment extends Fragment implements View.OnClickListener {
    private View view;
    private ImageView add;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_find_topic,container,false);
        add= (ImageView) view.findViewById(R.id.add_topic);
        add.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.add_topic:
                Intent intent=new Intent(getActivity(), NewTopicActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }
}
