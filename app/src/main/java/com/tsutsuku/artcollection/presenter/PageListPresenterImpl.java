package com.tsutsuku.artcollection.presenter;

import android.content.Context;
import android.support.annotation.NonNull;

import com.tsutsuku.artcollection.common.Constants;
import com.tsutsuku.artcollection.contract.base.AdapterItem;
import com.tsutsuku.artcollection.contract.base.OnItemSimpleClickListener;
import com.tsutsuku.artcollection.contract.lesson.PageListContract;
import com.tsutsuku.artcollection.http.HttpResponseHandler;
import com.tsutsuku.artcollection.http.HttpsClient;
import com.tsutsuku.artcollection.model.lesson.ItemVideo;
import com.tsutsuku.artcollection.ui.lesson.LessonDetailActivity;
import com.tsutsuku.artcollection.ui.base.BaseRvAdapter;
import com.tsutsuku.artcollection.ui.lesson.LessonAdapterItem;
import com.tsutsuku.artcollection.utils.GsonUtils;

import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by sunrenwei on 2017/03/05
 */

public class PageListPresenterImpl implements PageListContract.Presenter {

    private PageListContract.View view;
    private String cateId;
    private BaseRvAdapter adapter;
    private Context context;

    public PageListPresenterImpl(Context context, String cateId) {
        this.context = context;
        this.cateId = cateId;
    }

    @Override
    public BaseRvAdapter getAdapter() {
        if (adapter == null){
            adapter = new BaseRvAdapter<ItemVideo>(null) {

                @NonNull
                @Override
                public AdapterItem createItem(@NonNull Object type) {
                    return new LessonAdapterItem(context, new OnItemSimpleClickListener<ItemVideo>() {
                        @Override
                        public void onItemClick(ItemVideo item) {
                            LessonDetailActivity.launch(context, item.getVideoId());
                        }
                    });
                }
            };
        }
        return adapter;
    }

    @Override
    public void attachView(PageListContract.View view) {
        this.view = view;
    }

    @Override
    public void detachView() {
        this.view = null;
    }

    public void getData(boolean refresh) {
        getData("", refresh);
    }

    private void getData(String keyWord, final boolean refresh) {
        view.showProgress();

        HashMap<String, String> hashMap = new HashMap<>();
        HttpsClient client = new HttpsClient();
        hashMap.put("service", "Video.getVideoListByClass");
        hashMap.put("cateId", cateId);
        hashMap.put("keyWord", keyWord);
        hashMap.put("pageIndex", refresh ? adapter.clearPageIndex() : adapter.addPageIndex());
        hashMap.put("pageSize", String.valueOf(Constants.PAGE_SIZE));
        client.post(hashMap, new HttpResponseHandler() {
            @Override
            protected void onSuccess(int statusCode, JSONObject data) throws Exception {
                if (data.getInt("code") == 0) {
                    if (refresh){
                        adapter.setTotal(data.getJSONObject("list").getInt("total"));
                    }
                    adapter.getData().addAll(GsonUtils.parseJsonArray(data.getJSONObject("list").getString("list"), ItemVideo.class));
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
}