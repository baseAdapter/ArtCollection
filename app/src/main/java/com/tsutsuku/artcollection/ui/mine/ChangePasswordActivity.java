package com.tsutsuku.artcollection.ui.mine;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;


import com.tsutsuku.artcollection.R;
import com.tsutsuku.artcollection.common.Constants;
import com.tsutsuku.artcollection.http.HttpResponseHandler;
import com.tsutsuku.artcollection.http.HttpsClient;
import com.tsutsuku.artcollection.ui.base.BaseActivity;
import com.tsutsuku.artcollection.utils.EncryptUtils;
import com.tsutsuku.artcollection.utils.SharedPref;
import com.tsutsuku.artcollection.utils.ToastUtils;

import org.json.JSONObject;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @Author Sun Renwei
 * @Create 2016/9/21
 * @Description 修改密码
 */
public class ChangePasswordActivity extends BaseActivity {
    @BindView(R.id.etOld)
    EditText etOld;
    @BindView(R.id.etNew)
    EditText etNew;
    @BindView(R.id.etAffirm)
    EditText etAffirm;

    public static void launch(Context context) {
        context.startActivity(new Intent(context, ChangePasswordActivity.class));
    }

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_change_password);
    }

    @Override
    public void initViews() {
        initTitle(R.string.change_password, R.string.submit);
        ButterKnife.bind(this);
    }

    @Override
    public void initListeners() {
        tvTitleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(etOld.getText().toString())) {
                    ToastUtils.showMessage(R.string.old_password_hint);
                } else if (TextUtils.isEmpty(etNew.getText().toString())) {
                    ToastUtils.showMessage(R.string.new_password_hint);
                } else if (TextUtils.isEmpty(etAffirm.getText().toString())) {
                    ToastUtils.showMessage(R.string.affirm_password_hint);
                } else if (!etNew.getText().toString().equals(etAffirm.getText().toString())) {
                    ToastUtils.showMessage(R.string.affirm_password_error);
                } else {
                    setPassword();
                }
            }
        });
    }

    @Override
    public void initData() {

    }

    private void setPassword() {
        HashMap<String, String> hashMap = new HashMap<String, String>();
        hashMap.put("service", "User.setPwd");
        hashMap.put("userId", SharedPref.getString(Constants.USER_ID));
        hashMap.put("oldPwd", EncryptUtils.md5(etOld.getText().toString()));
        hashMap.put("newPwd", EncryptUtils.md5(etNew.getText().toString()));
        HttpsClient client = new HttpsClient();
        client.post(hashMap, new HttpResponseHandler() {
                    @Override
                    protected void onSuccess(int statusCode, JSONObject data) throws Exception {
                        if (data.getInt("code") == 0) {
                            finish();
                        }
                    }

                    @Override
                    protected void onFailed(int statusCode, Exception e) {

                    }
                }
        );
    }
}
