package com.tsutsuku.artcollection.ui.auction;

import android.app.Activity;
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
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MotionEvent;
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
import com.tsutsuku.artcollection.common.BusEvent;
import com.tsutsuku.artcollection.contract.AuctionDetailContract;
import com.tsutsuku.artcollection.model.shopping.ProductInfoBean;
import com.tsutsuku.artcollection.model.shopping.ProductInfoBean.AuctionRecodeBean;
import com.tsutsuku.artcollection.presenter.AuctionDetailPresenterImpl;
import com.tsutsuku.artcollection.ui.BannerHolder.BannerImageHolder;
import com.tsutsuku.artcollection.ui.base.BaseFragmentActivity;
import com.tsutsuku.artcollection.ui.base.NestedWebFragment;
import com.tsutsuku.artcollection.ui.base.WebActivity;
import com.tsutsuku.artcollection.ui.circle.PhotoViewActivity;
import com.tsutsuku.artcollection.utils.RxBus;
import com.tsutsuku.artcollection.utils.glideTransform.CircleTransform;
import com.tsutsuku.artcollection.view.ShadeBlankTitle;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

/**
 * @Author Sun Renwei
 * @Create 2017/2/20
 * @Description 拍卖详情页面
 */

public class AuctionDetailActivity extends BaseFragmentActivity implements AuctionDetailContract.View {
    private static final String PRODUCT_ID = "productId";

    @BindView(R.id.vpBase)
    ViewPager vpBase;
    @BindView(R.id.cbBase)
    ConvenientBanner cbBase;
    @BindView(R.id.tlBase)
    TabLayout tlBase;
    @BindView(R.id.tvName)
    TextView tvName;
    @BindView(R.id.tvPrice)
    TextView tvPrice;
    @BindView(R.id.tvOnlookers)
    TextView tvOnlookers;
    @BindView(R.id.tvOffers)
    TextView tvOffers;
    @BindView(R.id.tvDeposit)
    TextView tvDeposit;
    @BindView(R.id.llTab)
    LinearLayout llTab;
    @BindView(R.id.tvAuctionNum)
    TextView tvAuctionNum;
    @BindView(R.id.rvAuction)
    RecyclerView rvAuction;
    @BindView(R.id.tvStartPrice)
    TextView tvStartPrice;
    @BindView(R.id.tvDelayTime)
    TextView tvDelayTime;
    @BindView(R.id.tvStepPrice)
    TextView tvStepPrice;
    @BindView(R.id.tvAuctionFee)
    TextView tvAuctionFee;
    @BindView(R.id.ivMerchant)
    ImageView ivMerchant;
    @BindView(R.id.tvMerchantName)
    TextView tvMerchantName;
    @BindView(R.id.tvMerchantInfo)
    TextView tvMerchantInfo;
    @BindView(R.id.ibFollow)
    ImageButton ibFollow;
    @BindView(R.id.ctlBase)
    CollapsingToolbarLayout ctlBase;
    @BindView(R.id.ablBase)
    AppBarLayout ablBase;
    @BindView(R.id.flChat)
    FrameLayout flChat;
    @BindView(R.id.tvCurPrice)
    TextView tvCurPrice;
    @BindView(R.id.flBid)
    FrameLayout flBid;
    @BindView(R.id.btnDeposit)
    Button btnDeposit;
    @BindView(R.id.ivShare)
    ImageView ivShare;
    @BindView(R.id.ivCollection)
    ImageView ivCollection;
    @BindView(R.id.tvStatus)
    TextView tvStatus;
    @BindView(R.id.tvApplyTime)
    TextView tvApplyTime;
    @BindView(R.id.flStatus)
    FrameLayout flStatus;


    private AuctionDetailPresenterImpl presenter;
    private BidDialog bidDialog;
    private ShadeBlankTitle shadeTitle;

    private Observable<AuctionRecodeBean> bidBeanObservable;

    private String titles[] = {"拍品详情", "拍品参数", "帮助和保障"};
    private List<Fragment> fragmentList = new ArrayList<>();

