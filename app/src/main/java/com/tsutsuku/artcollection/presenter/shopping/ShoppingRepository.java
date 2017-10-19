package com.tsutsuku.artcollection.presenter.shopping;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.tencent.mm.sdk.modelpay.PayReq;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.tsutsuku.artcollection.R;
import com.tsutsuku.artcollection.alipay.ALiPayUtils;
import com.tsutsuku.artcollection.common.BusEvent;
import com.tsutsuku.artcollection.common.Constants;
import com.tsutsuku.artcollection.http.HttpResponseHandler;
import com.tsutsuku.artcollection.http.HttpsClient;
import com.tsutsuku.artcollection.model.shopping.OrderBean;
import com.tsutsuku.artcollection.ui.shoppingBase.OrderCommentActivity;
import com.tsutsuku.artcollection.ui.wallet.PayTypeDialog;
import com.tsutsuku.artcollection.utils.GsonUtils;
import com.tsutsuku.artcollection.utils.RxBus;
import com.tsutsuku.artcollection.utils.SharedPref;
import com.tsutsuku.artcollection.utils.ToastUtils;
import com.tsutsuku.artcollection.wxapi.WXRxBus;
import com.tsutsuku.artcollection.wxapi.WxPayConstants;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;

/**
 * @Author Sun Renwei
 * @Create 2017/1/25
 * @Description 购物相关工具类
 */

public class ShoppingRepository {

    public static void add(String userId, String productId, String productAmount) {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("service", "ShoppingCart.add");
        hashMap.put("userId", userId);
        hashMap.put("productId", productId);
        hashMap.put("productAmount", productAmount);
        HttpsClient client = new HttpsClient();
        client.post(hashMap, new HttpResponseHandler() {
            @Override
            protected void onSuccess(int statusCode, JSONObject data) throws Exception {

            }

            @Override
            protected void onFailed(int statusCode, Exception e) {

            }
        });

    }

    public static String getOrderStatus(String orderStatus, String deliveryStatus) {
        switch (orderStatus) {
            case "-2":
                return "支付超时";
            case "-1":
                return "等待支付";
            case "0":
                return "订单取消";
            case "2":
                return "退款完成";
            default: {
                switch (deliveryStatus) {
                    case "0":
                    case "3":
                        return "交易完成";
                    case "1":
                        return "卖家待发货";
                    case "2":
                        return "卖家已发货";
                    default:
                        return "退货中";
                }
            }
        }
    }

