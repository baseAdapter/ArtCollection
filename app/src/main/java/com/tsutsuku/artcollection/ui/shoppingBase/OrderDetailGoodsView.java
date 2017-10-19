package com.tsutsuku.artcollection.ui.shoppingBase;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.tsutsuku.artcollection.R;
import com.tsutsuku.artcollection.model.shopping.OrderInfo;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @Author Tsutsuku
 * @Create 2017/3/16
 * @Description
 */

public class OrderDetailGoodsView {
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
    @BindView(R.id.flGoods)
    FrameLayout flGoods;
    private Context context;

    public OrderDetailGoodsView(Context context) {
        this.context = context;
    }

    public View getView(OrderInfo.ItemsBean item) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_goods, null);
        ButterKnife.bind(this, view);
        cbCheck.setVisibility(View.GONE);

        tvName.setText(item.getItemTitle());
        tvPrice.setText(item.getUnitPrice());
        tvNum.setText("x" + item.getBuyAmount());
        Glide.with(context).load(item.getItemCover()).into(ivGoods);

        return view;
    }
}