    public static void launch(Context context, String productId) {
        context.startActivity(new Intent(context, AuctionDetailActivity.class).putExtra(PRODUCT_ID, productId));
    }

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_auction_detail);
    }

    @Override
    public void initViews() {
        ButterKnife.bind(this);
        presenter = new AuctionDetailPresenterImpl(context);
        presenter.attachView(this);
        tlBase.setTabTextColors(getResources().getColor(R.color.d), getResources().getColor(R.color.orange));

        LinearLayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        rvAuction.setLayoutManager(layoutManager);
        rvAuction.setAdapter(presenter.getAdapter());

        shadeTitle = new ShadeBlankTitle(context, new ShadeBlankTitle.BlankFunctionListener() {

            @Override
            public void back() {
                finish();
            }

        }, 170);
        ctlBase.addView(shadeTitle.getRootView());

        vpBase.setAdapter(new FragmentAdapter(getSupportFragmentManager()));
        tlBase.setupWithViewPager(vpBase);
    }

    @Override
    public void initListeners() {
        bidBeanObservable = RxBus.getDefault().register(BusEvent.BID, AuctionRecodeBean.class);
        bidBeanObservable.observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<AuctionRecodeBean>() {
            @Override
            public void call(AuctionRecodeBean recodeBean) {
                if (!presenter.getAdapter().getData().contains(recodeBean)) {
                    bidDialog.setPrice(recodeBean.getPrice());
                    tvCurPrice.setText(getString(R.string.cur_price) + " ¥" + recodeBean.getPrice());
                    presenter.addRecord(recodeBean);
                    rvAuction.scrollToPosition(0);
                }
            }
        });

        ablBase.addOnOffsetChangedListener(shadeTitle.getOffsetChangedListener());
        rvAuction.setNestedScrollingEnabled(false);
        rvAuction.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
                int action = e.getAction();
                switch (action) {
                    case MotionEvent.ACTION_MOVE:
                    case MotionEvent.ACTION_DOWN:
                        rv.getParent().requestDisallowInterceptTouchEvent(true);
                        break;
                }
                return false;
            }

            @Override
            public void onTouchEvent(RecyclerView rv, MotionEvent e) {

            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

            }
        });
    }

    @Override
    public void initData() {
        presenter.getData(getIntent().getStringExtra(PRODUCT_ID));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RxBus.getDefault().unregister(BusEvent.BID, bidBeanObservable);
        presenter.exitRoom();
        presenter.detachView();
    }

    @Override
    public void setView(final ProductInfoBean bean) {
        bidDialog = new BidDialog(context, bean);

        tvName.setText(bean.getProductName());
        tvCurPrice.setText(getString(R.string.cur_price) + " ¥" + bean.getAuctionInfo().getCurrentPrice());
        tvPrice.setText(getString(R.string.cur_price) + "¥" + bean.getAuctionInfo().getCurrentPrice());

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
                })
                .startTurning(3000);
        Glide.with(context).load(bean.getFarmCover()).transform(new CircleTransform(context)).into(ivMerchant);
        tvMerchantName.setText(bean.getFarmName());
        tvMerchantInfo.setText(bean.getBrief());

        tvOnlookers.setText("围观" + bean.getAuctionInfo().getViewCount() + "次");
        tvOffers.setText("出价" + bean.getAuctionInfo().getBidCount() + "人");
        tvDeposit.setText("保证金¥" + bean.getAuctionInfo().getDeposit());

        tvStartPrice.setText(bean.getAuctionInfo().getBeginPrice());
        tvDelayTime.setText(bean.getAuctionInfo().getDelayPeriod());
        tvStepPrice.setText(bean.getAuctionInfo().getAddPrice());
        tvAuctionFee.setText(bean.getAuctionInfo().getLotMoney());
        setRecordNum(bean.getAuctionRecode().size());

        switch (bean.getAuctionInfo().getAuctionState()) {
            case "0": {
                tvStatus.setText("未开始");
                tvApplyTime.setText(bean.getAuctionInfo().getBeginPrice() + "开始");
                btnDeposit.setText("未开始");
            }
            break;
            case "1": {
                tvStatus.setText("拍卖中");
                tvApplyTime.setText(bean.getAuctionInfo().getEndTime() + "结束");
                if ("1".equals(bean.getAuctionInfo().getOrderStatus())) {
                    setDepositPayed();
                }
                btnDeposit.setBackgroundResource(R.color.orange);
                btnDeposit.setText(R.string.pay_deposit);
            }
            break;
            case "3": {
                tvStatus.setText("成交");
                btnDeposit.setText("已结束");
            }
            break;
            case "4": {
                tvStatus.setText("流拍");
                btnDeposit.setText("已结束");
            }
            break;
        }

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
            ((ImageView) flTab.findViewById(R.id.ivTab)).setImageResource(R.drawable.icon_tab_correct);
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
    public void setRecordNum(int count) {
        tvAuctionNum.setText("(" + count + ")");
    }

    @Override
    public void setDepositPayed() {
        btnDeposit.setVisibility(View.GONE);
        flBid.setVisibility(View.VISIBLE);
    }

    @Override
    public void setCollection(boolean collection) {
        ivCollection.setImageResource(collection ? R.drawable.icon_collect_pressed_xxhdpi : R.drawable.icon_collect_normal_xxhdpi);
    }

    @OnClick({R.id.flBid, R.id.flChat, R.id.btnDeposit, R.id.ivShare, R.id.ivCollection})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.flBid: {
                bidDialog.show();
            }
            break;
            case R.id.flChat: {
                presenter.chat();
            }
            break;
            case R.id.btnDeposit: {
                presenter.payDeposit((Activity) context);
            }
            break;
            case R.id.ivShare:
                presenter.share();
                break;
            case R.id.ivCollection:
                presenter.collection();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            presenter.parseResult(data);
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
