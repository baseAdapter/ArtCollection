package com.tsutsuku.artcollection.http;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.tsutsuku.artcollection.api.ApiConstants;
import com.tsutsuku.artcollection.common.Constants;
import com.tsutsuku.artcollection.ui.base.BaseApplication;
import com.tsutsuku.artcollection.ui.login.LoginActivity;
import com.tsutsuku.artcollection.ui.main.MainActivity;
import com.tsutsuku.artcollection.utils.EncryptUtils;
import com.tsutsuku.artcollection.utils.SharedPref;
import com.tsutsuku.artcollection.utils.TLog;
import com.tsutsuku.artcollection.utils.ToastUtils;

import org.json.JSONObject;

import java.util.HashMap;


/**
 * Created by Tsutsuku on 2015/12/23.
 */
public class SysUtils extends BaseSysUtils {
    private static final Context context = BaseApplication.getInstance();

    private static SysUtils instance;
    private static final String kickDown = "您的登录信息已过期！";

    public static SysUtils getInstance() {
        if (instance == null) {
            instance = new SysUtils();
        }
        return instance;
    }

    public static void getAreaList() {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("service", "ReceiptAddress.getAreaList");
        HttpsClient client = new HttpsClient();
        client.post(hashMap, new HttpResponseHandler() {
            @Override
            protected void onSuccess(int statusCode, JSONObject data) throws Exception {
                if (data.getInt("code") == 0) {
                    SharedPref.putSysString(Constants.AREA_LIST, data.getString("list"));
                }
            }

            @Override
            protected void onFailed(int statusCode, Exception e) {

            }
        });

    }

    public static void getCateMore() {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("service", "Product.getCateMore");
        HttpsClient client = new HttpsClient();
        client.post(hashMap, new HttpResponseHandler() {
            @Override
            protected void onSuccess(int statusCode, JSONObject data) throws Exception {
                if (data.getInt("code") == 0) {
                    SharedPref.putSysString(Constants.CATE_MORE, data.getString("list"));
                }
            }

            @Override
            protected void onFailed(int statusCode, Exception e) {

            }
        });
    }
}
