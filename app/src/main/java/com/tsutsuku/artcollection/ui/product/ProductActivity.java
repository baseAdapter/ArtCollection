package com.tsutsuku.artcollection.ui.product;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.tsutsuku.artcollection.R;
import com.tsutsuku.artcollection.common.Constants;
import com.tsutsuku.artcollection.model.CateBean;
import com.tsutsuku.artcollection.presenter.circle.CircleModulePresenterImpl;
import com.tsutsuku.artcollection.ui.base.BaseFragmentActivity;
import com.tsutsuku.artcollection.ui.circle.CircleModuleFragment;
import com.tsutsuku.artcollection.ui.circle.ShareActivity;
import com.tsutsuku.artcollection.utils.DensityUtils;
import com.tsutsuku.artcollection.utils.GsonUtils;
import com.tsutsuku.artcollection.utils.SharedPref;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @Author Tsutsuku
 * @Create 2017/4/5
 * @Description
 */

public class ProductActivity extends BaseFragmentActivity {
    @BindView(R.id.tlBase)
    TabLayout tlBase;
    @BindView(R.id.vpBase)
    ViewPager vpBase;

    private List<CateBean> list;

    public static void launch(Context context) {
        context.startActivity(new Intent(context, ProductActivity.class));
    }

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_treasure);
    }

    @Override
    public void initViews() {
        initTitle(R.string.treasure);
        ButterKnife.bind(this);

        tlBase.setTabMode(TabLayout.MODE_SCROLLABLE);
        tlBase.setMinimumWidth(DensityUtils.dp2px(200));
        tlBase.setTabTextColors(getResources().getColor(R.color.d), getResources().getColor(R.color.orange));
    }

    @Override
    public void initListeners() {

    }

    @Override
    public void initData() {
        list = GsonUtils.parseJsonArray(SharedPref.getSysString(Constants.CATE_MORE), CateBean.class);

        vpBase.setAdapter(new FragmentAdapter(getSupportFragmentManager()));
        tlBase.setupWithViewPager(vpBase);
    }

    class FragmentAdapter extends FragmentPagerAdapter {
        public FragmentAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            if (position == 0) {
                return ProductADListFragment.newInstance();
            } else {
                return ProductListFragment.newInstance(list.get(position -1 ).getCateId());
            }
        }

        @Override
        public CharSequence getPageTitle(int position) {
            if (position == 0) {
                return "推荐";
            } else {
                return list.get(position - 1).getCateName();
            }
        }

        @Override
        public int getCount() {
            return list.size() + 1;
        }
    }
}
