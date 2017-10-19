package com.tsutsuku.artcollection.ui.activity;

import android.content.Context;
import android.view.Gravity;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;
import com.tsutsuku.artcollection.R;
import com.tsutsuku.artcollection.common.Constants;
import com.tsutsuku.artcollection.http.HttpResponseHandler;
import com.tsutsuku.artcollection.http.HttpsClient;
import com.tsutsuku.artcollection.utils.DensityUtils;
import com.tsutsuku.artcollection.utils.SharedPref;

import org.json.JSONObject;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @Author Tsutsuku
 * @Create 2017/3/26
 * @Description 打赏Dialog
 */

public class PayFeeDialog {
    @BindView(R.id.rgBase)
    RadioGroup rgBase;
    @BindView(R.id.btnOk)
    Button btnOk;
    private DialogPlus dialog;
    private String Id;
    private Context context;

    public PayFeeDialog(Context context, String Id) {
        this.Id = Id;
        this.context = context;
        init();
    }

    private void init() {
        dialog = DialogPlus.newDialog(context)
                .setContentHolder(new ViewHolder(R.layout.dialog_pay_fee))
                .setOverlayBackgroundResource(R.color.transparent)
                .setMargin(DensityUtils.dp2px(30), 0 ,DensityUtils.dp2px(30), 0)
                .setGravity(Gravity.CENTER)
                .create();

        ButterKnife.bind(this, dialog.getHolderView());
    }

    public void show() {
        dialog.show();
    }

    private void pay(String fee) {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("service", "Activities.doReward");
        hashMap.put("activityId", Id);
        hashMap.put("userId", SharedPref.getString(Constants.USER_ID));
        hashMap.put("doMoney", fee);
        HttpsClient client = new HttpsClient();
        client.post(hashMap, new HttpResponseHandler() {
            @Override
            protected void onSuccess(int statusCode, JSONObject data) throws Exception {
                if (data.getInt("code") == 0) {

                }
            }

            @Override
            protected void onFailed(int statusCode, Exception e) {

            }
        });
    }

    @OnClick(R.id.btnOk)
    public void onClick() {
        String fee = ((RadioButton) dialog.getHolderView().findViewById(rgBase.getCheckedRadioButtonId())).getText().toString();
        pay(fee.substring(0, fee.length() - 1));
        dialog.dismiss();
    }
}
