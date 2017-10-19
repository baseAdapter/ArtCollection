package com.tsutsuku.artcollection.presenter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.tsutsuku.artcollection.common.Constants;
import com.tsutsuku.artcollection.contract.shopping.OrdersListContract;
import com.tsutsuku.artcollection.contract.base.AdapterItem;
import com.tsutsuku.artcollection.contract.base.OnItemSimpleClickListener;
import com.tsutsuku.artcollection.http.HttpResponseHandler;
import com.tsutsuku.artcollection.http.HttpsClient;
import com.tsutsuku.artcollection.model.ItemRv;
import com.tsutsuku.artcollection.model.shopping.ItemOrder;
import com.tsutsuku.artcollection.model.shopping.OrderBean;
import com.tsutsuku.artcollection.ui.base.BaseRvAdapter;
import com.tsutsuku.artcollection.ui.shopping.VendorDetailActivity;
import com.tsutsuku.artcollection.ui.shoppingBase.OGoodsAdapterItem;
import com.tsutsuku.artcollection.ui.shoppingBase.OInfoAdapterItem;
import com.tsutsuku.artcollection.ui.shoppingBase.OVendorAdapterItem;
import com.tsutsuku.artcollection.ui.shoppingBase.OrderDetailActivity;
import com.tsutsuku.artcollection.utils.GsonUtils;
import com.tsutsuku.artcollection.utils.SharedPref;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by sunrenwei on 2017/02/12
 */

public class OrdersListPresenterImpl implements OrdersListContract.Presenter {
    private static final int FUNCTION_COMMENT = 0;

    private OrdersListContract.View view;
    private BaseRvAdapter adapter;
    private Context context;
    private String type;

    private List<ItemRv> viewList;
    private List<ItemOrder> dataList;
    private LinearLayoutManager layoutManager;

    public OrdersListPresenterImpl(Context context, String type) {
        this.context = context;
        this.type = type;
        this.viewList = new ArrayList<>();
        this.dataList = new ArrayList<>();
    }

    @Override
    public void attachView(OrdersListContract.View view) {
        this.view = view;
    }

    @Override
    public void detachView() {
        this.view = null;
    }

    @Override
    public BaseRvAdapter getAdapter() {
        if (adapter == null) {
            adapter = new BaseRvAdapter<ItemRv>(viewList) {
                @Override
                public Object getItemType(ItemRv itemRv) {
                    return itemRv.getType();
                }

                @NonNull
                @Override
                public Object getConvertedData(ItemRv data, Object type) {
                    return data.getData();
                }

                @NonNull
                @Override
                public AdapterItem createItem(@NonNull Object type) {
                    switch ((String) type) {
                        case "vendor": {
                            return new OVendorAdapterItem(context, new OnItemSimpleClickListener<ItemOrder>() {
                                @Override
                                public void onItemClick(ItemOrder item) {
                                    VendorDetailActivity.launch(context, item.getFarmId());
                                }
                            });
                        }
                        case "goods": {
                            return new OGoodsAdapterItem(context, new OnItemSimpleClickListener<ItemOrder.ItemsBean>() {
                                @Override
                                public void onItemClick(ItemOrder.ItemsBean item) {
                                    OrderDetailActivity.launch(context, item.getOrderId());
                                }
                            });
                        }
                        default: {
                            return new OInfoAdapterItem();
                        }
                    }
                }
            };
        }
        return adapter;
    }

    @Override
    public void refreshData() {
        getData(true);
    }

    @Override
    public void initViews(final RecyclerView rvBase) {
        layoutManager = new LinearLayoutManager(view.getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvBase.setLayoutManager(layoutManager);

        rvBase.setAdapter(getAdapter());
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


    }

    @Override
    public void parseBusEvent(OrderBean bean) {
        ItemOrder item = getOrderItem(bean);
        if (item != null) {
            item.setOrderStatus(bean.getOrderStatus());
            item.setDeliveryStatus(bean.getDeliveryStatus());

            switch (item.getOrderStatus()) {
                case "-5": {
                    deleteItem(item);
                }
                break;
                default: {
                    adapter.notifyItemChanged(getIndexOfInfo(item.getOrderId()), "1");
                    adapter.notifyItemChanged(getIndexOfVendor(item.getOrderId()), "1");
                }
                break;
            }
        }

    }

    private void data2view(List<ItemOrder> dataList, List<ItemRv> viewList) {
        for (ItemOrder order : dataList) {
            viewList.add(new ItemRv("vendor", order));

            for (ItemOrder.ItemsBean bean : order.getItems()) {
                bean.setOrderId(order.getOrderId());
                viewList.add(new ItemRv("goods", bean));
            }
            viewList.add(new ItemRv("info", order));
        }
    }

    private void getData(final boolean refresh) {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("service", "Order.getOrderList");
        hashMap.put("userId", SharedPref.getString(Constants.USER_ID));
        hashMap.put("type", type);
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

                    view.hideBlank();
                    List<ItemRv> tempViewList = new ArrayList<ItemRv>();
                    List<ItemOrder> tempDataList = GsonUtils.parseJsonArray(data.getJSONObject("list").getString("list"), ItemOrder.class);
                    data2view(tempDataList, tempViewList);
                    viewList.addAll(tempViewList);
                    dataList.addAll(tempDataList);
                    adapter.notifyDataSetChanged();
                } else {
                    view.showBlank();
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

    private ItemOrder getOrderItem(OrderBean bean) {
        for (ItemOrder order : dataList) {
            if (order.getOrderId().equals(bean.getOrderId())) {
                return order;
            }
        }
        return null;
    }

    private int getIndexOfInfo(String orderId) {
        for (int i = 0; i < viewList.size(); i++) {
            ItemRv item = viewList.get(i);
            if ("info".equals(item.getType()) && orderId.equals(((ItemOrder) item.getData()).getOrderId())) {
                return i;
            }
        }
        return 0;
    }

    private int getIndexOfVendor(String orderId) {
        for (int i = 0; i < viewList.size(); i++) {
            ItemRv item = viewList.get(i);
            if ("vendor".equals(item.getType()) && orderId.equals(((ItemOrder) item.getData()).getOrderId())) {
                return i;
            }
        }
        return 0;
    }

    private void deleteItem(ItemOrder item) {
        int viewSize = viewList.size();
        for (int i = viewSize - 1; i > 0; i--) {
            ItemRv rv = viewList.get(i);
            switch (rv.getType()) {
                case "info":
                case "vendor": {
                    if (item.getOrderId().equals(((ItemOrder) rv.getData()).getOrderId())) {
                        viewList.remove(i);
                    }
                }
                break;
                default: {
                    if (item.getOrderId().equals(((ItemOrder.ItemsBean) rv.getData()).getOrderId())) {
                        viewList.remove(i);
                    }
                }
                break;
            }
        }

        dataList.remove(item);
        adapter.notifyDataSetChanged();
    }
}