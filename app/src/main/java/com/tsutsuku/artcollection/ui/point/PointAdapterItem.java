package com.tsutsuku.artcollection.ui.point;

import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.tsutsuku.artcollection.R;
import com.tsutsuku.artcollection.model.point.ItemPoint;
import com.tsutsuku.artcollection.ui.base.BaseAdapterItem;
import com.tsutsuku.artcollection.utils.DensityUtils;

import butterknife.BindView;

/**
 * @Author Sun Renwei
 * @Create 2017/2/27
 * @Description Content
 */

public class PointAdapterItem extends BaseAdapterItem<ItemPoint> {
    @BindView(R.id.tvName)
    TextView tvName;
    @BindView(R.id.tvPoint)
    TextView tvPoint;
    @BindView(R.id.ivPoint)
    ImageView ivPoint;
    @BindView(R.id.flBase)
    FrameLayout flBase;

    @Override
    public void bindViews(View root) {
        super.bindViews(root);
        flBase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PointDetailActivity.launch(context, curItem);
            }
        });
    }

    @Override
    public int getLayoutResId() {
        return R.layout.item_point_goods;
    }

    @Override
    public void handleData(ItemPoint item, int position) {
        Glide.with(context).load(item.getCoverPhoto()).into(ivPoint);
        tvName.setText(item.getName());

        SpannableStringBuilder builder = new SpannableStringBuilder(context.getString(R.string.point));
        builder.append(item.getIntegrate());
        builder.setSpan(new AbsoluteSizeSpan(DensityUtils.dp2px(16)), 3, builder.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        builder.append("分");
        builder.setSpan(new ForegroundColorSpan(context.getResources().getColor(R.color.orange)), 3, builder.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        tvPoint.setText(builder);
    }
}
