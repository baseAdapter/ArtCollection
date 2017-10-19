package com.tsutsuku.artcollection.presenter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;

import com.tsutsuku.artcollection.common.Constants;
import com.tsutsuku.artcollection.contract.AuctionListContract;
import com.tsutsuku.artcollection.contract.base.AdapterItem;
import com.tsutsuku.artcollection.http.HttpResponseHandler;
import com.tsutsuku.artcollection.http.HttpsClient;
import com.tsutsuku.artcollection.model.auction.ItemAuctionList;
import com.tsutsuku.artcollection.ui.auction.AuctionListAdapterItem;
import com.tsutsuku.artcollection.ui.base.BaseRvAdapter;
import com.tsutsuku.artcollection.utils.GsonUtils;
import com.tsutsuku.artcollection.utils.SharedPref;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by sunrenwei on 2017/03/19
 */

public class AuctionListPresenterImpl implements AuctionListContract.Presenter {

    private AuctionListContract.View view;
    private BaseRvAdapter adapter;
    private List<ItemAuctionList> list;

    @Override
    public void attachView(AuctionListContract.View view) {
        this.view = view;
        list = new ArrayList<>();
    }

    @Override
    public void detachView() {
        this.view = null;
    }

    @Override
    public void getData(final boolean refresh) {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("service", "Product.getMyAuctionRecode");
        hashMap.put("userId", SharedPref.getString(Constants.USER_ID));
        hashMap.put("pageIndex", refresh ? adapter.clearPageIndex() : adapter.addPageIndex());
        hashMap.put("pageSize", String.valueOf(Constants.PAGE_SIZE));
        HttpsClient client = new HttpsClient();
        client.post(hashMap, new HttpResponseHandler() {
            @Override
            protected void onSuccess(int statusCode, JSONObject data) throws Exception {
                if (data.getInt("code") == 0) {
                    if (refresh) {
                        list.clear();
                        adapter.setTotal(data.getJSONObject("list").getInt("total"));
                    }
                    list.addAll(GsonUtils.parseJsonArray(data.getJSONObject("list").getString("list"), ItemAuctionList.class));
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            protected void onFailed(int statusCode, Exception e) {

            }
        });
    }

    @Override
    public BaseRvAdapter getAdapter() {
        if (adapter == null) {
            adapter = new BaseRvAdapter<ItemAuctionList>(list) {

                @NonNull
                @Override
                public AdapterItem createItem(@NonNull Object type) {
                    return new AuctionListAdapterItem();
                }
            };
        }
        return adapter;
    }


}