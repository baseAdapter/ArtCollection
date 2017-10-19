package com.tsutsuku.artcollection.presenter.activity;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;

import com.tsutsuku.artcollection.common.Constants;
import com.tsutsuku.artcollection.contract.activity.ActivityPageContract;
import com.tsutsuku.artcollection.contract.base.AdapterItem;
import com.tsutsuku.artcollection.contract.base.OnItemSimpleClickListener;
import com.tsutsuku.artcollection.http.HttpResponseHandler;
import com.tsutsuku.artcollection.http.HttpsClient;
import com.tsutsuku.artcollection.model.activity.ItemActivity;
import com.tsutsuku.artcollection.ui.activity.ActivityAdapterItem;
import com.tsutsuku.artcollection.ui.activity.ActivityDetailActivity;
import com.tsutsuku.artcollection.ui.base.BaseRvAdapter;
import com.tsutsuku.artcollection.utils.GsonUtils;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by sunrenwei on 2017/03/12
 */

public class ActivityPagePresenterImpl implements ActivityPageContract.Presenter {

    private BaseRvAdapter adapter;
    private List<ItemActivity> list;
    private Context context;
    private String ctype;

    private ActivityPageContract.View view;

    public ActivityPagePresenterImpl(Context context, String ctype) {
        this.context = context;
        this.ctype = ctype;
        list = new ArrayList<>();
    }

    @Override
    public BaseRvAdapter getAdapter() {
        if (adapter == null) {
            adapter = new BaseRvAdapter<ItemActivity>(list) {

                @NonNull
                @Override
                public AdapterItem createItem(@NonNull Object type) {
                    return new ActivityAdapterItem(context, new OnItemSimpleClickListener<ItemActivity>() {
                        @Override
                        public void onItemClick(ItemActivity item) {
                            ActivityDetailActivity.launch(context, item.getActivityId());
                        }
                    });
                }
            };
        }
        return adapter;
    }

    @Override
    public void attachView(ActivityPageContract.View view) {
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

        switch (ctype) {
            case "0": {
                hashMap.put("isTop", "1");
                hashMap.put("ctype", "1");
            }
            break;
            case "1": {
                hashMap.put("isOnline", "1");
                hashMap.put("ctype", "1");
            }
            break;
            case "2": {
                hashMap.put("isOnline", "2");
                hashMap.put("ctype", "1");
            }
            break;
            case "3": {
                hashMap.put("liveStatus", "1");
                hashMap.put("ctype", "2");
            }
            break;
            case "4": {
                hashMap.put("liveStatus", "0");
                hashMap.put("ctype", "2");
            }
            break;
            case "5": {
                hashMap.put("liveStatus", "2");
                hashMap.put("ctype", "2");
            }
            break;
        }
        hashMap.put("service", "Activities.getActivitieList");
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
                    list.addAll(GsonUtils.parseJsonArray(data.getJSONObject("list").getString("list"), ItemActivity.class));
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            protected void onFailed(int statusCode, Exception e) {

            }

            @Override
            protected void onFinish() {
                view.hideProgress();
                adapter.finishLoading();
            }
        });
    }
}