package com.tsutsuku.artcollection.ui.shoppingBase;

import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.tsutsuku.artcollection.R;
import com.tsutsuku.artcollection.model.shopping.ItemGoods;
import com.tsutsuku.artcollection.ui.base.BaseAdapterItem;

import butterknife.BindView;

/**
 * @Author Tsutsuku
 * @Create 2017/2/11
 * @Description
 */

public class SSGoodsAdapterItem extends BaseAdapterItem<ItemGoods> {
    @BindView(R.id.cbCheck)
    CheckBox cbCheck;
    @BindView(R.id.ivGoods)
    ImageView ivGoods;
    @BindView(R.id.tvName)
    TextView tvName;
    @BindView(R.id.tvPrice)
    TextView tvPrice;
    @BindView(R.id.tvNum)
    TextView tvNum;
    @BindView(R.id.flGoods)
    FrameLayout flGoods;
    @BindView(R.id.flNormal)
    FrameLayout flNormal;
    @BindView(R.id.ivAdd)
    ImageView ivAdd;
    @BindView(R.id.ivRemove)
    ImageView ivRemove;
    @BindView(R.id.tvEditNum)
    TextView tvEditNum;
    @BindView(R.id.btnDelete)
    Button btnDelete;
    @BindView(R.id.flEdit)
    FrameLayout flEdit;

    @Override
    public void bindViews(View root) {
        super.bindViews(root);
        cbCheck.setVisibility(View.GONE);
    }

    @Override
    public int getLayoutResId() {
        return R.layout.item_goods;
    }

    @Override
    public void handleData(ItemGoods item, int position) {
        tvName.setText(item.getProductName());
        tvPrice.setText(item.getProductPrice());
        tvNum.setText(item.getProductAmount());
        Glide.with(context).load(item.getProductCover()).into(ivGoods);
    }
}
