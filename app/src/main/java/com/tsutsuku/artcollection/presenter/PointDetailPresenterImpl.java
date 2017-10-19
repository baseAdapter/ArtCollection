package com.tsutsuku.artcollection.presenter;

import android.content.Intent;

import com.tsutsuku.artcollection.common.Constants;
import com.tsutsuku.artcollection.contract.PointDetailContract;
import com.tsutsuku.artcollection.http.HttpResponseHandler;
import com.tsutsuku.artcollection.http.HttpsClient;
import com.tsutsuku.artcollection.model.point.ItemPoint;
import com.tsutsuku.artcollection.utils.SharedPref;

import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by sunrenwei on 2017/02/27
 */

public class PointDetailPresenterImpl implements PointDetailContract.Presenter {

    private PointDetailContract.View view;
    private ItemPoint item;

    @Override
    public void parseItem(ItemPoint item) {
        this.item = item;
        view.setView(item);
    }

    @Override
    public void attachView(PointDetailContract.View view) {
        this.view = view;
    }

    @Override
    public void detachView() {
        this.view = null;
    }
}