package com.tsutsuku.artcollection.http;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.tsutsuku.artcollection.api.ApiConstants;
import com.tsutsuku.artcollection.common.Constants;
import com.tsutsuku.artcollection.ui.base.BaseApplication;
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
public class BaseSysUtils {
    protected static final Context context = BaseApplication.getInstance();

    protected static BaseSysUtils instance;
    private static final String kickDown = "您的登录信息已过期！";

    public static BaseSysUtils getInstance() {
        if (instance == null) {
            instance = new BaseSysUtils();
        }
        return instance;
    }

    /**
     * 被踢下线
     */
    public static void kickDown() {
        ToastUtils.showMessage(kickDown);
        initApp();
    }

    /**
     * 注销登录
     */
    public static void logout() {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("service", "User.logout");
        hashMap.put("userId", SharedPref.getString(Constants.USER_ID));
        HttpsClient client = new HttpsClient();
        client.post(hashMap, new HttpResponseHandler() {
            @Override
            protected void onSuccess(int statusCode, JSONObject data) throws Exception {
                if (data.getInt("code") == 0) {
                    EMClient.getInstance().logout(true);
                    initApp();
                }
            }

            @Override
            protected void onFailed(int statusCode, Exception e) {

            }
        });
    }

    /**
     * 重置app
     */
    private static void initApp() {
        SharedPref.clear();
        Intent intent = new Intent(BaseApplication.getInstance(), MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        BaseApplication.getInstance().startActivity(intent);
    }

    public static void getBaseInfo() {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("service", "User.getBaseInfo");
        hashMap.put("userId", SharedPref.getString(Constants.USER_ID));
        HttpsClient client = new HttpsClient();
        client.post(hashMap, new HttpResponseHandler() {
            @Override
            protected void onSuccess(int statusCode, JSONObject data) throws Exception {
                if (data.getInt("code") == 0) {
                    saveUserInfo(data.getString("info"));
                }
            }

            @Override
            protected void onFailed(int statusCode, Exception e) {

            }
        });

    }

    public static void saveUserInfo(String UserInfo) {
        try {
            JSONObject info = new JSONObject(UserInfo);

            SharedPref.putString(Constants.USER_ID, info.getString("userId"));
            SharedPref.putString(Constants.NICK, info.getString("nickname"));
            SharedPref.putString(Constants.SEX, info.getString("sex"));
            SharedPref.putString(Constants.AVATAR, info.getString("photo"));
            SharedPref.putString(Constants.MOBILE, info.getString("mobile"));
            SharedPref.putString(Constants.SIGN, info.getString("personalSign"));
            SharedPref.putString(Constants.BALANCE, info.getString("cashBalance"));

            SharedPref.putString(Constants.IM_ID, info.getJSONObject("hxAccount").getString("username"));

            SharedPref.putString(ApiConstants.Api.SECRET, info.getString("secret"));
        } catch (Exception e) {
            Log.e("saveUserInfo", "error" + e);
        }

    }

    public static void getConfigInfo() {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("service", "App.getInitConfig");
        HttpsClient client = new HttpsClient();
        client.post(hashMap, new HttpResponseHandler() {
            @Override
            protected void onSuccess(int statusCode, JSONObject data) throws Exception {
                if (data.getInt("code") == 0) {
                    saveConfigInfo(data);
                }
            }

            @Override
            protected void onFailed(int statusCode, Exception e) {

            }
        });
    }

    private static void saveConfigInfo(JSONObject data) {
        try {
            JSONObject info = new JSONObject(data.getString("list"));

            SharedPref.putSysString(Constants.SERVER_URL, info.getString("server_url"));
            SharedPref.putSysString(Constants.REG_URL, info.getString("reg_url"));
            SharedPref.putSysString(Constants.HELP_URL, info.getString("help_url"));
            SharedPref.putSysString(Constants.INTEGRATE_URL, info.getString("Integrate_url"));
            SharedPref.putSysString(Constants.ABOUT_ME_URL, info.getString("aboutme_url"));
            SharedPref.putSysString(Constants.JIANDING_URL, info.getString("jianding_url"));
            SharedPref.putSysString(Constants.DEPOSIT_URL, info.getString("deposit_url"));
            SharedPref.putSysString(Constants.TOPUP_UTL, info.getString("topup_url"));
            SharedPref.putSysString(Constants.FEE_URL, info.getString("feli_url"));
            SharedPref.putSysString(Constants.FLOW_URL, info.getString("liuchen_url"));
            SharedPref.putSysString(Constants.PAWN_URL, info.getString("diandang_url"));
            SharedPref.putSysString(Constants.JINGPAI_URL, info.getString("jingpai_url"));


            SharedPref.putSysInt(Constants.MIN_WITHDRAW, info.getInt("user_withdraw_min_money"));

            SharedPref.putSysString(Constants.REJECT_REASON, data.getString("orderrejected_reason"));
            SharedPref.putSysString(Constants.CANCEL_REASON, data.getString("ordercanel_reason"));
            SharedPref.putSysString(Constants.NEWS_CATE_LIST, data.getString("NewsCateList"));
            SharedPref.putSysString(Constants.VIDEO_CATE_LIST, data.getString("VideoCateList"));

            SharedPref.putSysString(Constants.Share.PRODUCT, info.getString("share_product_url"));
            SharedPref.putSysString(Constants.Share.AUCTION, info.getString("share_product_auction_url"));
            SharedPref.putSysString(Constants.Share.ACTIVITY, info.getString("share_activity_url"));
            SharedPref.putSysString(Constants.Share.SHOP, info.getString("share_shop_url"));
            SharedPref.putSysString(Constants.Share.VIDEO, info.getString("share_video_url"));
            SharedPref.putSysString(Constants.Share.CIRClE, info.getString("share_circle_url"));
            SharedPref.putSysString(Constants.Share.NEWS, info.getString("share_news_url"));

        } catch (Exception e) {
            Log.e("getConfigInfo", "error" + e);
        }
    }

    public static void loginIM(String imId, String imPassword) {
        EMClient.getInstance().login(imId, EncryptUtils.md5(EncryptUtils.md5(imPassword)), new EMCallBack() {
            @Override
            public void onSuccess() {
                try {
                    EMClient.getInstance().groupManager().getJoinedGroupsFromServer();
                } catch (Exception e) {
                    TLog.e("get Groups error=" + e);
                }
                EMClient.getInstance().groupManager().loadAllGroups();
                EMClient.getInstance().chatManager().loadAllConversations();

                TLog.d("login, Easemob login success!");

                MainActivity.launch(context);
            }

            @Override
            public void onError(int i, String s) {
                TLog.d("login, Easemob login failed!");
            }

            @Override
            public void onProgress(int i, String s) {

            }
        });
    }




}
