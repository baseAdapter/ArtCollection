package com.tsutsuku.artcollection.ui.main;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.tsutsuku.artcollection.BuildConfig;
import com.tsutsuku.artcollection.R;
import com.tsutsuku.artcollection.common.Constants;
import com.tsutsuku.artcollection.http.HttpResponseHandler;
import com.tsutsuku.artcollection.http.HttpsClient;
import com.tsutsuku.artcollection.http.SysUtils;
import com.tsutsuku.artcollection.other.update.CheckUpModel;
import com.tsutsuku.artcollection.other.update.CheckUpdate;
import com.tsutsuku.artcollection.utils.SharedPref;
import com.tsutsuku.artcollection.utils.ToastUtils;

import org.json.JSONObject;

import java.util.HashMap;

/**
 * @Author Sun Renwei
 * @Create 2017/1/4
 * @Description Content
 */

public class SplashActivity extends Activity {
    private Context context;
    Gson gson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        setContentView(R.layout.activity_splash);
        gson = new Gson();
        SysUtils.getConfigInfo();
        SysUtils.getAreaList();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (!TextUtils.isEmpty(SharedPref.getString(Constants.USER_ID))) {
                    SysUtils.getBaseInfo();
                }
                SysUtils.getCateMore();
                getData();

                finish();
            }
        }, 2000);

    }

    Handler checkUpdateHandler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case -2: {
                    ToastUtils.showMessage("下载失败，请检查网络");
                    MainActivity.launch(context);
                    return;
                }
                case 1:
                    finish();
                    break;
                case 2: //非强制更新， 取消更新
                    MainActivity.launch(context);
                    break;
            }
        }

    };

    private void getData() {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("service", "Update.checkAppUpdate");
        hashMap.put("client_version", BuildConfig.VERSION_NAME);
        hashMap.put("client_type", 0 + "");
        HttpsClient client = new HttpsClient();
        client.post(hashMap, new HttpResponseHandler() {
            @Override
            protected void onSuccess(int statusCode, JSONObject data) throws Exception {
                if (data.getInt("code") == 0) {
                    CheckUpModel info = gson.fromJson(data.get("info").toString(), CheckUpModel.class);
                    if ("2".equals(info.getInfo().getStatus())) {
                        MainActivity.launch(context);
                        return;
                    }
                    CheckUpdate.showUpdataDialog(SplashActivity.this, checkUpdateHandler, info);
                }else {
                    MainActivity.launch(context);
                }
            }

            @Override
            protected void onFailed(int statusCode, Exception e) {

            }
        });

    }
}
