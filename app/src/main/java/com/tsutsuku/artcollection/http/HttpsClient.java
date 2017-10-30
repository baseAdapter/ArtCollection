package com.tsutsuku.artcollection.http;

import android.text.TextUtils;
import android.util.Log;


import com.tsutsuku.artcollection.api.ApiConstants;
import com.tsutsuku.artcollection.common.Constants;
import com.tsutsuku.artcollection.ui.base.BaseApplication;
import com.tsutsuku.artcollection.ui.login.LoginActivity;
import com.tsutsuku.artcollection.utils.EncryptUtils;
import com.tsutsuku.artcollection.utils.SharedPref;
import com.tsutsuku.artcollection.utils.TLog;
import com.tsutsuku.artcollection.utils.TimeUtils;
import com.tsutsuku.artcollection.utils.ToastUtils;
import com.yolanda.nohttp.InputStreamBinary;
import com.yolanda.nohttp.NoHttp;
import com.yolanda.nohttp.RequestMethod;
import com.yolanda.nohttp.rest.OnResponseListener;
import com.yolanda.nohttp.rest.Request;
import com.yolanda.nohttp.rest.RequestQueue;
import com.yolanda.nohttp.rest.Response;

import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/**
 * @Author Sun Renwei
 * @Create 2016/8/22
 * @Description Content
 */
public class HttpsClient {
    public static final String TAG = HttpsClient.class.getSimpleName();

    private RequestQueue requestQueue;
    private HttpResponseHandler handler;
    private String service;

    public HttpsClient() {
        requestQueue = NoHttp.newRequestQueue();
    }

    public void post(HashMap<String, String> hashMap, final HttpResponseHandler handler) {
        post(ApiConstants.Api.HOST, hashMap, handler);
    }

    /**
     *
     *@param hashMap 传递过来的接口名和各种参数
     *@param handler
     *@param url
     *
     **/
    public void post(String url, HashMap<String, String> hashMap, final HttpResponseHandler handler) {
        final Request<String> request = NoHttp.createStringRequest(url, RequestMethod.POST);
        Log.i(TAG,"-----"+url);
        hashMap.put("time", TimeUtils.getTimeInSecond());
        hashMap.put("key", ApiConstants.Api.KEY);
        //公共参数 如果有userId 要加上userId
        if (hashMap.get("userId") != null) {
            hashMap.put("secret", SharedPref.getString(ApiConstants.Api.SECRET));
        }
        service = hashMap.get("service");

        // 对hashMap排序
        //按照英文顺序排序
        Collection<String> keySet = hashMap.keySet();
        List<String> list = new ArrayList<String>(keySet);
        Collections.sort(list);

        // 签名
        String translation = "";
        for (int i = 0; i < list.size(); i++) {
            if (!TextUtils.isEmpty(hashMap.get(list.get(i)))) {
                if (!list.get(i).equals("secret")) {
                    request.add(list.get(i), hashMap.get(list.get(i)));
                }
                translation = translation + list.get(i) + "=" + hashMap.get(list.get(i)) + "&";
                Log.i(TAG,translation);
            }
        }
        request.add("sign", EncryptUtils.md5(translation.substring(0, translation.length() - 1)));

        // 发送请求
        this.handler = handler;
        requestQueue.add(0, request, userResponseListener);
    }

    public void post(HashMap<String, String> hashMap, final String imagePath, final HttpResponseHandler handler) {
        post(hashMap, new ArrayList<String>() {{
            add(imagePath);
        }}, handler);
    }



    public void post(HashMap<String, String> hashMap, List<String> picList, final HttpResponseHandler handler) {
        final Request<String> request = NoHttp.createStringRequest(ApiConstants.Api.HOST, RequestMethod.POST);

        hashMap.put("time", TimeUtils.getTimeInSecond());
        hashMap.put("key", ApiConstants.Api.KEY);
        if (hashMap.get("userId") != null) {
            hashMap.put("secret", SharedPref.getString(ApiConstants.Api.SECRET));
        }
        service = hashMap.get("service");

        // 对hashMap排序
        Collection<String> keySet = hashMap.keySet();
        List<String> list = new ArrayList<String>(keySet);
        Collections.sort(list);

        // 签名
        String translation = "";
        for (int i = 0; i < list.size(); i++) {
            if (!TextUtils.isEmpty(hashMap.get(list.get(i)))) {
                if (!list.get(i).equals("secret")) {
                    request.add(list.get(i), hashMap.get(list.get(i)));
                }
                translation = translation + list.get(i) + "=" + hashMap.get(list.get(i)) + "&";
            }
        }
        request.add("sign", EncryptUtils.md5(translation.substring(0, translation.length() - 1)));

        if (picList.size() > 0) {
            for (int i = 0; i < picList.size(); i++) {
                try {
                    request.add(SharedPref.getString(Constants.USER_ID) + "[" + i + "]", new InputStreamBinary(new FileInputStream(new File(picList.get(i))), "public" + i + ".jpg"));
                } catch (Exception e) {
                    TLog.e("upload pics error" + "path: " + picList.get(i) + "not found");
                }
            }
        }

        // 发送请求
        this.handler = handler;
        requestQueue.add(0, request, userResponseListener);
    }

    private OnResponseListener<String> userResponseListener = new OnResponseListener<String>() {

        @Override
        public void onStart(int what) {
            handler.onStart();
        }

        @Override
        public void onSucceed(int what, Response<String> response) {
            TLog.i("Request onSucceed, service: " + service + "\n" + response);
            try {
                JSONObject value = new JSONObject(response.get());
                if (value.getInt("ret") == 200) {
                    JSONObject data = value.getJSONObject("data");
                    handler.onSuccess(response.responseCode(), data);
                    ToastUtils.showMessage(data.getString("message"));
                } else if (value.getInt("ret") == 401) {
                    SysUtils.kickDown();
                }
            } catch (Exception e) {
                TLog.e(service + ", http error=" + e);
            }
        }

        @Override
        public void onFailed(int what, Response<String> response) {
            TLog.i("Request onFailed, code" + what + "\n" + response);
            handler.onFailed(response.responseCode(), response.getException());
        }


        @Override
        public void onFinish(int what) {
            handler.onFinish();
        }
    };


}
