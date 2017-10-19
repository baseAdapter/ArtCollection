package com.tsutsuku.artcollection.ui.main;

import android.app.Activity;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.tsutsuku.artcollection.R;
import com.tsutsuku.artcollection.common.Intents;
import com.tsutsuku.artcollection.model.CateBean;
import com.tsutsuku.artcollection.ui.base.BaseFragment;
import com.tsutsuku.artcollection.ui.common.CateSelectActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @Author Sun Renwei
 * @Create 2017/1/4
 * @Description Content
 */

public class AuctionFragment extends BaseFragment {
    private static final int CATE_SELECT = 1;
    @BindView(R.id.tlBase)
    TabLayout tlBase;
    @BindView(R.id.vpBase)
    ViewPager vpBase;


    private List<Fragment> fragmentList;
    private String[] titles = {"正在拍", "推荐"};
    private AuctionListFragment nowFragment;
    private AuctionListFragment topFragment;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_auction;
    }

    @Override
    protected void initViews() {
        ButterKnife.bind(this, rootView);
        initTitle(getString(R.string.auction), R.drawable.ic_list_black_24dp);
        tvTitle.setTextColor(getResources().getColor(R.color.c));
        llBack.setVisibility(View.GONE);
        ivTitleButton.setVisibility(View.GONE);

        tlBase.setTabTextColors(getResources().getColor(R.color.d), getResources().getColor(R.color.orange));
    }

    @Override
    protected void initListeners() {

    }

    @Override
    protected void initData() {
        fragmentList = new ArrayList<>();
        nowFragment = AuctionListFragment.newInstanceTypeNow();
        topFragment = AuctionListFragment.newInstanceTypeTop();
        fragmentList.add(nowFragment);
        fragmentList.add(topFragment);

        vpBase.setAdapter(new FragmentAdapter(getChildFragmentManager()));
        tlBase.setupWithViewPager(vpBase);
    }

    @OnClick(R.id.ivTitleButton)
    public void onClick() {
        CateSelectActivity.launchTypeDetail(this, CATE_SELECT);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == CATE_SELECT) {
                if (tlBase.getSelectedTabPosition() == 0) {
                    nowFragment.getData(((CateBean)data.getParcelableExtra(Intents.CATE)).getCateId());
                } else {
                    topFragment.getData(((CateBean)data.getParcelableExtra(Intents.CATE)).getCateId());
                }
            }
        }
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
