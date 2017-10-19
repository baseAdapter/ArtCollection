package com.tsutsuku.artcollection.presenter.shopping;

import android.content.Context;
import android.os.Parcel;
import android.support.annotation.NonNull;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.tsutsuku.artcollection.R;
import com.tsutsuku.artcollection.common.Constants;
import com.tsutsuku.artcollection.contract.shopping.ShoppingCartContract;
import com.tsutsuku.artcollection.contract.base.AdapterItem;
import com.tsutsuku.artcollection.http.HttpResponseHandler;
import com.tsutsuku.artcollection.http.HttpsClient;
import com.tsutsuku.artcollection.model.shopping.ItemGoods;
import com.tsutsuku.artcollection.model.shopping.ItemShoppingCartView;
import com.tsutsuku.artcollection.model.shopping.ItemVendor;
import com.tsutsuku.artcollection.other.rent.ui.RentDetailActivity;
import com.tsutsuku.artcollection.ui.base.BaseAdapter;
import com.tsutsuku.artcollection.ui.base.BaseRvAdapter;
import com.tsutsuku.artcollection.ui.product.ProductDetailActivity;
import com.tsutsuku.artcollection.ui.shopping.VendorDetailActivity;
import com.tsutsuku.artcollection.ui.shoppingBase.SCGoodsAdapterItem;
import com.tsutsuku.artcollection.ui.shoppingBase.SCVendorAdapterItem;
import com.tsutsuku.artcollection.ui.shoppingBase.ShoppingSettleActivity;
import com.tsutsuku.artcollection.utils.Arith;
import com.tsutsuku.artcollection.utils.GsonUtils;
import com.tsutsuku.artcollection.utils.SharedPref;
import com.tsutsuku.artcollection.utils.ToastUtils;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by sunrenwei on 2017/02/06
 */

public class ShoppingCartPresenterImpl implements ShoppingCartContract.Presenter {
    private BaseAdapter adapter;
    private List<ItemShoppingCartView> viewList;
    private List<ItemVendor> dataList;

    private ShoppingCartContract.View view;
    private String totalPrice = "0";

    private PriceObserver priceObserver;
    private NotifyObserver notifyObserver;
    private Context context;

    public ShoppingCartPresenterImpl(Context context) {
        this.context = context;
        viewList = new ArrayList<>();
        dataList = new ArrayList<>();

        priceObserver = new PriceObserver();
        notifyObserver = new NotifyObserver();
    }

