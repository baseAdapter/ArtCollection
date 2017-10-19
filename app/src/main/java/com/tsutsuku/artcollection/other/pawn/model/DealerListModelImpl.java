package com.tsutsuku.artcollection.other.pawn.model;

import com.tsutsuku.artcollection.common.Constants;
import com.tsutsuku.artcollection.http.HttpResponseHandler;
import com.tsutsuku.artcollection.http.HttpsClient;
import com.tsutsuku.artcollection.other.pawn.contract.DealerListContract;

import java.util.HashMap;

/**
 * Created by sunrenwei on 2017/06/21
 */

public class DealerListModelImpl implements DealerListContract.Model {

    @Override
    public void getData(String pageIndex, HttpResponseHandler handler) {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("service", "Circle.getJinJiShiList");
        hashMap.put("pageIndex", pageIndex);
        hashMap.put("pageSize", String.valueOf(Constants.PAGE_SIZE));
        HttpsClient client = new HttpsClient();
        client.post(hashMap, handler);
    }
}