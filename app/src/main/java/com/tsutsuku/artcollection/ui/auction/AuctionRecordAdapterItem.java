package com.tsutsuku.artcollection.ui.auction;

import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.tsutsuku.artcollection.R;
import com.tsutsuku.artcollection.model.shopping.ProductInfoBean;
import com.tsutsuku.artcollection.ui.base.BaseAdapterItem;
import com.tsutsuku.artcollection.utils.glideTransform.CircleTransform;

import butterknife.BindView;

/**
 * @Author Sun Renwei
 * @Create 2017/2/23
 * @Description Content
 */

public class AuctionRecordAdapterItem extends BaseAdapterItem<ProductInfoBean.AuctionRecodeBean> {
    @BindView(R.id.ivAvatar)
    ImageView ivAvatar;
    @BindView(R.id.tvNick)
    TextView tvNick;
    @BindView(R.id.tvTime)
    TextView tvTime;
    @BindView(R.id.tvPrice)
    TextView tvPrice;

    @Override
    public int getLayoutResId() {
        return R.layout.item_auction_record;
    }

    @Override
    public void handleData(ProductInfoBean.AuctionRecodeBean item, int position) {
        Glide.with(context).load(item.getPhoto()).placeholder(R.drawable.ic_default_avatar).transform(new CircleTransform(context)).into(ivAvatar);
        tvNick.setText(item.getNickName());
        tvTime.setText(item.getAddTime());
        tvPrice.setText("¥" + item.getPrice());
    }


}
