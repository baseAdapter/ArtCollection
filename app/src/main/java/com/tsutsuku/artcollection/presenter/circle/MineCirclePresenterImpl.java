package com.tsutsuku.artcollection.presenter.circle;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;

import com.tsutsuku.artcollection.common.Constants;
import com.tsutsuku.artcollection.contract.MineCircleContract;
import com.tsutsuku.artcollection.contract.base.AdapterItem;
import com.tsutsuku.artcollection.http.HttpResponseHandler;
import com.tsutsuku.artcollection.http.HttpsClient;
import com.tsutsuku.artcollection.model.ItemCircle;
import com.tsutsuku.artcollection.ui.base.BaseRvAdapter;
import com.tsutsuku.artcollection.ui.circle.CircleAdapterItem;
import com.tsutsuku.artcollection.utils.GsonUtils;
import com.tsutsuku.artcollection.utils.SharedPref;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by sunrenwei on 2017/03/13
 */

public class MineCirclePresenterImpl implements MineCircleContract.Presenter {

    private MineCircleContract.View view;
    private String ctype;
    private Context context;
    private List<ItemCircle> list;
    private BaseRvAdapter adapter;

    private SparseBooleanArray mCollapsedStatus = new SparseBooleanArray();

    public MineCirclePresenterImpl(Context context, String ctype) {
        this.context = context;
        this.ctype = ctype;
        list = new ArrayList<>();
    }

    @Override
    public void attachView(MineCircleContract.View view) {
        this.view = view;
    }

    @Override
    public void detachView() {
        this.view = null;
    }

    @Override
    public void getData(final boolean refresh) {
        view.showProgress();

        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("service", "Circle.getMyCircle");
        hashMap.put("userId", SharedPref.getString(Constants.USER_ID));
        hashMap.put("ctype", ctype);
        hashMap.put("lastId", refresh ? "0" : list.get(list.size() - 1).getMsgId());
        HttpsClient client = new HttpsClient();
        client.post(hashMap, new HttpResponseHandler() {
            @Override
            protected void onSuccess(int statusCode, JSONObject data) throws Exception {
                if (data.getInt("code") == 0) {
                    if (refresh) {
                        list.clear();
                    }
                    list.addAll(GsonUtils.parseJsonArray(data.getString("list"), ItemCircle.class));
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            protected void onFailed(int statusCode, Exception e) {

            }

            @Override
            protected void onFinish() {
                view.hideProgress();
            }
        });
    }

    @Override
    public BaseRvAdapter getAdapter() {
        if (adapter == null) {
            adapter = new BaseRvAdapter<ItemCircle>(list) {

                @NonNull
                @Override
                public AdapterItem createItem(@NonNull Object type) {
                    return new CircleAdapterItem(mCollapsedStatus);
                }
            };
        }
        return adapter;
    }
}