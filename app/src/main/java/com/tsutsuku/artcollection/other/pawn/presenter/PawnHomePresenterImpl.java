package com.tsutsuku.artcollection.other.pawn.presenter;

import com.tsutsuku.artcollection.http.HttpResponseHandler;
import com.tsutsuku.artcollection.model.HomeBean;
import com.tsutsuku.artcollection.other.pawn.contract.PawnHomeContract;
import com.tsutsuku.artcollection.other.pawn.model.ItemCrowd;
import com.tsutsuku.artcollection.utils.GsonUtils;

import org.json.JSONObject;

/**
 * Created by sunrenwei on 2017/06/21
 */

public class PawnHomePresenterImpl implements PawnHomeContract.Presenter {
    private PawnHomeContract.View view;
    private PawnHomeContract.Model model;

    public PawnHomePresenterImpl(PawnHomeContract.View view, PawnHomeContract.Model model) {
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
                                GsonUtils.parseJsonArray(data.getJSONObject("list").getString("list"), ItemCrowd.class));
                    } else {
                        view.addData(GsonUtils.parseJsonArray(data.getJSONObject("list").getString("list"), ItemCrowd.class));
                    }
                }
            }

            @Override
            protected void onFailed(int statusCode, Exception e) {

            }
        });
    }

    @Override
    public void getTop() {
        model.getTop(new HttpResponseHandler() {
            @Override
            protected void onSuccess(int statusCode, JSONObject data) throws Exception {
                if (data.getInt("code") == 0) {
                    view.setTop(GsonUtils.parseJsonArray(data.getString("list"), HomeBean.ADBean.class));
                }

            }

            @Override
            protected void onFailed(int statusCode, Exception e) {

            }
        });
    }
}