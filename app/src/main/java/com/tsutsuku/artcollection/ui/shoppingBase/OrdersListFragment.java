package com.tsutsuku.artcollection.ui.shoppingBase;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;

import com.tsutsuku.artcollection.R;
import com.tsutsuku.artcollection.common.BusEvent;
import com.tsutsuku.artcollection.contract.shopping.OrdersListContract;
import com.tsutsuku.artcollection.model.shopping.OrderBean;
import com.tsutsuku.artcollection.presenter.OrdersListPresenterImpl;
import com.tsutsuku.artcollection.ui.base.BasePageFragment;
import com.tsutsuku.artcollection.utils.RxBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observable;
import rx.functions.Action1;

/**
 * @Author Tsutsuku
 * @Create 2017/2/12
 * @Description
 */

public class OrdersListFragment extends BasePageFragment implements OrdersListContract.View {
    private static final String TYPE = "type";

    @BindView(R.id.rvBase)
    RecyclerView rvBase;
    @BindView(R.id.flBlank)
    FrameLayout flBlank;

    private OrdersListPresenterImpl presenter;
    private Observable<OrderBean> observable;

    public static OrdersListFragment newInstance(String type) {
        OrdersListFragment fragment = new OrdersListFragment();
        Bundle bundle = new Bundle();
        bundle.putString(TYPE, type);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_orders_list;
    }

    @Override
    protected void initViews() {
        ButterKnife.bind(this, rootView);
        presenter = new OrdersListPresenterImpl(context, getArguments().getString(TYPE));
        presenter.attachView(this);
        presenter.initViews(rvBase);
    }

    @Override
    protected void initListeners() {
        observable = RxBus.getDefault().register(BusEvent.SHOPPING, OrderBean.class);
        observable.subscribe(new Action1<OrderBean>() {
            @Override
            public void call(OrderBean bean) {
                presenter.parseBusEvent(bean);
            }
        });
    }

    @Override
    protected void initData() {
        this.forceUpdate = true;
    }

    @Override
    public void hideBlank() {
        flBlank.setVisibility(View.GONE);
        rvBase.setVisibility(View.VISIBLE);
    }

    @Override
    public void showBlank() {
        flBlank.setVisibility(View.VISIBLE);
        rvBase.setVisibility(View.GONE);
    }

    @Override
    protected void fetchData() {
        presenter.refreshData();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.detachView();
        RxBus.getDefault().unregister(BusEvent.SHOPPING, observable);
    }
}
