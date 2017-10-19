package com.tsutsuku.artcollection.ui.mine;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.tsutsuku.artcollection.R;
import com.tsutsuku.artcollection.common.Constants;
import com.tsutsuku.artcollection.contract.MineInfoContract;
import com.tsutsuku.artcollection.http.SysUtils;
import com.tsutsuku.artcollection.presenter.MineInfoPresenterImpl;
import com.tsutsuku.artcollection.ui.base.BaseActivity;
import com.tsutsuku.artcollection.ui.shoppingBase.ShoppingAddressActivity;
import com.tsutsuku.artcollection.utils.SharedPref;
import com.tsutsuku.artcollection.utils.glideTransform.CircleTransform;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @Author Sun Renwei
 * @Create 2017/1/14
 * @Description
 */

public class MineInfoActivity extends BaseActivity implements MineInfoContract.View {
    @BindView(R.id.ivAvatar)
    ImageView ivAvatar;
    @BindView(R.id.rlAvatar)
    RelativeLayout rlAvatar;
    @BindView(R.id.tvNick)
    TextView tvNick;
    @BindView(R.id.rlNick)
    RelativeLayout rlNick;
    @BindView(R.id.tvSex)
    TextView tvSex;
    @BindView(R.id.rlSex)
    RelativeLayout rlSex;
    @BindView(R.id.tvPhone)
    TextView tvPhone;
    @BindView(R.id.rlPhone)
    RelativeLayout rlPhone;
    @BindView(R.id.rlResetPwd)
    RelativeLayout rlResetPwd;
    @BindView(R.id.rlAddress)
    RelativeLayout rlAddress;
    @BindView(R.id.btnLogout)
    Button btnLogout;

    public static void launch(Context context) {
        context.startActivity(new Intent(context, MineInfoActivity.class));
    }

    private MineInfoPresenterImpl presenter;

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_mine_info);
    }

    @Override
    public void initViews() {
        initTitle(R.string.mine_info);
        ButterKnife.bind(this);
        presenter = new MineInfoPresenterImpl(context);
        presenter.attachView(this);
    }

    @Override
    public void initListeners() {

    }

    @Override
    public void initData() {
        setSex(SharedPref.getString(Constants.SEX));
        setNick(SharedPref.getString(Constants.NICK));
        setAvatar(SharedPref.getString(Constants.AVATAR));

        setMobile(SharedPref.getString(Constants.MOBILE));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detachView();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            presenter.parseResult(requestCode, data);
        }
    }

    @OnClick({R.id.rlAvatar, R.id.rlNick, R.id.rlSex, R.id.rlPhone, R.id.rlResetPwd, R.id.rlAddress, R.id.btnLogout})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rlAvatar:
                presenter.setAvatar();
                break;
            case R.id.rlNick:
                presenter.setNick(tvNick.getText().toString());
                break;
            case R.id.rlSex:
                presenter.setSex(tvSex.getText().toString());
                break;
            case R.id.rlPhone:
                presenter.setMobile(tvPhone.getText().toString());
                break;
            case R.id.rlResetPwd:
                ChangePasswordActivity.launch(context);
                break;
            case R.id.rlAddress:
                ShoppingAddressActivity.launchTypeEdit(context);
                break;
            case R.id.btnLogout:
                SysUtils.logout();
                break;
        }
    }

    @Override
    public void setMobile(String mobile) {
        tvPhone.setText(mobile);
    }

    @Override
    public void setAvatar(String avatar) {
        Glide.with(context).load(avatar).placeholder(R.drawable.ic_default_avatar).transform(new CircleTransform(context)).into(ivAvatar);
    }

    @Override
    public void setNick(String nick) {
        tvNick.setText(nick);
    }

    @Override
    public void setSex(String sex) {
        tvSex.setText(sex);
    }
}
