package com.tcl.isport.Activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.tcl.isport.R;

/**
 * Created by user on 17-9-12.
 */
public class SportsHistoryActivity extends Activity implements View.OnClickListener {
    //主界面-我-历史记录
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_sports_history);
    }

    @Override
    public void onClick(View v) {

    }
}
