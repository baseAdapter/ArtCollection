package com.tsutsuku.artcollection.presenter.shopping;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.tencent.mm.sdk.modelpay.PayReq;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.tsutsuku.artcollection.alipay.ALiPayUtils;
import com.tsutsuku.artcollection.common.BusEvent;
import com.tsutsuku.artcollection.common.Constants;
import com.tsutsuku.artcollection.contract.base.AdapterItem;
import com.tsutsuku.artcollection.contract.base.OnItemSimpleClickListener;
import com.tsutsuku.artcollection.contract.shopping.ShoppingSettleContract;
import com.tsutsuku.artcollection.http.HttpResponseHandler;
import com.tsutsuku.artcollection.http.HttpsClient;
import com.tsutsuku.artcollection.model.shopping.ItemAddress;
import com.tsutsuku.artcollection.model.shopping.ItemGoods;
import com.tsutsuku.artcollection.model.shopping.ItemVendor;
import com.tsutsuku.artcollection.ui.base.BaseRvAdapter;
import com.tsutsuku.artcollection.ui.shoppingBase.DeliveryDialog;
import com.tsutsuku.artcollection.ui.shoppingBase.SSGoodsAdapterItem;
import com.tsutsuku.artcollection.ui.shoppingBase.SSInfoAdapterItem;
import com.tsutsuku.artcollection.ui.shoppingBase.SSVendorAdapterItem;
import com.tsutsuku.artcollection.ui.shoppingBase.SettleResultActivity;
import com.tsutsuku.artcollection.utils.Arith;
import com.tsutsuku.artcollection.utils.GsonUtils;
import com.tsutsuku.artcollection.utils.RxBus;
import com.tsutsuku.artcollection.utils.SharedPref;
import com.tsutsuku.artcollection.wxapi.WXRxBus;
import com.tsutsuku.artcollection.wxapi.WxPayConstants;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by sunrenwei on 2017/02/09
 */

public class ShoppingSettlePresenterImpl implements ShoppingSettleContract.Presenter {
    public static final String TAG = ShoppingSettlePresenterImpl.class.getSimpleName();

    ShoppingSettleContract.View view;
    private List<ItemVendor> dataList;
    private List<Object> viewList;
    private String balance;
    private String price;
    private Context context;

    private BaseRvAdapter adapter;
    private ItemAddress address;

    private DeliveryDialog deliveryDialog;

    public ShoppingSettlePresenterImpl(Context context, ArrayList<ItemVendor> dataList) {
        this.dataList = dataList;
        this.context = context;
        viewList = new ArrayList<>();

    }

    @Override
    public String getAddressId() {
        return address == null ? null : address.getAddressId();
    }

    @Override
    public void setAddress(ItemAddress address) {
        this.address = address;
    }

    /**
     * example productsInfo : {"86000001":[{"productId":1,"buyAmount":2},{"productId":2,"buyAmount":3}]}
     */
    public void getData(final List<ItemVendor> dataList) {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("service", "Order.getDeliveryAndCouponInfo");
        hashMap.put("userId", SharedPref.getString(Constants.USER_ID));
        hashMap.put("addressId", "0");
        hashMap.put("productsInfo", getProductsInfo(dataList));
        HttpsClient client = new HttpsClient();
        client.post(hashMap, new HttpResponseHandler() {
            @Override
            protected void onSuccess(int statusCode, JSONObject data) throws Exception {
                if (data.getInt("code") == 0) {
                    JSONObject info = data.getJSONObject("info");

                    address = GsonUtils.parseJson(info.getString("defaultAddress"), ItemAddress.class);
                    if (address.getAddressId() == null) {
                        view.showAddressEdit(true);
                    } else {
                        view.setAddress(address);
                    }

                    balance = info.getString("cashBalance");
                    price = "0";
                    for (ItemVendor vendor : dataList) {
                        vendor.setInfoBean(GsonUtils.parseJson(info.getString(vendor.getFarmId()), ItemVendor.InfoBean.class));
                        vendor.getInfoBean().setBean(vendor.getInfoBean().getDelivery().get(0));
                        vendor.getInfoBean().getDelivery().get(0).setCheck(true);

                        viewList.add(vendor);
                        String tempPrice = price;
                        String tempNum = "0";
                        for (ItemGoods goods : vendor.getProducts()) {
                            price = Arith.add(price, Arith.mul(goods.getProductAmount(), goods.getProductPrice()));
                            tempNum = Arith.add(tempNum, goods.getProductAmount());
                            viewList.add(goods);
                        }

                        vendor.getInfoBean().setTotalNum(tempNum);
                        vendor.getInfoBean().setTotalPrice(Arith.sub(price, tempPrice));
                        viewList.add(vendor.getInfoBean());
                    }

                    adapter.notifyDataSetChanged();
                    view.setPrice(price);
                }
            }

            @Override
            protected void onFailed(int statusCode, Exception e) {

            }
        });
    }

