package com.tsutsuku.artcollection.ui.auction;

import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.tsutsuku.artcollection.R;
import com.tsutsuku.artcollection.model.auction.ItemAuctionList;
import com.tsutsuku.artcollection.ui.base.BaseAdapterItem;
import com.tsutsuku.artcollection.utils.DensityUtils;

import butterknife.BindView;

/**
 * @Author Tsutsuku
 * @Create 2017/3/20
 * @Description
 */

public class AuctionListAdapterItem extends BaseAdapterItem<ItemAuctionList> {
    @BindView(R.id.ivPic)
    ImageView ivPic;
    @BindView(R.id.tvName)
    TextView tvName;
    @BindView(R.id.tvCurPrice)
    TextView tvCurPrice;
    @BindView(R.id.tvPrice)
    TextView tvPrice;
    @BindView(R.id.tvCount)
    TextView tvCount;
    @BindView(R.id.tvStatus)
    TextView tvStatus;

    @Override
    public int getLayoutResId() {
        return R.layout.item_auction_list;
    }

    @Override
    public void handleData(ItemAuctionList item, int position) {
        Glide.with(context).load(item.getPic()).into(ivPic);
        tvName.setText(item.getProductName());
        SpannableStringBuilder builder = new SpannableStringBuilder("当前价格：¥");
        builder.append(item.getCurrentPrice());
        builder.setSpan(new AbsoluteSizeSpan(DensityUtils.dp2px(19)), builder.length() - item.getCurrentPrice().length(), builder.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        builder.setSpan(new ForegroundColorSpan(context.getResources().getColor(item.getCurrentPrice().equals(item.getPrice())?R.color.d:R.color.orange)), builder.length() - 1 - item.getCurrentPrice().length(), builder.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        tvCurPrice.setText(builder);

        builder = new SpannableStringBuilder("我的出价：¥");
        builder.append(item.getPrice());
        builder.setSpan(new AbsoluteSizeSpan(DensityUtils.dp2px(19)), builder.length() - item.getPrice().length(), builder.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        builder.setSpan(new ForegroundColorSpan(context.getResources().getColor(item.getCurrentPrice().equals(item.getPrice())?R.color.orange:R.color.d)), builder.length() - 1 - item.getPrice().length(), builder.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        tvPrice.setText(builder);

        tvCount.setText("我出价" + String.valueOf(item.getMyAuctionCount()) + "次");

        switch (item.getAuctionState()) {
            case "0": {
                tvStatus.setText("未开始");
            }
            break;
            case "1": {
                if (item.getCurrentPrice().equals(item.getPrice())) {
                    builder = new SpannableStringBuilder("领先");
                } else {
                    builder = new SpannableStringBuilder("落后");
                }
                builder.setSpan(new ForegroundColorSpan(context.getResources().getColor(R.color.orange)), 0, 2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                builder.append(" | 竞拍中");
                tvStatus.setText(builder);
            }
            break;
            case "3": {
                if (item.getCurrentPrice().equals(item.getPrice())) {
                    tvStatus.setText("成交");
                    tvStatus.setTextColor(context.getResources().getColor(R.color.orange));
                } else {
                    tvStatus.setText("落后 | 竞拍结束");
                    tvStatus.setTextColor(context.getResources().getColor(R.color.d));
                }
            }
            break;
            case "4": {
                tvStatus.setText("流拍");
            }
            break;
        }
    }

}
