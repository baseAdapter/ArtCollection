package com.tsutsuku.artcollection.ui.mine;


import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.tsutsuku.artcollection.R;
import com.tsutsuku.artcollection.ui.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class InviteActivity extends BaseActivity {
    @BindView(R.id.inviteBt)
    Button mInviteBt;
    @BindView(R.id.tv_activity_detail)
    TextView mDetailTv;


    @Override
    public void setContentView() {
        setContentView(R.layout.activity_invite);
    }

    @Override
    public void initViews() {
        initTitle(R.string.invite_friends);
        ButterKnife.bind(this);
    }

    @Override
    public void initListeners() {

    }

    @Override
    public void initData() {

    }

    @OnClick({R.id.inviteBt,R.id.tv_activity_detail})
    public void onClick(View view){
        switch (view.getId()) {
            case R.id.inviteBt:

                break;
            case R.id.tv_activity_detail:
                startActivity(new Intent(this,ActivityDetailActivity.class));
                break;
            default:
                break;
        }

    }
}
