package com.tsutsuku.artcollection.ui.mine;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.tsutsuku.artcollection.R;
import com.tsutsuku.artcollection.common.Constants;
import com.tsutsuku.artcollection.http.HttpResponseHandler;
import com.tsutsuku.artcollection.http.HttpsClient;
import com.tsutsuku.artcollection.ui.base.BaseActivity;
import com.tsutsuku.artcollection.utils.SharedPref;

import org.json.JSONObject;

import java.util.HashMap;


/**
 * @Author Sun Renwei
 * @Create 2016/9/26
 * @Description Content
 */
public class FeedbackActivity extends BaseActivity {
    private EditText etOpinion;
    private Button btnSubmit;

    public static void launch(Context context) {
        context.startActivity(new Intent(context, FeedbackActivity.class));
    }
    @Override
    public void setContentView() {
        setContentView(R.layout.activity_feed_back);
    }

    @Override
    public void initViews() {
        initTitle(getString(R.string.mine_opinion));
        etOpinion = (EditText) findViewById(R.id.etOpinion);
        btnSubmit = (Button) findViewById(R.id.btnSubmit);
    }

    @Override
    public void initListeners() {
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(etOpinion.getText().toString().trim())) {
                    feedBack();
                }
            }

        });
    }

    @Override
    public void initData() {

    }

    private void feedBack() {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("service", "User.feedback");
        hashMap.put("userId", SharedPref.getString(Constants.USER_ID));
        hashMap.put("content", etOpinion.getText().toString());
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
        });
    }
}
