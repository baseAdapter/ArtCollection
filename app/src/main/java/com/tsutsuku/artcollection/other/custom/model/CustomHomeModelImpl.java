package com.tsutsuku.artcollection.other.custom.model;

import com.tsutsuku.artcollection.http.HttpResponseHandler;
import com.tsutsuku.artcollection.http.HttpsClient;
import com.tsutsuku.artcollection.other.custom.contract.CustomHomeContract;

import java.util.HashMap;

/**
* Created by sunrenwei on 2017/06/19
*/

public class CustomHomeModelImpl implements CustomHomeContract.Model{

    @Override
    public void getBar(HttpResponseHandler handler) {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("service", "App.getIndexAd");
        hashMap.put("positionId", "10");
        HttpsClient client = new HttpsClient();
        client.post(hashMap, handler);
    }

    @Override
    public void getType(HttpResponseHandler handler) {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("service", "CustDiy.getCustDiyCategory");
        HttpsClient client = new HttpsClient();
        client.post(hashMap, handler);
    }
}