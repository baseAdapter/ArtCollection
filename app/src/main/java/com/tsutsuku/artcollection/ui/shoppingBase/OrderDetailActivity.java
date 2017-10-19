package com.tsutsuku.artcollection.ui.shoppingBase;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tsutsuku.artcollection.R;
import com.tsutsuku.artcollection.common.BusEvent;
import com.tsutsuku.artcollection.contract.shopping.OrderDetailContract;
import com.tsutsuku.artcollection.model.shopping.OrderBean;
import com.tsutsuku.artcollection.model.shopping.OrderInfo;
import com.tsutsuku.artcollection.presenter.OrderDetailPresenterImpl;
import com.tsutsuku.artcollection.ui.base.BaseActivity;
import com.tsutsuku.artcollection.utils.RxBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

/**
 * @Author Sun Renwei
 * @Create 2017/2/16
 * @Description 订单详情页面
 */

public class OrderDetailActivity extends BaseActivity implements OrderDetailContract.View {
    public static final String ORDER_ID = "orderId";
    @BindView(R.id.ivStatus)
    ImageView ivStatus;
    @BindView(R.id.flStatus)
    FrameLayout flStatus;
    @BindView(R.id.tvVendor)
    TextView tvVendor;
    @BindView(R.id.ivRight)
    ImageView ivRight;
    @BindView(R.id.tvStatus)
    TextView tvStatus;
    @BindView(R.id.llVendor)
    LinearLayout llVendor;
    @BindView(R.id.llGoods)
    LinearLayout llGoods;
    @BindView(R.id.tvPrice)
    TextView tvPrice;
    @BindView(R.id.tvDelivery)
    TextView tvDelivery;
    @BindView(R.id.tvTotal)
    TextView tvTotal;
    @BindView(R.id.tvCreateTime)
    TextView tvCreateTime;
    @BindView(R.id.tvPayTime)
    TextView tvPayTime;
    @BindView(R.id.tvDeliveryTime)
    TextView tvDeliveryTime;
    @BindView(R.id.btnCancel)
    Button btnCancel;
    @BindView(R.id.btnDelete)
    Button btnDelete;
    @BindView(R.id.btnConfirm)
    Button btnConfirm;
    @BindView(R.id.btnPay)
    Button btnPay;
    @BindView(R.id.btnComment)
    Button btnComment;
    @BindView(R.id.tvName)
    TextView tvName;
    @BindView(R.id.tvMobile)
    TextView tvMobile;
    @BindView(R.id.tvAddress)
    TextView tvAddress;
    @BindView(R.id.flAddress)
    FrameLayout flAddress;
    @BindView(R.id.tvOrderId)
    TextView tvOrderId;

    private OrderDetailPresenterImpl presenter;
    private Observable<OrderBean> observable;

    public static void launch(Context context, String orderId) {
        context.startActivity(new Intent(context, OrderDetailActivity.class).putExtra(ORDER_ID, orderId));
    }

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_order_detail);
    }

    @Override
    public void initViews() {
        initTitle(R.string.order_detail);
        ButterKnife.bind(this);

        presenter = new OrderDetailPresenterImpl(context, getIntent().getStringExtra(ORDER_ID));
        presenter.attachView(this);
    }

    @Override
    public void initListeners() {
        observable = RxBus.getDefault().register(BusEvent.SHOPPING, OrderBean.class);
        observable.observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<OrderBean>() {
            @Override
            public void call(OrderBean bean) {
                setFunction(bean);
            }
        });
    }

    @Override
    public void initData() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detachView();
    }

    @OnClick({R.id.btnCancel, R.id.btnDelete, R.id.btnConfirm, R.id.btnPay, R.id.btnComment})
    public void onClick(View view) {
        presenter.orderFunction(view.getId());
    }

    @Override
    public void setViews(OrderInfo orderInfo) {
        switch (orderInfo.getOrderStatus()) {
            case "-2":
            case "0":
            case "2": {
                ivStatus.setImageResource(R.drawable.icon_order_close);
                flStatus.setBackgroundColor(Color.parseColor("#B0B0B0"));
                tvStatus.setText("订单关闭");
            }
            break;
            case "-1":{
                ivStatus.setImageResource(R.drawable.icon_order_unpay);
                flStatus.setBackgroundColor(context.getResources().getColor(R.color.orange));
                tvStatus.setText("等待支付");
            }
            break;
            default:{
                switch (orderInfo.getDeliveryStatus()){
                    case "0":
                    case "3":{
                        ivStatus.setImageResource(R.drawable.icon_order_deal);
                        flStatus.setBackgroundColor(context.getResources().getColor(R.color.orange));
                        tvStatus.setText("交易完成");
                    }
                    break;
                    case "1":{
                        ivStatus.setImageResource(R.drawable.icon_order_waiting);
                        flStatus.setBackgroundColor(context.getResources().getColor(R.color.btn_blue_normal));
                        tvStatus.setText("待发货");
                    }
                    case "2":{
                        ivStatus.setImageResource(R.drawable.icon_order_delivery);
                        flStatus.setBackgroundColor(context.getResources().getColor(R.color.orange));
                        tvStatus.setText("已发货");
                    }
                }
            }
            break;
        }

        tvName.setText(orderInfo.getConsigneeName());
        tvMobile.setText(orderInfo.getContactNumber());
        tvAddress.setText(orderInfo.getAddress());

        tvVendor.setText(orderInfo.getFarmName());

        OrderDetailGoodsView goodsView = new OrderDetailGoodsView(context);
        for (OrderInfo.ItemsBean itemsBean : orderInfo.getItems()) {
            llGoods.addView(goodsView.getView(itemsBean));
        }
        tvTotal.setText("¥" + orderInfo.getCashFee() + "(积分抵扣¥" + orderInfo.getVirtualFee() + ")" );

        tvOrderId.setText("订单编号：" + orderInfo.getOrderId());
        tvCreateTime.setText("创建时间：" + orderInfo.getOrderTime());
        if ("1".equals(orderInfo.getOrderStatus())) {
            tvPayTime.setVisibility(View.VISIBLE);
            tvPayTime.setText("付款时间：" + orderInfo.getPayTime());

            if (!TextUtils.isEmpty(orderInfo.getReceiveTime())) {
                tvDeliveryTime.setText("收货时间：" + orderInfo.getReceiveTime());
            } else if (!TextUtils.isEmpty(orderInfo.getDeliveryTime())) {
                tvDeliveryTime.setText("发货时间：" + orderInfo.getDeliveryTime());
            }
        }

    }

    private void setFunction(OrderBean bean) {
        switch (bean.getOrderStatus()) {
            case "-2":
            case "0":
            case "2": {
                btnDelete.setVisibility(View.VISIBLE);
            }
            break;
            case "-1": {
                btnCancel.setVisibility(View.VISIBLE);
                btnPay.setVisibility(View.VISIBLE);
            }
            break;
            case "1": {
                switch (bean.getDeliveryStatus()) {
                    case "0":
                    case "3": {
                        if ("0".equals(bean.getIsComment())) {
                            btnComment.setVisibility(View.VISIBLE);
                        }
                    }
                    break;
                    case "2": {
                        btnConfirm.setVisibility(View.VISIBLE);
                    }
                }
            }
        }
    }
}
