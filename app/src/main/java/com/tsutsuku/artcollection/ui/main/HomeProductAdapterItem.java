package com.tsutsuku.artcollection.ui.main;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.tsutsuku.artcollection.R;
import com.tsutsuku.artcollection.model.ItemProduct;
import com.tsutsuku.artcollection.ui.base.BaseAdapterItem;
import com.tsutsuku.artcollection.ui.product.ProductDetailActivity;
import com.tsutsuku.artcollection.utils.DensityUtils;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @Author Sun Renwei
 * @Create 2017/1/23
 * @Description Content
 */

public class HomeProductAdapterItem extends BaseAdapterItem<ItemProduct> {
    @BindView(R.id.tvName)
    TextView tvName;
    @BindView(R.id.tvPrice)
    TextView tvPrice;
    @BindView(R.id.ivPic)
    ImageView ivPic;
    @BindView(R.id.cvProduct)
    CardView cvProduct;
    private int type;

    public HomeProductAdapterItem(Context context) {
        this.context = context;
    }

    @Override
    public void bindViews(View root) {
        super.bindViews(root);
        ivPic.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (DensityUtils.getDisplayWidth() - DensityUtils.dp2px(3)) / 2));
    }

    @Override
    public int getLayoutResId() {
        return R.layout.item_home_product_goods;
    }

    @Override
    public void handleData(ItemProduct item, int position) {
        Glide.with(context).load(item.getPic()).into(ivPic);
        tvName.setText(item.getProductName());
        tvPrice.setText("¥" + item.getTotalPrice());
    }

    @OnClick(R.id.cvProduct)
    public void onClick() {
        ProductDetailActivity.launch(context, curItem.getProductId());
    }
}
