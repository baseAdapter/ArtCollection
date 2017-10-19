package com.tsutsuku.artcollection.ui.product;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.tsutsuku.artcollection.R;
import com.tsutsuku.artcollection.common.Constants;
import com.tsutsuku.artcollection.contract.base.AdapterItem;
import com.tsutsuku.artcollection.http.HttpResponseHandler;
import com.tsutsuku.artcollection.http.HttpsClient;
import com.tsutsuku.artcollection.model.HomeBean;
import com.tsutsuku.artcollection.model.ItemProduct;
import com.tsutsuku.artcollection.ui.base.BasePageFragment;
import com.tsutsuku.artcollection.ui.base.BaseRvAdapter;
import com.tsutsuku.artcollection.ui.main.ProductAdapterItem;
import com.tsutsuku.artcollection.utils.DensityUtils;
import com.tsutsuku.artcollection.utils.GsonUtils;
import com.tsutsuku.artcollection.view.ItemOffsetDecoration;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import org.json.JSONObject;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @Author Tsutsuku
 * @Create 2017/5/4
 * @Description
 */

public class ProductListFragment extends BasePageFragment {
    private static final String ID = "id";
    @BindView(R.id.rvBase)
    RecyclerView rvBase;
    @BindView(R.id.srlBase)
    SwipeRefreshLayout srlBase;
    Unbinder unbinder;

    private BaseRvAdapter<ItemProduct> adapter;
    private GridLayoutManager layoutManager;

    public static ProductListFragment newInstance(String id) {
        ProductListFragment fragment = new ProductListFragment();
        Bundle bundle = new Bundle();
        bundle.putString(ID, id);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected void fetchData() {
        getData(true);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_base_rv;
    }

    @Override
    protected void initViews() {
        unbinder = ButterKnife.bind(this, rootView);

        adapter = new BaseRvAdapter<ItemProduct>(null) {
            @NonNull
            @Override
            public AdapterItem createItem(@NonNull Object type) {
                return new ProductAdapterItem(context);
            }
        };
        adapter.setupScroll(rvBase, new BaseRvAdapter.CallBack() {
            @Override
            public int findLastVisibleItemPosition() {
                return layoutManager.findLastVisibleItemPosition();
            }

            @Override
            public void loadData() {
                getData(false);
            }
        });

        layoutManager = new GridLayoutManager(context, 2);
        rvBase.setLayoutManager(layoutManager);
        rvBase.addItemDecoration(new ItemOffsetDecoration(DensityUtils.dp2px(5)));

        rvBase.setAdapter(adapter);
    }

    @Override
    protected void initListeners() {
        srlBase.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getData(true);
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

    private void getData(final boolean refresh) {
        srlBase.setRefreshing(true);
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("service", "Product.getProductsList");
        hashMap.put("isAuction", "2");
        hashMap.put("spcateId", getArguments().getString(ID));
        hashMap.put("pageIndex", refresh ? adapter.clearPageIndex() : adapter.addPageIndex());
        hashMap.put("pageSize", String.valueOf(Constants.PAGE_SIZE));
        HttpsClient client = new HttpsClient();
        client.post(hashMap, new HttpResponseHandler() {
            @Override
            protected void onSuccess(int statusCode, JSONObject data) throws Exception {
                if (data.getInt("code") == 0) {
                    if (refresh) {
                        adapter.setTotal(data.getJSONObject("list").getInt("total"));
                    }
                    adapter.getData().addAll(GsonUtils.parseJsonArray(data.getJSONObject("list").getString("list"), ItemProduct.class));
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
