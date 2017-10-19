package com.tsutsuku.artcollection.ui.exchange;


import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.tsutsuku.artcollection.R;
import com.tsutsuku.artcollection.common.Constants;
import com.tsutsuku.artcollection.http.HttpResponseHandler;
import com.tsutsuku.artcollection.http.HttpsClient;
import com.tsutsuku.artcollection.ui.base.BaseActivity;
import com.tsutsuku.artcollection.utils.GsonUtils;
import com.tsutsuku.artcollection.utils.SharedPref;
import com.tsutsuku.artcollection.utils.ToastUtils;

import org.json.JSONObject;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ExchangeCoinActivity extends BaseActivity {
    public static final String TAG = ExchangeCoinActivity.class.getSimpleName();

    /**
     * 当前积分
     **/
    @BindView(R.id.currentIntegration)
    TextView mCurrentIntegration;

    /**
     * 当前金币数量
     **/
    @BindView(R.id.currentCoins)
    TextView mCurrentCoins;

    @BindView(R.id.coinMinus)
    ImageView mCoinMinus;
    @BindView(R.id.coinPlus)
    ImageView mCoisPlus;

    /**
     * 所要兑换的积分数量
     **/
    @BindView(R.id.showIntegration)
    Button mShowIntegration;

    /**
     * 兑换所获得的金币数
     **/
    @BindView(R.id.numCoinBt)
    Button mNumCoins;

    /**
     * 确认兑换按钮
     **/
    @BindView(R.id.sureExchangeBt)
    Button mSureExchange;

    /**
     * 当前积分的数量
     **/
    private int mIntegration;
    //当前金币
    private int mGold;
    //比例
    private float scale;


    private IntegrateExchangeGold mExchangeGold;

    /**
     * 过渡值
     **/
    private int mStateIntegration = 0;

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_exchange_coin);
    }

    @Override
    public void initViews() {
        ButterKnife.bind(this);
        initTitle(R.string.exchange_coins, R.string.exchange_record);
    }

    @Override
    public void initListeners() {

    }

    @Override
    public void initData() {
        getData();

    }

    /**
     * 获取数据
     **/
    private void getData() {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("service", "Integrate.integrateExchangeGold");
        hashMap.put("user_id", SharedPref.getString(Constants.USER_ID));
        HttpsClient client = new HttpsClient();
        client.post(hashMap, new HttpResponseHandler() {
            @Override
            protected void onSuccess(int statusCode, JSONObject data) throws Exception {
                if (data.getInt("code") == 0) {
                    mExchangeGold = GsonUtils.parseJson(data.getString("list"), IntegrateExchangeGold.class);
                    mCurrentIntegration.setText(mExchangeGold.getIntegrate_total().toString());
                    mCurrentCoins.setText(mExchangeGold.getGold_total().toString());
                    mIntegration = Integer.parseInt(mExchangeGold.getIntegrate_total());
                    mGold = Integer.parseInt(mExchangeGold.getGold_total());
                    scale = Float.parseFloat(mExchangeGold.getScale());
                }
            }

            @Override
            protected void onFailed(int statusCode, Exception e) {

            }
        });

    }

    @OnClick({R.id.coinMinus, R.id.coinPlus, R.id.sureExchangeBt})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.coinMinus:
                getMinusCoins();
                break;
            case R.id.coinPlus:
                getAddCoins();
                break;
            case R.id.sureExchangeBt:
                getConfirmExchangeData();
                break;
            default:
                break;
        }

    }

    /**
     * 确认兑换
     **/
    private void getConfirmExchangeData() {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("service", "Integrate.confirmExchange");
        hashMap.put("user_id", SharedPref.getString(Constants.USER_ID));
        hashMap.put("integrate", mStateIntegration + "");
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

    /**
     * 点击添加积分兑换金币
     * mCurrentIntegration : 当前积分
     * mCurrentCoins : 当前金币
     * scale : 兑换比例 0.02
     **/

    private void getAddCoins() {
        if (mIntegration - 100 >= 0) {
            mIntegration -= 100;
            mGold += 100 * scale;
            mStateIntegration += 100;
            mCurrentIntegration.setText(mIntegration + "");
            mShowIntegration.setText(mStateIntegration + "");
            mNumCoins.setText((int) (mStateIntegration * scale) + "");
            mCurrentCoins.setText(mGold + "");
        } else {
            ToastUtils.showMessage("积分不足");
        }

    }


    /**
     * 点击减少积分兑换金币
     **/
    private void getMinusCoins() {
        if (mStateIntegration >= 100) {
            mIntegration += 100;
            mGold -= 100 * scale;
            mStateIntegration -= 100;
            mCurrentIntegration.setText(mIntegration + "");
            mShowIntegration.setText(mStateIntegration + "");
            mNumCoins.setText((int) (mStateIntegration * scale) + "");
            mCurrentCoins.setText(mGold + "");
        }
    }


}
