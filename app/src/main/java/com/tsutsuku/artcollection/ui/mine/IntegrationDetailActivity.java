package com.tsutsuku.artcollection.ui.mine;


import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.tsutsuku.artcollection.R;
import com.tsutsuku.artcollection.common.Constants;
import com.tsutsuku.artcollection.http.HttpResponseHandler;
import com.tsutsuku.artcollection.http.HttpsClient;
import com.tsutsuku.artcollection.ui.base.BaseActivity;
import com.tsutsuku.artcollection.ui.exchange.IntegrationDetail;
import com.tsutsuku.artcollection.ui.exchange.IntegrationDetailAdapter;
import com.tsutsuku.artcollection.utils.GsonUtils;
import com.tsutsuku.artcollection.utils.SharedPref;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class IntegrationDetailActivity extends BaseActivity {
    public static final String TAG = IntegrationDetailActivity.class.getSimpleName();
    @BindView(R.id.rvBase)
    RecyclerView mRecyclerView;

    private IntegrationDetailAdapter mAdapter;
    private List<IntegrationDetail> mList = new ArrayList<>();
    private LinearLayoutManager mLayoutManager ;

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_integration_detail);
    }

    @Override
    public void initViews() {
        initTitle(R.string.integration_detail);
        ButterKnife.bind(this);
        mLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
    }

    @Override
    public void initListeners() {

    }

    @Override
    public void initData() {
        mAdapter = new IntegrationDetailAdapter(this,null);
        getData();
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
    }

    private void getData() {
        HashMap<String , String> hashMap = new HashMap<>();
        hashMap.put("service","Integrate.getIntegrateRecord");
        hashMap.put("userId", SharedPref.getString(Constants.USER_ID));
        HttpsClient client = new HttpsClient();
        client.post(hashMap, new HttpResponseHandler() {
            @Override
            protected void onSuccess(int statusCode, JSONObject data) throws Exception {
                List<IntegrationDetail> detailList = GsonUtils.parseJsonArray(data.getJSONObject("list").getString("list"), IntegrationDetail.class);
                for (int i = 0; i < detailList.size(); i++) {
                    IntegrationDetail detail = detailList.get(i);
                    mList.add(detail);
                }
                Log.i(TAG,"onSuccess----" + mList.size());
                if (mList.size() !=0 ) {
                    mAdapter.update(mList);
                }
            }

            @Override
            protected void onFailed(int statusCode, Exception e) {

            }
        });

    }
}
