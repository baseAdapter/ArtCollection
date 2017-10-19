package com.tsutsuku.artcollection.presenter.login;
import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;

import com.tsutsuku.artcollection.R;
import com.tsutsuku.artcollection.contract.login.LoginContract;
import com.tsutsuku.artcollection.http.HttpResponseHandler;
import com.tsutsuku.artcollection.http.HttpsClient;
import com.tsutsuku.artcollection.http.SysUtils;
import com.tsutsuku.artcollection.utils.EncryptUtils;
import com.tsutsuku.artcollection.utils.KeyBoardUtils;
import com.tsutsuku.artcollection.utils.PhoneFormatCheckUtils;
import com.tsutsuku.artcollection.utils.ToastUtils;

import org.json.JSONObject;

import java.util.HashMap;

/**
 * @Author Sun Renwei
 * @Create 2017/1/3
 * @Description 登录presenter
 */

public class LoginPresenterImpl implements LoginContract.Presenter{

    LoginContract.View loginView;
    private Context context;

    public LoginPresenterImpl(Context context) {
        this.context = context;
    }

    @Override
    public void attachView(LoginContract.View view) {
        this.loginView = view;
    }

    @Override
    public void detachView() {
        this.loginView = null;
    }

    @Override
    public void login(String account, String password) {
        if (TextUtils.isEmpty(account)){
            ToastUtils.showMessage(R.string.account_phone_blank);
        } else if (!PhoneFormatCheckUtils.isPhoneLegal(account)) {
            ToastUtils.showMessage(R.string.phone_format_error);
        } else if (TextUtils.isEmpty(password)){
            ToastUtils.showMessage(R.string.password_blank);
        } else {
            loginImpl(account, password);
        }
    }

    private void loginImpl(String account, final String password) {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("service", "User.login");
        hashMap.put("mobile", account);
        hashMap.put("passwd", EncryptUtils.md5(password));
        HttpsClient client = new HttpsClient();
        client.post(hashMap, new HttpResponseHandler() {
            @Override
            protected void onSuccess(int statusCode, JSONObject data) throws Exception {
                if (data.getInt("code") == 0) {
                    KeyBoardUtils.closeKeyboard((Activity)context);
                    SysUtils.saveUserInfo(data.getString("info"));
                    SysUtils.loginIM(data.getJSONObject("info").getJSONObject("hxAccount").getString("username"), password);
                }
            }

            @Override
            protected void onFailed(int statusCode, Exception e) {

            }
        });

    }


}