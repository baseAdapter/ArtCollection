package com.tsutsuku.artcollection.presenter;

import android.support.annotation.NonNull;

import com.tsutsuku.artcollection.common.Constants;
import com.tsutsuku.artcollection.contract.MinePointContract;
import com.tsutsuku.artcollection.contract.base.AdapterItem;
import com.tsutsuku.artcollection.http.HttpResponseHandler;
import com.tsutsuku.artcollection.http.HttpsClient;
import com.tsutsuku.artcollection.model.point.ItemPointRecord;
import com.tsutsuku.artcollection.ui.base.BaseRvAdapter;
import com.tsutsuku.artcollection.ui.point.PointRecordAdapterItem;
import com.tsutsuku.artcollection.utils.GsonUtils;
import com.tsutsuku.artcollection.utils.SharedPref;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by sunrenwei on 2017/02/27
 */

public class MinePointPresenterImpl implements MinePointContract.Presenter {

    private MinePointContract.View view;
    private BaseRvAdapter adapter;
    private List<ItemPointRecord> list;

    public MinePointPresenterImpl() {
        this.list = new ArrayList();
    }

    @Override
    public BaseRvAdapter getAdapter() {
        if (adapter == null) {
            adapter = new BaseRvAdapter<ItemPointRecord>(list) {
                @NonNull
                @Override
                public AdapterItem createItem(@NonNull Object type) {
                    return new PointRecordAdapterItem();
                }
            };
        }
        return adapter;
    }

    @Override
    public void attachView(MinePointContract.View view) {
        this.view = view;
    }

    @Override
    public void detachView() {
        this.view = null;
    }

    @Override
    public void getData() {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("service", "Integrate.getIntegrateRecord");
        hashMap.put("userId", SharedPref.getString(Constants.USER_ID));
        HttpsClient client = new HttpsClient();
        client.post(hashMap, new HttpResponseHandler() {
            @Override
            protected void onSuccess(int statusCode, JSONObject data) throws Exception {
                if (data.getInt("code") == 0) {
                    list.clear();
                    list.addAll(GsonUtils.parseJsonArray(data.getJSONObject("list").getString("list"), ItemPointRecord.class));
                    adapter.notifyDataSetChanged();

                    view.setPoint(data.getJSONObject("list").getString("total"));
                }
            }

            @Override
            protected void onFailed(int statusCode, Exception e) {

            }
        });

    }
}