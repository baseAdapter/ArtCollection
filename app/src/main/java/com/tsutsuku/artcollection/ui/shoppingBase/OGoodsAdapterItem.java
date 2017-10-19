package com.tsutsuku.artcollection.ui.shoppingBase;

import android.content.Context;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.tsutsuku.artcollection.R;
import com.tsutsuku.artcollection.contract.base.OnItemSimpleClickListener;
import com.tsutsuku.artcollection.model.shopping.ItemOrder;
import com.tsutsuku.artcollection.ui.base.BaseAdapterItem;
import com.tsutsuku.artcollection.utils.Arith;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @Author Sun Renwei
 * @Create 2017/2/14
 * @Description Content
 */

public class OGoodsAdapterItem extends BaseAdapterItem<ItemOrder.ItemsBean> {
    @BindView(R.id.ivCover)
    ImageView ivCover;
    @BindView(R.id.tvName)
    TextView tvName;
    @BindView(R.id.tvDesc)
    TextView tvDesc;
    @BindView(R.id.tvPrice)
    TextView tvPrice;
    @BindView(R.id.tvNum)
    TextView tvNum;
    @BindView(R.id.flGoods)
    LinearLayout flGoods;

    private OnItemSimpleClickListener listener;

    public OGoodsAdapterItem(Context context, OnItemSimpleClickListener listener) {
        this.listener = listener;
        this.context = context;
    }

    @Override
    public int getLayoutResId() {
        return R.layout.item_order_goods;
    }

    @Override
    public void handleData(ItemOrder.ItemsBean item, int position) {
        Glide.with(context).load(item.getItemCover()).into(ivCover);
        tvName.setText(item.getItemTitle());
        tvDesc.setText(item.getItemBrief());
        tvPrice.setText("¥" + Arith.mul(item.getBuyAmount(), item.getUnitPrice()));
        tvNum.setText("x" + item.getBuyAmount());
    }

    @OnClick(R.id.flGoods)
    public void onClick() {
        listener.onItemClick(curItem);
    }
}
