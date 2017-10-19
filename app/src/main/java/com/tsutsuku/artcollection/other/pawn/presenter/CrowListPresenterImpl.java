package com.tsutsuku.artcollection.other.pawn.presenter;

import android.content.Context;

import com.tsutsuku.artcollection.http.HttpResponseHandler;
import com.tsutsuku.artcollection.other.pawn.contract.CrowListContract;
import com.tsutsuku.artcollection.other.pawn.model.ItemCrowdType;
import com.tsutsuku.artcollection.utils.GsonUtils;
import com.tsutsuku.artcollection.utils.ShareUtils;

import org.json.JSONObject;

/**
 * Created by sunrenwei on 2017/06/26
 */

public class CrowListPresenterImpl implements CrowListContract.Presenter {

    private CrowListContract.View view;
    private CrowListContract.Model model;
    private String picUrl;
    private Context context;
    private String id;

    public CrowListPresenterImpl(String id, Context context, CrowListContract.View view, CrowListContract.Model model, String picUrl) {
        this.view = view;
        this.model = model;
        this.picUrl = picUrl;
        this.id = id;
        this.context = context;
    }

    @Override
    public void share() {
        ShareUtils.showShare(context, picUrl, "艺术品众筹", "");
    }

    @Override
    public void getData(final String pageIndex) {
        model.getData(id, pageIndex, new HttpResponseHandler() {
            @Override
            protected void onSuccess(int statusCode, JSONObject data) throws Exception {
                if (data.getInt("code") == 0) {
                    if ("1".equals(pageIndex)) {
                        view.setData(data.getJSONObject("list").getInt("total"),
                                GsonUtils.parseJsonArray(data.getJSONObject("list").getString("list"), ItemCrowdType.class));
                    } else {
                        view.addData(GsonUtils.parseJsonArray(data.getJSONObject("list").getString("list"), ItemCrowdType.class));
                    }
                }
            }

            @Override
            protected void onFailed(int statusCode, Exception e) {

            }
        });
    }
}