package com.tsutsuku.artcollection.ui.shoppingBase;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.tsutsuku.artcollection.R;
import com.tsutsuku.artcollection.common.Constants;
import com.tsutsuku.artcollection.ui.base.BaseFragmentActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @Author Tsutsuku
 * @Create 2017/2/12
 * @Description 我的订单
 */

public class MineOrdersActivity extends BaseFragmentActivity {
    private static final String TYPE = "type";
    @BindView(R.id.tlBase)
    TabLayout tlBase;
    @BindView(R.id.vpBase)
    ViewPager vpBase;

    private String[] titles = {"全部", "待付款", "待发货", "待收货", "待评价"};
    private List<Fragment> fragmentList;

    public static void launch(Context context, int type) {
        context.startActivity(new Intent(context, MineOrdersActivity.class).putExtra(TYPE, type));
    }

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_mine_orders);
    }

    @Override
    public void initViews() {
        ButterKnife.bind(this);
        initTitle(R.string.mine_orders);

        tlBase.setTabTextColors(getResources().getColor(R.color.d), getResources().getColor(R.color.orange));
    }

    @Override
    public void initListeners() {
    }

    @Override
    public void initData() {
        fragmentList =new ArrayList<>();

        fragmentList.add(OrdersListFragment.newInstance(Constants.ShoppingStatus.ALL));
        fragmentList.add(OrdersListFragment.newInstance(Constants.ShoppingStatus.UNPAID));
        fragmentList.add(OrdersListFragment.newInstance(Constants.ShoppingStatus.UNDELIVERY));
        fragmentList.add(OrdersListFragment.newInstance(Constants.ShoppingStatus.UNRECEIVE));
        fragmentList.add(OrdersListFragment.newInstance(Constants.ShoppingStatus.UNCOMMENT));

        vpBase.setAdapter(new FragmentAdapter(getSupportFragmentManager()));
        tlBase.setupWithViewPager(vpBase);
        vpBase.setOffscreenPageLimit(4);

        vpBase.setCurrentItem(getIntent().getIntExtra(TYPE, 0));
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
            return titles[position];
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }
    }
}
