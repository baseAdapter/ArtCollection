package com.tsutsuku.artcollection.other.custom.presenter;

import android.content.Context;
import android.text.TextUtils;

import com.tsutsuku.artcollection.common.Constants;
import com.tsutsuku.artcollection.http.HttpResponseHandler;
import com.tsutsuku.artcollection.im.ui.ChatActivity;
import com.tsutsuku.artcollection.model.shopping.ItemGoods;
import com.tsutsuku.artcollection.model.shopping.ItemVendor;
import com.tsutsuku.artcollection.other.custom.contract.CustomDetailContract;
import com.tsutsuku.artcollection.other.custom.model.CustomDetailInfo;
import com.tsutsuku.artcollection.ui.login.LoginActivity;
import com.tsutsuku.artcollection.ui.shoppingBase.ShoppingSettleActivity;
import com.tsutsuku.artcollection.utils.GsonUtils;
import com.tsutsuku.artcollection.utils.SharedPref;

import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by sunrenwei on 2017/06/08
 */

public class CustomDetailPresenterImpl implements CustomDetailContract.Presenter {

    private CustomDetailContract.View view;
    private CustomDetailContract.Model model;
    private String diyId;
    private CustomDetailInfo info;
    private Context context;

    public CustomDetailPresenterImpl(Context context, CustomDetailContract.View view, CustomDetailContract.Model model) {
        this.context = context;
        this.view = view;
        this.model = model;
    }

    @Override
    public void start() {
        model.getData(diyId, new HttpResponseHandler() {
            @Override
            protected void onSuccess(int statusCode, JSONObject data) throws Exception {
                if (data.getInt("code") == 0) {
                    info = GsonUtils.parseJson(data.getString("info"), CustomDetailInfo.class);
                    view.setInfo(info);
                }
            }

            @Override
            protected void onFailed(int statusCode, Exception e) {

            }
        });
    }

    @Override
    public void setId(String diyId) {
        this.diyId = diyId;
    }

    @Override
    public void chat() {
        ChatActivity.launch(context, info.getHxAccount().getUsername(), info.getServerUId(), info.getHxAccount().getDisname(), info.getHxAccount().getPhoto());
    }

    @Override
    public void buy() {
        if (TextUtils.isEmpty(SharedPref.getString(Constants.USER_ID))){
            LoginActivity.launch(context);
        } else {
            final ItemVendor vendor = new ItemVendor();
            vendor.setFarmId("0");
            vendor.setFarmName("平台");
            final ItemGoods goods = new ItemGoods();
            goods.setProductAmount("1");
            goods.setProductName(info.getTitle());
            goods.setProductCover(info.getLogo());
            goods.setProductId(info.getDiyId());
            goods.setProductPrice(info.getPrice());
            vendor.setProducts(new ArrayList<ItemGoods>() {{
                add(goods);
            }});
            ShoppingSettleActivity.launch(context, "5", new ArrayList<ItemVendor>() {{
                add(vendor);
            }});
        }

    }


}