    private String getProductsInfo(List<ItemVendor> dataList) {
        JSONObject goodsInfo = new JSONObject();
        try {
            for (ItemVendor vendor : dataList) {
                JSONArray vendorArray = new JSONArray();
                for (ItemGoods goods : vendor.getProducts()) {
                    vendorArray.put(new JSONObject()
                            .put("productId", goods.getProductId())
                            .put("buyAmount", goods.getProductAmount()));
                }

                if (vendorArray.length() > 0) {
                    goodsInfo.put(vendor.getFarmId(), vendorArray);
                }
            }
        } catch (Exception e) {
            Log.e("shopping cart", "settle error" + e);
        }
        return goodsInfo.toString();
    }

    private String getSubmitInfo(List<ItemVendor> dataList) {
        JSONObject goodsInfo = new JSONObject();
        try {
            for (ItemVendor vendor : dataList) {
                JSONObject goodsObject = new JSONObject();

                goodsObject.put("couponId", vendor.getInfoBean().getCouponId());
                goodsObject.put("deliveryType", vendor.getInfoBean().getBean().getType());
                goodsObject.put("deliveryFee", vendor.getInfoBean().getBean().getFee());
                goodsObject.put("userNote", vendor.getInfoBean().getUserNote());
                goodsObject.put("isUseIntegral", vendor.getInfoBean().isUsePoint() ? "1" : "0");

                JSONArray items = new JSONArray();
                for (ItemGoods goods : vendor.getProducts()) {
                    items.put(new JSONObject()
                            .put("productId", goods.getProductId())
                            .put("buyAmount", goods.getProductAmount()));
                }
                goodsObject.put("items", items);
                goodsInfo.put(vendor.getFarmId(), goodsObject);
            }


        } catch (Exception e) {
            Log.e("submit info", "settle error" + e);
        }
        return goodsInfo.toString();
    }


    @Override
    public void submit(String payType) {
        submit(payType, "0");
    }

