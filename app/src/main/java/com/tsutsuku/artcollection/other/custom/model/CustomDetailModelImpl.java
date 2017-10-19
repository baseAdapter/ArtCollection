package com.tsutsuku.artcollection.other.custom.model;

import com.tsutsuku.artcollection.http.HttpResponseHandler;
import com.tsutsuku.artcollection.http.HttpsClient;
import com.tsutsuku.artcollection.other.custom.contract.CustomDetailContract;

import java.util.HashMap;

/**
 * Created by sunrenwei on 2017/06/08
 */

public class CustomDetailModelImpl implements CustomDetailContract.Model {

    @Override
    public void getData(String diyId, HttpResponseHandler handler) {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("service", "CustDiy.getCustDiyInfo");
        hashMap.put("diyId", diyId);
        HttpsClient client = new HttpsClient();
        client.post(hashMap, handler);
    }
}