    @Override
    public BaseAdapter getAdapter() {
        if (adapter == null) {
            adapter = new BaseRvAdapter<ItemShoppingCartView>(viewList) {

                @Override
                public Object getItemType(ItemShoppingCartView itemShoppingCartView) {
                    return itemShoppingCartView.isVendor() ? "vendor" : "goods";
                }

                @NonNull
                @Override
                public Object getConvertedData(ItemShoppingCartView data, Object type) {
                    return data.getData();
                }

                @NonNull
                @Override
                public AdapterItem createItem(@NonNull Object type) {
                    switch ((String) type) {
                        case "vendor": {
                            return new SCVendorAdapterItem(context, new SCVendorAdapterItem.onVendorClickListener() {

                                @Override
                                public void onVendorClick(ItemVendor item) {
                                    if (!"0".equals(item.getFarmId())) {
                                        VendorDetailActivity.launch(context, item.getFarmId());
                                    }
                                }

                                @Override
                                public void onEditClick(ItemVendor item) {
                                    item.setEdit(!item.isEdit());
                                    for (ItemGoods goods : item.getProducts()) {
                                        goods.setEdit(item.isEdit());
                                    }
                                }

                                @Override
                                public void onCheckClick(ItemVendor item) {
                                    item.setChecked(!item.isChecked());
                                    for (ItemGoods goods : item.getProducts()) {
                                        goods.setChecked(item.isChecked());
                                    }
                                }
                            });
                        }
                        default: {
                            return new SCGoodsAdapterItem(context, new SCGoodsAdapterItem.onGoodsClickListener() {

                                @Override
                                public void add(ItemGoods item) {
                                    int temp = Integer.valueOf(item.getProductAmount());
                                    if (temp >= Integer.valueOf(item.getInventory())) {
                                        ToastUtils.showMessage(R.string.inventory_overflow);
                                    } else {
                                        item.setProductAmount("1");
                                        updateItem(item);
                                    }
                                }

                                @Override
                                public void remove(ItemGoods item) {
                                    int temp = Integer.valueOf(item.getProductAmount());
                                    if (temp <= 1) {
                                        ToastUtils.showMessage(R.string.inventory_underflow);
                                    } else {
                                        item.setProductAmount("1");
                                        updateItem(item);
                                    }
                                }

                                @Override
                                public void delete(final ItemGoods item) {
                                    new MaterialDialog.Builder(context)
                                            .title("提示")
                                            .content("确认删除？")
                                            .positiveText(context.getString(R.string.ok))
                                            .onPositive(new MaterialDialog.SingleButtonCallback() {
                                                @Override
                                                public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                                    deleteItem(item);
                                                }
                                            })
                                            .negativeText(context.getString(R.string.cancel))
                                            .show();
                                }

                                @Override
                                public void onGoodsClick(ItemGoods item) {
                                    if ("2".equals(item.getIsAuction())) {
                                        RentDetailActivity.launch(context, item.getProductId());
                                    } else {
                                        ProductDetailActivity.launch(context, item.getProductId());
                                    }
                                }

                                @Override
                                public void editNum(ItemGoods item) {
                                    ToastUtils.showMessage("edit num");
                                }

                                @Override
                                public void onCheckClick(ItemGoods item) {
                                    item.setChecked(!item.isChecked());
                                }
                            });
                        }
                    }

                }
            };
        }
        return adapter;
    }

    @Override
    public void refresh() {
        getData();
    }

    @Override
    public void settle() {
        ArrayList<ItemVendor> tempList = new ArrayList<>();
        for (ItemVendor vendor : dataList) {
            Parcel p = Parcel.obtain();
            vendor.writeToParcel(p, 0);
            p.setDataPosition(0); // <-- this is the key
            tempList.add(ItemVendor.CREATOR.createFromParcel(p));
            p.recycle();
        }

        Iterator<ItemVendor> itVendor = tempList.iterator();
        while (itVendor.hasNext()) {
            ItemVendor vendor = itVendor.next();
            Iterator<ItemGoods> itGoods = vendor.getProducts().iterator();
            while (itGoods.hasNext()) {
                ItemGoods goods = itGoods.next();
                if (!goods.isChecked()) {
                    itGoods.remove();
                }
            }

            if (vendor.getProducts().size() == 0) {
                itVendor.remove();
            }
        }

        if (tempList.size() > 0) {
            ShoppingSettleActivity.launch(view.getContext(), "0", (ArrayList) tempList);
        } else {
            ToastUtils.showMessage(R.string.cannot_settle);
        }
    }

    private void getData() {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("service", "ShoppingCart.get");
        hashMap.put("userId", SharedPref.getString(Constants.USER_ID));
        HttpsClient client = new HttpsClient();
        client.post(hashMap, new HttpResponseHandler() {
            @Override
            protected void onSuccess(int statusCode, JSONObject data) throws Exception {
                if (data.getInt("code") == 0) {
                    dataList = GsonUtils.parseJsonArray(data.getString("list"), ItemVendor.class);
                    data2view(dataList, viewList);
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            protected void onFailed(int statusCode, Exception e) {

            }
        });
    }

    private void deleteItem(final ItemGoods item) {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("service", "ShoppingCart.add");
        hashMap.put("userId", SharedPref.getString(Constants.USER_ID));
        hashMap.put("productId", item.getProductId());
        hashMap.put("productAmount", "0");
        HttpsClient client = new HttpsClient();
        client.post(hashMap, new HttpResponseHandler() {
            @Override
            protected void onSuccess(int statusCode, JSONObject data) throws Exception {
                if (data.getInt("code") == 0) {
                    deleteItem(dataList, viewList, item);
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
    private void data2view(List<ItemVendor> dataList, List<ItemShoppingCartView> viewList) {
        viewList.clear();
        for (ItemVendor vendor : dataList) {
            viewList.add(new ItemShoppingCartView(true, vendor));
            vendor.addObserver(notifyObserver);
            for (ItemGoods goods : vendor.getProducts()) {
                viewList.add(new ItemShoppingCartView(false, goods));
                vendor.addObserver(goods);
                goods.addObserver(vendor);
                goods.addObserver(priceObserver);

                goods.addObserver(notifyObserver);
            }
        }
    }

    /**
     * 将商品从数据格式和显示格式List中删除，刷新购物车UI
     *
     * @param dataList  数据格式List
     * @param viewList  显示格式List
     * @param itemGoods 需删除的商品
     */
    private void deleteItem(List<ItemVendor> dataList, List<ItemShoppingCartView> viewList, ItemGoods itemGoods) {
        for (ItemVendor vendor : dataList) {
            if (vendor.getProducts().contains(itemGoods)) {
                viewList.remove(getIndexOfList(itemGoods));
                vendor.getProducts().remove(itemGoods);
                if (vendor.getProducts().size() == 0) {
                    viewList.remove(getIndexOfList(vendor));
                    dataList.remove(vendor);
                }
                return;
            }
        }
        adapter.notifyDataSetChanged();
    }

    private void updateItem(ItemGoods item) {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("service", "ShoppingCart.update");
        hashMap.put("userId", SharedPref.getString(Constants.USER_ID));
        hashMap.put("productId", item.getProductId());
        hashMap.put("productAmount", item.getProductAmount());
        HttpsClient client = new HttpsClient();
        client.post(hashMap, new HttpResponseHandler() {
            @Override
            protected void onSuccess(int statusCode, JSONObject data) throws Exception {

            }

            @Override
            protected void onFailed(int statusCode, Exception e) {

            }
        });
    }

    @Override
    public void attachView(ShoppingCartContract.View view) {
        this.view = view;
    }

    private class PriceObserver implements Observer {

        @Override
        public void update(Observable o, Object arg) {
            if (arg instanceof String) {
                totalPrice = Arith.add(totalPrice, (String) arg);
                view.setTotalPrice(totalPrice);
            }
        }
    }

    private class NotifyObserver implements Observer {

        @Override
        public void update(Observable o, Object arg) {
            if (arg instanceof Character) {
                adapter.notifyItemChanged(getIndexOfList(o), arg);
            }
        }
    }

    private int getIndexOfList(Object object) {
        for (int i = 0; i < viewList.size(); i++) {
            if (viewList.get(i).getData() == object) {
                return i;
            }
        }
        return 0;
    }


    @Override
    public void detachView() {
        this.view = null;
    }
}