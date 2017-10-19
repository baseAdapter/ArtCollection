package com.tsutsuku.artcollection.presenter;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.tsutsuku.artcollection.common.Constants;
import com.tsutsuku.artcollection.contract.shopping.ProductDetailContract;
import com.tsutsuku.artcollection.http.HttpResponseHandler;
import com.tsutsuku.artcollection.http.HttpsClient;
import com.tsutsuku.artcollection.im.ui.ChatActivity;
import com.tsutsuku.artcollection.model.shopping.ItemGoods;
import com.tsutsuku.artcollection.model.shopping.ItemVendor;
import com.tsutsuku.artcollection.model.shopping.ProductInfoBean;
import com.tsutsuku.artcollection.presenter.shopping.ShoppingRepository;
import com.tsutsuku.artcollection.ui.shoppingBase.ShoppingSettleActivity;
import com.tsutsuku.artcollection.utils.ShareUtils;
import com.tsutsuku.artcollection.utils.SharedPref;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by sunrenwei on 2017/01/25
 */

public class ProductDetailPresenterImpl implements ProductDetailContract.Presenter {

    ProductDetailContract.View view;
    ProductInfoBean infoBean;
    private String productId;
    private Context context;
    private boolean collection;

    private Gson gson = new Gson();
    private Type type = new TypeToken<ProductInfoBean>() {
    }.getType();

    public ProductDetailPresenterImpl(Context context, String productId) {
        this.context = context;
        this.productId = productId;
    }

    @Override
    public void attachView(ProductDetailContract.View view) {
        this.view = view;
        getData();
    }

    @Override
    public void detachView() {
        this.view = null;
    }

    @Override
    public void add() {
        ShoppingRepository.add(SharedPref.getString(Constants.USER_ID),
                infoBean.getProductId(),
                "1");
    }

    @Override
    public void buy() {
        final ItemVendor vendor = new ItemVendor();
        vendor.setFarmId(infoBean.getFarmId());
        vendor.setFarmName(infoBean.getFarmName());
        final ItemGoods goods = new ItemGoods();
        goods.setProductAmount("1");
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
        ChatActivity.launch(context, infoBean.getFarm().getUsername(), "", infoBean.getFarm().getDisname(), infoBean.getFarm().getPhoto());
    }

    @Override
    public void follow() {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("service", infoBean.getIsFollow() == 1 ? "Follow.delete" : "Follow.add");
        hashMap.put("pId", infoBean.getFarmId());
        hashMap.put("ctype", "1");
        hashMap.put("userId", SharedPref.getString(Constants.USER_ID));
        HttpsClient client = new HttpsClient();
        client.post(hashMap, new HttpResponseHandler() {
            @Override
            protected void onSuccess(int statusCode, JSONObject data) throws Exception {
                if (data.getInt("code") == 0) {
                    infoBean.setIsFollow(infoBean.getIsFollow() == 1 ? 0 : 1);
                    view.setFollow(infoBean.getIsFollow() == 1);
                }
            }

            @Override
            protected void onFailed(int statusCode, Exception e) {

            }
        });
    }

    @Override
    public void collection() {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("service", collection ? "Collections.delete" : "Collections.add");
        hashMap.put("pId", productId);
        hashMap.put("ctype", "2");
        hashMap.put("userId", SharedPref.getString(Constants.USER_ID));
        HttpsClient client = new HttpsClient();
        client.post(hashMap, new HttpResponseHandler() {
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

    private void getData() {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("service", "Product.getProductInfo");
        hashMap.put("productId", productId);
        hashMap.put("user_Id", SharedPref.getString(Constants.USER_ID));
        HttpsClient client = new HttpsClient();
        client.post(hashMap, new HttpResponseHandler() {
            @Override
            protected void onSuccess(int statusCode, JSONObject data) throws Exception {
                if (data.getInt("code") == 0) {
                    infoBean = gson.fromJson(data.getString("info"), type);
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