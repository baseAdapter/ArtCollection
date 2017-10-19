package com.tsutsuku.artcollection.ui.auction;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;
import android.widget.TextView;

import com.tsutsuku.artcollection.R;
import com.tsutsuku.artcollection.contract.contract.AuctionDealContract;
import com.tsutsuku.artcollection.presenter.AuctionDealPresenterImpl;
import com.tsutsuku.artcollection.ui.base.BaseFragment;
import com.tsutsuku.artcollection.ui.base.BaseRvAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * @Author Tsutsuku
 * @Create 2017/3/19
 * @Description 中拍拍品Fragment
 */

public class AuctionDealFragment extends BaseFragment implements AuctionDealContract.View {
    @BindView(R.id.rvBase)
    RecyclerView rvBase;
    @BindView(R.id.tvPrice)
    TextView tvPrice;
    @BindView(R.id.btnCmd)
    Button btnCmd;
    Unbinder unbinder;
    Unbinder unbinder1;

    private AuctionDealPresenterImpl presenter;
    private LinearLayoutManager layoutManager;

    public static AuctionDealFragment newInstance() {
        AuctionDealFragment fragment = new AuctionDealFragment();
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_auction_deal;
    }

    @Override
    protected void initViews() {
        unbinder = ButterKnife.bind(this, rootView);
        presenter = new AuctionDealPresenterImpl(context);
        presenter.attachView(this);

        layoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        rvBase.setLayoutManager(layoutManager);

        presenter.getAdapter().setupScroll(rvBase, new BaseRvAdapter.CallBack() {
            @Override
            public int findLastVisibleItemPosition() {
                return layoutManager.findLastVisibleItemPosition();
            }

            @Override
            public void loadData() {
                presenter.getData(false);
            }
        });
        rvBase.setAdapter(presenter.getAdapter());
    }

    @Override
    protected void initListeners() {

    }

    @Override
    protected void initData() {
        presenter.getData(true);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        presenter.detachView();
        unbinder.unbind();
    }

    @OnClick(R.id.btnCmd)
    public void onViewClicked() {
        presenter.settle();
    }

    @Override
    public void setPrice(String price) {
        tvPrice.setText(price);
    }
}
