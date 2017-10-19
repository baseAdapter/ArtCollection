package com.tsutsuku.artcollection.ui.point;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;
import com.tsutsuku.artcollection.R;
import com.tsutsuku.artcollection.model.point.ItemPoint;
import com.tsutsuku.artcollection.model.shopping.ItemGoods;
import com.tsutsuku.artcollection.model.shopping.ItemVendor;
import com.tsutsuku.artcollection.ui.shoppingBase.ShoppingSettleActivity;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @Author Tsutsuku
 * @Create 2017/3/28
 * @Description
 */

public class PointExchangeDialog {
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

    private DialogPlus dialog;
    private Context context;
    private ItemPoint item;
    private int num = 1;

    public PointExchangeDialog(Context context, ItemPoint item) {
        this.context = context;
        this.item = item;
        init();
    }

    public void show(){
        dialog.show();
    }

    private void init() {
        dialog = DialogPlus.newDialog(context)
                .setContentHolder(new ViewHolder(R.layout.dialog_point_exchange))
                .setGravity(Gravity.BOTTOM)
                .setContentBackgroundResource(R.color.transparent)
                .setCancelable(true)
                .create();

        ButterKnife.bind(this, dialog.getHolderView());

        Glide.with(context).load(item.getCoverPhoto()).into(ivPic);
        tvCurPrice.setText(item.getName());
        tvStepPrice.setText(item.getBrief());
        tvEditNum.setText("1");

    }

    @OnClick({R.id.btnExchange, R.id.ivAdd, R.id.ivRemove, R.id.ivClose})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnExchange:
                exchange();
                break;
            case R.id.ivAdd: {
                if (!item.getInventory().equals(String.valueOf(num))) {
                    num ++;
                    tvEditNum.setText(String.valueOf(num));
                }
            }
            break;
            case R.id.ivRemove: {
                if (num > 1) {
                    num --;
                    tvEditNum.setText(String.valueOf(num));
                }
            }
            break;
            case R.id.ivClose:{
                dialog.dismiss();
            }
            break;
        }
    }

    private void exchange(){
        final ItemVendor vendor = new ItemVendor();
        vendor.setFarmId("0");
        vendor.setFarmName("积分兑换");
        final ItemGoods goods = new ItemGoods();
        goods.setProductAmount("1");
        goods.setProductCover(item.getCoverPhoto());
        goods.setProductId(item.getId());
        goods.setProductPrice(item.getIntegrate());
        goods.setProductAmount(String.valueOf(num));
        goods.setProductName(item.getName());
        goods.setProductBrief(item.getBrief());
        vendor.setProducts(new ArrayList<ItemGoods>() {{
            add(goods);
        }});
        ShoppingSettleActivity.launch(context, "4", new ArrayList<ItemVendor>() {{
            add(vendor);
        }});
    }
}
