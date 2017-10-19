package com.tsutsuku.artcollection.presenter;

import android.support.annotation.NonNull;

import com.tsutsuku.artcollection.contract.PointListContract;
import com.tsutsuku.artcollection.contract.base.AdapterItem;
import com.tsutsuku.artcollection.http.HttpResponseHandler;
import com.tsutsuku.artcollection.http.HttpsClient;
import com.tsutsuku.artcollection.model.point.ItemPoint;
import com.tsutsuku.artcollection.ui.base.BaseRvAdapter;
import com.tsutsuku.artcollection.ui.point.PointAdapterItem;
import com.tsutsuku.artcollection.utils.GsonUtils;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by sunrenwei on 2017/02/27
 */

public class PointListPresenterImpl implements PointListContract.Presenter {

    private List<ItemPoint> list;
    private BaseRvAdapter adapter;
    private PointListContract.View view;

    public PointListPresenterImpl() {
        list = new ArrayList<>();
    }

    @Override
    public BaseRvAdapter getAdapter() {
        if (adapter == null) {
            adapter = new BaseRvAdapter<ItemPoint>(list) {
                @NonNull
                @Override
                public AdapterItem createItem(@NonNull Object type) {
                    return new PointAdapterItem();
                }
            };
        }
        return adapter;
    }

    @Override
    public void attachView(PointListContract.View view) {
        this.view = view;
    }

    @Override
    public void detachView() {
        this.view = null;
    }

    @Override
    public void getData(){
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("service", "Integrate.getExchanges");
        HttpsClient client = new HttpsClient();
        client.post(hashMap, new HttpResponseHandler() {
            @Override
            protected void onSuccess(int statusCode, JSONObject data) throws Exception {
                if (data.getInt("code") == 0) {
                    list.clear();
                    list.addAll(GsonUtils.parseJsonArray(data.getJSONObject("list").getString("list"), ItemPoint.class));
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            protected void onFailed(int statusCode, Exception e) {

            }
        });

    }
}