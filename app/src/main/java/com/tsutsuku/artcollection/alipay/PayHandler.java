package com.tsutsuku.artcollection.alipay;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;


/**
 * Created by HZX on 2015/12/15.
 */
public class PayHandler extends Handler {
    private Context context;
    public PayHandler(Context context) {
        this.context = context;
    }


    // 成功
    protected void succeed() {

    }

    // 失败
    protected void defeated() {

    }
    // 支付中
    protected void affirm() {

    }

    protected void otherHandleMessage(Message message) {
    }
    public void handleMessage(Message message) {
        PayResult payResult = new PayResult((String) message.obj);

        // 支付宝返回此次支付结果及加签，建议对支付宝签名信息拿签约时支付宝提供的公钥做验签
        String resultInfo = payResult.getResult();
        String resultStatus = payResult.getResultStatus();
        // 判断resultStatus 为“9000”则代表支付成功，具体状态码代表含义可参考接口文档
        if (TextUtils.equals(resultStatus, "9000")) {
            succeed();
        } else {
            // 判断resultStatus 为非“9000”则代表可能支付失败
            // “8000”代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
            if (TextUtils.equals(resultStatus, "8000")) {
                affirm();
            } else {
                // 其他值就可以判断为支付失败，包括用户主动取消支付，或者系统返回的错误
                defeated();
            }
        }
    }
}
