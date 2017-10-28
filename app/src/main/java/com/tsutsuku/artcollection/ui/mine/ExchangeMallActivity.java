package com.tsutsuku.artcollection.ui.mine;


import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.tsutsuku.artcollection.R;
import com.tsutsuku.artcollection.common.Constants;
import com.tsutsuku.artcollection.http.HttpResponseHandler;
import com.tsutsuku.artcollection.http.HttpsClient;
import com.tsutsuku.artcollection.model.ExchangeBean;
import com.tsutsuku.artcollection.ui.base.BaseActivity;
import com.tsutsuku.artcollection.ui.exchange.ExchangeAdapter;
import com.tsutsuku.artcollection.ui.exchange.ExchangeProductDetailActivity;
import com.tsutsuku.artcollection.ui.exchange.MallRulesActivity;
import com.tsutsuku.artcollection.ui.utils.OnRecyclerViewListener;
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

public class ExchangeMallActivity extends BaseActivity implements OnRecyclerViewListener {
    public static final String TAG = ExchangeMallActivity.class.getSimpleName();
    @BindView(R.id.rvBase)
    RecyclerView mRvBase;
    @BindView(R.id.tvDetail)
    TextView mDetailTv;

    private List<ExchangeBean> mList = new ArrayList<>();
    private List<String> mImageList = new ArrayList<>();
    private GridLayoutManager mLayoutManager;
    private ExchangeAdapter mAdapter;


    @Override
    public void setContentView() {
        setContentView(R.layout.activity_exchange_mall);
    }

    @Override
    public void initViews() {
        initTitle(R.string.exchange_mall);
        ButterKnife.bind(this);
        mLayoutManager = new GridLayoutManager(this, 2);
        mLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                if (position == 0) {
                    return mLayoutManager.getSpanCount();
                }
                return 1;
            }
        });
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
    protected void onResume() {
        super.onResume();
        getData();
    }

    @Override
    public void initData() {
        mAdapter = new ExchangeAdapter(this);
        mRvBase.setLayoutManager(mLayoutManager);
        mRvBase.setAdapter(mAdapter);
//        mAdapter.setOnItemClickListener(new ExchangeAdapter.OnItemClickListener() {
//            @Override
//            public void onItemClick(View view, int position) {
//                Intent intent = new Intent(getBaseContext(), ExchangeProductDetailActivity.class);
//                intent.putExtra("ExchangeBean.data", mList.get(position));
//                startActivity(intent);
//            }
//        });

    }


    private void getData() {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("service", "Gold.getExchanges");
        hashMap.put("userId", SharedPref.getString(Constants.USER_ID));
        HttpsClient client = new HttpsClient();
        client.post(hashMap, new HttpResponseHandler() {
            @Override
            protected void onSuccess(int statusCode, JSONObject data) throws Exception {
                Log.i(TAG, "onSuccess" + data);
                mList = GsonUtils.parseJsonArray(data.getJSONObject("list").getString("list"), ExchangeBean.class);
                mImageList.clear();
                for (int i = 0; i < mList.size(); i++) {
                    mImageList.add(mList.get(i).getCoverPhoto());
                }

                if (mList.size() != 0) {
                    mAdapter.setData(mList);
                    mAdapter.addHeadView(mImageList);
                }
            }

            @Override
            protected void onFailed(int statusCode, Exception e) {
                Log.i(TAG, "onFailed" + e);

            }
        });
    }

    @OnClick({R.id.tvDetail})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvDetail:
                startActivity(new Intent(this, MallRulesActivity.class));
                break;
        }
    }


    @Override
    public void onItemClick(View view, int position) {
        Intent intent = new Intent(getBaseContext(), ExchangeProductDetailActivity.class);
        intent.putExtra("ExchangeBean.data", mList.get(position - 1));
        startActivity(intent);
    }
}
