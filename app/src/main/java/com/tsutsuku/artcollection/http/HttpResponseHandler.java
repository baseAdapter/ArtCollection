package com.tsutsuku.artcollection.http;

import android.content.Context;

import org.json.JSONObject;

/**
 * @Author Sun Renwei
 * @Create 2016/7/4
 * @Description Http工具类，封装第三方Http库，当前使用nohttp
 */
public abstract class HttpResponseHandler {
    private Context context;

    public HttpResponseHandler() {
    }

    public HttpResponseHandler(Context context) {
        this.context = context;
    }

    public Context getContext() {
        return this.context;
    }

    protected abstract void onSuccess(int statusCode, JSONObject data) throws Exception;

    protected abstract void onFailed(int statusCode, Exception e);

    protected void onStart(){}

    protected void onFinish(){}
}
