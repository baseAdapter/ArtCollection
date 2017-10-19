package com.tsutsuku.artcollection.ui.wallet;

import android.content.Context;
import android.content.Intent;
import android.text.InputFilter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.tencent.mm.sdk.modelpay.PayReq;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.tsutsuku.artcollection.R;
import com.tsutsuku.artcollection.alipay.ALiPayConstants;
import com.tsutsuku.artcollection.alipay.ALiPayUtils;
import com.tsutsuku.artcollection.common.Constants;
import com.tsutsuku.artcollection.http.HttpResponseHandler;
import com.tsutsuku.artcollection.http.HttpsClient;
import com.tsutsuku.artcollection.ui.base.BaseActivity;
import com.tsutsuku.artcollection.ui.base.WebActivity;
import com.tsutsuku.artcollection.utils.KeyBoardUtils;
import com.tsutsuku.artcollection.utils.SharedPref;
import com.tsutsuku.artcollection.utils.ToastUtils;
import com.tsutsuku.artcollection.wxapi.WXRxBus;
import com.tsutsuku.artcollection.wxapi.WxPayConstants;

import org.json.JSONObject;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @Author Sun Renwei
 * @Create 2017/2/15
 * @Description 充值页面
 */

public class RechargeActivity extends BaseActivity {
    @BindView(R.id.etCash)
    EditText etCash;
    @BindView(R.id.btnCmd)
    Button btnCmd;
    @BindView(R.id.cbAgree)
    CheckBox cbAgree;

    private PayTypeDialog dialog;
    private static int MAX_CASH_NUM = 10;

    public static void launch(Context context) {
        context.startActivity(new Intent(context, RechargeActivity.class));
    }

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_recharge);
    }

    @Override
    public void initViews() {
        initTitle(R.string.account_recharge);
        ButterKnife.bind(this);

        dialog = new PayTypeDialog(context, new PayTypeDialog.CallBack() {
            @Override
            public void finish(String payType) {
                topupByThird(etCash.getText().toString().trim(), payType);
            }
        });
    }

    @Override
    public void initListeners() {
        etCash.setFilters(new InputFilter[]{
                KeyBoardUtils.getCashInputFilter()
        });
    }

    @Override
    public void initData() {

    }

    @OnClick(R.id.btnCmd)
    public void onClick() {
        if (cbAgree.isChecked()){
            closeKeyboard();
            dialog.show(false);
        } else {
            ToastUtils.showMessage("请阅读并同意《充值和提现协议》");
        }

    }

    private void topupByThird(String cash, final String payType) {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("service", "Balance.topupByThird");
        hashMap.put("userId", SharedPref.getString(Constants.USER_ID));
        hashMap.put("topupAmount", cash);
        hashMap.put("topupVirtual", "0");
        hashMap.put("otherType", payType);
        HttpsClient client = new HttpsClient();
        client.post(hashMap, new HttpResponseHandler() {
            @Override
            protected void onSuccess(int statusCode, JSONObject data) throws Exception {
                if (data.getInt("code") == 0) {
                    switch (payType) {
                        case "alipay": {
                            JSONObject jo = data.getJSONObject("info");
                            JSONObject tradeNo = jo.getJSONObject("tradeNo");
                            {
                                new ALiPayUtils(tradeNo.getString("parmsstr"), tradeNo.getString("sign"), context, new ALiPayUtils.PaySucceed() {
                                    @Override
                                    public void onSuccess() {
                                        ToastUtils.showMessage(ALiPayConstants.PAY_SUCCESS);
                                        finish();
                                    }
                                }, new ALiPayUtils.PayAffirm() {
                                    @Override
                                    public void onAffirm() {
                                        ToastUtils.showMessage(ALiPayConstants.PAY_FAILED);
                                    }
                                }, new ALiPayUtils.PayDefeated() {
                                    @Override
                                    public void onDefeated() {
                                        ToastUtils.showMessage(ALiPayConstants.PAY_FAILED);
                                    }
                                }).submitSign();
                            }
                        }
                        break;
                        case "wxpay": {
                            JSONObject jo = data.getJSONObject("info");
                            JSONObject tradeNojo = jo.getJSONObject("tradeNo");

                            PayReq req = new PayReq();
                            IWXAPI api;
                            api = WXAPIFactory.createWXAPI(context, null);
                            // 将该app注册到微信
                            api.registerApp(WxPayConstants.WXAPP_ID);
                            if (api.openWXApp()) {
                                req.appId = tradeNojo.getString("appid");
                                req.partnerId = tradeNojo.getString("partnerid");
                                req.prepayId = tradeNojo.getString("prepayid");
                                req.packageValue = tradeNojo.getString("package");
                                req.nonceStr = tradeNojo.getString("noncestr");
                                req.timeStamp = tradeNojo.getString("timestamp");
                                req.sign = tradeNojo.getString("sign");
                                // genPayReq();
                                api.sendReq(req);
                            }

                            new WXRxBus() {
                                @Override
                                public void cancel() {

                                }

                                @Override
                                public void fail() {

                                }

                                @Override
                                public void success() {
                                    finish();
                                }
                            };
                        }
                        break;
                        default:
                            break;
                    }
                }
            }

            @Override
            protected void onFailed(int statusCode, Exception e) {

            }
        });
    }

    @OnClick(R.id.tvAgree)
    public void onViewClicked() {
        WebActivity.launch(context, SharedPref.getSysString(Constants.TOPUP_UTL));

    }
}
