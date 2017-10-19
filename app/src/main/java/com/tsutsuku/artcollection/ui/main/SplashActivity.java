package com.tsutsuku.artcollection.ui.main;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;

import com.tsutsuku.artcollection.R;
import com.tsutsuku.artcollection.common.Constants;
import com.tsutsuku.artcollection.http.SysUtils;
import com.tsutsuku.artcollection.ui.login.LoginActivity;
import com.tsutsuku.artcollection.utils.SharedPref;

/**
 * @Author Sun Renwei
 * @Create 2017/1/4
 * @Description Content
 */

public class SplashActivity extends Activity{
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        setContentView(R.layout.activity_splash);
        SysUtils.getConfigInfo();
        SysUtils.getAreaList();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (!TextUtils.isEmpty(SharedPref.getString(Constants.USER_ID))) {
                    SysUtils.getBaseInfo();
                }
                SysUtils.getCateMore();
                MainActivity.launch(context);
                finish();
            }
        }, 2000);
    }
}
