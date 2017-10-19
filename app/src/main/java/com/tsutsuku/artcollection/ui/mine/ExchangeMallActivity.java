package com.tsutsuku.artcollection.ui.mine;



import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.tsutsuku.artcollection.R;
import com.tsutsuku.artcollection.common.Constants;
import com.tsutsuku.artcollection.http.HttpResponseHandler;
import com.tsutsuku.artcollection.http.HttpsClient;
import com.tsutsuku.artcollection.model.ExchangeBean;
import com.tsutsuku.artcollection.ui.base.BaseActivity;
import com.tsutsuku.artcollection.ui.exchange.ExchangeAdapter;
import com.tsutsuku.artcollection.ui.exchange.ExchangeProductDetailActivity;
import com.tsutsuku.artcollection.ui.exchange.ExchangeRecordActivity;
import com.tsutsuku.artcollection.ui.exchange.HeadPageAdapter;
import com.tsutsuku.artcollection.ui.point.MinePointActivity;
import com.tsutsuku.artcollection.ui.shoppingBase.ShoppingAddressActivity;
import com.tsutsuku.artcollection.utils.DensityUtils;
import com.tsutsuku.artcollection.utils.GlideImageLoader;
import com.tsutsuku.artcollection.utils.GsonUtils;
import com.tsutsuku.artcollection.utils.SharedPref;
import com.tsutsuku.artcollection.utils.TLog;
import com.tsutsuku.artcollection.view.ItemOffsetDecoration;
import com.youth.banner.Banner;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ExchangeMallActivity extends BaseActivity implements View.OnClickListener{
    public static final String TAG = ExchangeMallActivity.class.getSimpleName();
    @BindView(R.id.rvBase)
    RecyclerView mRvBase;

    private List<ExchangeBean> mList = new ArrayList<>();
    private List<String> mImageList = new ArrayList<>();
    private GridLayoutManager mLayoutManager;
    private ExchangeAdapter mAdapter;

    private View mHeaderView;
    private LinearLayout mGoldCoinExchangeLay;
    private LinearLayout mRecordExchangeLay;
    private LinearLayout mAddressExchangeLay;

    private Banner mBanner;

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_exchange_mall);
    }

    @Override
    public void initViews() {
        initTitle(R.string.exchange_mall);
        ButterKnife.bind(this);
        mHeaderView = LayoutInflater.from(this).inflate(R.layout.layout_head_exchange,null);
        mBanner = (Banner) mHeaderView.findViewById(R.id.banner_exchangeMall);

        mGoldCoinExchangeLay = (LinearLayout) mHeaderView.findViewById(R.id.goldCoinExchangeLay);

        mRecordExchangeLay = (LinearLayout) mHeaderView.findViewById(R.id.recordExchangeLay);

        mAddressExchangeLay = (LinearLayout) mHeaderView.findViewById(R.id.addressExchangeLay);

        mLayoutManager = new GridLayoutManager(this,2);

        mLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                if (position == 0){
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
        mGoldCoinExchangeLay.setOnClickListener(this);
        mRecordExchangeLay.setOnClickListener(this);
        mAddressExchangeLay.setOnClickListener(this);

    }

    @Override
    public void initData() {
        mAdapter = new ExchangeAdapter(this , null);
        getData();
        mRvBase.setLayoutManager(mLayoutManager);
        mRvBase.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new ExchangeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(getBaseContext(), ExchangeProductDetailActivity.class);
                intent.putExtra("ExchangeBean.data",mList.get(position));
                startActivity(intent);
            }
        });

    }


    private void getData() {
        HashMap<String,String> hashMap = new HashMap<>();
        hashMap.put("service","Gold.getExchanges");
        hashMap.put("userId", SharedPref.getString(Constants.USER_ID));
        HttpsClient client = new HttpsClient();
        client.post(hashMap, new HttpResponseHandler() {
            @Override
            protected void onSuccess(int statusCode, JSONObject data) throws Exception {
                Log.i(TAG,"onSuccess" + data);
                List<ExchangeBean> list = GsonUtils.parseJsonArray(data.getJSONObject("list").getString("list"), ExchangeBean.class);
                for (int i = 0; i <list.size() ; i++) {
                    ExchangeBean bean = list.get(i);
                    mList.add(bean);
                    Log.i(TAG,"onSuccess" + bean.getCoverPhoto());
                    mImageList.add(bean.getCoverPhoto());
                }

                if (mList.size() != 0) {
                    mAdapter.update(mList);
                    mAdapter.addHeadView(null);
                }
                getBannerData();

            }

            @Override
            protected void onFailed(int statusCode, Exception e) {
                Log.i(TAG,"onFailed" + e);

            }
        });
    }

    private void getBannerData() {
        Log.i(TAG,"getBannerData" + mImageList.size());
        mBanner.setImageLoader(new GlideImageLoader())
                .setImages(mImageList)
                .start();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.goldCoinExchangeLay:
                Intent intent = new Intent(this, MinePointActivity.class);
                startActivity(intent);
                break;
            case R.id.recordExchangeLay:
                startActivity(new Intent(this,ExchangeRecordActivity.class));
                break;
            case R.id.addressExchangeLay:
                startActivity(new Intent(this, ShoppingAddressActivity.class));
                break;
            default:
                break;
        }

    }
}
