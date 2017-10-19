package com.tsutsuku.artcollection.presenter;

import com.tsutsuku.artcollection.common.Constants;
import com.tsutsuku.artcollection.http.HttpResponseHandler;
import com.tsutsuku.artcollection.http.HttpsClient;
import com.tsutsuku.artcollection.utils.SharedPref;

import org.json.JSONObject;

import java.util.HashMap;

/**
 * @Author Tsutsuku
 * @Create 2017/3/25
 * @Description 关注  工具类
 */

public class FollowRepository {

    public static void follow(final boolean follow, String ctype, String pId, final OnFollowListener listener) {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("service", follow ? "Follow.delete" : "Follow.add");
        hashMap.put("userId", SharedPref.getString(Constants.USER_ID));
        hashMap.put("ctype", ctype);
        hashMap.put("pId", pId);
        HttpsClient client = new HttpsClient();
        client.post(hashMap, new HttpResponseHandler() {
            @Override
            protected void onSuccess(int statusCode, JSONObject data) throws Exception {
                if (data.getInt("code") == 0) {
                    listener.success(!follow);
                }
            }

            @Override
            protected void onFailed(int statusCode, Exception e) {

            }
        });
    }

    public interface OnFollowListener {
        void success(boolean follow);
    }
}
