package com.tsutsuku.artcollection.ui.product;

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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.listener.OnItemClickListener;
import com.bumptech.glide.Glide;
import com.tsutsuku.artcollection.R;
import com.tsutsuku.artcollection.common.Constants;
import com.tsutsuku.artcollection.contract.shopping.ProductDetailContract;
import com.tsutsuku.artcollection.model.shopping.ProductInfoBean;
import com.tsutsuku.artcollection.presenter.ProductDetailPresenterImpl;
import com.tsutsuku.artcollection.ui.BannerHolder.BannerImageHolder;
import com.tsutsuku.artcollection.ui.base.BaseFragmentActivity;
import com.tsutsuku.artcollection.ui.base.NestedWebFragment;
import com.tsutsuku.artcollection.ui.base.WebActivity;
import com.tsutsuku.artcollection.ui.circle.PhotoViewActivity;
import com.tsutsuku.artcollection.ui.login.LoginActivity;
import com.tsutsuku.artcollection.ui.shoppingBase.ShoppingCartActivity;
import com.tsutsuku.artcollection.utils.DensityUtils;
import com.tsutsuku.artcollection.utils.SharedPref;
import com.tsutsuku.artcollection.utils.glideTransform.CircleTransform;
import com.tsutsuku.artcollection.view.ShadeShoppingTitle;

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

public class ProductDetailActivity extends BaseFragmentActivity implements ProductDetailContract.View {
    private static final String PRODUCT_ID = "productId";

    ProductDetailPresenterImpl presenter;
    @BindView(R.id.ablBase)
    AppBarLayout ablBase;
    @BindView(R.id.cbBase)
    ConvenientBanner cbBase;
    @BindView(R.id.tvName)
    TextView tvName;
    @BindView(R.id.tvPrice)
    TextView tvPrice;
    @BindView(R.id.ivMerchant)
    ImageView ivMerchant;
    @BindView(R.id.tvMerchantName)
    TextView tvMerchantName;
    @BindView(R.id.tvMerchantInfo)
    TextView tvMerchantInfo;
    @BindView(R.id.ibFollow)
    ImageButton ibFollow;
    @BindView(R.id.tlBase)
    TabLayout tlBase;
    @BindView(R.id.vpBase)
    ViewPager vpBase;
    @BindView(R.id.flChat)
    FrameLayout flChat;
    @BindView(R.id.btnAdd)
    Button btnAdd;
    @BindView(R.id.btnBuy)
    Button btnBuy;
    @BindView(R.id.ctlBase)
    CollapsingToolbarLayout ctlBase;
    @BindView(R.id.llTab)
    LinearLayout llTab;
    @BindView(R.id.ivCollection)
    ImageView ivCollection;
    @BindView(R.id.tvCollection)
    TextView tvCollection;
    @BindView(R.id.flCollection)
    FrameLayout flCollection;

    private String titles[] = {"拍品详情", "拍品参数", "帮助和保障"};
    private List<Fragment> fragmentList = new ArrayList<>();
    private ShadeShoppingTitle shadeTitle;

    public static void launch(Context context, String productId) {
        context.startActivity(new Intent(context, ProductDetailActivity.class).putExtra(PRODUCT_ID, productId));
    }

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_product_detail);
    }

    @Override
    public void initViews() {
        ButterKnife.bind(this);
        presenter = new ProductDetailPresenterImpl(context, getIntent().getStringExtra(PRODUCT_ID));
        presenter.attachView(this);
        tlBase.setTabTextColors(getResources().getColor(R.color.d), getResources().getColor(R.color.orange));

        shadeTitle = new ShadeShoppingTitle(context, new ShadeShoppingTitle.ShoppingFunctionListener() {
            @Override
            public void gotoCart() {
                ShoppingCartActivity.launch(context);
            }

            @Override
            public void back() {
                finish();
            }

            @Override
            public void share() {
                presenter.share();
            }
        }, 170);

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

    @OnClick({R.id.ibFollow, R.id.btnAdd, R.id.btnBuy, R.id.flChat, R.id.flCollection})
    public void onClick(View view) {
        if (TextUtils.isEmpty(SharedPref.getString(Constants.USER_ID))){
            LoginActivity.launch(context);
            return;
        }
        switch (view.getId()) {
            case R.id.ibFollow:
                presenter.follow();
                break;
            case R.id.btnAdd:
                presenter.add();
                break;
            case R.id.btnBuy:
                presenter.buy();
                break;
            case R.id.flChat:
                presenter.chat();
                break;
            case R.id.flCollection: {
                presenter.collection();
            }
            break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detachView();
    }

    @Override
    public void setData(final ProductInfoBean bean) {
        tvName.setText(bean.getProductName());
        SpannableStringBuilder builder = new SpannableStringBuilder("¥" + bean.getTotalPrice());
        builder.setSpan(new AbsoluteSizeSpan(DensityUtils.sp2px(13)), 0, 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        tvPrice.setText(builder);

        Glide.with(context).load(bean.getFarmCover()).transform(new CircleTransform(context)).into(ivMerchant);
        tvMerchantName.setText(bean.getFarmName());
        ibFollow.setImageResource(bean.getIsFollow() == 1 ? R.drawable.icon_followed : R.drawable.icon_follow);

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
                });

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 1);
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

        if (!TextUtils.isEmpty(bean.getProduct_yzsj_url())) {
            LinearLayout flTab = (LinearLayout) LayoutInflater.from(context).inflate(R.layout.item_vendor_tab, null);
            flTab.setLayoutParams(layoutParams);
            ((TextView) flTab.findViewById(R.id.tvTab)).setText(R.string.vendor_authentication_tab);
            flTab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    WebActivity.launch(context, bean.getProduct_yzsj_url());
                }
            });
            ((ImageView) flTab.findViewById(R.id.ivTab)).setImageResource(R.drawable.icon_tab_author);
            llTab.addView(flTab, 0);
        } else {
            Space space = new Space(context);
            space.setLayoutParams(layoutParams);
            llTab.addView(space);
        }

        if (!TextUtils.isEmpty(bean.getProduct_buy_url())) {
            LinearLayout flTab = (LinearLayout) LayoutInflater.from(context).inflate(R.layout.item_vendor_tab, null);
            flTab.setLayoutParams(layoutParams);
            ((TextView) flTab.findViewById(R.id.tvTab)).setText(R.string.vendor_buy_tab);
            flTab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    WebActivity.launch(context, bean.getProduct_buy_url());
                }
            });
            ((ImageView) flTab.findViewById(R.id.ivTab)).setImageResource(R.drawable.icon_tab_right);
            llTab.addView(flTab, 0);
        } else {
            Space space = new Space(context);
            space.setLayoutParams(layoutParams);
            llTab.addView(space);
        }

        fragmentList.add(NestedWebFragment.newInstance(bean.getDetailUrl()));
        fragmentList.add(NestedWebFragment.newInstance(bean.getParameterUrl()));
        fragmentList.add(NestedWebFragment.newInstance(bean.getHelpUrl()));

        vpBase.getAdapter().notifyDataSetChanged();
    }

    @Override
    public void setFollow(boolean isFollow) {
        ibFollow.setImageResource(isFollow ? R.drawable.icon_followed : R.drawable.icon_follow);
    }

    @Override
    public void setCollection(boolean isCollection) {
        tvCollection.setText(isCollection ? "已收藏" : "收藏");
        ivCollection.setImageResource(isCollection ? R.drawable.icon_collected : R.drawable.icon_collection);
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
