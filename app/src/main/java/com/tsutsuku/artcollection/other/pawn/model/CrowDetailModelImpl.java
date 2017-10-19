package com.tsutsuku.artcollection.other.pawn.model;

import com.tsutsuku.artcollection.http.HttpResponseHandler;
import com.tsutsuku.artcollection.http.HttpsClient;
import com.tsutsuku.artcollection.other.pawn.contract.CrowDetailContract;

import java.util.HashMap;

/**
* Created by sunrenwei on 2017/06/26
*/

public class CrowDetailModelImpl implements CrowDetailContract.Model{

    @Override
    public void getData(String id, HttpResponseHandler handler) {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("service", "Crowd.getCrowdInfo");
        hashMap.put("id", id);
        HttpsClient client = new HttpsClient();
        client.post(hashMap, handler);
    }
}