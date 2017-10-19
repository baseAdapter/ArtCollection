package com.tsutsuku.artcollection.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;
import com.tsutsuku.artcollection.R;
import com.tsutsuku.artcollection.common.Constants;
import com.tsutsuku.artcollection.http.HttpResponseHandler;
import com.tsutsuku.artcollection.http.HttpsClient;
import com.tsutsuku.artcollection.model.CountInfoBean;
import com.tsutsuku.artcollection.ui.activity.MineActivitiesActivity;
import com.tsutsuku.artcollection.ui.auction.MineAuctionActivity;
import com.tsutsuku.artcollection.ui.base.BasePageFragment;
import com.tsutsuku.artcollection.ui.collection.MineCollectionActivity;
import com.tsutsuku.artcollection.ui.mine.MineCircleActivity;
import com.tsutsuku.artcollection.ui.mine.MineFansActivity;
import com.tsutsuku.artcollection.ui.mine.MineFollowActivity;
import com.tsutsuku.artcollection.ui.mine.MineInfoActivity;
import com.tsutsuku.artcollection.ui.mine.SettingActivity;
import com.tsutsuku.artcollection.ui.mine.VendorApplyActivity;
import com.tsutsuku.artcollection.ui.point.MinePointActivity;
import com.tsutsuku.artcollection.ui.shoppingBase.MineOrdersActivity;
import com.tsutsuku.artcollection.ui.shoppingBase.ShoppingCartActivity;
import com.tsutsuku.artcollection.ui.wallet.MineWalletActivity;
import com.tsutsuku.artcollection.utils.GsonUtils;
import com.tsutsuku.artcollection.utils.SharedPref;
import com.tsutsuku.artcollection.utils.glideTransform.CircleTransform;

import org.json.JSONObject;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * @Author Sun Renwei
 * @Create 2017/1/4
 * @Description Content
 */

public class MineFragment extends BasePageFragment {
    @BindView(R.id.ivAvatar)
    ImageView ivAvatar;
    @BindView(R.id.ivSetting)
    ImageView ivSetting;
    @BindView(R.id.tvNick)
    TextView tvNick;
    @BindView(R.id.tvPoint)
    TextView tvPoint;
    @BindView(R.id.flPoint)
    FrameLayout flPoint;
    @BindView(R.id.tvFans)
    TextView tvFans;
    @BindView(R.id.flFans)
    FrameLayout flFans;
    @BindView(R.id.tvThumb)
    TextView tvFollow;
    @BindView(R.id.flFollow)
    FrameLayout flFollow;
    @BindView(R.id.rlOrder)
    RelativeLayout rlOrder;
    @BindView(R.id.rlWaitingPayment)
    FrameLayout rlWaitingPayment;
    @BindView(R.id.rlWaitingDelivery)
    FrameLayout rlWaitingDelivery;
    @BindView(R.id.rlWaitingReceiving)
    FrameLayout rlWaitingReceiving;
    @BindView(R.id.rlWaitingComment)
    FrameLayout rlWaitingComment;
    @BindView(R.id.rlWallet)
    RelativeLayout rlWallet;
    @BindView(R.id.rlAuction)
    RelativeLayout rlAuction;
    @BindView(R.id.rlShoppingCart)
    RelativeLayout rlShoppingCart;
    @BindView(R.id.rlTreasure)
    RelativeLayout rlTreasure;
    @BindView(R.id.rlIdentify)
    RelativeLayout rlIdentify;
    @BindView(R.id.rlCollection)
    RelativeLayout rlCollection;
    @BindView(R.id.rlActivities)
    RelativeLayout rlActivities;
    @BindView(R.id.rlApply)
    RelativeLayout rlApply;
    @BindView(R.id.tvUnpay)
    TextView tvUnpay;
    @BindView(R.id.tvUnDelivery)
    TextView tvUnDelivery;
    @BindView(R.id.tvUnReceive)
    TextView tvUnReceive;
    @BindView(R.id.tvUnComment)
    TextView tvUnComment;
    @BindView(R.id.imageView)
    ImageView imageView;
    Unbinder unbinder;

    private DialogPlus applyDialog;


    @Override
    protected void fetchData() {
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_mine;
    }

    @Override
    protected void initViews() {
        ButterKnife.bind(this, rootView);
    }

    @Override
    protected void initListeners() {

    }

    @Override
    protected void initData() {
        initApplyDialog();
    }

    @Override
    public void onResume() {
        super.onResume();
        refresh();
    }

