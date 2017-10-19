package com.tsutsuku.artcollection.presenter.address;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.tsutsuku.artcollection.R;
import com.tsutsuku.artcollection.common.Constants;
import com.tsutsuku.artcollection.common.Intents;
import com.tsutsuku.artcollection.contract.shopping.ShoppingAddressContract;
import com.tsutsuku.artcollection.contract.base.AdapterItem;
import com.tsutsuku.artcollection.http.HttpResponseHandler;
import com.tsutsuku.artcollection.http.HttpsClient;
import com.tsutsuku.artcollection.model.shopping.ItemAddress;
import com.tsutsuku.artcollection.ui.base.BaseAdapter;
import com.tsutsuku.artcollection.ui.base.BaseRvAdapter;
import com.tsutsuku.artcollection.ui.shoppingBase.AddressAdapterEditItem;
import com.tsutsuku.artcollection.ui.shoppingBase.AddressAdapterItem;
import com.tsutsuku.artcollection.ui.shoppingBase.ShoppingAddressDetailActivity;
import com.tsutsuku.artcollection.utils.SharedPref;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;

/**
 * Created by sunrenwei on 2017/01/18
 */

public class ShoppingAddressPresenterImpl implements ShoppingAddressContract.Presenter {
    public static final int TYPE_EDIT = 0;
    public static final int TYPE_VIEW = 1;

    private Gson gson = new Gson();
    private Type type = new TypeToken<List<ItemAddress>>() {
    }.getType();

    private ShoppingAddressContract.View view;
    private BaseRvAdapter adapter;
    private Context context;
    private int viewType = 0;

    public ShoppingAddressPresenterImpl(Context context, int viewType) {
        this.context = context;
        this.viewType = viewType;
    }

    @Override
    public BaseAdapter getAdapter() {
        if (adapter == null) {
            switch (viewType) {
                case TYPE_EDIT: {
                    adapter = new BaseRvAdapter<ItemAddress>(null) {

                        @NonNull
                        @Override
                        public AdapterItem createItem(@NonNull Object item) {
                            return new AddressAdapterEditItem(new AddressAdapterEditItem.ItemClickListener() {

                                @Override
                                public void onSelectClick(ItemAddress item) {
                                    selectDefault(item.getAddressId());
                                }

                                @Override
                                public void onDeleteClick(ItemAddress item) {
                                    deleteAddress(item.getAddressId());
                                }

                                @Override
                                public void onEditClick(ItemAddress item) {
                                    ShoppingAddressDetailActivity.launch(view.getContext(), item);
                                }
                            });
                        }
                    };
                }
                break;
                case TYPE_VIEW: {
                    adapter = new BaseRvAdapter<ItemAddress>(null) {

                        @NonNull
                        @Override
                        public AdapterItem createItem(@NonNull Object item) {
                            return new AddressAdapterItem(new AddressAdapterItem.onItemClickListener() {
                                @Override
                                public void onItemClick(ItemAddress item) {
                                    view.finishView(item);
                                }
                            });
                        }
                    };
                }
                break;
            }

        }
        return adapter;
    }

    @Override
    public void loadData() {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("service", "ReceiptAddress.get");
        hashMap.put("userId", SharedPref.getString(Constants.USER_ID));
        HttpsClient client = new HttpsClient();
        client.post(hashMap, new HttpResponseHandler() {
            @Override
            protected void onSuccess(int statusCode, JSONObject data) throws Exception {
                if (data.getInt("code") == 0) {
                    adapter.getData().clear();
                    adapter.getData().addAll((List<ItemAddress>) gson.fromJson(data.getString("list"), type));
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            protected void onFailed(int statusCode, Exception e) {

            }
        });


    }

    @Override
    public void parseResult(int requestCode, Bundle data) {
        ItemAddress item = data.getParcelable(Intents.ADDRESS);
        List<ItemAddress> list = adapter.getData();
        for (ItemAddress address : list) {
            if (item.getAddressId().equals(address.getAddressId())) {
                list.set(list.indexOf(address), item);
            } else if ("1".equals(item.getIsDefault())) {
                address.setIsDefault("0");
            }
        }

        if (!list.contains(item)) {
            list.add("1".equals(item.getIsDefault()) || list.size() == 0 ? 0 : 1, item);
            if (list.size() == 1) {
                item.setIsDefault("1");
            }
        }

        adapter.notifyDataSetChanged();
    }

    private void deleteAddress(final String addressId) {
        new MaterialDialog.Builder(context)
                .title("提示")
                .content("确认删除该地址？")
                .positiveText(context.getString(R.string.ok))
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        deleteAddressImpl(addressId);
                        dialog.dismiss();
                    }
                })
                .negativeText(context.getString(R.string.cancel))
                .onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        dialog.dismiss();
                    }
                }).show();


    }

    private void deleteAddressImpl(final String addressId) {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("service", "ReceiptAddress.delete");
        hashMap.put("userId", SharedPref.getString(Constants.USER_ID));
        hashMap.put("addressId", addressId);
        HttpsClient client = new HttpsClient();
        client.post(hashMap, new HttpResponseHandler() {
            @Override
            protected void onSuccess(int statusCode, JSONObject data) throws Exception {
                if (data.getInt("code") == 0) {
                    List<ItemAddress> list = adapter.getData();
                    for (ItemAddress item : list) {
                        if (addressId.equals(item.getAddressId())) {
                            list.remove(item);
                            adapter.notifyDataSetChanged();
                            return;
                        }
                    }
                }
            }

            @Override
            protected void onFailed(int statusCode, Exception e) {

            }
        });
    }

    private void selectDefault(final String addressId) {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("service", "ReceiptAddress.setDefault");
        hashMap.put("userId", SharedPref.getString(Constants.USER_ID));
        hashMap.put("addressId", addressId);
        HttpsClient client = new HttpsClient();
        client.post(hashMap, new HttpResponseHandler() {
            @Override
            protected void onSuccess(int statusCode, JSONObject data) throws Exception {
                if (data.getInt("code") == 0) {
                    List<ItemAddress> list = adapter.getData();
                    for (ItemAddress item : list) {
                        if (addressId.equals(item.getAddressId())) {
                            item.setIsDefault("1");
                        } else {
                            item.setIsDefault("0");
                        }
                    }
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            protected void onFailed(int statusCode, Exception e) {
                adapter.notifyDataSetChanged();
            }
        });

    }

    @Override
    public void attachView(ShoppingAddressContract.View view) {
        this.view = view;
    }

    @Override
    public void detachView() {
        this.view = null;
    }
}