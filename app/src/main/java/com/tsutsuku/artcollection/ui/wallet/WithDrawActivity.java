package com.tsutsuku.artcollection.ui.wallet;

import android.content.Context;
import android.content.Intent;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.tsutsuku.artcollection.R;
import com.tsutsuku.artcollection.common.Constants;
import com.tsutsuku.artcollection.http.HttpResponseHandler;
import com.tsutsuku.artcollection.http.HttpsClient;
import com.tsutsuku.artcollection.model.RecordsInfoBean;
import com.tsutsuku.artcollection.ui.base.BaseActivity;
import com.tsutsuku.artcollection.ui.base.WebActivity;
import com.tsutsuku.artcollection.utils.Arith;
import com.tsutsuku.artcollection.utils.KeyBoardUtils;
import com.tsutsuku.artcollection.utils.SharedPref;
import com.tsutsuku.artcollection.utils.ToastUtils;

import org.json.JSONObject;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @Author Sun Renwei
 * @Create 2017/2/16
 * @Description 提现页面
 */

public class WithDrawActivity extends BaseActivity {
    private static final String INFO = "info";
    @BindView(R.id.etCard)
    EditText etCard;
    @BindView(R.id.etAccount)
    EditText etAccount;
    @BindView(R.id.etBank)
    EditText etBank;
    @BindView(R.id.etCash)
    EditText etCash;
    @BindView(R.id.tvInfo)
    TextView tvInfo;
    @BindView(R.id.btnCmd)
    Button btnCmd;
    @BindView(R.id.cbAgree)
    CheckBox cbAgree;

    private RecordsInfoBean info;

    public static void launch(Context context, RecordsInfoBean info) {
        context.startActivity(new Intent(context, WithDrawActivity.class).putExtra(INFO, info));
    }

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_withdraw);
    }

    @Override
    public void initViews() {
        initTitle(R.string.withdraw);
        ButterKnife.bind(this);

        info = getIntent().getParcelableExtra(INFO);
    }

    @Override
    public void initListeners() {
        etCash.setFilters(new InputFilter[]{
                KeyBoardUtils.getCashInputFilter()
        });

        etCash.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    if (Float.valueOf(Arith.sub(s.toString(), info.getCashBalance())) > 0) {
                        tvInfo.setText(R.string.withdraw_too_much);
                        setCmdClickable(false);
                    } else if (Float.valueOf(Arith.sub(s.toString(), info.getWithdrawMinMoney())) < 0) {
                        tvInfo.setText(R.string.withdraw_too_little);
                        setCmdClickable(false);
                    } else {
                        tvInfo.setText("可用余额" + info.getCashBalance() + "元");
                        if (Float.valueOf(info.getWithdrawMinMoney()) > 0) {
                            tvInfo.append(",最小提现金额" + info.getWithdrawMinMoney() + "元");
                        }
                        setCmdClickable(true);
                    }
                } else {
                    tvInfo.setText("可用余额" + info.getCashBalance() + "元");
                    if (Float.valueOf(info.getWithdrawMinMoney()) > 0) {
                        tvInfo.append(",最小提现金额" + info.getWithdrawMinMoney() + "元");
                    }
                    setCmdClickable(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    public void initData() {
        tvInfo.setText("可用余额" + info.getCashBalance() + "元");
        if (Float.valueOf(info.getWithdrawMinMoney()) > 0) {
            tvInfo.append(",最小提现金额" + info.getWithdrawMinMoney() + "元");
        }
        setCmdClickable(true);
    }

    private void setCmdClickable(boolean clickable) {
        if (clickable) {
            btnCmd.setClickable(true);
            tvInfo.setTextColor(getResources().getColor(R.color.d));
            btnCmd.setBackgroundResource(R.drawable.btn_orange);
            btnCmd.setTextColor(getResources().getColor(R.color.white));
        } else {
            btnCmd.setClickable(false);
            tvInfo.setTextColor(getResources().getColor(R.color.red));
            btnCmd.setBackgroundResource(R.drawable.btn_e);
            btnCmd.setTextColor(getResources().getColor(R.color.d));
        }
    }

    @OnClick(R.id.btnCmd)
    public void onClick() {
        if (TextUtils.isEmpty(etCard.getText().toString())) {
            ToastUtils.showMessage("请输入卡号");
        } else if (TextUtils.isEmpty(etAccount.getText().toString())) {
            ToastUtils.showMessage("请输入开户名");
        } else if (TextUtils.isEmpty(etBank.getText().toString())) {
            ToastUtils.showMessage("请输入开户行");
        } else if (!cbAgree.isChecked()) {
            ToastUtils.showMessage("请阅读并同意《充值和提现协议》");
        } else {
            applyWithdraw(etCard.getText().toString().trim(),
                    etAccount.getText().toString().trim(),
                    etBank.getText().toString().trim(),
                    etCash.getText().toString().trim());
        }
    }

    private void applyWithdraw(String card, String account, String bank, String cash) {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("service", "Balance.applyWithdraw");
        hashMap.put("userId", SharedPref.getString(Constants.USER_ID));
        hashMap.put("amount", cash);
        hashMap.put("toAccount", card);
        hashMap.put("bankName", bank);
        hashMap.put("name", account);
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

    @OnClick(R.id.tvAgree)
    public void onViewClicked() {
        WebActivity.launch(context, SharedPref.getSysString(Constants.TOPUP_UTL));
    }
}
