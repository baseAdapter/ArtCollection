package com.tsutsuku.artcollection.ui.exchange;

import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.tsutsuku.artcollection.R;
import com.tsutsuku.artcollection.common.Constants;
import com.tsutsuku.artcollection.http.HttpResponseHandler;
import com.tsutsuku.artcollection.http.HttpsClient;
import com.tsutsuku.artcollection.model.OrderDetailBean;
import com.tsutsuku.artcollection.ui.base.BaseActivity;
import com.tsutsuku.artcollection.utils.SharedPref;
import com.tsutsuku.artcollection.utils.TLog;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ExchangeOrderDetailActivity extends BaseActivity {
    @BindView(R.id.orderAddressName)
    TextView mOrderAddressName;
    @BindView(R.id.orderAddressMobile)
    TextView mOrderAddressMobile;
    @BindView(R.id.orderAddress)
    TextView mOrderAddress;

    @BindView(R.id.order_icon_product)
    ImageView mOrderIconProduct;
    @BindView(R.id.order_product_need_coin)
    TextView mOrderProductNeedCoin;
    @BindView(R.id.order_count)
    TextView mOrderCount;

    @BindView(R.id.order_product_name)
    TextView mOrderProductName;
    @BindView(R.id.order_create_time)
    TextView mOrderCreateTime;
    @BindView(R.id.order_payment_time)
    TextView mOrderPaymentTime;
    @BindView(R.id.order_id)
    TextView mOrderId;

    //实付款
    @BindView(R.id.disbursements)
    TextView mDisbursements;

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_exchange_order_detail);
    }

    @Override
    public void initViews() {
        initTitle(R.string.order_detail);
        ButterKnife.bind(this);

    }

    @Override
    public void initListeners() {

    }

    @Override
    public void initData() {
        getOrderDetail();

    }

    private void getOrderDetail() {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("service", "Gold.orderExDetail");
        hashMap.put("user_id", SharedPref.getString(Constants.USER_ID));
        //请注意
        hashMap.put("order_id", "2017102210257101");
        HttpsClient client = new HttpsClient();
        client.post(hashMap, new HttpResponseHandler() {
            @Override
            protected void onSuccess(int statusCode, JSONObject data) throws Exception {
                Log.w(TAG, "onSuccess" + data);
                Gson gson = new Gson();
                OrderDetailBean orderDetailBean = gson.fromJson(data.toString(), OrderDetailBean.class);
                if (orderDetailBean.getCode() == 0) {
                    List<OrderDetailBean.ListBean> list = orderDetailBean.getList();
                    OrderDetailBean.ListBean bean = new OrderDetailBean.ListBean();
                    if (list != null && list.size() > 0) {
                        bean = list.get(0);
                    }
                    mOrderAddressName.setText(bean.getConsignee());
                    mOrderAddressMobile.setText(bean.getPhone());
                    mOrderAddress.setText(bean.getAddress());
                    Glide.with(ExchangeOrderDetailActivity.this).load(bean.getPhoto()).into(mOrderIconProduct);
                    mOrderProductNeedCoin.setText(bean.getTotalGolds() + "金币");
                    mOrderProductName.setText(bean.getGoodsName());
                    mOrderCount.setText(bean.getNums());
                    mOrderId.setText("订单编号 : " + bean.getOrderId());
                    mOrderCreateTime.setText("创建时间 : " + bean.getCreateTime());
                    mOrderPaymentTime.setText("付款时间 : " + bean.getCreateTime());
                    mDisbursements.setText(bean.getTotalGolds() + "金币");
                }

            }

            @Override
            protected void onFailed(int statusCode, Exception e) {

            }
        });

    }
}
