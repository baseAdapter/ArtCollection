package com.tsutsuku.artcollection.presenter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.tsutsuku.artcollection.common.Constants;
import com.tsutsuku.artcollection.contract.IdentifyInfoContract;
import com.tsutsuku.artcollection.http.HttpResponseHandler;
import com.tsutsuku.artcollection.http.HttpsClient;
import com.tsutsuku.artcollection.model.IdentifyInfoBean;
import com.tsutsuku.artcollection.utils.SharedPref;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.HashMap;

/**
 * Created by sunrenwei on 2017/01/18
 */

public class IdentifyInfoPresenterImpl implements IdentifyInfoContract.Presenter {

    IdentifyInfoContract.View view;

    private String msgId;
    private String expertId;

    private Gson gson = new Gson();
    private Type type = new TypeToken<IdentifyInfoBean>() {
    }.getType();

    public IdentifyInfoPresenterImpl(String msgId) {
        this.msgId = msgId;
    }

    @Override
    public void identify(final String year, final String money, final String meno) {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("service", "Circle.addCheckInfo");
        hashMap.put("msgId", msgId);
        hashMap.put("expertId", expertId);
        hashMap.put("checkYear", year);
        hashMap.put("checkMoney", money);
        hashMap.put("checkMeno", meno);
        HttpsClient client = new HttpsClient();
        client.post(hashMap, new HttpResponseHandler() {
            @Override
            protected void onSuccess(int statusCode, JSONObject data) throws Exception {
                if (data.getInt("code") == 0) {
                    IdentifyInfoBean bean = new IdentifyInfoBean(meno, money, "1", year, expertId, msgId,
                            SharedPref.getString(Constants.NICK),
                            SharedPref.getString(Constants.AVATAR));
                    view.hideDialog(bean);
                }
            }

            @Override
            protected void onFailed(int statusCode, Exception e) {

            }
        });
    }

    @Override
    public void attachView(IdentifyInfoContract.View view) {
        this.view = view;
        getData();
    }

    @Override
    public void detachView() {
        this.view = null;
    }

    private void getData() {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("service", "Circle.getCheckInfo");
        hashMap.put("msgId", msgId);
        HttpsClient client = new HttpsClient();
        client.post(hashMap, new HttpResponseHandler() {
            @Override
            protected void onSuccess(int statusCode, JSONObject data) throws Exception {
                if (data.getInt("code") == 0) {
                    IdentifyInfoBean bean = gson.fromJson(data.getString("info"), type);
                    expertId = bean.getExpertId();
                    view.setData(bean);
                }
            }

            @Override
            protected void onFailed(int statusCode, Exception e) {

            }
        });

    }
}