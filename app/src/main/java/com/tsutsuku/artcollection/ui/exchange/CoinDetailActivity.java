package com.tsutsuku.artcollection.ui.exchange;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

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

public class CoinDetailActivity extends BaseActivity {
    public static final String TAG = CoinDetailActivity.class.getSimpleName();
    private List<CoinDetail> mList = new ArrayList<>();
    private CoinDetailAdapter mAdapter;
    private LinearLayoutManager mLayoutManager;

    @BindView(R.id.rvBase)
    RecyclerView mRvBase;

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_coin_detail);
    }

    @Override
    public void initViews() {
        initTitle(R.string.coin_detail);
        ButterKnife.bind(this);
        mLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
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
        mAdapter = new CoinDetailAdapter(this,null);
        getData();
        mRvBase.setLayoutManager(mLayoutManager);
        mRvBase.setAdapter(mAdapter);

    }

    private void getData() {
        Log.i(TAG,"getData");
        HashMap<String , String> hashMap = new HashMap<>();
        hashMap.put("service","Gold.getDetail");
        hashMap.put("user_id", SharedPref.getString(Constants.USER_ID));
        hashMap.put("type","1");
        HttpsClient client = new HttpsClient();
        client.post(hashMap, new HttpResponseHandler() {
            @Override
            protected void onSuccess(int statusCode, JSONObject data) throws Exception {
                Log.i(TAG,"onSuccess----" + data);
                List<CoinDetail> list = GsonUtils.parseJsonArray(data.getJSONObject("list").getString("list"), CoinDetail.class);
                for (int i = 0; i < list.size(); i++) {
                    CoinDetail coinDetail = list.get(i);
                    mList.add(coinDetail);
                }
                Log.i(TAG,"onSuccess----" + mList.size());
                if (mList.size() != 0){
                    mAdapter.update(mList);
                }
            }

            @Override
            protected void onFailed(int statusCode, Exception e) {
                Log.i(TAG,"onFailed" + statusCode);

            }
        });
    }
}
