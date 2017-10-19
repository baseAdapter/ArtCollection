package com.tsutsuku.artcollection.other.rent.model;

import com.tsutsuku.artcollection.common.Constants;
import com.tsutsuku.artcollection.http.HttpResponseHandler;
import com.tsutsuku.artcollection.http.HttpsClient;
import com.tsutsuku.artcollection.other.rent.contract.RentListContract;

import java.util.HashMap;

/**
 * Created by sunrenwei on 2017/06/08
 */

public class RentListModelImpl implements RentListContract.Model {


    @Override
    public void getData(String pageIndex, boolean isTop, HttpResponseHandler handler) {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("service", "Product.getProductsList");
        hashMap.put("isAuction", "3");
        hashMap.put("top", isTop ? "1" : "0");
        hashMap.put("pageIndex", pageIndex);
        hashMap.put("pageSize", String.valueOf(Constants.PAGE_SIZE));
        HttpsClient client = new HttpsClient();
        client.post(hashMap, handler);
    }
}