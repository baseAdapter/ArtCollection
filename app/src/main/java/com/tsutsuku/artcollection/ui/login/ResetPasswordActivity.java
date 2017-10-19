package com.tsutsuku.artcollection.ui.login;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.tsutsuku.artcollection.R;
import com.tsutsuku.artcollection.common.Intents;
import com.tsutsuku.artcollection.contract.login.ResetPasswordContract;
import com.tsutsuku.artcollection.presenter.login.ResetPasswordPresenterImpl;
import com.tsutsuku.artcollection.ui.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @Author Sun Renwei
 * @Create 2017/1/4
 * @Description Content
 */

public class ResetPasswordActivity extends BaseActivity implements ResetPasswordContract.View {
    @BindView(R.id.etAccount)
    EditText etAccount;
    @BindView(R.id.etCaptcha)
    EditText etCaptcha;
    @BindView(R.id.btnCaptcha)
    Button btnCaptcha;
    @BindView(R.id.etPassword)
    EditText etPassword;
    @BindView(R.id.btnReset)
    Button btnReset;

    ResetPasswordContract.Presenter presenter;
    @Override
    public void setContentView() {
        setContentView(R.layout.activity_reset_password);
    }

    @Override
    public void initViews() {
        initTitle(R.string.find_password);
        ButterKnife.bind(this);

        presenter = new ResetPasswordPresenterImpl();
        presenter.attachView(this);
    }

    @Override
    public void initListeners() {

    }

    @Override
    public void initData() {

    }

    @Override
    public void finishReset(String account, String password) {
        setResult(RESULT_OK, new Intent().putExtra(Intents.ACCOUNT, account)
                .putExtra(Intents.PASSWORD, password));
        finish();
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

    @OnClick({R.id.btnCaptcha, R.id.btnReset})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnCaptcha:{
                presenter.getCaptcha(etAccount.getText().toString());
            }
                break;
            case R.id.btnReset:{
                presenter.reset(etAccount.getText().toString(),
                        etPassword.getText().toString(),
                        etCaptcha.getText().toString());
            }
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detachView();
    }
}
