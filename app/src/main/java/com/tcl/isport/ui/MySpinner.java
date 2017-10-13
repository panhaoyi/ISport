package com.tcl.isport.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Spinner;

/**
 * Created by haoyi.pan on 17-10-13.
 */
public class MySpinner extends Spinner {
    public MySpinner(Context context) {
        super(context);
    }

    public MySpinner(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MySpinner(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void setSelection(int position, boolean animate) {
        boolean sameSelected=position==getSelectedItemPosition();
        super.setSelection(position);
        if (sameSelected){
            getOnItemSelectedListener().onItemSelected(this,getSelectedView(),position,getSelectedItemId());
        }
    }

    @Override
    public void setSelection(int position) {
        boolean sameSelected=position==getSelectedItemPosition();
        super.setSelection(position);
        if (sameSelected){
            getOnItemSelectedListener().onItemSelected(this,getSelectedView(),position,getSelectedItemId());
        }
    }
}
