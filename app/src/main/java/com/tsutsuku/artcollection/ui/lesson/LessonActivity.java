package com.tsutsuku.artcollection.ui.lesson;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.RelativeLayout;

import com.google.gson.Gson;
import com.tsutsuku.artcollection.R;
import com.tsutsuku.artcollection.common.Constants;
import com.tsutsuku.artcollection.common.Intents;
import com.tsutsuku.artcollection.model.lesson.ItemCate;
import com.tsutsuku.artcollection.ui.base.BaseFragmentActivity;
import com.tsutsuku.artcollection.ui.search.SearchActivity;
import com.tsutsuku.artcollection.utils.GsonUtils;
import com.tsutsuku.artcollection.utils.SharedPref;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @Author Tsutsuku
 * @Create 2017/3/5
 * @Description
 */

public class LessonActivity extends BaseFragmentActivity {
    private static final int MENU = 1;

    @BindView(R.id.tlMain)
    TabLayout tlMain;
    @BindView(R.id.rlClick)
    RelativeLayout rlClick;
    @BindView(R.id.vpMain)
    ViewPager vpMain;

    private List<ItemCate> curList; // 当前选择的类别List
    private List<ItemCate> allList; // 全部类别的List

    public static void launch(Context context) {
        context.startActivity(new Intent(context, LessonActivity.class));
    }

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_lesson);
    }

    @Override
    public void initViews() {
        initTitle("讲堂", R.drawable.icon_search);
        ButterKnife.bind(this);
        tlMain.setTabTextColors(getResources().getColor(R.color.d), getResources().getColor(R.color.orange));
    }

    @Override
    public void initListeners() {

    }

    @Override
    public void initData() {
        allList = GsonUtils.parseJsonArray(SharedPref.getSysString(Constants.VIDEO_CATE_LIST), ItemCate.class);
        curList = GsonUtils.parseJsonArray(SharedPref.getString(Constants.CUR_VIDEO_LIST), ItemCate.class);
        if (curList.size() == 0) {
            for (ItemCate item : allList) {
                if (Integer.valueOf(item.getCateId()) < 0) {
                    curList.add(item);
                }
            }
            SharedPref.putString(Constants.CUR_VIDEO_LIST, new Gson().toJson(curList));
        }

        vpMain.setAdapter(new FragmentAdapter(getSupportFragmentManager()));
        tlMain.setupWithViewPager(vpMain);
    }

    @OnClick({R.id.rlClick, R.id.ivTitleButton})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rlClick: {
                MenuActivity.launch(this, curList, allList, vpMain.getCurrentItem(), MENU);
            }
            break;
            case R.id.ivTitleButton: {
                SearchActivity.launch(context, Constants.Search.ACTIVITY);
            }
            break;
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == MENU) {
            curList.clear();
            curList.addAll((ArrayList) data.getParcelableArrayListExtra(Intents.CUR_LIST));
            SharedPref.putString(Constants.CUR_VIDEO_LIST, new Gson().toJson(curList));
            vpMain.getAdapter().notifyDataSetChanged();
            vpMain.setCurrentItem(data.getIntExtra(Intents.INDEX, 0));
        }
    }

    class FragmentAdapter extends FragmentPagerAdapter {
        public FragmentAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return PageListFragment.newInstance(curList.get(position).getCateId());
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return curList.get(position).getCateName();
        }

        @Override
        public int getCount() {
            return curList.size();
        }
    }
}
