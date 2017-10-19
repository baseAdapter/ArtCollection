package com.tsutsuku.artcollection.other.pawn.presenter;

import com.tsutsuku.artcollection.http.HttpResponseHandler;
import com.tsutsuku.artcollection.other.pawn.contract.PawnPublishContract;
import com.tsutsuku.artcollection.presenter.circle.ShareBasePresenterImpl;

import org.json.JSONObject;

import java.util.List;

/**
 * Created by sunrenwei on 2017/06/25
 */

public class PawnPublishPresenterImpl extends ShareBasePresenterImpl implements PawnPublishContract.Presenter {

    private PawnPublishContract.View view;
    private PawnPublishContract.Model model;

    public PawnPublishPresenterImpl(PawnPublishContract.View view, PawnPublishContract.Model model) {
        this.view = view;
        this.model = model;
    }

    @Override
    public void share(String title, String type, String size, String content, String tel, String city, List<String> list) {
        model.share(title, type, size, content, tel, city, list, new HttpResponseHandler() {
            @Override
            protected void onSuccess(int statusCode, JSONObject data) throws Exception {
                if (data.getInt("code") == 0) {
                    view.finish();
                }
            }

            @Override
            protected void onFailed(int statusCode, Exception e) {

            }
        });
    }
}