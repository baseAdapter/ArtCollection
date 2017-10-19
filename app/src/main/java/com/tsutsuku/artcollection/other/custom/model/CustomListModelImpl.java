package com.tsutsuku.artcollection.other.custom.model;

import com.tsutsuku.artcollection.common.Constants;
import com.tsutsuku.artcollection.http.HttpResponseHandler;
import com.tsutsuku.artcollection.http.HttpsClient;
import com.tsutsuku.artcollection.other.custom.contract.CustomListContract;

import java.util.HashMap;

/**
 * Created by sunrenwei on 2017/06/08
 */

public class CustomListModelImpl implements CustomListContract.Model {


    @Override
    public void getData(String id, String pageIndex, HttpResponseHandler httpResponseHandler) {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("service", "CustDiy.getCustDiysList");
        hashMap.put("cateId", id);
        hashMap.put("pageIndex", pageIndex);
        hashMap.put("pageSize", String.valueOf(Constants.PAGE_SIZE));
        HttpsClient client = new HttpsClient();
        client.post(hashMap, httpResponseHandler);
    }
}