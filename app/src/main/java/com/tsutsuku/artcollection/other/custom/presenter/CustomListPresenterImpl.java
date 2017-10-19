package com.tsutsuku.artcollection.other.custom.presenter;

import com.tsutsuku.artcollection.http.HttpResponseHandler;
import com.tsutsuku.artcollection.other.custom.contract.CustomListContract;
import com.tsutsuku.artcollection.other.custom.model.ItemCustomBean;
import com.tsutsuku.artcollection.utils.GsonUtils;

import org.json.JSONObject;

/**
 * Created by sunrenwei on 2017/06/08
 */

public class CustomListPresenterImpl implements CustomListContract.Presenter {
    private CustomListContract.Model model;
    private CustomListContract.View view;
    private String id;

    public CustomListPresenterImpl(CustomListContract.View view, CustomListContract.Model model) {
        this.model = model;
        this.view = view;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    @Override
    public void getData(final String pageIndex) {
        model.getData(id, pageIndex, new HttpResponseHandler() {
            @Override
            protected void onSuccess(int statusCode, JSONObject data) throws Exception {
                if (data.getInt("code") == 0) {
                    if ("1".equals(pageIndex)) {
                        view.setTotal(data.getJSONObject("list").getInt("total"));
                    }
                    view.addData(GsonUtils.parseJsonArray(data.getJSONObject("list").getString("list"), ItemCustomBean.class));
                }
            }

            @Override
            protected void onFailed(int statusCode, Exception e) {

            }

            @Override
            protected void onFinish() {
                view.finishLoading();
            }
        });
    }
}