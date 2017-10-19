package com.tsutsuku.artcollection.ui.shopping;

import android.os.Bundle;
import android.support.annotation.NonNull;
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
import com.tsutsuku.artcollection.ui.main.AuctionAdapterItem;
import com.tsutsuku.artcollection.ui.main.ProductAdapterItem;
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

public class VendorDetailFragment extends BaseFragment {
    private static final String IS_AUCTION = "isAuction";
    private static final String VENDOR_ID = "vendorId";
    @BindView(R.id.rvBase)
    RecyclerView rvBase;

    private boolean isAuction;
    private String vendorId;

    private BaseRvAdapter adapter;
    private GridLayoutManager layoutManager;
    private List<ItemProduct> list;

    public static VendorDetailFragment newInstance(String vendorId, boolean isAuction) {
        VendorDetailFragment fragment = new VendorDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putString(VENDOR_ID, vendorId);
        bundle.putBoolean(IS_AUCTION, isAuction);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_vendor_detail;
    }

    @Override
    protected void initViews() {
        ButterKnife.bind(this, rootView);
        layoutManager = new GridLayoutManager(context, 2);
        rvBase.setLayoutManager(layoutManager);
        rvBase.addItemDecoration(new ItemOffsetDecoration(DensityUtils.dp2px(3)));
    }

    @Override
    protected void initListeners() {

    }

    @Override
    protected void initData() {
        list = new ArrayList<>();

        vendorId = getArguments().getString(VENDOR_ID);
        isAuction = getArguments().getBoolean(IS_AUCTION);
        if (isAuction) {
            adapter = new BaseRvAdapter<ItemProduct>(list) {

                @NonNull
                @Override
                public AdapterItem createItem(@NonNull Object type) {
                    return new AuctionAdapterItem(context);
                }
            };
        } else {
            adapter = new BaseRvAdapter<ItemProduct>(list) {
                @NonNull
                @Override
                public AdapterItem createItem(@NonNull Object type) {
                    return new ProductAdapterItem(context);
                }
            };
        }
        rvBase.setAdapter(adapter);
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

        getData(true);
    }

    private void getData(final boolean refresh) {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("service", "Farm.getFarmProductsList");
        hashMap.put("farmId", vendorId);
        hashMap.put("isAuction", isAuction ? "1" : "2");
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
                adapter.finishLoading();
            }
        });

    }
}
