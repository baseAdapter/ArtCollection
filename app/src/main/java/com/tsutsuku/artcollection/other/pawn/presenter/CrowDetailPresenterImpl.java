package com.tsutsuku.artcollection.other.pawn.presenter;

import android.content.Context;

import com.tsutsuku.artcollection.http.HttpResponseHandler;
import com.tsutsuku.artcollection.other.pawn.contract.CrowDetailContract;
import com.tsutsuku.artcollection.other.pawn.model.CrowdInfo;
import com.tsutsuku.artcollection.other.pawn.ui.CrowdListActivity;
import com.tsutsuku.artcollection.utils.GsonUtils;
import com.tsutsuku.artcollection.utils.ShareUtils;

import org.json.JSONObject;

/**
 * Created by sunrenwei on 2017/06/26
 */

public class CrowDetailPresenterImpl implements CrowDetailContract.Presenter {
    private CrowdInfo info;
    private Context context;
    private CrowDetailContract.Model model;
    private CrowDetailContract.View view;
    private String id;

    public CrowDetailPresenterImpl(String id, Context context, CrowDetailContract.Model model, CrowDetailContract.View view) {
        this.id = id;
        this.context = context;
        this.model = model;
        this.view = view;
    }

    @Override
    public void share() {
        ShareUtils.showShare(context, info.getLogo(), info.getTitle(), "");
    }

    @Override
    public void start() {
        model.getData(id, new HttpResponseHandler() {
            @Override
            protected void onSuccess(int statusCode, JSONObject data) throws Exception {
                if (data.getInt("code") == 0) {
                    info = GsonUtils.parseJson(data.getString("info"), CrowdInfo.class);
                    view.setData(info);
                }
            }

            @Override
            protected void onFailed(int statusCode, Exception e) {

            }
        });
    }

    @Override
    public void support() {
        CrowdListActivity.launch(context, info.getId(), info.getBriefPics());
    }
}