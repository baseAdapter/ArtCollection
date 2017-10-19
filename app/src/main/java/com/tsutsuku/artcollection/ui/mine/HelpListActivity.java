package com.tsutsuku.artcollection.ui.mine;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.tsutsuku.artcollection.R;
import com.tsutsuku.artcollection.common.Constants;
import com.tsutsuku.artcollection.ui.base.BaseActivity;
import com.tsutsuku.artcollection.ui.base.WebActivity;
import com.tsutsuku.artcollection.utils.SharedPref;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @Author Tsutsuku
 * @Create 2017/8/13
 * @Description
 */

public class HelpListActivity extends BaseActivity {

    public static void launch(Context context) {
        context.startActivity(new Intent(context, HelpListActivity.class));
    }
    @Override
    public void setContentView() {
        setContentView(R.layout.activity_help_list);
    }

    @Override
    public void initViews() {
        initTitle("帮助中心");
        ButterKnife.bind(this);
    }

    @Override
    public void initListeners() {

    }

    @Override
    public void initData() {

    }

    @OnClick({R.id.llFee, R.id.llFlow, R.id.llPawn, R.id.llIdentify, R.id.llRecharge, R.id.llAbout, R.id.llPoint, R.id.llAgree, R.id.llAuction})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.llFee: {
                WebActivity.launch(context, SharedPref.getSysString(Constants.FEE_URL), "物货费率");
            }
            break;
            case R.id.llFlow: {
                WebActivity.launch(context, SharedPref.getSysString(Constants.FLOW_URL), "物货流程");
            }
            break;
            case R.id.llPawn:{
                WebActivity.launch(context, SharedPref.getSysString(Constants.PAWN_URL), "什么是典当");
            }
                break;
            case R.id.llIdentify:{
                WebActivity.launch(context, SharedPref.getSysString(Constants.JIANDING_URL), "鉴定协议");
            }
                break;
            case R.id.llRecharge:{
                WebActivity.launch(context, SharedPref.getSysString(Constants.TOPUP_UTL), "充值与提现协议");
            }
                break;
            case R.id.llAbout:{
                WebActivity.launch(context, SharedPref.getSysString(Constants.ABOUT_ME_URL), "关于我们");
            }
                break;
            case R.id.llPoint:{
                WebActivity.launch(context, SharedPref.getSysString(Constants.INTEGRATE_URL), "积分说明");
            }
                break;
            case R.id.llAgree:{
                WebActivity.launch(context, SharedPref.getSysString(Constants.REG_URL), "艺术收藏网用户协议");
            }
                break;
            case R.id.llAuction:{
                WebActivity.launch(context, SharedPref.getSysString(Constants.JINGPAI_URL), "竞拍协议");
            }
                break;
        }
    }
}
