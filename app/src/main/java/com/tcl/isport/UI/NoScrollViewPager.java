package com.tcl.isport.UI;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by user on 17-9-5.
 */
public class NoScrollViewPager extends ViewPager {
    //自定义不能滑动的ViewPager
    private boolean isCanScroll;

    public NoScrollViewPager(Context context) {
        super(context);
    }

    public NoScrollViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setCanScroll(boolean isCanScroll) {
        //调用该方法设置isCanScroll的boolean值
        this.isCanScroll = isCanScroll;
    }


    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return isCanScroll && super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        //当isCanScroll设为false，则不能滑动
        return isCanScroll && super.onTouchEvent(ev);
    }
}
