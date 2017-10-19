package com.tsutsuku.artcollection.ui.main;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.tsutsuku.artcollection.R;
import com.tsutsuku.artcollection.common.Constants;
import com.tsutsuku.artcollection.contract.base.AdapterItem;
import com.tsutsuku.artcollection.http.HttpResponseHandler;
import com.tsutsuku.artcollection.http.HttpsClient;
import com.tsutsuku.artcollection.model.ItemProduct;
import com.tsutsuku.artcollection.ui.base.BaseFragment;
import com.tsutsuku.artcollection.ui.base.BaseRvAdapter;
import com.tsutsuku.artcollection.utils.DensityUtils;
import com.tsutsuku.artcollection.utils.GsonUtils;
import com.tsutsuku.artcollection.view.ItemOffsetDecoration;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @Author Sun Renwei
 * @Create 2017/3/2
 * @Description Content
 */

public class AuctionListFragment extends BaseFragment {
    private static final String TYPE = "type";
    private static final int TYPE_NOW = 0;
    private static final int TYPE_TOP = 1;

    @BindView(R.id.rvBase)
    RecyclerView rvBase;
    @BindView(R.id.srlBase)
    SwipeRefreshLayout srlBase;

    private GridLayoutManager layoutManager;
    private BaseRvAdapter adapter;
    private int type;
    private String cate = "0";
    private List<ItemProduct> list;

    public static AuctionListFragment newInstanceTypeNow() {
        AuctionListFragment fragment = new AuctionListFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(TYPE, TYPE_NOW);
        fragment.setArguments(bundle);
        return fragment;
    }

    public static AuctionListFragment newInstanceTypeTop() {
        AuctionListFragment fragment = new AuctionListFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(TYPE, TYPE_TOP);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_base_rv;
    }

    @Override
    protected void initViews() {
        ButterKnife.bind(this, rootView);
    }

    @Override
    protected void initListeners() {
        layoutManager = new GridLayoutManager(context, 2);
        rvBase.setLayoutManager(layoutManager);
        rvBase.addItemDecoration(new ItemOffsetDecoration(DensityUtils.dp2px(3)));



        srlBase.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getData(true);
            }
        });
    }

    @Override
    protected void initData() {
        type = getArguments().getInt(TYPE);
        list = new ArrayList<>();
        adapter = new BaseRvAdapter<ItemProduct>(list) {

            @NonNull
            @Override
            public AdapterItem createItem(@NonNull Object type) {
                return new AuctionAdapterItem(context);
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

        rvBase.setAdapter(adapter);
        getData(true);
    }

    public void getData(String cate) {
        this.cate = cate;
        getData(true);
    }

    private void getData(final boolean refresh) {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("service", "Product.getProductsList");
        hashMap.put("isAuction", "1");
        hashMap.put("top", type == TYPE_NOW ? "0" : "1");
        hashMap.put("auctionIng", type == TYPE_NOW ? "1" : "0");
        hashMap.put("spcateId", cate);
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
                    list.addAll(GsonUtils.parseJsonArray(data.getJSONObject("list").getString("list"), ItemProduct.class));
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
