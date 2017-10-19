package com.tsutsuku.artcollection.ui.collection;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.tsutsuku.artcollection.R;
import com.tsutsuku.artcollection.ui.base.BaseActivity;
import com.tsutsuku.artcollection.ui.base.BaseFragmentActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @Author Tsutsuku
 * @Create 2017/3/25
 * @Description
 */

public class MineCollectionActivity extends BaseFragmentActivity {
    @BindView(R.id.tlBase)
    TabLayout tlBase;
    @BindView(R.id.vpBase)
    ViewPager vpBase;

    private String[] titles = {"讲堂", "商品"};

    public static void launch(Context context) {
        context.startActivity(new Intent(context, MineCollectionActivity.class));
    }

    @Override
    public void setContentView() {
        setContentView(R.layout.base_bar_activity);
    }

    @Override
    public void initViews() {
        initTitle(R.string.mine_collection);
        ButterKnife.bind(this);
        tlBase.setTabTextColors(getResources().getColor(R.color.d), getResources().getColor(R.color.orange));
    }

    @Override
    public void initListeners() {

    }

    @Override
    public void initData() {
        vpBase.setAdapter(new FragmentAdapter(getSupportFragmentManager()));
        tlBase.setupWithViewPager(vpBase);
    }

    class FragmentAdapter extends FragmentPagerAdapter {
        public FragmentAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return CollectionPageFragment.newInstance(String.valueOf(position + 1));
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titles[position];
        }

        @Override
        public int getCount() {
            return 2;
        }
    }
}
