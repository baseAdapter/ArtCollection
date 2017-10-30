package com.tsutsuku.artcollection.ui.exchange;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.tsutsuku.artcollection.R;
import com.tsutsuku.artcollection.common.Constants;
import com.tsutsuku.artcollection.http.HttpResponseHandler;
import com.tsutsuku.artcollection.http.HttpsClient;
import com.tsutsuku.artcollection.model.CountInfoBean;
import com.tsutsuku.artcollection.model.ExchangeBean;
import com.tsutsuku.artcollection.model.shopping.ItemAddress;
import com.tsutsuku.artcollection.ui.base.BaseActivity;
import com.tsutsuku.artcollection.ui.shoppingBase.ShoppingAddressActivity;
import com.tsutsuku.artcollection.utils.GlideImageLoader;
import com.tsutsuku.artcollection.utils.GsonUtils;
import com.tsutsuku.artcollection.utils.SharedPref;
import com.tsutsuku.artcollection.utils.ToastUtils;
import com.youth.banner.Banner;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.sharesdk.onekeyshare.OnekeyShare;

public class ExchangeProductDetailActivity extends BaseActivity {

    public static final String TAG = ExchangeProductDetailActivity.class.getSimpleName();

    @BindView(R.id.detailCollect)
    ImageView mDetailCollect;
    @BindView(R.id.detailShare)
    ImageView mDetailShare;
    @BindView(R.id.detailNameTv)
    TextView mDetailName;
    /**
     * 兑换产品所需的金币数
     **/
    @BindView(R.id.ExchangeCoin)
    TextView mDetailCoin;
    @BindView(R.id.minusBt)
    TextView minusBt;
    /**
     * 兑换的产品数量
     **/
    @BindView(R.id.numberBt)
    TextView mNumberBt;
    @BindView(R.id.plusBt)
    TextView mPlusBt;
    @BindView(R.id.back_Img)
    ImageView mBack;

    @BindView(R.id.webView)
    WebView mWebView;
    @BindView(R.id.bannerPager)
    Banner mBanner;
    @BindView(R.id.exchangeRight)
    Button mExchangeRight;

    @BindView(R.id.restCoins)
    TextView mRestCoins;

    private ExchangeBean mBean;

    private List<String> mList = new ArrayList<>();

    private int mTotalGold;//剩余金币
    private int need_gold;//需要金币
    private boolean coins;//金币不足标记
    private int num = 1;


    @Override
    public void setContentView() {
        setContentView(R.layout.activity_exchange_product_detail);
    }

    @Override
    public void initViews() {
        ButterKnife.bind(this);
        mBean = (ExchangeBean) getIntent().getSerializableExtra("ExchangeBean.data");

    }

    @Override
    public void initListeners() {

    }

