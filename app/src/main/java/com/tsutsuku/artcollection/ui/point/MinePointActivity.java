package com.tsutsuku.artcollection.ui.point;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.tsutsuku.artcollection.R;
import com.tsutsuku.artcollection.common.Constants;
import com.tsutsuku.artcollection.http.HttpResponseHandler;
import com.tsutsuku.artcollection.http.HttpsClient;
import com.tsutsuku.artcollection.model.CountInfoBean;
import com.tsutsuku.artcollection.ui.base.BaseActivity;
import com.tsutsuku.artcollection.ui.exchange.ExchangeCoinActivity;
import com.tsutsuku.artcollection.ui.exchange.IntegrationRulesActivity;
import com.tsutsuku.artcollection.ui.mine.IntegrationDetailActivity;
import com.tsutsuku.artcollection.utils.GsonUtils;
import com.tsutsuku.artcollection.utils.SharedPref;
import com.tsutsuku.artcollection.utils.ToastUtils;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @Author Sun Renwei
 * @Create 2017/2/27
 * @Description 我的积分页面View
 */

public class MinePointActivity extends BaseActivity {
    @BindView(R.id.tvPoint)
    TextView tvPoint;
    @BindView(R.id.flExchange)
    FrameLayout flExchange;
    @BindView(R.id.integrationRules)
    TextView mIntegrationRules;


    private List<CountInfoBean> mList = new ArrayList<>();

    private CountInfoBean mInfoBean;


    public static void launch(Context context) {
        context.startActivity(new Intent(context, MinePointActivity.class));
    }

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_mine_point);
    }

    @Override
    public void initViews() {
        initTitle(R.string.mine_point, R.string.mine_details);
        ButterKnife.bind(this);

    }

    @Override
    public void initListeners() {

    }

    @Override
    public void initData() {


    }

    /**
     * 请求积分数据
     **/
    private void getIntegrationData() {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("service", "User.getCountInfo");
        hashMap.put("userId", SharedPref.getString(Constants.USER_ID));
        HttpsClient client = new HttpsClient();
        client.post(hashMap, new HttpResponseHandler() {


            @Override
            protected void onSuccess(int statusCode, JSONObject data) throws Exception {
                if (data.getInt("code") == 0) {
                    mInfoBean = GsonUtils.parseJson(data.getString("info"), CountInfoBean.class);
                    mList.add(mInfoBean);
                    tvPoint.setText(mInfoBean.getIntegrateTotal() + "分");
                }
            }

            @Override
            protected void onFailed(int statusCode, Exception e) {

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        getIntegrationData();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    @OnClick({R.id.exchange_coinsTv, R.id.purchase_offsetTv, R.id.tvTitleButton,R.id.integrationRules})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.exchange_coinsTv:
                Intent intent = new Intent(this, ExchangeCoinActivity.class);
                startActivity(intent);
                break;
            case R.id.purchase_offsetTv:
                ToastUtils.showMessage("该功能尚未开通!");
                break;
            case R.id.tvTitleButton:
                startActivity(new Intent(this, IntegrationDetailActivity.class));
                break;
            case R.id.integrationRules:
                startActivity(new Intent(this, IntegrationRulesActivity.class));
                break;
            default:
                break;
        }
    }


}
