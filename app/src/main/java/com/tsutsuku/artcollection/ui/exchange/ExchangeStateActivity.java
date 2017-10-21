package com.tsutsuku.artcollection.ui.exchange;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.tsutsuku.artcollection.R;
import com.tsutsuku.artcollection.common.Constants;
import com.tsutsuku.artcollection.common.Intents;
import com.tsutsuku.artcollection.http.HttpResponseHandler;
import com.tsutsuku.artcollection.http.HttpsClient;
import com.tsutsuku.artcollection.model.ExchangeBean;
import com.tsutsuku.artcollection.model.shopping.ItemAddress;
import com.tsutsuku.artcollection.ui.base.BaseActivity;
import com.tsutsuku.artcollection.ui.shoppingBase.ShoppingAddressActivity;
import com.tsutsuku.artcollection.utils.GsonUtils;
import com.tsutsuku.artcollection.utils.SharedPref;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ExchangeStateActivity extends BaseActivity {

    @BindView(R.id.submitOrder)
    Button mSubmitOrder;
    @BindView(R.id.rlExchangeAddress)
    RelativeLayout mRelativeAddress;
    @BindView(R.id.rlDelivery)
    RelativeLayout mRelativeDelivery;
    /**
     * 收货人的姓名
     **/
    @BindView(R.id.addressName)
    TextView mAddressName;
    /**
     * 收货人的电话
     **/
    @BindView(R.id.addressMobile)
    TextView mAddressMobile;
    /**
     * 收货人的地址
     **/
    @BindView(R.id.address)
    TextView mAddressTv;
    @BindView(R.id.icon_product)
    ImageView mIconProduct;
    /**
     * 兑换的产品名称
     **/
    @BindView(R.id.exchange_product_name)
    TextView mProductName;

    /**
     * 兑换产品所需要的金币数
     **/
    @BindView(R.id.product_need_coin)
    TextView mNeedCoin;

    /**
     * 兑换的产品数量
     **/
    @BindView(R.id.count)
    TextView mCount;
    @BindView(R.id.icon_arrow_right)
    ImageView mIconRight;

    /**
     * 兑换的数量
     **/
    @BindView(R.id.totalCountNumber)
    TextView mCountNumber;
    private ExchangeBean mBean;

    @BindView(R.id.totalCountCoin)
    TextView mTotalCoin;
    /**
     * 使用金币
     **/
    @BindView(R.id.userCoins)
    TextView mUserCoins;

    @BindView(R.id.delivery_way)
    TextView mDeliveryWay;

    private int price, num;

    /**
     * 地址entity
     **/
    private ItemAddress mItemAddress;

    /**
     * 配送entity
     **/
    private DeliveryBean mDeliveryBean;

    /**
     * 过渡值
     **/
    private String exchangeNumber;

    private Gson gson = new Gson();
    private Type type = new TypeToken<List<ItemAddress>>() {
    }.getType();


    @Override
    public void setContentView() {
        setContentView(R.layout.activity_exchange_state);
    }

    @Override
    public void initViews() {
        ButterKnife.bind(this);
        initTitle(R.string.sure_order);
        mBean = (ExchangeBean) getIntent().getSerializableExtra("ExchangeBean.data");


    }

    @Override
    public void initListeners() {


    }

    @Override
    public void initData() {
        mProductName.setText(mBean.getName());
        exchangeNumber = getIntent().getExtras().get("ExchangeNumber").toString();
        num = Integer.valueOf(exchangeNumber);
        price = Integer.valueOf(mBean.getNeed_gold());
        mCount.setText("x" + exchangeNumber);
        mCountNumber.setText("共" + num + "件商品");
        mTotalCoin.setText(num * price + "金币");
        mNeedCoin.setText(price + "金币");
        mUserCoins.setText(num * price + "金币");
        Glide.with(this).load(mBean.getCoverPhoto()).into(mIconProduct);
        getDefaultAddress();
        getDeliveryWay();
    }


    @OnClick({R.id.rlExchangeAddress, R.id.rlDelivery, R.id.submitOrder})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rlExchangeAddress:
                //getDefaultAddress();
                ShoppingAddressActivity.launchTypeView(this, 2);
                //startActivity(new Intent(this,ShoppingAddressActivity.class));
                break;
            case R.id.rlDelivery:
                getDeliveryWay();
                break;
            case R.id.submitOrder:
                checkUserOrder();
                break;
            default:
                break;
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case 2:
                    ItemAddress item = data.getParcelableExtra(Intents.ADDRESS);
                    setAddressData(item);
                    break;
            }
        }
    }

    /**
     * 请求接口:
     * 判断采用哪种方式配送
     **/
    private void getDeliveryWay() {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("service", "Gold.getMydeliveryType");
        HttpsClient client = new HttpsClient();
        client.post(hashMap, new HttpResponseHandler() {
            @Override
            protected void onSuccess(int statusCode, JSONObject data) throws Exception {
                if (data.getInt("code") == 0) {
                    List<DeliveryBean> deliveryBeanList = GsonUtils.parseJsonArray(data.getString("list"), DeliveryBean.class);
                    for (int i = 0; i < deliveryBeanList.size(); i++) {
                        mDeliveryBean = deliveryBeanList.get(i);
                        switch (mDeliveryBean.getDeliveryId()) {
                            case 1:
                                mDeliveryWay.setText(mDeliveryBean.getDeliveryName());
                                break;
                            case 2:
                                mDeliveryWay.setText(mDeliveryBean.getDeliveryName());
                                break;
                            case 3:
                                mDeliveryWay.setText(mDeliveryBean.getDeliveryName());
                                break;
                        }
                    }

                }
            }

            @Override
            protected void onFailed(int statusCode, Exception e) {

            }
        });
    }

    /**
     * 请求接口:checkUserOrder
     * 判断是否兑换成功
     **/
    private void checkUserOrder() {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("service", "Gold.checkUserOrder");
        hashMap.put("user_id", SharedPref.getString(Constants.USER_ID));
        hashMap.put("product_id", mBean.getId());
        hashMap.put("product_nums", exchangeNumber);
        hashMap.put("gold_nums", String.valueOf(num * price));
        hashMap.put("address_id", mItemAddress.getAddressId());
        hashMap.put("delivery_type", String.valueOf(mDeliveryBean.getDeliveryId()));

        HttpsClient client = new HttpsClient();
        client.post(hashMap, new HttpResponseHandler() {
            @Override
            protected void onSuccess(int statusCode, JSONObject data) throws Exception {
                if (data.getInt("code") == 0) {
                    finish();
                    startActivity(new Intent(ExchangeStateActivity.this, ExchangeSuccessActivity.class));
                }
            }

            @Override
            protected void onFailed(int statusCode, Exception e) {

            }
        });

    }

    /**
     * 请求接口：address.get
     * 根据默认地址的变化随时更新数据
     **/
    private void getDefaultAddress() {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("service", "ReceiptAddress.get");
        hashMap.put("userId", SharedPref.getString(Constants.USER_ID));
        HttpsClient client = new HttpsClient();
        client.post(hashMap, new HttpResponseHandler() {
            @Override
            protected void onSuccess(int statusCode, JSONObject data) throws Exception {
                if (data.getInt("code") == 0) {
                    List<ItemAddress> list = GsonUtils.parseJsonArray(data.getString("list"), ItemAddress.class);
                    for (int i = 0; i < list.size(); i++) {
                        mItemAddress = list.get(i);
                        if (Integer.parseInt(mItemAddress.getIsDefault()) == 1) {
                            setAddressData(mItemAddress);
                        }
                    }
                }
            }

            @Override
            protected void onFailed(int statusCode, Exception e) {

            }
        });

    }


    /**
     * @param itemAddress 设置默认的地址信息
     **/

    private void setAddressData(ItemAddress itemAddress) {
        mAddressName.setText("收货人: " + itemAddress.getConsigneeName());
        mAddressMobile.setText(itemAddress.getContactNumber());
        mAddressTv.setText("收货地址： " + itemAddress.getProvince() + itemAddress.getCity() + itemAddress.getCounty());
    }
}
