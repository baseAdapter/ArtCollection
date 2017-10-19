package com.tsutsuku.artcollection.ui.wallet;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.tsutsuku.artcollection.R;
import com.tsutsuku.artcollection.contract.MineWalletContract;
import com.tsutsuku.artcollection.presenter.MineWalletPresenterImpl;
import com.tsutsuku.artcollection.ui.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @Author Sun Renwei
 * @Create 2017/2/14
 * @Description Content
 */

public class MineWalletActivity extends BaseActivity implements MineWalletContract.View {
    @BindView(R.id.ivBack)
    ImageView ivBack;
    @BindView(R.id.tvBalance)
    TextView tvBalance;
    @BindView(R.id.rlRecharge)
    FrameLayout rlRecharge;
    @BindView(R.id.rlWithdraw)
    FrameLayout rlWithdraw;
    @BindView(R.id.rvBase)
    RecyclerView rvBase;

    private MineWalletPresenterImpl presenter;

    public static void launch(Context context) {
        context.startActivity(new Intent(context, MineWalletActivity.class));
    }

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_mine_wallet);
    }

    @Override
    public void initViews() {
        ButterKnife.bind(this);

        presenter = new MineWalletPresenterImpl();
        presenter.attachView(this);

        LinearLayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        rvBase.setLayoutManager(layoutManager);
    }

    @Override
    public void initListeners() {

    }

    @Override
    public void initData() {
        rvBase.setAdapter(presenter.getAdapter());
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.getData();
    }

    @OnClick({R.id.ivBack, R.id.rlRecharge, R.id.rlWithdraw})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ivBack:
                finish();
                break;
            case R.id.rlRecharge:
                RechargeActivity.launch(context);
                break;
            case R.id.rlWithdraw:
                WithDrawActivity.launch(context, presenter.getInfo());
                break;
        }
    }

    @Override
    public void setBalance(String balance) {
        tvBalance.setText(balance);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detachView();
    }
}
