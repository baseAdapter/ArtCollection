package com.tsutsuku.artcollection.view;

import android.content.Context;
import android.support.v4.widget.NestedScrollView;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * @Author Tsutsuku
 * @Create 2017/3/7
 * @Description
 */

public class BaseScrollView extends NestedScrollView {
    private boolean scroll;
    public BaseScrollView(Context context) {
        super(context);
    }

    public BaseScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BaseScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if(scroll){
            return super.onInterceptTouchEvent(ev);
        } else {
            super.onInterceptTouchEvent(ev);
            return false;
        }
    }

    public void setScroll(boolean scroll){
        this.scroll = scroll;
    }
}
