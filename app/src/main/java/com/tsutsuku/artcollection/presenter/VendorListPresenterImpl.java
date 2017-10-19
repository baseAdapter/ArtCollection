package com.tsutsuku.artcollection.presenter;

import android.content.Context;
import android.support.annotation.NonNull;

import com.tsutsuku.artcollection.common.Constants;
import com.tsutsuku.artcollection.contract.shopping.VendorListContract;
import com.tsutsuku.artcollection.contract.base.AdapterItem;
import com.tsutsuku.artcollection.contract.base.OnItemSimpleClickListener;
import com.tsutsuku.artcollection.http.HttpResponseHandler;
import com.tsutsuku.artcollection.http.HttpsClient;
import com.tsutsuku.artcollection.model.HomeBean;
import com.tsutsuku.artcollection.model.ItemVendor;
import com.tsutsuku.artcollection.ui.base.BaseRvAdapter;
import com.tsutsuku.artcollection.ui.shopping.VendorAdapterItem;
import com.tsutsuku.artcollection.ui.shopping.VendorDetailActivity;
import com.tsutsuku.artcollection.utils.GsonUtils;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by sunrenwei on 2017/03/01
 */

public class VendorListPresenterImpl implements VendorListContract.Presenter {
    private static final int UP = 1;
    private static final int DOWN = 2;

    private BaseRvAdapter adapter;
    private List<ItemVendor> list;
    private int orderSale = UP;
    private int orderScore = UP;
    private Context context;

    private VendorListContract.View view;

    public VendorListPresenterImpl(Context context) {
        this.context = context;
        list = new ArrayList<>();
    }

    @Override
    public BaseRvAdapter getAdapter() {
        if (adapter == null) {
            adapter = new BaseRvAdapter<ItemVendor>(list) {

                @NonNull
                @Override
                public AdapterItem createItem(@NonNull Object type) {
                    return new VendorAdapterItem(context, new OnItemSimpleClickListener<ItemVendor>() {
                        @Override
                        public void onItemClick(ItemVendor item) {
                            VendorDetailActivity.launch(context, item.getFarmId());
                        }
                    });
                }
            };
        }
        return adapter;
    }

    @Override
    public void orderSale() {
        orderSale = orderSale == UP ? DOWN : UP;
        getData(true);
    }

    @Override
    public void orderPraise() {
        orderScore = orderScore == UP ? DOWN : UP;
        getData(true);
    }

    @Override
    public void loadData(boolean refresh) {
        getData(refresh);
    }

    @Override
    public void attachView(VendorListContract.View view) {
        this.view = view;
        getAD();
    }

    @Override
    public void detachView() {
        this.view = null;
    }

    private void getData(final boolean refresh) {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("service", "Farm.getFarmsList");
        hashMap.put("orderSale", String.valueOf(orderSale));
        hashMap.put("orderScore", String.valueOf(orderScore));
        hashMap.put("pageIndex", refresh ? adapter.clearPageIndex() : adapter.addPageIndex());
        hashMap.put("pageSize", String.valueOf(Constants.PAGE_SIZE));
        HttpsClient client = new HttpsClient();
        client.post(hashMap, new HttpResponseHandler() {
            @Override
            protected void onSuccess(int statusCode, JSONObject data) throws Exception {
                if (data.getInt("code") == 0) {
                    if (refresh) {
                        adapter.setTotal(data.getJSONObject("list").getInt("total"));
                    }
                    list.addAll(GsonUtils.parseJsonArray(data.getJSONObject("list").getString("list"), ItemVendor.class));
                    adapter.notifyDataSetChanged();

                    view.setPraise(orderScore == 1);
                    view.setSales(orderSale == 1);
                }
            }

            @Override
            protected void onFailed(int statusCode, Exception e) {

            }

            @Override
            protected void onFinish() {
                adapter.finishLoading();
            }
        });

    }

    private void getAD() {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("service", "App.getIndexAd");
        hashMap.put("positionId", "7");
        HttpsClient client = new HttpsClient();
        client.post(hashMap, new HttpResponseHandler() {
            @Override
            protected void onSuccess(int statusCode, JSONObject data) throws Exception {
                if (data.getInt("code") == 0) {
                    view.setAD(GsonUtils.parseJsonArray(data.getString("list"), HomeBean.ADBean.class));
                }
            }

            @Override
            protected void onFailed(int statusCode, Exception e) {

            }
        });

    }
}