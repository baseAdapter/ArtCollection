package com.tsutsuku.artcollection.ui.mine;



import android.support.v7.widget.RecyclerView;

import com.tsutsuku.artcollection.R;
import com.tsutsuku.artcollection.ui.base.BaseActivity;

import butterknife.BindView;

public class ActivityDetailActivity extends BaseActivity {

    @BindView(R.id.rvBase)
    RecyclerView mRvBase;


    @Override
    public void setContentView() {
        setContentView(R.layout.activity_acitivity_detail);
    }

    @Override
    public void initViews() {


    }

    @Override
    public void initListeners() {

    }

    @Override
    public void initData() {

    }
}
