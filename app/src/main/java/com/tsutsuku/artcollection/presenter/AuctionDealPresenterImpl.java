package com.tsutsuku.artcollection.presenter;

import android.content.Context;
import android.support.annotation.NonNull;

import com.tsutsuku.artcollection.R;
import com.tsutsuku.artcollection.common.Constants;
import com.tsutsuku.artcollection.contract.base.AdapterItem;
import com.tsutsuku.artcollection.contract.base.OnItemSimpleClickListener;
import com.tsutsuku.artcollection.contract.contract.AuctionDealContract;
import com.tsutsuku.artcollection.http.HttpResponseHandler;
import com.tsutsuku.artcollection.http.HttpsClient;
import com.tsutsuku.artcollection.model.auction.ItemDealProduct;
import com.tsutsuku.artcollection.model.auction.ItemDealVendor;
import com.tsutsuku.artcollection.model.shopping.ItemGoods;
import com.tsutsuku.artcollection.model.shopping.ItemVendor;
import com.tsutsuku.artcollection.ui.auction.AuctionDealAdapterItem;
import com.tsutsuku.artcollection.ui.auction.AuctionVendorAdapterItem;
import com.tsutsuku.artcollection.ui.base.BaseRvAdapter;
import com.tsutsuku.artcollection.ui.shoppingBase.ShoppingSettleActivity;
import com.tsutsuku.artcollection.utils.Arith;
import com.tsutsuku.artcollection.utils.GsonUtils;
import com.tsutsuku.artcollection.utils.SharedPref;
import com.tsutsuku.artcollection.utils.ToastUtils;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by sunrenwei on 2017/04/03
 */

public class AuctionDealPresenterImpl implements AuctionDealContract.Presenter {
    private AuctionDealContract.View view;

    private List<ItemDealVendor> dataList;
    private List<Object> viewList;

    private BaseRvAdapter adapter;
    private NotifyObserver notifyObserver;
    private Context context;

    public AuctionDealPresenterImpl(Context context) {
        this.context = context;
        notifyObserver = new NotifyObserver();
    }

    @Override
    public void attachView(AuctionDealContract.View view) {
        this.view = view;
        dataList = new ArrayList<>();
        viewList = new ArrayList<>();
    }

    @Override
    public void detachView() {
        this.view = null;
    }

    @Override
    public void getData(final boolean refresh) {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("service", "Product.getMyAuctionSucessRecode");
        hashMap.put("userId", SharedPref.getString(Constants.USER_ID));
        hashMap.put("pageIndex", refresh ? adapter.clearPageIndex() : adapter.addPageIndex());
        hashMap.put("pageSize", String.valueOf(Constants.PAGE_SIZE));
        HttpsClient client = new HttpsClient();
        client.post(hashMap, new HttpResponseHandler() {
            @Override
            protected void onSuccess(int statusCode, JSONObject data) throws Exception {
                if (data.getInt("code") == 0) {
                    if (refresh) {
                        dataList.clear();
                        viewList.clear();
                        adapter.setTotal(data.getJSONObject("list").getInt("total"));
                    }
                    List<Object> tempViewList = new ArrayList<>();
                    List<ItemDealVendor> tempDataList = GsonUtils.parseJsonArray(data.getJSONObject("list").getString("list"), ItemDealVendor.class);
                    data2view(tempDataList, tempViewList);
                    viewList.addAll(tempViewList);
                    dataList.addAll(tempDataList);

                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            protected void onFailed(int statusCode, Exception e) {

            }
        });

    }

    /**
     * 将数据格式转化为adapter列表显示格式
     *
     * @param dataList 数据格式
     * @param viewList 显示格式
     */
    private void data2view(List<ItemDealVendor> dataList, List<Object> viewList) {
        for (ItemDealVendor vendor : dataList) {
            viewList.add(vendor);
            vendor.addObserver(notifyObserver);
            for (ItemDealProduct item : vendor.getProducts()) {
                viewList.add(item);

                vendor.addObserver(item);
                item.addObserver(vendor);
                item.addObserver(notifyObserver);
            }
        }
    }

    @Override
    public BaseRvAdapter getAdapter() {
        if (adapter == null) {
            adapter = new BaseRvAdapter<Object>(viewList) {

                @Override
                public Object getItemType(Object o) {
                    return o instanceof ItemDealVendor ? 0 : 1;
                }

                @NonNull
                @Override
                public AdapterItem createItem(@NonNull Object type) {
                    if (((int) type) == 0) {
                        return new AuctionVendorAdapterItem(new OnItemSimpleClickListener<ItemDealVendor>() {
                            @Override
                            public void onItemClick(ItemDealVendor item) {
                                item.setChecked(!item.isChecked());
                                for (ItemDealProduct goods : item.getProducts()) {
                                    goods.setChecked(item.isChecked());
                                }
                            }
                        });
                    } else {
                        return new AuctionDealAdapterItem();
                    }

                }
            };
        }
        return adapter;
    }

    @Override
    public void settle() {
        ArrayList<ItemVendor> tempList = new ArrayList<>();
        for (ItemDealVendor vendor : dataList) {
            ItemVendor tempVendor = new ItemVendor();
            List<ItemGoods> temp = new ArrayList<>();
            for (ItemDealProduct product : vendor.getProducts()){
                if (product.isChecked()){
                    ItemGoods goods = new ItemGoods();
                    goods.setProductId(product.getProductId());
                    goods.setProductName(product.getProductName());
                    goods.setProductBrief("");
                    goods.setProductPrice(product.getProductPrice());
                    goods.setProductAmount("1");
                    goods.setProductCover(product.getPic());

                    temp.add(goods);
                }
            }

            if (temp.size() > 0){
                tempVendor.setFarmId(vendor.getFarmId());
                tempVendor.setFarmName(vendor.getFarmName());
                tempVendor.setProducts(temp);
                tempList.add(tempVendor);
            }
        }

        if (tempList.size() > 0) {
            ShoppingSettleActivity.launch(context, "0", (ArrayList) tempList);
        } else {
            ToastUtils.showMessage(R.string.cannot_settle);
        }
    }

    private class NotifyObserver implements Observer {

        @Override
        public void update(Observable o, Object arg) {
            if (arg instanceof Character) {
                adapter.notifyItemChanged(getIndexOfList(o), arg);
                setPrice();
            }
        }
    }

    private int getIndexOfList(Object object) {
        for (int i = 0; i < viewList.size(); i++) {
            if (viewList.get(i) == object) {
                return i;
            }
        }
        return 0;
    }

    private void setPrice() {
        String price = "0";
        for (ItemDealVendor vendor : dataList) {
            for (ItemDealProduct item : vendor.getProducts()) {
                if (item.isChecked()) {
                    price = Arith.add(price, item.getProductPrice());
                }
            }
        }
        view.setPrice(price);
    }
}