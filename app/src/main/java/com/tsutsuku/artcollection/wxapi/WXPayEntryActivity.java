package com.tsutsuku.artcollection.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.tencent.mm.sdk.constants.ConstantsAPI;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.tsutsuku.artcollection.utils.RxBus;

public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler {

    private static final String TAG = "MicroMsg.SDKSample.WXPayEntryActivity";


    private IWXAPI api;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        api = WXAPIFactory.createWXAPI(this, WxPayConstants.WXAPP_ID);
        api.handleIntent(getIntent(), this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);
    }

    @Override
    public void onReq(BaseReq req) {
    }

    @Override
    public void onResp(BaseResp resp) {
        if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
            if (resp.errCode == -2) {
                Toast.makeText(WXPayEntryActivity.this, WxPayConstants.PAY_CANCEL, Toast.LENGTH_SHORT).show();
                RxBus.getDefault().post("wxpay", new WXItem(WXRxBus.PAY_CANCEL, ""));
            }
            if (resp.errCode == -1) {
                Toast.makeText(WXPayEntryActivity.this, WxPayConstants.PAY_FAILED, Toast.LENGTH_SHORT).show();
                RxBus.getDefault().post("wxpay", new WXItem(WXRxBus.PAY_FAILED, ""));
            }
            if (resp.errCode == 0) {
                Toast.makeText(WXPayEntryActivity.this, WxPayConstants.PAY_SUCCESS, Toast.LENGTH_SHORT).show();
                RxBus.getDefault().post("wxpay", new WXItem(WXRxBus.PAY_SUCCESS, ""));
            }
        }
        finish();
    }
}