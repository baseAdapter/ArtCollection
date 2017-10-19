package com.tsutsuku.artcollection.ui.shopping;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.listener.OnItemClickListener;
import com.tsutsuku.artcollection.R;
import com.tsutsuku.artcollection.common.Constants;
import com.tsutsuku.artcollection.contract.shopping.VendorListContract;
import com.tsutsuku.artcollection.model.HomeBean;
import com.tsutsuku.artcollection.presenter.VendorListPresenterImpl;
import com.tsutsuku.artcollection.presenter.main.ADRepository;
import com.tsutsuku.artcollection.ui.BannerHolder.ADImageHolder;
import com.tsutsuku.artcollection.ui.base.BaseActivity;
import com.tsutsuku.artcollection.ui.base.BaseRvAdapter;
import com.tsutsuku.artcollection.ui.search.SearchActivity;
import com.tsutsuku.artcollection.utils.DensityUtils;
import com.tsutsuku.artcollection.view.ItemOffsetDecoration;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @Author Sun Renwei
 * @Create 2017/3/1
 * @Description Content
 */

public class VendorListActivity extends BaseActivity implements VendorListContract.View {
    @BindView(R.id.cbBase)
    ConvenientBanner cbBase;
    @BindView(R.id.tvSales)
    TextView tvSales;
    @BindView(R.id.tvPraise)
    TextView tvPraise;
    @BindView(R.id.rvBase)
    RecyclerView rvBase;
    @BindView(R.id.ivSales)
    ImageView ivSales;
    @BindView(R.id.ivPraise)
    ImageView ivPraise;
    @BindView(R.id.flTitle)
    RelativeLayout flTitle;
    @BindView(R.id.ablBase)
    AppBarLayout ablBase;

    VendorListPresenterImpl presenter;
    GridLayoutManager layoutManager;

    public static void launch(Context context) {
        context.startActivity(new Intent(context, VendorListActivity.class));
    }

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_vendor_list);
    }

    @Override
    public void initViews() {
        initTitle(R.string.vendor_title, R.drawable.icon_search);
        ButterKnife.bind(this);
        presenter = new VendorListPresenterImpl(context);
        presenter.attachView(this);
    }

    @Override
    public void initListeners() {
        ablBase.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            int offset;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (offset != verticalOffset) {
                    offset = verticalOffset;
                    CollapsingToolbarLayout.LayoutParams layoutParams = new CollapsingToolbarLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, DensityUtils.dp2px(40));
                    layoutParams.setMargins(0, -verticalOffset, 0, 0);
                    flTitle.setLayoutParams(layoutParams);
                }

            }
        });

        presenter.getAdapter().setupScroll(rvBase, new BaseRvAdapter.CallBack() {
            @Override
            public int findLastVisibleItemPosition() {
                return layoutManager.findLastVisibleItemPosition();
            }

            @Override
            public void loadData() {
                presenter.loadData(false);
            }
        });
    }

    @Override
    public void initData() {
        layoutManager = new GridLayoutManager(context, 2);
        rvBase.setLayoutManager(layoutManager);
        rvBase.addItemDecoration(new ItemOffsetDecoration(DensityUtils.dp2px(3)));
        rvBase.setAdapter(presenter.getAdapter());

        presenter.loadData(true);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detachView();
    }

    @Override
    public void setSales(boolean up) {
        tvSales.setTextColor(getResources().getColor(up ? R.color.orange : R.color.d));
        ivSales.setImageResource(up ? R.drawable.icon_up : R.drawable.icon_down);
    }

    @Override
    public void setPraise(boolean up) {
        tvPraise.setTextColor(getResources().getColor(up ? R.color.orange : R.color.d));
        ivPraise.setImageResource(up ? R.drawable.icon_up : R.drawable.icon_down);
    }

    @Override
    public void setAD(final List<HomeBean.ADBean> list) {
        cbBase.setPages(
                new CBViewHolderCreator<ADImageHolder>() {
                    @Override
                    public ADImageHolder createHolder() {
                        return new ADImageHolder();
                    }
                }, list)
                //设置两个点图片作为翻页指示器，不设置则没有指示器，可以根据自己需求自行配合自己的指示器,不需要圆点指示器可用不设
                .setPageIndicator(new int[]{R.drawable.icon_dot, R.drawable.icon_dot_selected})
                //设置指示器的方向
                .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL)
                .setOnItemClickListener(new OnItemClickListener() {
                    @Override
                    public void onItemClick(int position) {
                        ADRepository.parseAD(context, list.get(position));
                    }
                });
    }

    @OnClick({R.id.flSales, R.id.flPraise, R.id.ivTitleButton})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.flSales: {
                presenter.orderSale();
            }
            break;
            case R.id.flPraise: {
                presenter.orderPraise();
            }
            break;
            case R.id.ivTitleButton: {
                SearchActivity.launch(context, Constants.Search.VENDOR);
            }
            break;
        }
    }
}
