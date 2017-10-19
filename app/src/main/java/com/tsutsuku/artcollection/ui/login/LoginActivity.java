package com.tsutsuku.artcollection.ui.login;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.tsutsuku.artcollection.R;
import com.tsutsuku.artcollection.common.Intents;
import com.tsutsuku.artcollection.contract.login.LoginContract;
import com.tsutsuku.artcollection.presenter.login.LoginPresenterImpl;
import com.tsutsuku.artcollection.ui.main.MainActivity;
import com.tsutsuku.artcollection.ui.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @Author Sun Renwei
 * @Create 2017/1/3
 * @Description 登录View
 */

public class LoginActivity extends BaseActivity implements LoginContract.View {
    private static final int RESET_PASSWORD = 1;

    @BindView(R.id.etAccount)
    EditText etAccount;
    @BindView(R.id.etPassword)
    EditText etPassword;
    @BindView(R.id.btnLogin)
    Button btnLogin;
    @BindView(R.id.tvResetPwd)
    TextView tvResetPwd;
    private LoginPresenterImpl presenter;

    public static void launch(Context context) {
        context.startActivity(new Intent(context, LoginActivity.class));
    }

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_login);
    }

    @Override
    public void initViews() {

        presenter = new LoginPresenterImpl(context);
        presenter.attachView(this);
    }

    @Override
    public void initListeners() {
    }

    @Override
    public void initData() {
        ButterKnife.bind(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detachView();
    }

    @OnClick({R.id.btnLogin, R.id.tvResetPwd, R.id.registerTv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnLogin: {
                presenter.login(etAccount.getText().toString(), etPassword.getText().toString());
            }
            break;
            case R.id.tvResetPwd: {
                startActivity(new Intent(context, ResetPasswordActivity.class));
            }
            break;
            case R.id.registerTv: {
                startActivityForResult(new Intent(context, RegisterActivity.class), RESET_PASSWORD);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == RESET_PASSWORD) {
            presenter.login(data.getStringExtra(Intents.ACCOUNT), data.getStringExtra(Intents.PASSWORD));
        }
    }
}
