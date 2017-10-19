package com.tsutsuku.artcollection.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * @Author Sun Renwei
 * @Create 2017/1/17
 * @Description Content
 */

public class PicRecyclerView extends RecyclerView {
    public PicRecyclerView(Context context) {
        super(context);
    }

    public PicRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public PicRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent e) {
        View child = this.findChildViewUnder(e.getX(), e.getY());
        if (child == null) {
            return true;
        }
        return super.onInterceptTouchEvent(e);
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        View child = this.findChildViewUnder(e.getX(), e.getY());
        if (child == null) {
            return false;
        }
        return super.onTouchEvent(e);
    }
}
