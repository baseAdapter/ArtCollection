package com.tsutsuku.artcollection.other.pawn.model;

import com.tsutsuku.artcollection.common.Constants;
import com.tsutsuku.artcollection.http.HttpResponseHandler;
import com.tsutsuku.artcollection.http.HttpsClient;
import com.tsutsuku.artcollection.other.pawn.contract.PawnPublishContract;
import com.tsutsuku.artcollection.utils.SharedPref;

import java.util.HashMap;
import java.util.List;

/**
* Created by sunrenwei on 2017/06/25
*/

public class PawnPublishModelImpl implements PawnPublishContract.Model{

    @Override
    public void share(String title, String type, String size, String content, String tel, String city, List<String> list, HttpResponseHandler handler) {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("service", "Circle.publishPawn");
        hashMap.put("userId", SharedPref.getString(Constants.USER_ID));
        hashMap.put("title", title);
        hashMap.put("caizhi", type);
        hashMap.put("size", size);
        hashMap.put("content", content);
        hashMap.put("tel", tel);
        hashMap.put("city", city);
        HttpsClient client = new HttpsClient();
        client.post(hashMap, list, handler);
    }
}