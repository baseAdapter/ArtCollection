package com.tsutsuku.artcollection.other.rent.ui;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.Space;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.listener.OnItemClickListener;
import com.tsutsuku.artcollection.R;
import com.tsutsuku.artcollection.common.Constants;
import com.tsutsuku.artcollection.model.shopping.ProductInfoBean;
import com.tsutsuku.artcollection.other.rent.contract.RentDetailContract;
import com.tsutsuku.artcollection.other.rent.model.RentDetailModelImpl;
import com.tsutsuku.artcollection.other.rent.presenter.RentDetailPresenterImpl;
import com.tsutsuku.artcollection.ui.BannerHolder.BannerImageHolder;
import com.tsutsuku.artcollection.ui.base.BaseFragmentActivity;
import com.tsutsuku.artcollection.ui.base.NestedWebFragment;
import com.tsutsuku.artcollection.ui.base.WebActivity;
import com.tsutsuku.artcollection.ui.circle.PhotoViewActivity;
import com.tsutsuku.artcollection.ui.login.LoginActivity;
import com.tsutsuku.artcollection.utils.SharedPref;
import com.tsutsuku.artcollection.view.ShadeBlankTitle;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @Author Sun Renwei
 * @Create 2017/1/23
 * @Description Content
 */

public class RentDetailActivity extends BaseFragmentActivity implements RentDetailContract.View {
    private static final String PRODUCT_ID = "productId";


    @BindView(R.id.vpBase)
    ViewPager vpBase;
    @BindView(R.id.flChat)
    FrameLayout flChat;
    @BindView(R.id.btnAdd)
    Button btnAdd;
    @BindView(R.id.btnBuy)
    Button btnBuy;
    @BindView(R.id.tvName)
    TextView tvName;
    @BindView(R.id.tvPrice)
    TextView tvPrice;
    @BindView(R.id.ivRemove)
    ImageView ivRemove;
    @BindView(R.id.tvDate)
    TextView tvDate;
    @BindView(R.id.ivAdd)
    ImageView ivAdd;
    @BindView(R.id.ivShare)
    ImageView ivShare;
    @BindView(R.id.ivCollection)
    ImageView ivCollection;
    @BindView(R.id.llTab)
    LinearLayout llTab;
    @BindView(R.id.tlBase)
    TabLayout tlBase;
    @BindView(R.id.cbBase)
    ConvenientBanner cbBase;
    @BindView(R.id.ctlBase)
    CollapsingToolbarLayout ctlBase;
    @BindView(R.id.ablBase)
    AppBarLayout ablBase;


    RentDetailContract.Presenter presenter;

    private String titles[] = {"商品详情", "租赁须知"};
    private List<Fragment> fragmentList = new ArrayList<>();
    private ShadeBlankTitle shadeTitle;

    public static void launch(Context context, String productId) {
        context.startActivity(new Intent(context, RentDetailActivity.class).putExtra(PRODUCT_ID, productId));
    }

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_rent_detail);
    }

    @Override
    public void initViews() {
        ButterKnife.bind(this);
        presenter = new RentDetailPresenterImpl(this, getIntent().getStringExtra(PRODUCT_ID), context, new RentDetailModelImpl());

        tlBase.setTabTextColors(getResources().getColor(R.color.d), getResources().getColor(R.color.orange));

        shadeTitle = new ShadeBlankTitle(context, new ShadeBlankTitle.BlankFunctionListener() {
            @Override
            public void back() {
                finish();
            }
        }, 200);

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
        presenter.start();
    }

    @OnClick({R.id.ivCollection, R.id.btnAdd, R.id.btnBuy, R.id.flChat, R.id.ivShare, R.id.ivAdd, R.id.ivRemove})
    public void onClick(View view) {
        if (TextUtils.isEmpty(SharedPref.getString(Constants.USER_ID))) {
            LoginActivity.launch(context);
            return;
        }

        switch (view.getId()) {
            case R.id.ivCollection:
                presenter.collection();
                break;
            case R.id.btnAdd:
                presenter.add();
                break;
            case R.id.btnBuy:
                presenter.buy(tvDate.getText().toString());
                break;
            case R.id.flChat:
                presenter.chat();
                break;
            case R.id.ivShare:
                presenter.share();
                break;
            case R.id.ivAdd:
                tvDate.setText(String.valueOf(Integer.valueOf(tvDate.getText().toString()) + 1));
                break;
            case R.id.ivRemove:
                if (!"1".equals(tvDate.getText().toString())) {
                    tvDate.setText(String.valueOf(Integer.valueOf(tvDate.getText().toString()) - 1));
                }
                break;
        }
    }

    @Override
    public void setData(final ProductInfoBean bean) {
        tvName.setText(bean.getProductName());
        SpannableStringBuilder builder = new SpannableStringBuilder("租金：");
        builder.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.c)), 0, builder.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        String rent = "¥" + bean.getTotalPrice() + bean.getPriceUnit();
        builder.append(rent);
        String real = "  市值：¥" + bean.getGuzhiPrice();
        builder.append(real);
        builder.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.d)), builder.length() - real.length(), builder.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        builder.setSpan(new AbsoluteSizeSpan(13, true), builder.length() - real.length(), builder.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        tvPrice.setText(builder);

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
                        if (!TextUtils.isEmpty(bean.getBriefPics().get(position))) {
                            PhotoViewActivity.launchTypeView(context, position, (ArrayList) bean.getBriefPics());
                        }
                    }
                })
                .startTurning(4000);


        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 1);
        if (!TextUtils.isEmpty(bean.getProduct_buy_url())) {
            LinearLayout flTab = (LinearLayout) LayoutInflater.from(context).inflate(R.layout.item_vendor_tab, null);
            flTab.setLayoutParams(layoutParams);
            ((TextView) flTab.findViewById(R.id.tvTab)).setText("租赁承诺");
            flTab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    WebActivity.launch(context, bean.getProduct_buy_url());
                }
            });
            ((ImageView) flTab.findViewById(R.id.ivTab)).setImageResource(R.drawable.icon_tab_correct);
            llTab.addView(flTab, 0);
        } else {
            Space space = new Space(context);
            space.setLayoutParams(layoutParams);
            llTab.addView(space);
        }

        if (!TextUtils.isEmpty(bean.getProduct_zpbz_url())) {
            LinearLayout flTab = (LinearLayout) LayoutInflater.from(context).inflate(R.layout.item_vendor_tab, null);
            flTab.setLayoutParams(layoutParams);
            ((TextView) flTab.findViewById(R.id.tvTab)).setText(R.string.vendor_guarantee_tab);
            flTab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    WebActivity.launch(context, bean.getProduct_zpbz_url());
                }
            });
            ((ImageView) flTab.findViewById(R.id.ivTab)).setImageResource(R.drawable.icon_tab_right);
            llTab.addView(flTab);
        } else {
            Space space = new Space(context);
            space.setLayoutParams(layoutParams);
            llTab.addView(space);
        }

        fragmentList.add(NestedWebFragment.newInstance(bean.getDetailUrl()));
        fragmentList.add(NestedWebFragment.newInstance(bean.getParameterUrl()));

        vpBase.getAdapter().notifyDataSetChanged();
    }

    @Override
    public void setCollection(boolean isCollection) {
        ivCollection.setImageResource(isCollection ? R.drawable.icon_collect_pressed_xxhdpi : R.drawable.icon_collect_normal_xxhdpi);
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