    @Override
    public void initData() {

        mDetailName.setText(mBean.getName());
        need_gold = Integer.parseInt(mBean.getNeed_gold());
        mDetailCoin.setText(need_gold + "金币");
        mList.add(mBean.getCoverPhoto());

        mBanner.setImageLoader(new GlideImageLoader())
                .setImages(mList)
                .start();

        mWebView.getSettings().setJavaScriptEnabled(true);

        //支持播放
        mWebView.getSettings().setSupportMultipleWindows(true);
        mWebView.getSettings().setBuiltInZoomControls(true);


        //加载网页
        mWebView.loadUrl(mBean.getDetailUrl());

        //本地显示
        mWebView.setWebViewClient(new android.webkit.WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
        if (mBean.getIsCollection() == 0) {
            mDetailCollect.setImageResource(R.drawable.icon_collect);
        } else {
            mDetailCollect.setImageResource(R.drawable.icon_collected);
        }
        getData();

    }

    /**
     * 请求数据
     **/
    private void getData() {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("service", "User.getCountInfo");
        hashMap.put("userId", SharedPref.getString(Constants.USER_ID));
        HttpsClient client = new HttpsClient();
        client.post(hashMap, new HttpResponseHandler() {
            @Override
            protected void onSuccess(int statusCode, JSONObject data) throws Exception {
                if (data.getInt("code") == 0) {
                    mTotalGold = GsonUtils.parseJson(data.getString("info"), CountInfoBean.class).getGoldTotal();
                    if (mTotalGold < need_gold) {
                        mRestCoins.setText("金币不足");
                        coins = true;
                    } else {
                        num = 1;
                        mTotalGold -= need_gold;
                        mNumberBt.setText(num + "");
                        mRestCoins.setText("剩余:" + mTotalGold + "金币");
                    }
                }
            }

            @Override
            protected void onFailed(int statusCode, Exception e) {

            }
        });
    }

    @OnClick({R.id.back_Img,R.id.detailCollect, R.id.detailShare, R.id.minusBt, R.id.plusBt, R.id.exchangeRight})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back_Img:
                finish();
                break;
            case R.id.detailCollect:
                if (mBean.getIsCollection() == 0) {
                    addCollection();
                } else {
                    deleteCollection();
                }
                // getCollectionInfo();
                break;
            case R.id.detailShare:
                oneKeyShare();

                break;
            case R.id.minusBt:
                minusExchangeProduct();
                break;
            case R.id.plusBt:
                addExchangeProduct();
                break;
            case R.id.exchangeRight:
                if (coins) {
                    ToastUtils.showMessage("金币不足");
                    return;
                }
                getDefaultAddress();

                break;
            default:
                break;
        }

    }

    private void oneKeyShare() {
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();

        // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间等使用
        oks.setTitle("艺术收藏网");
        // text是分享文本，所有平台都需要这个字段
        oks.setText(mBean.getName());

        // url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl(mBean.getCoverPhoto());
        oks.setTitleUrl("http://c.hiphotos.baidu.com/image/pic/item/d000baa1cd11728be518a314c2fcc3cec3fd2cca.jpg");//QQ

        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        oks.setImageUrl("http://c.hiphotos.baidu.com/image/pic/item/d000baa1cd11728be518a314c2fcc3cec3fd2cca.jpg");//确保SDcard下面存在此张图片

        // 启动分享GUI
        oks.show(this);
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
            private ItemAddress itemAddress;

            @Override
            protected void onSuccess(int statusCode, JSONObject data) throws Exception {
                if (data.getInt("code") == 0) {
                    List<ItemAddress> list = GsonUtils.parseJsonArray(data.getString("list"), ItemAddress.class);
                    for (int i = 0; i < list.size(); i++) {
                        itemAddress = list.get(i);
                    }
                        if (list.size() == 0) {
                            dialogShow();
                        }else {
                            Intent intent = new Intent(getApplicationContext(), ExchangeStateActivity.class);
                            intent.putExtra("ExchangeBean.data", getIntent().getSerializableExtra("ExchangeBean.data"));
                            intent.putExtra("ExchangeNumber", num);
                            startActivity(intent);

                        }
                } else {
                    dialogShow();
                }
            }


            @Override
            protected void onFailed(int statusCode, Exception e) {

            }
        });

    }

    /**
     * 提醒框弹出
     **/
    private void dialogShow() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("去添加默认地址?");
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(getBaseContext(), ShoppingAddressActivity.class);
                startActivity(intent);
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }


    /**
     * 请求接口：Collection.add
     * 收藏商品
     ***/
    private void addCollection() {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("service", "Collections.add");
        hashMap.put("userId", SharedPref.getString(Constants.USER_ID));
        hashMap.put("ctype", "4");
        hashMap.put("pId", mBean.getId());

        HttpsClient client = new HttpsClient();
        client.post(hashMap, new HttpResponseHandler() {
            @Override
            protected void onSuccess(int statusCode, JSONObject data) throws Exception {
                if (data.getInt("code") == 0) {
                    mDetailCollect.setImageResource(R.drawable.icon_collected);
                    mBean.setIsCollection(1);
                }
            }

            @Override
            protected void onFailed(int statusCode, Exception e) {

            }
        });

    }

    /**
     * 请求接口：Collection.add
     * 收藏商品
     ***/
    private void deleteCollection() {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("service", "Collections.delete");
        hashMap.put("userId", SharedPref.getString(Constants.USER_ID));
        hashMap.put("ctype", "4");
        hashMap.put("pId", mBean.getId());

        HttpsClient client = new HttpsClient();
        client.post(hashMap, new HttpResponseHandler() {
            @Override
            protected void onSuccess(int statusCode, JSONObject data) throws Exception {
                if (data.getInt("code") == 0) {
                    mDetailCollect.setImageResource(R.drawable.icon_collect);
                    mBean.setIsCollection(0);
                }
            }

            @Override
            protected void onFailed(int statusCode, Exception e) {

            }
        });

    }

    /**
     * 点击添加兑换商品的数量
     **/
    private void addExchangeProduct() {
        if (mTotalGold < need_gold) {
            ToastUtils.showMessage("金币不足");
        } else {
            num += 1;
            mTotalGold -= need_gold;
        }
        mNumberBt.setText(num + "");
        mRestCoins.setText("剩余:" + mTotalGold + "金币");

    }

    /**
     * 点击减少兑换商品的数量
     **/
    private void minusExchangeProduct() {
        if (num > 1) {
            num -= 1;
            mTotalGold += need_gold;
        }
        mNumberBt.setText(num + "");
        mRestCoins.setText("剩余:" + mTotalGold + "金币");
    }


}
