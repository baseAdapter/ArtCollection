package com.tsutsuku.artcollection.presenter.login;
import android.os.Handler;
import android.text.TextUtils;

import com.tsutsuku.artcollection.R;
import com.tsutsuku.artcollection.contract.login.ResetPasswordContract;
import com.tsutsuku.artcollection.http.HttpResponseHandler;
import com.tsutsuku.artcollection.http.HttpsClient;
import com.tsutsuku.artcollection.utils.EncryptUtils;
import com.tsutsuku.artcollection.utils.PhoneFormatCheckUtils;
import com.tsutsuku.artcollection.utils.ToastUtils;

import org.json.JSONObject;

import java.util.HashMap;

/**
* Created by sunrenwei on 2017/01/04
*/

public class ResetPasswordPresenterImpl implements ResetPasswordContract.Presenter{
    private ResetPasswordContract.View view;

    private String captcha;
    private int seconds;

    private Handler handler = new Handler();
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            seconds--;
            view.setCaptchaTime(seconds);
            handler.postDelayed(this, 1000);
            if (seconds <= 0) {
                handler.removeCallbacks(runnable);
            }
        }
    };

    @Override
    public void getCaptcha(String account) {
        if (TextUtils.isEmpty(account)){
            ToastUtils.showMessage(R.string.account_phone_blank);
        } else if (!PhoneFormatCheckUtils.isPhoneLegal(account)) {
            ToastUtils.showMessage(R.string.phone_format_error);
        } else {
            getCaptchaImpl(account);
        }
    }

    @Override
    public void reset(String account, String password, String captcha) {
        if (TextUtils.isEmpty(account)){
            ToastUtils.showMessage(R.string.account_phone_blank);
        } else if (!PhoneFormatCheckUtils.isPhoneLegal(account)) {
            ToastUtils.showMessage(R.string.phone_format_error);
        } else if (TextUtils.isEmpty(password)){
            ToastUtils.showMessage(R.string.password_blank);
        } else if (!captcha.equals(this.captcha)){
            ToastUtils.showMessage(R.string.captcha_error);
        } else {
            resetImpl(account, password);
        }
    }

    private void getCaptchaImpl(String account){
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("service", "User.getToken");
        hashMap.put("mobile", account);
        hashMap.put("checkExists", "0");
        HttpsClient client = new HttpsClient();
        client.post(hashMap, new HttpResponseHandler() {
            @Override
            protected void onSuccess(int statusCode, JSONObject data) throws Exception {
                if (data.getInt("code") == 0) {
                    captcha = data.getString("token");
                    seconds = 60;
                    handler.post(runnable);
                }
            }

            @Override
            protected void onFailed(int statusCode, Exception e) {

            }
        });
    }

    private void resetImpl(final String account, final String password){
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("service", "User.resetPwd");
        hashMap.put("mobile", account);
        hashMap.put("newPwd", EncryptUtils.md5(password));
        HttpsClient client = new HttpsClient();
        client.post(hashMap, new HttpResponseHandler() {
            @Override
            protected void onSuccess(int statusCode, JSONObject data) throws Exception {
                if (data.getInt("code") == 0) {
                    view.finishReset(account, password);
                }
            }

            @Override
            protected void onFailed(int statusCode, Exception e) {

            }
        });

    }

    @Override
    public void attachView(ResetPasswordContract.View view) {
        this.view = view;
    }

    @Override
    public void detachView() {
        handler.removeCallbacks(runnable);
        this.view = null;
    }
}