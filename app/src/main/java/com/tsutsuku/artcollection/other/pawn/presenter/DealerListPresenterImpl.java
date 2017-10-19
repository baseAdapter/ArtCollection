package com.tsutsuku.artcollection.other.pawn.presenter;

import com.tsutsuku.artcollection.http.HttpResponseHandler;
import com.tsutsuku.artcollection.other.pawn.contract.DealerListContract;
import com.tsutsuku.artcollection.other.pawn.model.ItemDealer;
import com.tsutsuku.artcollection.utils.GsonUtils;

import org.json.JSONObject;

/**
 * Created by sunrenwei on 2017/06/21
 */

public class DealerListPresenterImpl implements DealerListContract.Presenter {

    private DealerListContract.View view;
    private DealerListContract.Model model;

    public DealerListPresenterImpl(DealerListContract.View view, DealerListContract.Model model) {
        this.view = view;
        this.model = model;
    }

    @Override
    public void getData(final String pageIndex) {
        model.getData(pageIndex, new HttpResponseHandler() {
            @Override
            protected void onSuccess(int statusCode, JSONObject data) throws Exception {
                if (data.getInt("code") == 0) {
                    if ("1".equals(pageIndex)) {
                        view.setData(data.getJSONObject("list").getInt("total"),
                                GsonUtils.parseJsonArray(data.getJSONObject("list").getString("list"), ItemDealer.class));
                    } else {
                        view.addData(GsonUtils.parseJsonArray(data.getJSONObject("list").getString("list"), ItemDealer.class));
                    }
                }
            }

            @Override
            protected void onFailed(int statusCode, Exception e) {

            }
        });
    }
}