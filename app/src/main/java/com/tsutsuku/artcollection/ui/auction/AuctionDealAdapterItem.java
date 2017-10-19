package com.tsutsuku.artcollection.ui.auction;

import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.tsutsuku.artcollection.R;
import com.tsutsuku.artcollection.model.auction.ItemDealProduct;
import com.tsutsuku.artcollection.ui.base.BaseAdapterItem;
import com.tsutsuku.artcollection.utils.DensityUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @Author Tsutsuku
 * @Create 2017/4/3
 * @Description
 */

public class AuctionDealAdapterItem extends BaseAdapterItem<ItemDealProduct> {
    @BindView(R.id.cbCheck)
    CheckBox cbCheck;
    @BindView(R.id.ivGoods)
    ImageView ivGoods;
    @BindView(R.id.tvName)
    TextView tvName;
    @BindView(R.id.tvPrice)
    TextView tvPrice;
    @BindView(R.id.tvSuccessTime)
    TextView tvSuccessTime;
    @BindView(R.id.tvEndTime)
    TextView tvEndTime;
    @BindView(R.id.flGoods)
    FrameLayout flGoods;

    @Override
    public int getLayoutResId() {
        return R.layout.item_auction_deal_product;
    }

    @Override
    public void handleData(ItemDealProduct item, int position) {
        Glide.with(context).load(item.getPic()).into(ivGoods);
        tvName.setText(item.getProductName());
        SpannableStringBuilder builder = new SpannableStringBuilder("成交价 ¥");
        builder.append(item.getProductPrice());
        builder.setSpan(new AbsoluteSizeSpan(DensityUtils.dp2px(18)), builder.length() - item.getProductPrice().length(), builder.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        builder.setSpan(new ForegroundColorSpan(context.getResources().getColor(R.color.orange)), builder.length() - item.getProductPrice().length() - 1, builder.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        tvPrice.setText(builder);

        tvSuccessTime.setText("中标时间：" + item.getSucessTime());
        tvEndTime.setText("失效时间：" + item.getEndPayTime());
    }


    @Override
    public void handleData(ItemDealProduct item, int position, List<Object> payloads) {
        switch ((char) payloads.get(0)) {
            case '1': {
                cbCheck.setChecked(item.isChecked());
            }
            break;
        }
    }

    @OnClick(R.id.cbCheck)
    public void onViewClicked() {
        curItem.setChecked(!curItem.isChecked());
    }
}
