package com.tsutsuku.artcollection.ui.shoppingBase;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.text.TextUtils;
import android.widget.TextView;

import com.tsutsuku.artcollection.R;
import com.tsutsuku.artcollection.ui.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @Author Tsutsuku
 * @Create 2017/7/7
 * @Description
 */

public class SettleResultActivity extends BaseActivity {

    private static final String NAME = "name";
    private static final String ADDRESS = "address";

    @BindView(R.id.tvResult)
    TextView tvResult;
    @BindView(R.id.tvName)
    TextView tvName;
    @BindView(R.id.tvAddress)
    TextView tvAddress;

    public static void launch(Context context) {
        launch(context, "", "");
    }

    public static void launch(final Context context, final String name, final String address) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                context.startActivity(new Intent(context, SettleResultActivity.class)
                        .putExtra(NAME, name)
                        .putExtra(ADDRESS, address));
            }
        }, 300);

    }

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_settle_result);
    }

    @Override
    public void initViews() {
        initTitle("支付结果");
        ButterKnife.bind(this);
    }

    @Override
    public void initListeners() {

    }

    @Override
    public void initData() {
        if (TextUtils.isEmpty(getIntent().getStringExtra(NAME))) {
            tvResult.setText("支付失败");
        } else {
            tvResult.setText("支付成功");
            tvName.setText("收货人：" + getIntent().getStringExtra(NAME));
            tvAddress.setText("收货地址：" + getIntent().getStringExtra(ADDRESS));

        }
    }

    @Override
    protected void doBeforeFinish() {
        if (!TextUtils.isEmpty(getIntent().getStringExtra(NAME))) {
            MineOrdersActivity.launch(context, 0);
        }
    }

}
