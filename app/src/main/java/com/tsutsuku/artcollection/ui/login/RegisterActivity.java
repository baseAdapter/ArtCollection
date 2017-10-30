package com.tsutsuku.artcollection.ui.login;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.tsutsuku.artcollection.R;
import com.tsutsuku.artcollection.common.Constants;
import com.tsutsuku.artcollection.common.Intents;
import com.tsutsuku.artcollection.contract.login.RegisterContract;
import com.tsutsuku.artcollection.presenter.login.RegisterPresenterImpl;
import com.tsutsuku.artcollection.ui.main.MainActivity;
import com.tsutsuku.artcollection.ui.base.BaseActivity;
import com.tsutsuku.artcollection.ui.base.WebActivity;
import com.tsutsuku.artcollection.utils.SharedPref;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @Author Sun Renwei
 * @Create 2017/1/4
 * @Description 注册页面
 */

public class RegisterActivity extends BaseActivity implements RegisterContract.View {


    @BindView(R.id.etInvitePassword)
    EditText etInviteCode;
    @BindView(R.id.etNickname)
    EditText etNickname;
    @BindView(R.id.etAccount)
    EditText etAccount;
    @BindView(R.id.etPassword)
    EditText etPassword;
    @BindView(R.id.etCaptcha)
    EditText etCaptcha;
    @BindView(R.id.btnCaptcha)
    Button btnCaptcha;
    @BindView(R.id.btnRegister)
    Button btnRegister;
    @BindView(R.id.tvAgree)
    TextView tvAgree;
    @BindView(R.id.cbAgree)
    CheckBox cbAgree;

    private RegisterPresenterImpl presenter;

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_register);
    }

    @Override
    public void initViews() {
        ButterKnife.bind(this);

        presenter = new RegisterPresenterImpl();
        presenter.attachView(this);
    }

    @Override
    public void initListeners() {

    }

    @Override
    public void initData() {

    }

    @Override
    public void setCaptchaTime(int time) {
        if (time > 0) {
            btnCaptcha.setBackgroundDrawable(getResources().getDrawable(R.drawable.box_d));
            btnCaptcha.setTextColor(getResources().getColor(R.color.d));
            btnCaptcha.setText(time + getString(R.string.get_captcha_again));
            btnCaptcha.setClickable(false);
        } else {
            btnCaptcha.setBackgroundDrawable(getResources().getDrawable(R.drawable.btn_orange));
            btnCaptcha.setTextColor(getResources().getColor(R.color.white));
            btnCaptcha.setText(R.string.get_captcha);
            btnCaptcha.setClickable(true);
        }
    }

    @Override
    public void registerSuccess() {
        Log.e(TAG, "registerSuccess: ");
        Intent intent = new Intent();
        intent.putExtra(Intents.ACCOUNT,"");
        intent.putExtra(Intents.PASSWORD,"");
        setResult(RESULT_OK,intent);
        this.finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detachView();
    }

    @OnClick({R.id.btnCaptcha, R.id.btnRegister, R.id.tvAgree,R.id.loginTv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnCaptcha:{
                presenter.getCaptcha(etAccount.getText().toString());
            }
                break;
            case R.id.btnRegister:{
                presenter.register(etNickname.getText().toString(),
                        etAccount.getText().toString(),
                        etPassword.getText().toString(),
                        etCaptcha.getText().toString(),
                        etInviteCode.getText().toString(),
                        cbAgree.isChecked());
            }
                break;
            case R.id.tvAgree:{
                startActivity(WebActivity.createIntent(context, SharedPref.getSysString(Constants.REG_URL)));
            }
            case R.id.loginTv:
                startActivity(new Intent(this,LoginActivity.class));
                break;
            default:
            break;
        }
    }
}
