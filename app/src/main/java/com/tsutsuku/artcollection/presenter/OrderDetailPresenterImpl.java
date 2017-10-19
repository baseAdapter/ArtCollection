package com.tsutsuku.artcollection.presenter;

import android.content.Context;

import com.tsutsuku.artcollection.common.Constants;
import com.tsutsuku.artcollection.contract.shopping.OrderDetailContract;
import com.tsutsuku.artcollection.http.HttpResponseHandler;
import com.tsutsuku.artcollection.http.HttpsClient;
import com.tsutsuku.artcollection.model.shopping.OrderBean;
import com.tsutsuku.artcollection.model.shopping.OrderInfo;
import com.tsutsuku.artcollection.presenter.shopping.ShoppingRepository;
import com.tsutsuku.artcollection.utils.GsonUtils;
import com.tsutsuku.artcollection.utils.SharedPref;

import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by sunrenwei on 2017/03/16
 */

public class OrderDetailPresenterImpl implements OrderDetailContract.Presenter {

    private OrderDetailContract.View view;
    private OrderInfo orderInfo;
    private String orderId;
    private Context context;

    public OrderDetailPresenterImpl(Context context, String orderId) {
        this.context = context;
        this.orderId = orderId;
    }

    @Override
    public void getData() {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("service", "Order.getOrderInfo");
        hashMap.put("userId", SharedPref.getString(Constants.USER_ID));
        hashMap.put("orderId", orderId);
        HttpsClient client = new HttpsClient();
        client.post(hashMap, new HttpResponseHandler() {
            @Override
            protected void onSuccess(int statusCode, JSONObject data) throws Exception {
                if (data.getInt("code") == 0) {
                    orderInfo = GsonUtils.parseJson(data.getString("info"), OrderInfo.class);
                    view.setViews(orderInfo);
                }
            }

            @Override
            protected void onFailed(int statusCode, Exception e) {

            }
        });
    }

    @Override
    public void orderFunction(int ResId) {
        ShoppingRepository.orderFunction(context, ResId, new OrderBean(orderId, orderInfo.getDeliveryStatus(), orderInfo.getOrderStatus(), orderInfo.getTotalFee(), orderInfo.getIsComment()));
    }

    @Override
    public void attachView(OrderDetailContract.View view) {
        this.view = view;
        getData();
    }

    @Override
    public void detachView() {
        this.view = null;
    }
}