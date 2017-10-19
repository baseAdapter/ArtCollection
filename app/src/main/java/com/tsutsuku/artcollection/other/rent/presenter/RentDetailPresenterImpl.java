package com.tsutsuku.artcollection.other.rent.presenter;
import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.tsutsuku.artcollection.common.Constants;
import com.tsutsuku.artcollection.http.HttpResponseHandler;
import com.tsutsuku.artcollection.im.ui.ChatActivity;
import com.tsutsuku.artcollection.model.shopping.ItemGoods;
import com.tsutsuku.artcollection.model.shopping.ItemVendor;
import com.tsutsuku.artcollection.model.shopping.ProductInfoBean;
import com.tsutsuku.artcollection.other.rent.contract.RentDetailContract;
import com.tsutsuku.artcollection.presenter.shopping.ShoppingRepository;
import com.tsutsuku.artcollection.ui.shoppingBase.ShoppingSettleActivity;
import com.tsutsuku.artcollection.utils.Arith;
import com.tsutsuku.artcollection.utils.GsonUtils;
import com.tsutsuku.artcollection.utils.ShareUtils;
import com.tsutsuku.artcollection.utils.SharedPref;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;

/**
* Created by sunrenwei on 2017/06/25
*/

public class RentDetailPresenterImpl implements RentDetailContract.Presenter{
    ProductInfoBean infoBean;
    private String productId;
    private Context context;
    private boolean collection;

    private Gson gson = new Gson();
    private Type type = new TypeToken<ProductInfoBean>() {
    }.getType();
    private RentDetailContract.View view;
    private RentDetailContract.Model model;

    public RentDetailPresenterImpl(RentDetailContract.View view, String productId, Context context, RentDetailContract.Model model) {
        this.view = view;
        this.productId = productId;
        this.context = context;
        this.model = model;
    }

    @Override
    public void add() {
        ShoppingRepository.add(SharedPref.getString(Constants.USER_ID),
                infoBean.getProductId(),
                "1");
    }

    @Override
    public void buy(String num) {
        final ItemVendor vendor = new ItemVendor();
        vendor.setFarmId(infoBean.getFarmId());
        vendor.setFarmName(infoBean.getFarmName());
        final ItemGoods goods = new ItemGoods();
        goods.setProductAmount(Arith.mul(num, "1"));
        goods.setProductName(infoBean.getProductName());
        goods.setProductCover(infoBean.getCover());
        goods.setProductId(infoBean.getProductId());
        goods.setProductPrice(infoBean.getTotalPrice());
        vendor.setProducts(new ArrayList<ItemGoods>() {{
            add(goods);
        }});
        ShoppingSettleActivity.launch(context, "0", new ArrayList<ItemVendor>() {{
            add(vendor);
        }});
    }

    @Override
    public void chat() {
        ChatActivity.launch(context, infoBean.getFarm().getUsername(), "0", infoBean.getFarm().getDisname(), infoBean.getFarm().getPhoto());
    }

    @Override
    public void collection() {
        model.collection(collection, productId, new HttpResponseHandler() {
            @Override
            protected void onSuccess(int statusCode, JSONObject data) throws Exception {
                if (data.getInt("code") == 0) {
                    collection = !collection;
                    view.setCollection(collection);
                }
            }

            @Override
            protected void onFailed(int statusCode, Exception e) {

            }
        });

    }

    @Override
    public void share() {
        ShareUtils.showShare(context, infoBean.getCover(), infoBean.getProductName(), SharedPref.getSysString(Constants.Share.PRODUCT) + productId);
    }


    @Override
    public void start() {
        model.getData(productId, new HttpResponseHandler() {
            @Override
            protected void onSuccess(int statusCode, JSONObject data) throws Exception {
                if (data.getInt("code") == 0) {
                    infoBean = GsonUtils.parseJson(data.getString("info"), ProductInfoBean.class);
                    collection = infoBean.getIsCollection() == 1;
                    view.setData(infoBean);
                }
            }

            @Override
            protected void onFailed(int statusCode, Exception e) {

            }
        });

    }
}