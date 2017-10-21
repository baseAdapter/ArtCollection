package com.tsutsuku.artcollection.ui.exchange;


import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.tsutsuku.artcollection.R;
import com.tsutsuku.artcollection.common.Constants;
import com.tsutsuku.artcollection.http.HttpResponseHandler;
import com.tsutsuku.artcollection.http.HttpsClient;
import com.tsutsuku.artcollection.ui.base.BaseActivity;
import com.tsutsuku.artcollection.utils.DensityUtils;
import com.tsutsuku.artcollection.utils.GsonUtils;
import com.tsutsuku.artcollection.utils.SharedPref;
import com.tsutsuku.artcollection.view.ItemOffsetDecoration;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ExchangeRecordActivity extends BaseActivity implements ExchangeRecordAdapter.OnDeleteListener {
    public static final String TAG = ExchangeRecordActivity.class.getSimpleName();

    private List<ExchangeRecord> mList = new ArrayList<>();
    private ExchangeRecordAdapter mAdapter;
    private LinearLayoutManager mLayoutManager;

    @BindView(R.id.rvBase)
    RecyclerView mRvBase;


    @Override
    public void setContentView() {
        setContentView(R.layout.activity_exchange_record);
    }

    @Override
    public void initViews() {
        initTitle(R.string.exchange_record);
        ButterKnife.bind(this);
        mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRvBase.addItemDecoration(new ItemOffsetDecoration(DensityUtils.dp2px(3)));
        mRvBase.addItemDecoration(new HorizontalDividerItemDecoration.Builder(context)
                .size(DensityUtils.dp2px(3))
                .colorResId(R.color.transparent)
                .build());

    }

    @Override
    public void initListeners() {

    }

    @Override
    public void initData() {
        mAdapter = new ExchangeRecordAdapter(this, null);
        getData();
        mRvBase.setLayoutManager(mLayoutManager);
        mRvBase.setAdapter(mAdapter);
    }

    private void getData() {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("service", "Gold.getExchangesRecord");
        hashMap.put("user_id", SharedPref.getString(Constants.USER_ID));
        HttpsClient client = new HttpsClient();
        client.post(hashMap, new HttpResponseHandler() {
            @Override
            protected void onSuccess(int statusCode, JSONObject data) throws Exception {
                List<ExchangeRecord> list = GsonUtils.parseJsonArray(data.getJSONObject("list").getString("list"), ExchangeRecord.class);
                for (int i = 0; i < list.size(); i++) {
                    ExchangeRecord record = list.get(i);
                    mList.add(record);
                }
                if (mList.size() != 0) {
                    mAdapter.update(mList);
                }

            }

            @Override
            protected void onFailed(int statusCode, Exception e) {

            }
        });

    }

    private void deleteRecord(int id) {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("service", "Gold.deleteSingleRecord");
        hashMap.put("user_id", SharedPref.getString(Constants.USER_ID));
        hashMap.put("order_id", id + "");
        HttpsClient client = new HttpsClient();
        client.post(hashMap, new HttpResponseHandler() {
            @Override
            protected void onSuccess(int statusCode, JSONObject data) throws Exception {
                getData();
            }

            @Override
            protected void onFailed(int statusCode, Exception e) {

            }
        });

    }

    @OnClick({R.id.tvTitleButton})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tvTitleButton:
                break;
            default:
                break;
        }

    }

    @Override
    public void delete(int id) {
        deleteRecord(id);
    }

}
