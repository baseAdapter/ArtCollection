package com.tsutsuku.artcollection.ui.shopping;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.tsutsuku.artcollection.R;
import com.tsutsuku.artcollection.contract.base.OnItemSimpleClickListener;
import com.tsutsuku.artcollection.model.ItemVendor;
import com.tsutsuku.artcollection.ui.base.BaseAdapterItem;
import com.tsutsuku.artcollection.utils.DensityUtils;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @Author Sun Renwei
 * @Create 2017/3/1
 * @Description Content
 */

public class VendorAdapterItem extends BaseAdapterItem<ItemVendor> {
    @BindView(R.id.tvName)
    TextView tvName;
    @BindView(R.id.tvPrice)
    TextView tvPrice;
    @BindView(R.id.tvScore)
    TextView tvScore;
    @BindView(R.id.ivPic)
    ImageView ivPic;
    @BindView(R.id.cvProduct)
    CardView cvProduct;

    private OnItemSimpleClickListener<ItemVendor> listener;

    public VendorAdapterItem(Context context, OnItemSimpleClickListener<ItemVendor> listener) {
        this.context = context;
        this.listener = listener;
    }

    @Override
    public int getLayoutResId() {
        return R.layout.item_vendor;
    }

    @Override
    public void handleData(ItemVendor item, int position) {
        Glide.with(context).load(item.getPic()).into(ivPic);
        tvName.setText(item.getFarmName());
        tvScore.setText("好评：" + item.getScore() + "%");
        SpannableStringBuilder builder = new SpannableStringBuilder("成交额： ￥");
        builder.append(item.getSale());
        builder.setSpan(new AbsoluteSizeSpan(DensityUtils.dp2px(16)), builder.length() - item.getSale().length(), builder.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        builder.setSpan(new ForegroundColorSpan(context.getResources().getColor(R.color.orange)), builder.length() - item.getSale().length() - 1, builder.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        tvPrice.setText(builder);
    }

    @OnClick(R.id.cvProduct)
    public void onClick() {
        listener.onItemClick(curItem);
    }
}
