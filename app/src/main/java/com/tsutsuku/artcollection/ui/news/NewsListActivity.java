package com.tsutsuku.artcollection.ui.news;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.tsutsuku.artcollection.R;
import com.tsutsuku.artcollection.common.Constants;
import com.tsutsuku.artcollection.model.ItemNewsCate;
import com.tsutsuku.artcollection.presenter.find.NewsPresenterImpl;
import com.tsutsuku.artcollection.ui.base.BaseFragmentActivity;
import com.tsutsuku.artcollection.ui.base.BaseRvFragment;
import com.tsutsuku.artcollection.ui.search.SearchActivity;
import com.tsutsuku.artcollection.utils.DensityUtils;
import com.tsutsuku.artcollection.utils.GsonUtils;
import com.tsutsuku.artcollection.utils.SharedPref;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @Author Tsutsuku
 * @Create 2017/3/25
 * @Description 资讯
 */

public class NewsListActivity extends BaseFragmentActivity {

    @BindView(R.id.tlBase)
    TabLayout tlBase;
    @BindView(R.id.vpBase)
    ViewPager vpBase;
    private List<String> titles;
    List<Fragment> fragmentList;

    public static void launch(Context context) {
        context.startActivity(new Intent(context, NewsListActivity.class));
    }

    @Override
    public void setContentView() {
        setContentView(R.layout.base_bar_activity);
    }

    @Override
    public void initViews() {
        initTitle("资讯", R.drawable.icon_search);
        ButterKnife.bind(this);


        tlBase.setTabTextColors(getResources().getColor(R.color.d), getResources().getColor(R.color.orange));
    }

    @Override
    public void initListeners() {

    }

    @Override
    public void initData() {
        fragmentList = new ArrayList<>();
        titles = new ArrayList<>();

        List<ItemNewsCate> list = GsonUtils.parseJsonArray(SharedPref.getSysString(Constants.NEWS_CATE_LIST), ItemNewsCate.class);
        for (ItemNewsCate item : list) {
            titles.add(item.getCateName());
            fragmentList.add(BaseRvFragment.newInstance(new NewsPresenterImpl(item.getCateId())));
        }

        if (list.size() > 4) {
            tlBase.setTabMode(TabLayout.MODE_SCROLLABLE);
            tlBase.setMinimumWidth(DensityUtils.dp2px(200));
        }
        vpBase.setAdapter(new FragmentAdapter(getSupportFragmentManager()));
        tlBase.setupWithViewPager(vpBase);
    }

    @OnClick(R.id.ivTitleButton)
    public void onViewClicked() {
        SearchActivity.launch(context, Constants.Search.NEWS);
    }

    class FragmentAdapter extends FragmentPagerAdapter {
        public FragmentAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titles.get(position);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }
    }
}
