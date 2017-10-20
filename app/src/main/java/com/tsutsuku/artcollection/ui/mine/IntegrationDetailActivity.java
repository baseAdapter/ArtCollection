package com.tsutsuku.artcollection.ui.mine;


import android.support.v7.widget.RecyclerView;

import com.tsutsuku.artcollection.R;
import com.tsutsuku.artcollection.ui.base.BaseActivity;

import butterknife.BindView;

public class IntegrationDetailActivity extends BaseActivity {
    @BindView(R.id.rvBase)
    RecyclerView mRecyclerView;

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_integration_detail);
    }

    @Override
    public void initViews() {
        initTitle(R.string.integration_detail);

    }

    @Override
    public void initListeners() {

    }

    @Override
    public void initData() {

    }
}
