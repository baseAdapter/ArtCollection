package com.tsutsuku.artcollection.other.rent.model;
import com.tsutsuku.artcollection.common.Constants;
import com.tsutsuku.artcollection.http.HttpResponseHandler;
import com.tsutsuku.artcollection.http.HttpsClient;
import com.tsutsuku.artcollection.other.rent.contract.RentDetailContract;
import com.tsutsuku.artcollection.utils.SharedPref;

import java.util.HashMap;

/**
* Created by sunrenwei on 2017/06/25
*/

public class RentDetailModelImpl implements RentDetailContract.Model{

    @Override
    public void getData(String productId, HttpResponseHandler handler) {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("service", "Product.getProductInfo");
        hashMap.put("productId", productId);
        hashMap.put("user_Id", SharedPref.getString(Constants.USER_ID));
        HttpsClient client = new HttpsClient();
        client.post(hashMap, handler);
    }

    @Override
    public void collection(boolean collection, String productId, HttpResponseHandler handler) {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("service", collection ? "Collections.delete" : "Collections.add");
        hashMap.put("pId", productId);
        hashMap.put("ctype", "2");
        hashMap.put("userId", SharedPref.getString(Constants.USER_ID));
        HttpsClient client = new HttpsClient();
        client.post(hashMap, handler);
    }

}