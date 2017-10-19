package com.tsutsuku.artcollection.live.stream;

import com.tsutsuku.artcollection.http.HttpResponseHandler;
import com.tsutsuku.artcollection.http.HttpsClient;

import org.json.JSONObject;

import java.util.HashMap;

/**
 * @Author Tsutsuku
 * @Create 2017/5/6
 * @Description
 */

public class StreamRepository {

    public static void setRecord(String cId, boolean needRecord, String activityId, String pushUserId) {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("service", "App.setAlwaysRecordByCid");
        hashMap.put("cId", cId);
        hashMap.put("needRecord", needRecord ? "1" : "0");
        hashMap.put("activityId", activityId);
        hashMap.put("pushUserId", pushUserId);
        HttpsClient client = new HttpsClient();
        client.post(hashMap, new HttpResponseHandler() {
            @Override
            protected void onSuccess(int statusCode, JSONObject data) throws Exception {

            }

            @Override
            protected void onFailed(int statusCode, Exception e) {

            }
        });
    }
    
    public static void keepPush(String activityId, String pushUserId){
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("service", "Activities.keepPush");
        hashMap.put("activityId", activityId);
        hashMap.put("pushUserId", pushUserId);
        HttpsClient client = new HttpsClient();
        client.post(hashMap, new HttpResponseHandler() {
            @Override
            protected void onSuccess(int statusCode, JSONObject data) throws Exception {

            }

            @Override
            protected void onFailed(int statusCode, Exception e) {

            }
        });
    }

    public static void sleepPush(String activityId, String pushUserId){
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("service", "Activities.sleepPush");
        hashMap.put("activityId", activityId);
        hashMap.put("pushUserId", pushUserId);
        HttpsClient client = new HttpsClient();
        client.post(hashMap, new HttpResponseHandler() {
            @Override
            protected void onSuccess(int statusCode, JSONObject data) throws Exception {

            }

            @Override
            protected void onFailed(int statusCode, Exception e) {

            }
        });
    }
    
    public static void endPush(String activityId, String pushUserId){
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("service", "Activities.endPush");
        hashMap.put("activityId", activityId);
        hashMap.put("pushUserId", pushUserId);
        HttpsClient client = new HttpsClient();
        client.post(hashMap, new HttpResponseHandler() {
            @Override
            protected void onSuccess(int statusCode, JSONObject data) throws Exception {

            }

            @Override
            protected void onFailed(int statusCode, Exception e) {

            }
        });
    }

    public static void resetPush(String activityId, String pushUserId){
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("service", "Activities.resetPush");
        hashMap.put("activityId", activityId);
        hashMap.put("pushUserId", pushUserId);
        HttpsClient client = new HttpsClient();
        client.post(hashMap, new HttpResponseHandler() {
            @Override
            protected void onSuccess(int statusCode, JSONObject data) throws Exception {

            }

            @Override
            protected void onFailed(int statusCode, Exception e) {

            }
        });
    }

    public static void beginPush(String activityId, String pushUserId){
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("service", "Activities.beginPush");
        hashMap.put("activityId", activityId);
        hashMap.put("pushUserId", pushUserId);
        HttpsClient client = new HttpsClient();
        client.post(hashMap, new HttpResponseHandler() {
            @Override
            protected void onSuccess(int statusCode, JSONObject data) throws Exception {

            }

            @Override
            protected void onFailed(int statusCode, Exception e) {

            }
        });
    }
}
