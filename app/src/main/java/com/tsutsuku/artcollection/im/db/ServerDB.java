package com.tsutsuku.artcollection.im.db;

import android.content.Context;

import com.hyphenate.chat.EMClient;
import com.tsutsuku.artcollection.common.Constants;
import com.tsutsuku.artcollection.http.HttpResponseHandler;
import com.tsutsuku.artcollection.http.HttpsClient;
import com.tsutsuku.artcollection.im.Constant;
import com.tsutsuku.artcollection.im.entity.IMMember;
import com.tsutsuku.artcollection.ui.base.BaseApplication;
import com.tsutsuku.artcollection.utils.RxBus;
import com.tsutsuku.artcollection.utils.SharedPref;

import org.json.JSONObject;

import java.util.HashMap;

import io.realm.Realm;

/**
 * @Author Sun Renwei
 * @Create 2016/7/29
 * @Description 处理车友圈与好友相关的部分
 */
public class ServerDB {
    private static Context context = BaseApplication.getInstance();

    /**
     * 添加好友
     * @param toUserId 添加好友的userId
     * @param noteName
     * @param addNote 验证消息
     */
    public static void addFriend(final String toUserId, String noteName, String addNote) {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("service", "Friend.addFriend");
        hashMap.put("userId", SharedPref.getString(Constants.USER_ID));
        hashMap.put("userName", SharedPref.getString(Constants.NICK));
        hashMap.put("fUid", toUserId);
        hashMap.put("note", noteName);
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

    /**
     * 确认或拒绝添加好友
     * @param fromUserId 请求添加好友的userId
     */
    public static void affirmFriend(final String fromUserId, String fromUserName, final String type) {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("service", "Friend.agreeFriend");
        hashMap.put("userId", SharedPref.getString(Constants.USER_ID));
        hashMap.put("userName", SharedPref.getString(Constants.NICK));
        hashMap.put("fUid", fromUserId);
        hashMap.put("fUserName", fromUserName);
        hashMap.put("type", type);
        HttpsClient client = new HttpsClient();
        client.post(hashMap, new HttpResponseHandler() {
            @Override
            protected void onSuccess(int statusCode, JSONObject data) throws Exception {
                if (data.getInt("code") == 0) {
                    Realm realm = Realm.getDefaultInstance();
                    final IMMember member = realm.where(IMMember.class).equalTo("userId", fromUserId).findFirst();
                    if (type == "1"){
                        realm.executeTransaction(new Realm.Transaction() {
                            @Override
                            public void execute(Realm realm) {
                                member.setStatus(IMMember.FRIEND);
                            }
                        });
                    } else {
                        realm.executeTransaction(new Realm.Transaction() {
                            @Override
                            public void execute(Realm realm) {
                                member.setStatus(IMMember.NORMAL);
                            }
                        });
                    }
                    RxBus.getDefault().post("FriendStatus", fromUserId);
                }
            }

            @Override
            protected void onFailed(int statusCode, Exception e) {

            }
        });
    }

    /**
     * 删除好友
     * @param userId 好友userId
     */
    public static void deleteFriend(final String userId) {
        HashMap<String, String> hashMap = new HashMap<String, String>();
        hashMap.put("service", "Friend.delFriend");// 固定值
        hashMap.put("userId", SharedPref.getString(Constants.USER_ID));
        hashMap.put("fUid", userId);
        HttpsClient client = new HttpsClient();
        client.post(hashMap, new HttpResponseHandler() {
            @Override
            protected void onSuccess(int statusCode, JSONObject data) throws Exception {
                if (data.getInt("code") == 0) {
                    Realm realm = Realm.getDefaultInstance();
                    realm.executeTransaction(new Realm.Transaction() {
                        @Override
                        public void execute(Realm realm) {
                            realm.where(IMMember.class).equalTo("userId", userId).findFirst().setStatus(IMMember.NORMAL);
                            EMClient.getInstance().chatManager().deleteConversation(DBManager.getMember(userId).getImId(), true);
                        }
                    });
                    RxBus.getDefault().post("FriendStatus", "");
                    realm.close();
                }
            }

            @Override
            protected void onFailed(int statusCode, Exception e) {

            }
        });
    }

}
