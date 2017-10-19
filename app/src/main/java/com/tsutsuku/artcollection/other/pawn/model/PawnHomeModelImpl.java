package com.tsutsuku.artcollection.other.pawn.model;

import com.tsutsuku.artcollection.common.Constants;
import com.tsutsuku.artcollection.http.HttpResponseHandler;
import com.tsutsuku.artcollection.http.HttpsClient;
import com.tsutsuku.artcollection.other.pawn.contract.PawnHomeContract;

import java.util.HashMap;

/**
* Created by sunrenwei on 2017/06/21
*/

public class PawnHomeModelImpl implements PawnHomeContract.Model{

    @Override
    public void getData(String pageIndex, HttpResponseHandler handler) {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("service", "Crowd.getCrowdList");
        hashMap.put("pageIndex", pageIndex);
        hashMap.put("pageSize", String.valueOf(Constants.PAGE_SIZE));
        HttpsClient client = new HttpsClient();
        client.post(hashMap, handler);
    }

    @Override
    public void getTop(HttpResponseHandler handler) {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("service", "App.getIndexAd");
        hashMap.put("positionId", "11");
        HttpsClient client = new HttpsClient();
        client.post(hashMap, handler);
    }
}