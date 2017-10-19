package com.tsutsuku.artcollection.ui.shoppingBase;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;
import android.widget.TextView;

import com.tsutsuku.artcollection.R;
import com.tsutsuku.artcollection.common.BusEvent;
import com.tsutsuku.artcollection.contract.shopping.ShoppingCartContract;
import com.tsutsuku.artcollection.presenter.shopping.ShoppingCartPresenterImpl;
import com.tsutsuku.artcollection.ui.base.BaseActivity;
import com.tsutsuku.artcollection.utils.RxBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

/**
 * @Author Tsutsuku
 * @Create 2017/2/8
 * @Description
 */

public class ShoppingCartActivity extends BaseActivity implements ShoppingCartContract.View {

    @BindView(R.id.rvBase)
    RecyclerView rvBase;
    @BindView(R.id.tvPrice)
    TextView tvPrice;
    @BindView(R.id.btnCmd)
    Button btnCmd;

    private ShoppingCartPresenterImpl presenter;
    private Observable observable;

    public static void launch(Context context) {
        context.startActivity(new Intent(context, ShoppingCartActivity.class));
    }

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_shopping_cart);
    }

    @Override
    public void initViews() {
        initTitle(R.string.shopping_cart);
        ButterKnife.bind(this);
        presenter = new ShoppingCartPresenterImpl(context);
        presenter.attachView(this);

        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvBase.setLayoutManager(layoutManager);
    }

    @Override
    public void initListeners() {
        observable = RxBus.getDefault().register(BusEvent.DEAL_FINISH, String.class);
        observable.observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1() {
            @Override
            public void call(Object o) {
                finish();
            }
        });
    }

    @Override
    public void initData() {
        rvBase.setAdapter(presenter.getAdapter());
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.refresh();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RxBus.getDefault().unregister(BusEvent.DEAL_FINISH, observable);
        presenter.detachView();
    }

    @OnClick(R.id.btnCmd)
    public void onClick() {
        presenter.settle();
    }

    @Override
    public void setTotalPrice(String totalPrice) {
        tvPrice.setText(totalPrice);
    }

    @Override
    public Context getContext() {
        return context;
    }
}