    private void initApplyDialog() {

        applyDialog = DialogPlus.newDialog(context)
                .setContentHolder(new ViewHolder(R.layout.dialog_apply))
                .setContentBackgroundResource(R.drawable.btn_white)
                .setContentWidth(ViewGroup.LayoutParams.WRAP_CONTENT)
                .setGravity(Gravity.CENTER)
                .create();
        applyDialog.getHolderView().findViewById(R.id.tvPerson).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                VendorApplyActivity.launchTypePerson(context);
                applyDialog.dismiss();
            }
        });
        applyDialog.getHolderView().findViewById(R.id.tvEnterprise).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                VendorApplyActivity.launchTypeEnterprise(context);
                applyDialog.dismiss();
            }
        });
    }

    @OnClick({R.id.rlIdentifyApply, R.id.rlArtApply, R.id.rlOrder, R.id.ivAvatar, R.id.ivSetting, R.id.flPoint, R.id.flFans, R.id.flFollow, R.id.rlWaitingPayment, R.id.rlWaitingDelivery, R.id.rlWaitingReceiving, R.id.rlWaitingComment, R.id.rlWallet, R.id.rlAuction, R.id.rlShoppingCart, R.id.rlTreasure, R.id.rlIdentify, R.id.rlCollection, R.id.rlActivities, R.id.rlApply})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ivAvatar:
                startActivity(new Intent(context, MineInfoActivity.class));
                break;
            case R.id.ivSetting:
                SettingActivity.launch(context);
                break;
            case R.id.flPoint:
                MinePointActivity.launch(context);
                break;
            case R.id.flFans:
                MineFansActivity.launch(context);
                break;
            case R.id.flFollow:
                MineFollowActivity.launch(context);
                break;
            case R.id.rlOrder:
                MineOrdersActivity.launch(context, 0);
                break;
            case R.id.rlWaitingPayment:
                MineOrdersActivity.launch(context, 1);
                break;
            case R.id.rlWaitingDelivery:
                MineOrdersActivity.launch(context, 2);
                break;
            case R.id.rlWaitingReceiving:
                MineOrdersActivity.launch(context, 3);
                break;
            case R.id.rlWaitingComment:
                MineOrdersActivity.launch(context, 4);
                break;
            case R.id.rlWallet:
                MineWalletActivity.launch(context);
                break;
            case R.id.rlAuction:
                MineAuctionActivity.launch(context);
                break;
            case R.id.rlShoppingCart:
                ShoppingCartActivity.launch(context);
                break;
            case R.id.rlTreasure:
                MineCircleActivity.launch(context, getString(R.string.mine_treasure), "0");
                break;
            case R.id.rlIdentify:
                MineCircleActivity.launch(context, getString(R.string.mine_identify), "1");
                break;
            case R.id.rlCollection:
                MineCollectionActivity.launch(context);
                break;
            case R.id.rlActivities:
                MineActivitiesActivity.launch(context);
                break;
            case R.id.rlApply:
                applyDialog.show();
                break;
            case R.id.rlIdentifyApply:
                VendorApplyActivity.launchTypeIdentify(context);
                break;
            case R.id.rlArtApply:
                VendorApplyActivity.launchTypeArt(context);
                break;
        }
    }

    private void refreshCount() {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("service", "User.getCountInfo");
        hashMap.put("userId", SharedPref.getString(Constants.USER_ID));
        HttpsClient client = new HttpsClient();
        client.post(hashMap, new HttpResponseHandler() {
            @Override
            protected void onSuccess(int statusCode, JSONObject data) throws Exception {
                if (data.getInt("code") == 0) {
                    setInfo(GsonUtils.parseJson(data.getString("info"), CountInfoBean.class));
                }
            }

            @Override
            protected void onFailed(int statusCode, Exception e) {

            }
        });
    }

    private void refresh() {
        Glide.with(context).load(SharedPref.getString(Constants.AVATAR)).placeholder(R.drawable.ic_default_avatar).transform(new CircleTransform(context)).into(ivAvatar);
        tvNick.setText(SharedPref.getString(Constants.NICK));
        refreshCount();
    }

    private void setInfo(CountInfoBean bean) {
        tvPoint.setText(bean.getIntegrateTotal());
        tvFans.setText(bean.getGoldTotal() + "");
        tvFollow.setText(bean.getFollowTotal());

        if (bean.getNeedPayCount() > 0) {
            tvUnpay.setVisibility(View.VISIBLE);
            tvUnpay.setTextSize(TypedValue.COMPLEX_UNIT_DIP, bean.getNeedPayCount() > 99 ? 8 : bean.getNeedPayCount() > 9 ? 10 : 12);
            tvUnpay.setText(String.valueOf(bean.getNeedPayCount()));
        } else {
            tvUnpay.setVisibility(View.GONE);
        }

        if (bean.getNeedSendCount() > 0) {
            tvUnDelivery.setVisibility(View.VISIBLE);
            tvUnDelivery.setTextSize(TypedValue.COMPLEX_UNIT_DIP, bean.getNeedSendCount() > 99 ? 8 : bean.getNeedSendCount() > 9 ? 10 : 12);
            tvUnDelivery.setText(String.valueOf(bean.getNeedSendCount()));
        } else {
            tvUnDelivery.setVisibility(View.GONE);
        }

        if (bean.getNeedRevCount() > 0) {
            tvUnReceive.setVisibility(View.VISIBLE);
            tvUnReceive.setTextSize(TypedValue.COMPLEX_UNIT_DIP, bean.getNeedRevCount() > 99 ? 8 : bean.getNeedRevCount() > 9 ? 10 : 12);
            tvUnReceive.setText(String.valueOf(bean.getNeedRevCount()));
        } else {
            tvUnReceive.setVisibility(View.GONE);
        }

        if (bean.getNeedEvaluateCount() > 0) {
            tvUnComment.setVisibility(View.VISIBLE);
            tvUnComment.setTextSize(TypedValue.COMPLEX_UNIT_DIP, bean.getNeedEvaluateCount() > 99 ? 8 : bean.getNeedEvaluateCount() > 9 ? 10 : 12);
            tvUnComment.setText(String.valueOf(bean.getNeedEvaluateCount()));
        } else {
            tvUnComment.setVisibility(View.GONE);
        }

    }
}
