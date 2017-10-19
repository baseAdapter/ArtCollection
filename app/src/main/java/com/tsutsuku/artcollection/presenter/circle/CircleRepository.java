package com.tsutsuku.artcollection.presenter.circle;

import android.util.Log;

import com.tsutsuku.artcollection.common.BusEvent;
import com.tsutsuku.artcollection.common.Constants;
import com.tsutsuku.artcollection.http.HttpResponseHandler;
import com.tsutsuku.artcollection.http.HttpsClient;
import com.tsutsuku.artcollection.model.BusBean;
import com.tsutsuku.artcollection.utils.RxBus;
import com.tsutsuku.artcollection.utils.SharedPref;

import org.json.JSONObject;

import java.util.HashMap;

/**
 * @Author Tsutsuku
 * @Create 2017/2/23
 * @Description
 */

public class CircleRepository {
    public static final String TAG = CircleRepository.class.getSimpleName();

    public static void delete(final String msgId) {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("service", "Circle.deleteMessage");
        hashMap.put("msgId", msgId);
        hashMap.put("userId", SharedPref.getString(Constants.USER_ID));
        HttpsClient client = new HttpsClient();
        client.post(hashMap, new HttpResponseHandler() {
            @Override
            protected void onSuccess(int statusCode, JSONObject data) throws Exception {
                if (data.getInt("code") == 0) {
                    Log.e(TAG,"onSuccess---"+statusCode);

                    RxBus.getDefault().post(BusEvent.CIRCLE, new BusBean(Constants.CIRCLE_DELETE, msgId));
                }
            }

            @Override
            protected void onFailed(int statusCode, Exception e) {
                Log.e(TAG,"onFailed---"+statusCode);

            }
        });
    }
}
