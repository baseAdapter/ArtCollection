package com.tsutsuku.artcollection.ui.mine;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.tsutsuku.artcollection.R;
import com.tsutsuku.artcollection.common.Constants;
import com.tsutsuku.artcollection.http.HttpResponseHandler;
import com.tsutsuku.artcollection.http.HttpsClient;
import com.tsutsuku.artcollection.model.CountInfoBean;
import com.tsutsuku.artcollection.ui.base.BaseActivity;
import com.tsutsuku.artcollection.ui.exchange.CoinDetailActivity;
import com.tsutsuku.artcollection.utils.GsonUtils;
import com.tsutsuku.artcollection.utils.SharedPref;

import org.json.JSONObject;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @Author Tsutsuku
 * @Create 2017/4/2
 * @Description
 */

public class MineFansActivity extends BaseActivity {


    @BindView(R.id.getCoinBt)
    Button mCoinBt;
    @BindView(R.id.tvCash)
    TextView mCashTv;
    @BindView(R.id.tvGift)
    TextView mGiftTv;
    @BindView(R.id.rulesTv)
    TextView mRulesTv;
    @BindView(R.id.CoinBt)
    Button mCoinShowBt;


    public static void launch(Context context) {
        context.startActivity(new Intent(context, MineFansActivity.class));
    }
    @Override
    public void setContentView() {
        setContentView(R.layout.activity_mine_fans);
    }

    @Override
    public void initViews() {
        initTitle(getString(R.string.mine_coins),getString(R.string.mine_details));
        ButterKnife.bind(this);

    }

    @Override
    public void initListeners() {

    }

    @Override
    public void initData() {
        getData();
    }

    /**
     *
     *请求数据
     *
     **/
    private void getData() {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("service", "User.getCountInfo");
        hashMap.put("userId", SharedPref.getString(Constants.USER_ID));
        HttpsClient client = new HttpsClient();
        client.post(hashMap, new HttpResponseHandler() {
            @Override
            protected void onSuccess(int statusCode, JSONObject data) throws Exception {
                if (data.getInt("code") == 0) {
                    int info = GsonUtils.parseJson(data.getString("info"), CountInfoBean.class).getGoldTotal();
                    Log.i(TAG,"onSuccess" + info);
                    mCoinShowBt.setText(info + "枚");

                }
            }

            @Override
            protected void onFailed(int statusCode, Exception e) {

            }
        });
    }

    @OnClick({R.id.tvGift,R.id.tvCash,R.id.getCoinBt,R.id.rulesTv,R.id.tvTitleButton})
    public void onClick(View view){
        switch (view.getId()) {
            case R.id.tvGift:
                Intent intent = new Intent(this, ExchangeMallActivity.class);
                startActivity(intent);
                break;
            case R.id.tvCash:
                Toast.makeText(this,"此功能尚未开放",Toast.LENGTH_SHORT).show();
                break;
            case R.id.getCoinBt:
                startActivity(new Intent(this,InviteActivity.class));
                break;
            case R.id.rulesTv:
                break;
            case R.id.tvTitleButton:
                startActivity(new Intent(this, CoinDetailActivity.class));
                break;
                default:
                break;
        }

    }

}
