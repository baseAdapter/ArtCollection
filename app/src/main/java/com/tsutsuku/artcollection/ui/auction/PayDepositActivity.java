package com.tsutsuku.artcollection.ui.auction;

import android.app.Activity;
import android.content.Intent;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.tencent.mm.sdk.modelpay.PayReq;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.tsutsuku.artcollection.R;
import com.tsutsuku.artcollection.alipay.ALiPayUtils;
import com.tsutsuku.artcollection.common.Constants;
import com.tsutsuku.artcollection.http.HttpResponseHandler;
import com.tsutsuku.artcollection.http.HttpsClient;
import com.tsutsuku.artcollection.ui.base.BaseActivity;
import com.tsutsuku.artcollection.ui.base.WebActivity;
import com.tsutsuku.artcollection.ui.wallet.PayTypeDialog;
import com.tsutsuku.artcollection.utils.DensityUtils;
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
 * @Author Tsutsuku
 * @Create 2017/3/23
 * @Description
 */

public class PayDepositActivity extends BaseActivity {
    private static final String PRODUCT_INFO = "productInfo";
    private static final String CASH = "cash";
    @BindView(R.id.tvPrice)
    TextView tvPrice;
    @BindView(R.id.cbAgree)
    CheckBox cbAgree;
    @BindView(R.id.tvAgree)
    TextView tvAgree;
    @BindView(R.id.btnCmd)
    Button btnCmd;

    private PayTypeDialog dialog;

    public static void launch(Activity activity, String productInfo, String cash, int requestCode) {
        activity.startActivityForResult(new Intent(activity, PayDepositActivity.class)
                .putExtra(PRODUCT_INFO, productInfo)
                .putExtra(CASH, cash), requestCode);
    }

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_pay_deposit);
    }

    @Override
    public void initViews() {
        ButterKnife.bind(this);
        initTitle("提交保证金");
        SpannableStringBuilder builder = new SpannableStringBuilder("¥");
        String price = getIntent().getStringExtra(CASH);
        builder.append(price);
        builder.setSpan(new AbsoluteSizeSpan(DensityUtils.dp2px(19)), 1, builder.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        tvPrice.setText(builder);

        dialog = new PayTypeDialog(context, new PayTypeDialog.CallBack() {
            @Override
            public void finish(String payType) {
                pay(payType);
            }
        });
    }

    @Override
    public void initListeners() {

    }

    @Override
    public void initData() {

    }

    private void pay(final String payType) {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("service", "Order.payForProducts");
        hashMap.put("userId", SharedPref.getString(Constants.USER_ID));
        hashMap.put("addressId", "0");
        hashMap.put("payType", "2");
        hashMap.put("productsInfo", getIntent().getStringExtra(PRODUCT_INFO));
        hashMap.put("otherType", payType);
        hashMap.put("otherFee", "balance".equals(payType) ? "0" : getIntent().getStringExtra(CASH));
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
                                        payed();
                                    }
                                }, new ALiPayUtils.PayAffirm() {
                                    @Override
                                    public void onAffirm() {
                                    }
                                }, new ALiPayUtils.PayDefeated() {
                                    @Override
                                    public void onDefeated() {
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
                                    payed();
                                }
                            };
                        }
                        break;
                        case "balance": {
                            payed();
                        }
                        break;
                        default:
                            break;
                    }
                } else if ("balance".equals(payType)){
                    ToastUtils.showMessage("余额不足");
                }
            }

            @Override
            protected void onFailed(int statusCode, Exception e) {

            }
        });
    }

    private void payed(){
        setResult(RESULT_OK);
        finish();
    }

    @OnClick({R.id.btnCmd, R.id.tvAgree})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnCmd: {
                if (!cbAgree.isChecked()) {
                    ToastUtils.showMessage("请阅读并同意" + context.getString(R.string.agree_auction));
                } else {
                    dialog.show();
                }
            }
            break;
            case R.id.tvAgree: {
                WebActivity.launch(context, SharedPref.getSysString(Constants.DEPOSIT_URL));
            }
            break;
        }

    }
}
