package com.tsutsuku.artcollection.ui.identify;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.tsutsuku.artcollection.R;
import com.tsutsuku.artcollection.common.BusEvent;
import com.tsutsuku.artcollection.model.BusBean;
import com.tsutsuku.artcollection.presenter.circle.CircleModulePresenterImpl;
import com.tsutsuku.artcollection.ui.base.BaseFragmentActivity;
import com.tsutsuku.artcollection.ui.circle.CircleModuleFragment;
import com.tsutsuku.artcollection.ui.circle.ShareActivity;
import com.tsutsuku.artcollection.utils.RxBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

/**
 * @Author Tsutsuku
 * @Create 2017/5/5
 * @Description
 */

public class IdentifyActivity extends BaseFragmentActivity {
    @BindView(R.id.tlBase)
    TabLayout tlBase;
    @BindView(R.id.vpBase)
    ViewPager vpBase;


    private Observable<BusBean> observable;

    public static void launch(Context context) {
        context.startActivity(new Intent(context, IdentifyActivity.class));
    }

    private List<String> titles = new ArrayList<String>() {{
        add("无偿鉴定");
        add("有偿鉴定");
    }};

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_identify);
    }

    @Override
    public void initViews() {
        ButterKnife.bind(this);
        initTitle("鉴定", "发布");

        tlBase.setTabTextColors(getResources().getColor(R.color.d), getResources().getColor(R.color.orange));
    }

    @Override
    public void initListeners() {

        observable = RxBus.getDefault().register(BusEvent.CIRCLE, BusBean.class);
        observable.observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<BusBean>() {
            @Override
            public void call(BusBean bean) {
                if (bean.getExt() != null){
                    vpBase.setCurrentItem(Integer.valueOf(bean.getExt()));
                }
            }
        });
    }

    @Override
    public void initData() {
        vpBase.setAdapter(new FragmentAdapter(getSupportFragmentManager()));
        tlBase.setupWithViewPager(vpBase);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RxBus.getDefault().unregister(BusEvent.CIRCLE, observable);
    }

    @OnClick(R.id.tvTitleButton)
    public void onViewClicked() {
        ShareActivity.launchTypeIdentify(context);
    }

    class FragmentAdapter extends FragmentPagerAdapter {
        public FragmentAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return CircleModuleFragment.newInstance(CircleModulePresenterImpl.createTypeIdentify(position == 0));
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
