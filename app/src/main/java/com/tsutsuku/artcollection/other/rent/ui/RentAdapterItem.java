package com.tsutsuku.artcollection.other.rent.ui;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.tsutsuku.artcollection.R;
import com.tsutsuku.artcollection.model.ItemProduct;
import com.tsutsuku.artcollection.ui.base.BaseAdapterItem;
import com.tsutsuku.artcollection.utils.DensityUtils;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @Author Sun Renwei
 * @Create 2017/1/23
 * @Description Content
 */

public class RentAdapterItem extends BaseAdapterItem<ItemProduct> {
    @BindView(R.id.tvName)
    TextView tvName;
    @BindView(R.id.tvPrice)
    TextView tvPrice;
    @BindView(R.id.ivPic)
    ImageView ivPic;

    public RentAdapterItem(Context context) {
        this.context = context;
    }

    @Override
    public void bindViews(View root) {
        super.bindViews(root);
        ivPic.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (DensityUtils.getDisplayWidth() - DensityUtils.dp2px(3)) / 2));
    }

    @Override
    public int getLayoutResId() {
        return R.layout.item_product_rent;
    }

    @Override
    public void handleData(ItemProduct item, int position) {
        Glide.with(context).load(item.getPic()).into(ivPic);
        tvName.setText(item.getProductName());
        tvPrice.setText("¥" + item.getTotalPrice() + item.getPriceUnit());
    }

    @OnClick(R.id.cvProduct)
    public void onClick() {
        RentDetailActivity.launch(context, curItem.getProductId());
    }
}
