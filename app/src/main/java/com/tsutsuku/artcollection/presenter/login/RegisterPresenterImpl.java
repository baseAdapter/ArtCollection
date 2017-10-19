package com.tsutsuku.artcollection.presenter.login;

import android.os.Handler;
import android.text.TextUtils;

import com.tsutsuku.artcollection.R;
import com.tsutsuku.artcollection.contract.login.RegisterContract;
import com.tsutsuku.artcollection.http.HttpResponseHandler;
import com.tsutsuku.artcollection.http.HttpsClient;
import com.tsutsuku.artcollection.http.SysUtils;
import com.tsutsuku.artcollection.utils.EncryptUtils;
import com.tsutsuku.artcollection.utils.PhoneFormatCheckUtils;
import com.tsutsuku.artcollection.utils.ToastUtils;

import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by sunrenwei on 2017/01/04
 */

public class RegisterPresenterImpl implements RegisterContract.Presenter {

    private RegisterContract.View registerView;

    private String captcha;
    private int seconds;

    private Handler handler = new Handler();
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            seconds--;
            registerView.setCaptchaTime(seconds);
            handler.postDelayed(this, 1000);
            if (seconds <= 0) {
                handler.removeCallbacks(runnable);
            }
        }
    };

    @Override
    public void attachView(RegisterContract.View view) {
        this.registerView = view;
    }

    @Override
    public void detachView() {
        this.registerView = null;
    }

    @Override
    public void register(String nickname,String account, String password, String captcha, boolean agree) {
        if (TextUtils.isEmpty(nickname)){
            ToastUtils.showMessage("请输入您的昵称");
        } else if (TextUtils.isEmpty(account)){
            ToastUtils.showMessage(R.string.account_phone_blank);
        } else if (!PhoneFormatCheckUtils.isPhoneLegal(account)) {
            ToastUtils.showMessage(R.string.phone_format_error);
        } else if (TextUtils.isEmpty(password)){
            ToastUtils.showMessage(R.string.password_blank);
        } else if (TextUtils.isEmpty(captcha)) {
            ToastUtils.showMessage(R.string.captcha_blank);
        } else if (!captcha.equals(this.captcha)){
            ToastUtils.showMessage(R.string.captcha_error);
        } else if (!agree){
            ToastUtils.showMessage(R.string.please_read_and_agree);
        } else {
            registerImpl(nickname,account, password, captcha);
        }
    }

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

    private void getCaptchaImpl(String account){
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("service", "User.getToken");
        hashMap.put("mobile", account);
        hashMap.put("checkExists", "1");
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

    private void registerImpl(String nickname,String account, final String password, String captcha) {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("service", "User.register");
        hashMap.put("mobile", account);
        hashMap.put("password", EncryptUtils.md5(password));
        hashMap.put("token", captcha);
        hashMap.put("nickname", nickname);
        HttpsClient client = new HttpsClient();
        client.post(hashMap, new HttpResponseHandler() {
            @Override
            protected void onSuccess(int statusCode, JSONObject data) throws Exception {
                if (data.getInt("code") == 0) {
                    SysUtils.saveUserInfo(data.getString("info"));
                    SysUtils.loginIM(data.getJSONObject("hxAccount").getString("username"), password);
                }
            }

            @Override
            protected void onFailed(int statusCode, Exception e) {

            }
        });

    }


}