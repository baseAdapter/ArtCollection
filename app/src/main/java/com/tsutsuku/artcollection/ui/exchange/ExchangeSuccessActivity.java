package com.tsutsuku.artcollection.ui.exchange;


import android.content.Intent;
import android.view.View;
import android.widget.Button;

import com.tsutsuku.artcollection.R;
import com.tsutsuku.artcollection.ui.base.BaseActivity;
import com.tsutsuku.artcollection.ui.main.MainActivity;
import com.tsutsuku.artcollection.ui.shoppingBase.OrderDetailActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ExchangeSuccessActivity extends BaseActivity {
    @BindView(R.id.continue_exchange)
    Button mContinueExchangeBt;
    @BindView(R.id.order_detail)
    Button mOrderDetailBt;

    private ExchangeRecord mRecord;

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_exchange_success);
    }

    @Override
    public void initViews() {
        ButterKnife.bind(this);
        initTitle(R.string.exchange_success);
        Intent intent = getIntent();
        mRecord = (ExchangeRecord) intent.getSerializableExtra("Record.data");


    }

    @Override
    public void initListeners() {

    }

    @Override
    public void initData() {

    }

    @OnClick({R.id.continue_exchange,R.id.order_detail})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.order_detail:
                Intent intent = new Intent(this, ExchangeOrderDetailActivity.class);
                intent.putExtra("Record.data",mRecord);
                startActivity(intent);
                break;
            case R.id.continue_exchange:
                finish();
                startActivity(new Intent(this, MainActivity.class));
                break;
        }
    }
}
