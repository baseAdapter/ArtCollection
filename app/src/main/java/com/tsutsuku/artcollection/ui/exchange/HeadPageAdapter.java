package com.tsutsuku.artcollection.ui.exchange;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/10/10.
 */

public class HeadPageAdapter extends PagerAdapter {

    private ViewPager mViewPager;
    private List<ImageView> mData;

    public HeadPageAdapter(ViewPager viewPager , List<ImageView> data) {
        if (data != null){
            mData = data;
        }else {
            mData = new ArrayList<>();
        }
        this.mViewPager = viewPager;
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(mData.get(position));
        return mData.get(position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(mData.get(position));
    }
}
