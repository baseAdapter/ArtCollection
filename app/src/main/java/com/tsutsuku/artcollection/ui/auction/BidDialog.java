package com.tsutsuku.artcollection.ui.auction;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;
import com.tsutsuku.artcollection.R;
import com.tsutsuku.artcollection.common.Constants;
import com.tsutsuku.artcollection.http.HttpResponseHandler;
import com.tsutsuku.artcollection.http.HttpsClient;
import com.tsutsuku.artcollection.model.shopping.ProductInfoBean;
import com.tsutsuku.artcollection.utils.Arith;
import com.tsutsuku.artcollection.utils.DensityUtils;
import com.tsutsuku.artcollection.utils.SharedPref;

import org.json.JSONObject;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @Author Sun Renwei
 * @Create 2017/2/22
 * @Description Content
 */

public class BidDialog {

    @BindView(R.id.btnExchange)
    Button btnBid;
    @BindView(R.id.tvCurPrice)
    TextView tvCurPrice;
    @BindView(R.id.tvStepPrice)
    TextView tvStepPrice;
    @BindView(R.id.ivAdd)
    ImageView ivAdd;
    @BindView(R.id.ivRemove)
    ImageView ivRemove;
    @BindView(R.id.tvEditNum)
    TextView tvEditNum;
    @BindView(R.id.flEdit)
    FrameLayout flEdit;
    @BindView(R.id.ivClose)
    ImageView ivClose;
    @BindView(R.id.ivPic)
    ImageView ivPic;
    private Context context;

    private DialogPlus bidDialog;
    private ProductInfoBean infoBean;
    private String bidPrice;
    private String curPrice;

    public BidDialog(Context context, ProductInfoBean infoBean) {
        this.context = context;
        this.infoBean = infoBean;
        initBidDialog();
    }

    private void initBidDialog() {

        View view = LayoutInflater.from(context).inflate(R.layout.dialog_bid, null);
        bidDialog = DialogPlus.newDialog(context)
                .setContentHolder(new ViewHolder(view))
                .setGravity(Gravity.BOTTOM)
                .setContentBackgroundResource(R.color.transparent)
                .setCancelable(true)
                .create();

        ButterKnife.bind(this, view);

        Glide.with(context).load(infoBean.getCover()).into(ivPic);
        setPrice(infoBean.getAuctionInfo().getCurrentPrice());
        tvStepPrice.setText("加价幅度 ¥" + infoBean.getAuctionInfo().getAddPrice());
        tvEditNum.setText(curPrice);
        bidPrice = Arith.add(curPrice, infoBean.getAuctionInfo().getAddPrice());

        Drawable icon = context.getResources().getDrawable(R.drawable.ic_remove_black_24dp);
        Drawable tintIcon = DrawableCompat.wrap(icon);
        DrawableCompat.setTintList(tintIcon, ColorStateList.valueOf(context.getResources().getColor(R.color.d)));
        ivRemove.setImageDrawable(tintIcon);
    }

    @OnClick({R.id.ivAdd, R.id.ivRemove, R.id.ivClose, R.id.btnExchange})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ivAdd: {
                bidPrice = Arith.add(bidPrice, infoBean.getAuctionInfo().getAddPrice());
                tvEditNum.setText(bidPrice);
                if (bidPrice.equals(Arith.add(Arith.add(curPrice, infoBean.getAuctionInfo().getAddPrice()), infoBean.getAuctionInfo().getAddPrice()))) {
                    Drawable icon = context.getResources().getDrawable(R.drawable.ic_remove_black_24dp);
                    Drawable tintIcon = DrawableCompat.wrap(icon);
                    DrawableCompat.setTintList(tintIcon, ColorStateList.valueOf(context.getResources().getColor(R.color.orange)));
                    ivRemove.setImageDrawable(tintIcon);
                }
            }
            break;
            case R.id.ivRemove:
                if (!bidPrice.equals(Arith.add(curPrice, infoBean.getAuctionInfo().getAddPrice()))) {
                    bidPrice = Arith.sub(bidPrice, infoBean.getAuctionInfo().getAddPrice());
                    tvEditNum.setText(bidPrice);
                    if (bidPrice.equals(Arith.add(curPrice, infoBean.getAuctionInfo().getAddPrice()))) {
                        Drawable icon = context.getResources().getDrawable(R.drawable.ic_remove_black_24dp);
                        Drawable tintIcon = DrawableCompat.wrap(icon);
                        DrawableCompat.setTintList(tintIcon, ColorStateList.valueOf(context.getResources().getColor(R.color.d)));
                        ivRemove.setImageDrawable(tintIcon);
                    }
                }
                break;
            case R.id.ivClose:
                bidDialog.dismiss();
                break;
            case R.id.btnExchange:
                bid();
                break;
        }
    }

    public void show() {

        bidDialog.show();
    }

    public void setPrice(String price) {
        curPrice = price;

        SpannableStringBuilder builder = new SpannableStringBuilder();
        builder.append("当前价 ¥" + price);
        builder.setSpan(new AbsoluteSizeSpan(DensityUtils.dp2px(18)), builder.length() - price.length(), builder.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        tvCurPrice.setText(builder);
        tvEditNum.setText(Arith.add(curPrice, infoBean.getAuctionInfo().getAddPrice()));
        bidPrice = Arith.add(curPrice, infoBean.getAuctionInfo().getAddPrice());
    }

    private void bid() {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("service", "Product.addAuctionRecode");
        hashMap.put("productId", infoBean.getProductId());
        hashMap.put("userId", SharedPref.getString(Constants.USER_ID));
        hashMap.put("currentPrice", curPrice);
        hashMap.put("addPrice", Arith.sub(bidPrice, curPrice));
        HttpsClient client = new HttpsClient();
        client.post(hashMap, new HttpResponseHandler() {
            @Override
            protected void onSuccess(int statusCode, JSONObject data) throws Exception {
                if (data.getInt("code") == 0) {
                    bidDialog.dismiss();
                }
            }

            @Override
            protected void onFailed(int statusCode, Exception e) {

            }
        });

    }
}
