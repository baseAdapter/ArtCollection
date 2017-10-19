package com.tsutsuku.artcollection.other.custom.presenter;

import com.tsutsuku.artcollection.http.HttpResponseHandler;
import com.tsutsuku.artcollection.model.HomeBean;
import com.tsutsuku.artcollection.other.custom.contract.CustomHomeContract;
import com.tsutsuku.artcollection.other.custom.model.ItemCustomType;
import com.tsutsuku.artcollection.utils.GsonUtils;

import org.json.JSONObject;

/**
 * Created by sunrenwei on 2017/06/19
 */

public class CustomHomePresenterImpl implements CustomHomeContract.Presenter {
    private CustomHomeContract.View view;
    private CustomHomeContract.Model model;

    public CustomHomePresenterImpl(CustomHomeContract.View view, CustomHomeContract.Model model) {
        this.view = view;
        this.model = model;
    }

    @Override
    public void start() {
        model.getBar(new HttpResponseHandler() {
            @Override
            protected void onSuccess(int statusCode, JSONObject data) throws Exception {
                if (data.getInt("code") == 0) {
                    view.setBar(GsonUtils.parseJsonArray(data.getString("list"), HomeBean.ADBean.class));
                }
            }

            @Override
            protected void onFailed(int statusCode, Exception e) {

            }
        });
        
        model.getType(new HttpResponseHandler() {
            @Override
            protected void onSuccess(int statusCode, JSONObject data) throws Exception {
                if (data.getInt("code") == 0) {
                    view.setCustomList(GsonUtils.parseJsonArray(data.getString("list"), ItemCustomType.class));
                }
            }

            @Override
            protected void onFailed(int statusCode, Exception e) {

            }
        });
    }
}