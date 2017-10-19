package com.tsutsuku.artcollection.other.rent.presenter;

import com.tsutsuku.artcollection.http.HttpResponseHandler;
import com.tsutsuku.artcollection.model.ItemProduct;
import com.tsutsuku.artcollection.other.rent.contract.RentListContract;
import com.tsutsuku.artcollection.utils.GsonUtils;

import org.json.JSONObject;

/**
 * Created by sunrenwei on 2017/06/08
 */

public class RentListPresenterImpl implements RentListContract.Presenter {
    private RentListContract.Model model;
    private RentListContract.View view;
    private boolean isTop;

    public RentListPresenterImpl(RentListContract.Model model, RentListContract.View view) {
        this.model = model;
        this.view = view;
    }


    @Override
    public void setTop() {
        isTop = true;
    }

    @Override
    public void getData(final String pageIndex) {
        model.getData(pageIndex, isTop, new HttpResponseHandler() {
            @Override
            protected void onSuccess(int statusCode, JSONObject data) throws Exception {
                if (data.getInt("code") == 0) {
                    if ("1".equals(pageIndex)) {
                        view.setData(data.getJSONObject("list").getInt("total"),
                                GsonUtils.parseJsonArray(data.getJSONObject("list").getString("list"), ItemProduct.class));
                    } else {
                        view.addData(GsonUtils.parseJsonArray(data.getJSONObject("list").getString("list"), ItemProduct.class));
                    }
                }
            }

            @Override
            protected void onFailed(int statusCode, Exception e) {

            }
        });
    }
}