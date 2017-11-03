package com.tsutsuku.artcollection.presenter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.tsutsuku.artcollection.common.Constants;
import com.tsutsuku.artcollection.contract.AuctionDetailContract;
import com.tsutsuku.artcollection.contract.base.AdapterItem;
import com.tsutsuku.artcollection.http.HttpResponseHandler;
import com.tsutsuku.artcollection.http.HttpsClient;
import com.tsutsuku.artcollection.im.ui.ChatActivity;
import com.tsutsuku.artcollection.model.shopping.ProductInfoBean;
import com.tsutsuku.artcollection.ui.auction.AuctionRecordAdapterItem;
import com.tsutsuku.artcollection.ui.auction.PayDepositActivity;
import com.tsutsuku.artcollection.ui.base.BaseRvAdapter;
import com.tsutsuku.artcollection.ui.login.LoginActivity;
import com.tsutsuku.artcollection.utils.GsonUtils;
import com.tsutsuku.artcollection.utils.ShareUtils;
import com.tsutsuku.artcollection.utils.SharedPref;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cn.sharesdk.onekeyshare.OnekeyShare;

/**
 * Created by sunrenwei on 2017/02/20
 */

public class AuctionDetailPresenterImpl implements AuctionDetailContract.Presenter {
    private static final int PAY_DEPOSIT = 0;

    private AuctionDetailContract.View view;
    private ProductInfoBean infoBean;
    private BaseRvAdapter adapter;
    private List<ProductInfoBean.AuctionRecodeBean> list;
    private ChatRoomRepository repository;

    private String productId;
    private Context context;
    private boolean collection;

    public AuctionDetailPresenterImpl(Context context) {
        this.context = context;
        list = new ArrayList<>();
    }

    @Override
    public void attachView(AuctionDetailContract.View view) {
        this.view = view;
    }

    @Override
    public void detachView() {
        this.view = null;
    }


    @Override
    public void getData(String productId) {
        this.productId = productId;

        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("service", "Product.getProductInfo");
        hashMap.put("productId", productId);
        hashMap.put("user_Id", SharedPref.getString(Constants.USER_ID));
        HttpsClient client = new HttpsClient();
        client.post(hashMap, new HttpResponseHandler() {
            @Override
            protected void onSuccess(int statusCode, JSONObject data) throws Exception {
                if (data.getInt("code") == 0) {
                    infoBean = GsonUtils.parseJson(data.getString("info"), ProductInfoBean.class);
                    view.setView(infoBean);
                    list.addAll(infoBean.getAuctionRecode());
                    adapter.notifyDataSetChanged();
                    collection = infoBean.getIsCollection() == 1;

                    if ("1".equals(infoBean.getAuctionInfo().getAuctionState())) {
                        repository = new ChatRoomRepository(infoBean.getChatRoom().getGroupId(), new ChatRoomRepository.CallBack() {
                            @Override
                            public void enterRoom() {

                            }

                            @Override
                            public void exitRoom() {

                            }
                        });
                        repository.enterRoom();
                    }


                }
            }

            @Override
            protected void onFailed(int statusCode, Exception e) {

            }
        });

    }

    @Override
    public void exitRoom() {
            if ("1".equals(infoBean.getAuctionInfo().getAuctionState())) {
            repository.exitRoom();
        }
    }

    @Override
    public BaseRvAdapter getAdapter() {
        if (adapter == null) {
            adapter = new BaseRvAdapter<ProductInfoBean.AuctionRecodeBean>(list) {

                @NonNull
                @Override
                public AdapterItem createItem(@NonNull Object type) {
                    return new AuctionRecordAdapterItem();
                }
            };
        }
        return adapter;
    }

    @Override
    public void addRecord(ProductInfoBean.AuctionRecodeBean bean) {
        list.add(0, bean);
        view.setRecordNum(list.size());
        adapter.notifyItemInserted(0);
    }

    @Override
    public void payDeposit(Activity activity) {
        if (TextUtils.isEmpty(SharedPref.getString(Constants.USER_ID))) {
            LoginActivity.launch(context);
        } else {
            PayDepositActivity.launch(activity, infoBean.getProductInfo(), infoBean.getAuctionInfo().getDeposit(), PAY_DEPOSIT);
        }
    }

    @Override
    public void parseResult(Intent intent) {
        view.setDepositPayed();
    }

    @Override
    public void share() {
//        ShareUtils.showShare(context, infoBean.getCover(), infoBean.getProductName(), SharedPref.getSysString(Constants.Share.AUCTION) + productId);
        oneKeyShare();
    }

    @Override
    public void chat() {
        ChatActivity.launch(context, infoBean.getFarm().getUsername(), "", infoBean.getFarm().getDisname(), infoBean.getFarm().getPhoto());
    }

    @Override
    public void collection() {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("service", collection ? "Collections.delete" : "Collections.add");
        hashMap.put("pId", productId);
        hashMap.put("ctype", "2");
        hashMap.put("userId", SharedPref.getString(Constants.USER_ID));
        HttpsClient client = new HttpsClient();
        client.post(hashMap, new HttpResponseHandler() {
            @Override
            protected void onSuccess(int statusCode, JSONObject data) throws Exception {
                if (data.getInt("code") == 0) {
                    collection = !collection;
                    view.setCollection(collection);
                }
            }

            @Override
            protected void onFailed(int statusCode, Exception e) {

            }
        });
    }
    private void oneKeyShare() {
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();

        // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间等使用
        oks.setTitle("艺术收藏网");
        // text是分享文本，所有平台都需要这个字段
        oks.setText(infoBean.getProductName());

        // url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl(infoBean.getFarmCover());
        oks.setTitleUrl(infoBean.getFarmCover());//QQ

        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        oks.setImageUrl(infoBean.getFarmCover());//确保SDcard下面存在此张图片

        // 启动分享GUI
        oks.show(context);
    }
}