    /**
     * 提交订单
     * productsInfo example:
     */
    public void submit(final String payType, String type) {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("service", "Order.payForProducts");
        hashMap.put("userId", SharedPref.getString(Constants.USER_ID));
        hashMap.put("addressId", address.getAddressId());
        hashMap.put("payType", type);
        hashMap.put("productsInfo", getSubmitInfo(dataList));
        if (!"4".equals(type)) {
            hashMap.put("otherType", payType);
            hashMap.put("otherFee", "balance".equals(payType) ? "0" : price);
        }
        HttpsClient client = new HttpsClient();
        client.post(hashMap, new HttpResponseHandler() {
            @Override
            protected void onSuccess(int statusCode, JSONObject data) throws Exception {
                if (data.getInt("code") == 0) {
                    RxBus.getDefault().post(BusEvent.DEAL_FINISH, "");

                    switch (payType) {
                        case "alipay": {
                            JSONObject jo = data.getJSONObject("info");
                            JSONObject tradeNo = jo.getJSONObject("tradeNo");
                            {
                                new ALiPayUtils(tradeNo.getString("parmsstr"), tradeNo.getString("sign"), context, new ALiPayUtils.PaySucceed() {
                                    @Override
                                    public void onSuccess() {
                                        SettleResultActivity.launch(context, address.getConsigneeName(), address.getAddress());
                                        view.dealFinish();
                                    }
                                }, new ALiPayUtils.PayAffirm() {
                                    @Override
                                    public void onAffirm() {
                                        SettleResultActivity.launch(context);
                                        view.dealFinish();
                                    }
                                }, new ALiPayUtils.PayDefeated() {
                                    @Override
                                    public void onDefeated() {
                                        SettleResultActivity.launch(context);
                                        view.dealFinish();
                                    }
                                }).submitSign();
                            }
                        }
                        break;
                        case "wxpay": {
                            JSONObject jo = data.getJSONObject("info");
                            JSONObject tradeNojo = jo.getJSONObject("tradeNo");

                            PayReq req = new PayReq();
                            IWXAPI api;
                            api = WXAPIFactory.createWXAPI(context, null);
                            // 将该app注册到微信
                            api.registerApp(WxPayConstants.WXAPP_ID);
                            if (api.openWXApp()) {
                                req.appId = tradeNojo.getString("appid");
                                req.partnerId = tradeNojo.getString("partnerid");
                                req.prepayId = tradeNojo.getString("prepayid");
                                req.packageValue = tradeNojo.getString("package");
                                req.nonceStr = tradeNojo.getString("noncestr");
                                req.timeStamp = tradeNojo.getString("timestamp");
                                req.sign = tradeNojo.getString("sign");
                                // genPayReq();
                                api.sendReq(req);
                                Log.e(TAG, "onSuccess() returned: " + req);
                        }

                            new WXRxBus() {
                                @Override
                                public void cancel() {
                                    SettleResultActivity.launch(context);
                                }

                                @Override
                                public void fail() {
                                    SettleResultActivity.launch(context);
                                }

                                @Override
                                public void success() {
                                    SettleResultActivity.launch(context, address.getConsigneeName(), address.getAddress());
                                }

                                @Override
                                public void finish() {
                                    view.dealFinish();
                                }
                            };
                        }
                        break;
//                        case "balance": {
//                            SettleResultActivity.launch(context, address.getConsigneeName(), address.getAddress());
//                            view.dealFinish();
//                        }
//                        break;
                        default:
                            break;
                    }


                }
            }

            @Override
            protected void onFailed(int statusCode, Exception e) {

            }
        });
    }

    @Override
    public BaseRvAdapter getAdapter() {
        if (adapter == null) {
            adapter = new BaseRvAdapter<Object>(viewList) {
                @Override
                public Object getItemType(Object o) {
                    if (o instanceof ItemVendor) {
                        return "vendor";
                    } else if (o instanceof ItemGoods) {
                        return "goods";
                    } else {
                        return "info";
                    }
                }

                @NonNull
                @Override
                public AdapterItem createItem(@NonNull Object type) {
                    switch ((String) type) {
                        case "vendor": {
                            return new SSVendorAdapterItem();
                        }
                        case "goods": {
                            return new SSGoodsAdapterItem();
                        }
                        default: {
                            return new SSInfoAdapterItem(new SSInfoAdapterItem.SSInfoClick<ItemVendor.InfoBean>() {
                                @Override
                                public void onDeliveryClick(ItemVendor.InfoBean item) {
                                    deliveryDialog.showDialog(item);
                                }

                                @Override
                                public void onPointClick(int position, String point) {
                                    price = Arith.add(price, point);
                                    view.setPrice(price);
                                    adapter.notifyItemChanged(position, "1");
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
    public void attachView(ShoppingSettleContract.View view) {
        this.view = view;
        getData(dataList);
        deliveryDialog = new DeliveryDialog(view.getContext(), new OnItemSimpleClickListener<ItemVendor.InfoBean>() {
            @Override
            public void onItemClick(ItemVendor.InfoBean item) {
                adapter.notifyItemChanged(viewList.indexOf(item), "1");
            }
        });
    }

    @Override
    public void detachView() {
        this.view = null;
    }


}