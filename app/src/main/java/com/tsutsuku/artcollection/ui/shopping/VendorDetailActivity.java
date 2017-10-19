package com.tsutsuku.artcollection.ui.shopping;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.listener.OnItemClickListener;
import com.bumptech.glide.Glide;
import com.tsutsuku.artcollection.R;
import com.tsutsuku.artcollection.common.Constants;
import com.tsutsuku.artcollection.contract.shopping.VendorDetailContract;
import com.tsutsuku.artcollection.model.shopping.VendorInfoBean;
import com.tsutsuku.artcollection.presenter.VendorDetailPresenterImpl;
import com.tsutsuku.artcollection.ui.BannerHolder.BannerImageHolder;
import com.tsutsuku.artcollection.ui.base.BaseFragmentActivity;
import com.tsutsuku.artcollection.ui.circle.PhotoViewActivity;
import com.tsutsuku.artcollection.ui.login.LoginActivity;
import com.tsutsuku.artcollection.utils.SharedPref;
import com.tsutsuku.artcollection.utils.glideTransform.CircleTransform;
import com.tsutsuku.artcollection.view.ShadeNormalTitle;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @Author Sun Renwei
 * @Create 2017/3/1
 * @Description Content
 */

public class VendorDetailActivity extends BaseFragmentActivity implements VendorDetailContract.View {
    private static final String VENDOR_ID = "vendorId";
    @BindView(R.id.cbBase)
    ConvenientBanner cbBase;
    @BindView(R.id.tvSales)
    TextView tvSales;
    @BindView(R.id.tvScore)
    TextView tvScore;
    @BindView(R.id.tlBase)
    TabLayout tlBase;
    @BindView(R.id.ctlBase)
    CollapsingToolbarLayout ctlBase;
    @BindView(R.id.ablBase)
    AppBarLayout ablBase;
    @BindView(R.id.vpBase)
    ViewPager vpBase;
    @BindView(R.id.ivMerchant)
    ImageView ivMerchant;
    @BindView(R.id.tvMerchantName)
    TextView tvMerchantName;
    @BindView(R.id.tvMerchantInfo)
    TextView tvMerchantInfo;
    @BindView(R.id.ibFollow)
    ImageButton ibFollow;

    private ShadeNormalTitle shadeTitle;
    private String[] titles = {"一口价", "拍卖"};
    private VendorDetailPresenterImpl presenter;

    public static void launch(Context context, String vendorId) {
        context.startActivity(new Intent(context, VendorDetailActivity.class).putExtra(VENDOR_ID, vendorId));
    }

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_vendor_detail);
    }

    @Override
    public void initViews() {
        ButterKnife.bind(this);
        presenter = new VendorDetailPresenterImpl(context, getIntent().getStringExtra(VENDOR_ID));
        presenter.attachView(this);

        tlBase.setTabTextColors(getResources().getColor(R.color.d), getResources().getColor(R.color.orange));

        shadeTitle = new ShadeNormalTitle(context, new ShadeNormalTitle.NormalFunctionListener() {
            @Override
            public void back() {
                finish();
            }

            @Override
            public void share() {
                presenter.share();
            }
        }, 150);
        ctlBase.addView(shadeTitle.getRootView());
    }

    @Override
    public void initListeners() {
        ablBase.addOnOffsetChangedListener(shadeTitle.getOffsetChangedListener());
    }

    @Override
    public void initData() {
        vpBase.setAdapter(new FragmentAdapter(getSupportFragmentManager()));
        tlBase.setupWithViewPager(vpBase);
    }

    @Override
    public void setViews(final VendorInfoBean bean) {
        cbBase.setPages(
                new CBViewHolderCreator<BannerImageHolder>() {
                    @Override
                    public BannerImageHolder createHolder() {
                        return new BannerImageHolder();
                    }
                }, bean.getBriefPics())
                //设置两个点图片作为翻页指示器，不设置则没有指示器，可以根据自己需求自行配合自己的指示器,不需要圆点指示器可用不设
                .setPageIndicator(new int[]{R.drawable.icon_dot, R.drawable.icon_dot_selected})
                //设置指示器的方向
                .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL)
                .setOnItemClickListener(new OnItemClickListener() {
                    @Override
                    public void onItemClick(int position) {
                        PhotoViewActivity.launchTypeView(context, position, (ArrayList) bean.getBriefPics());
                    }
                });
        Glide.with(context).load(bean.getCoverPic()).transform(new CircleTransform(context)).into(ivMerchant);
        tvMerchantName.setText(bean.getFarmName());
        tvMerchantInfo.setText("粉丝：" + bean.getFollowCount());
        tvSales.setText("成交：¥" + bean.getSale());
        tvScore.setText("好评：" + bean.getScore() + "%");
        ibFollow.setImageResource(bean.getIsFollow() == 1 ? R.drawable.icon_followed : R.drawable.icon_follow);
    }

    @Override
    public void setFollow(boolean isFollow) {
        ibFollow.setImageResource(isFollow ? R.drawable.icon_followed : R.drawable.icon_follow);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detachView();
    }

    @OnClick(R.id.ibFollow)
    public void onClick() {
        if (TextUtils.isEmpty(SharedPref.getString(Constants.USER_ID))) {
            LoginActivity.launch(context);
            return;
        }
        presenter.follow();
    }

    class FragmentAdapter extends FragmentPagerAdapter {
        public FragmentAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return VendorDetailFragment.newInstance(getIntent().getStringExtra(VENDOR_ID), position != 0);
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
