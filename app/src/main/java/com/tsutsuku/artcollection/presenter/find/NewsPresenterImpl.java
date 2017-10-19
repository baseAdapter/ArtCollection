package com.tsutsuku.artcollection.presenter.find;

import android.support.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.tsutsuku.artcollection.common.Constants;
import com.tsutsuku.artcollection.contract.base.AdapterItem;
import com.tsutsuku.artcollection.contract.base.BaseRvContract;
import com.tsutsuku.artcollection.http.HttpResponseHandler;
import com.tsutsuku.artcollection.http.HttpsClient;
import com.tsutsuku.artcollection.model.ItemNews;
import com.tsutsuku.artcollection.ui.base.BaseRvAdapter;
import com.tsutsuku.artcollection.ui.news.NewsAdapterItem;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;

/**
 * @Author Sun Renwei
 * @Create 2017/1/10
 * @Description Content
 */

public class NewsPresenterImpl implements BaseRvContract.Presenter {
    BaseRvContract.View view;

    private String type;
    private BaseRvAdapter<ItemNews> adapter;
    private int pageIndex = 1;
    private int total;

    private Gson gson = new Gson();
    private Type itemType = new TypeToken<List<ItemNews>>() {
    }.getType();


    public NewsPresenterImpl(String type) {
        this.type = type;
    }

    @Override
    public void loadCircle(final boolean refresh) {
        pageIndex = refresh ? 1 : pageIndex + 1;

        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("service", "App.getNewsList");
        hashMap.put("ctype", type);
        hashMap.put("pageIndex", String.valueOf(pageIndex));
        hashMap.put("pageSize", String.valueOf(Constants.PAGE_SIZE));
        HttpsClient client = new HttpsClient();
        client.post(hashMap, new HttpResponseHandler() {
            @Override
            protected void onSuccess(int statusCode, JSONObject data) throws Exception {
                if (data.getInt("code") == 0) {
                    JSONObject list = data.getJSONObject("list");

                    if (refresh) {
                        adapter.setTotal(list.getInt("total"));
                    }

                    adapter.getData().addAll((List<ItemNews>) gson.fromJson(list.getString("list"), itemType));
                    adapter.notifyDataSetChanged();

                    view.hideProgress();
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
            adapter = new BaseRvAdapter<ItemNews>(null) {

                @NonNull
                @Override
                public AdapterItem createItem(@NonNull Object item) {
                    return new NewsAdapterItem();
                }
            };
        }
        return adapter;
    }


    @Override
    public void attachView(BaseRvContract.View view) {
        this.view = view;
    }

    @Override
    public void detachView() {
        this.view = null;
    }
}
