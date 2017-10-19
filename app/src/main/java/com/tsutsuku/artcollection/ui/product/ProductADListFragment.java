package com.tsutsuku.artcollection.ui.product;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tsutsuku.artcollection.R;
import com.tsutsuku.artcollection.contract.base.AdapterItem;
import com.tsutsuku.artcollection.http.HttpResponseHandler;
import com.tsutsuku.artcollection.http.HttpsClient;
import com.tsutsuku.artcollection.model.HomeBean;
import com.tsutsuku.artcollection.ui.base.BasePageFragment;
import com.tsutsuku.artcollection.ui.base.BaseRvAdapter;
import com.tsutsuku.artcollection.ui.main.ProductAdapterItem;
import com.tsutsuku.artcollection.utils.DensityUtils;
import com.tsutsuku.artcollection.utils.GsonUtils;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;
import com.yqritc.recyclerviewflexibledivider.VerticalDividerItemDecoration;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @Author Tsutsuku
 * @Create 2017/5/4
 * @Description
 */

public class ProductADListFragment extends BasePageFragment {
    @BindView(R.id.rvBase)
    RecyclerView rvBase;
    @BindView(R.id.srlBase)
    SwipeRefreshLayout srlBase;
    Unbinder unbinder;

    private BaseRvAdapter<HomeBean.ADBean> adapter;

    public static ProductADListFragment newInstance() {
        ProductADListFragment fragment = new ProductADListFragment();
        return fragment;
    }

    @Override
    protected void fetchData() {
        getData();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_base_rv;
    }

    @Override
    protected void initViews() {
        unbinder = ButterKnife.bind(this, rootView);

        adapter = new BaseRvAdapter<HomeBean.ADBean>(null) {
            @NonNull
            @Override
            public AdapterItem createItem(@NonNull Object type) {
                return new ProductADAdapterItem();
            }
        };
        rvBase.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        rvBase.addItemDecoration(new HorizontalDividerItemDecoration.Builder(context)
                .size(DensityUtils.dp2px(10))
                .build());

        rvBase.setAdapter(adapter);
    }

    @Override
    protected void initListeners() {
        srlBase.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getData();
            }
        });
    }

    @Override
    protected void initData() {
        forceUpdate = true;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    private void getData() {
        srlBase.setRefreshing(true);
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("service", "App.getBaoBeiIndexAd");
        HttpsClient client = new HttpsClient();
        client.post(hashMap, new HttpResponseHandler() {
            @Override
            protected void onSuccess(int statusCode, JSONObject data) throws Exception {
                if (data.getInt("code") == 0) {
                    adapter.setData(GsonUtils.parseJsonArray(data.getString("list"), HomeBean.ADBean.class));
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            protected void onFailed(int statusCode, Exception e) {

            }

            @Override
            protected void onFinish() {
                srlBase.setRefreshing(false);
            }
        });
    }
}