    public static void orderFunction(final Context context, int ResId, final OrderBean bean) {
        switch (ResId) {
            case R.id.btnCancel: {
                List<String> items = GsonUtils.parseJsonArray(SharedPref.getSysString(Constants.CANCEL_REASON), String.class);
                new MaterialDialog.Builder(context)
                        .title("提示")
                        .items(items)
                        .itemsCallbackSingleChoice(-1, new MaterialDialog.ListCallbackSingleChoice() {
                            @Override
                            public boolean onSelection(MaterialDialog dialog, View itemView, int which, CharSequence text) {
                                cancelOrder(bean, String.valueOf(text));
                                return true;
                            }
                        })
                        .positiveText(R.string.ok)
                        .negativeText(R.string.cancel)
                        .show();
            }
            break;
            case R.id.btnDelete: {
                new MaterialDialog.Builder(context)
                        .title("提示")
                        .content("确认删除该订单?")
                        .positiveText(R.string.ok)
                        .negativeText(R.string.cancel)
                        .onPositive(new com.afollestad.materialdialogs.MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                deleteOrder(bean);
                            }
                        })
                        .onNegative(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {

                            }
                        }).build()
                        .show();
            }
            break;
            case R.id.btnConfirm: {
                new MaterialDialog.Builder(context)
                        .title("提示")
                        .content("确认收货?")
                        .positiveText(R.string.ok)
                        .negativeText(R.string.cancel)
                        .onPositive(new com.afollestad.materialdialogs.MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                confirmOrder(bean);
                            }
                        })
                        .onNegative(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {

                            }
                        }).build()
                        .show();
            }
            break;
            case R.id.btnPay: {
                new PayTypeDialog(context, new PayTypeDialog.CallBack() {
                    @Override
                    public void finish(String payType) {
                        payOrder(context, bean, payType);
                    }
                }).show();
            }
            break;
            case R.id.btnComment: {
                OrderCommentActivity.launch(context, bean.getOrderId());
            }
            break;
        }
    }

    private static void cancelOrder(final OrderBean bean, String cancelReason) {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("service", "Order.cancelOrder");
        hashMap.put("userId", SharedPref.getString(Constants.USER_ID));
        hashMap.put("orderId", bean.getOrderId());
        hashMap.put("canelReason", cancelReason);
        HttpsClient client = new HttpsClient();
        client.post(hashMap, new HttpResponseHandler() {
            @Override
            protected void onSuccess(int statusCode, JSONObject data) throws Exception {
                if (data.getInt("code") == 0) {
                    bean.setOrderStatus("0");
                    RxBus.getDefault().post(BusEvent.SHOPPING, bean);
                }
            }

            @Override
            protected void onFailed(int statusCode, Exception e) {

            }
        });
    }

    private static void deleteOrder(final OrderBean bean) {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("service", "Order.deleteOrder");
        hashMap.put("userId", SharedPref.getString(Constants.USER_ID));
        hashMap.put("orderId", bean.getOrderId());
        HttpsClient client = new HttpsClient();
        client.post(hashMap, new HttpResponseHandler() {
            @Override
            protected void onSuccess(int statusCode, JSONObject data) throws Exception {
                if (data.getInt("code") == 0) {
                    bean.setOrderStatus("-5");
                    RxBus.getDefault().post(BusEvent.SHOPPING, bean);
                }
            }

            @Override
            protected void onFailed(int statusCode, Exception e) {

            }
        });

    }

    private static void confirmOrder(final OrderBean bean) {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("service", "Order.receiveOrder");
        hashMap.put("userId", SharedPref.getString(Constants.USER_ID));
        hashMap.put("orderId", bean.getOrderId());
        HttpsClient client = new HttpsClient();
        client.post(hashMap, new HttpResponseHandler() {
            @Override
            protected void onSuccess(int statusCode, JSONObject data) throws Exception {
                if (data.getInt("code") == 0) {
                    bean.setDeliveryStatus("3");
                    RxBus.getDefault().post(BusEvent.SHOPPING, bean);
                }
            }

            @Override
            protected void onFailed(int statusCode, Exception e) {

            }
        });

    }

    private static void payOrder(final Context context, final OrderBean bean, final String payType) {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("service", "Order.payForOrder");
        hashMap.put("userId", SharedPref.getString(Constants.USER_ID));
        hashMap.put("orderId", bean.getOrderId());
        if (!"4".equals(payType)) {
            hashMap.put("otherType", payType);
            hashMap.put("otherFee", "balance".equals(payType) ? "0" : bean.getTotalFee());
        }
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
                                        ToastUtils.showMessage("支付成功");
                                        paySuccess(bean);
                                    }
                                }, new ALiPayUtils.PayAffirm() {
                                    @Override
                                    public void onAffirm() {
                                        ToastUtils.showMessage("支付失败");
                                    }
                                }, new ALiPayUtils.PayDefeated() {
                                    @Override
                                    public void onDefeated() {
                                        ToastUtils.showMessage("支付失败");
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
                                    ToastUtils.showMessage("支付失败");
                                }

                                @Override
                                public void fail() {
                                    ToastUtils.showMessage("支付失败");
                                }

                                @Override
                                public void success() {
                                    ToastUtils.showMessage("支付成功");
                                    paySuccess(bean);
                                }


                            };
                        }
                        break;
                        case "balance": {
                            ToastUtils.showMessage("支付成功");
                            paySuccess(bean);
                        }
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

    private static void paySuccess(OrderBean bean){
        bean.setOrderStatus("1");
        bean.setDeliveryStatus("1");
        RxBus.getDefault().post(BusEvent.SHOPPING, bean);
    }
}
