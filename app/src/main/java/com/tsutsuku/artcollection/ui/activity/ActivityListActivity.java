package com.tsutsuku.artcollection.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.tsutsuku.artcollection.R;
import com.tsutsuku.artcollection.common.Constants;
import com.tsutsuku.artcollection.ui.base.BaseActivity;
import com.tsutsuku.artcollection.ui.base.BaseFragmentActivity;
import com.tsutsuku.artcollection.ui.search.SearchActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @Author Tsutsuku
 * @Create 2017/3/12
 * @Description 活动列表Activity
 */

public class ActivityListActivity extends BaseFragmentActivity {
    @BindView(R.id.tlBase)
    TabLayout tlBase;
    @BindView(R.id.vpBase)
    ViewPager vpBase;

    private String[] titles = {"推荐", "线上", "线下"};

    public static void launch(Context context) {
        context.startActivity(new Intent(context, ActivityListActivity.class));
    }
    @Override
    public void setContentView() {
        setContentView(R.layout.activity_activity_list);
    }

    @Override
    public void initViews() {
        initTitle(R.string.activity, R.drawable.icon_search);
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

    @OnClick(R.id.ivTitleButton)
    public void onClick() {
        SearchActivity.launch(context, Constants.Search.ACTIVITY);
    }

    class FragmentAdapter extends FragmentPagerAdapter {
        public FragmentAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return ActivityPageFragment.newInstance(String.valueOf(position));
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titles[position];
        }

        @Override
        public int getCount() {
            return 3;
        }
    }
}
