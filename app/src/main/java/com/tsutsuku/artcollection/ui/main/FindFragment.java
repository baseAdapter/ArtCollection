package com.tsutsuku.artcollection.ui.main;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.View;

import com.tsutsuku.artcollection.R;
import com.tsutsuku.artcollection.common.BusEvent;
import com.tsutsuku.artcollection.common.Constants;
import com.tsutsuku.artcollection.model.BusBean;
import com.tsutsuku.artcollection.presenter.circle.CircleModulePresenterImpl;
import com.tsutsuku.artcollection.ui.base.BaseFragment;
import com.tsutsuku.artcollection.ui.circle.CircleModuleFragment;
import com.tsutsuku.artcollection.ui.circle.ShareActivity;
import com.tsutsuku.artcollection.ui.login.LoginActivity;
import com.tsutsuku.artcollection.utils.DensityUtils;
import com.tsutsuku.artcollection.utils.RxBus;
import com.tsutsuku.artcollection.utils.SharedPref;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

/**
 * @Author Sun Renwei
 * @Create 2017/1/4
 * @Description Content
 */

public class FindFragment extends BaseFragment {

    @BindView(R.id.tlFind)
    TabLayout tlFind;
    @BindView(R.id.vpBase)
    ViewPager vpBase;
    private List<String> titles = new ArrayList<String>() {{
        add("最新分享");
        add("我的关注");
    }};

    private Observable<BusBean> observable;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_find;
    }

    @Override
    protected void initViews() {
        initTitle(getString(R.string.find), R.string.publish);
        ButterKnife.bind(this, rootView);

        llBack.setVisibility(View.GONE);
        tlFind.setMinimumWidth(DensityUtils.dp2px(200));
        tlFind.setTabTextColors(getResources().getColor(R.color.d), getResources().getColor(R.color.orange));
    }

    @Override
    protected void initListeners() {
        observable = RxBus.getDefault().register(BusEvent.CIRCLE, BusBean.class);
        observable.observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<BusBean>() {
            @Override
            public void call(BusBean bean) {
                if (bean.getType() > 0) {
                    if (bean.getType() == Constants.TYPE_TREASURE) {
                        vpBase.setCurrentItem(0);
                    } else {
                        vpBase.setCurrentItem(1);
                    }
                }
            }
        });

        tvTitleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(SharedPref.getString(Constants.USER_ID))) {
                    ShareActivity.launchTypeTreasure(context);
                } else {
                    LoginActivity.launch(context);
                }
            }
        });

        vpBase.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                tvTitleButton.setVisibility(position == 0 ? View.VISIBLE : View.GONE);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    protected void initData() {

        vpBase.setAdapter(new FindAdapter(getChildFragmentManager()));
        tlFind.setupWithViewPager(vpBase);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        RxBus.getDefault().unregister(BusEvent.CIRCLE, observable);
    }

    class FindAdapter extends FragmentPagerAdapter {
        public FindAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return CircleModuleFragment.newInstance(CircleModulePresenterImpl.createTypeTreasure(position == 1));
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titles.get(position);
        }

        @Override
        public int getCount() {
            return 2;
        }
    }
}
