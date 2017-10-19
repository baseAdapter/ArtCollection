package com.tsutsuku.artcollection.ui.activity;

import android.content.Context;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.tsutsuku.artcollection.R;
import com.tsutsuku.artcollection.contract.base.OnItemSimpleClickListener;
import com.tsutsuku.artcollection.model.activity.ItemActivity;
import com.tsutsuku.artcollection.ui.base.BaseAdapterItem;
import com.tsutsuku.artcollection.utils.DensityUtils;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @Author Tsutsuku
 * @Create 2017/3/12
 * @Description
 */

public class ActivityAdapterItem extends BaseAdapterItem<ItemActivity> {
    @BindView(R.id.ivPic)
    ImageView ivPic;
    @BindView(R.id.tvStatus)
    TextView tvStatus;
    @BindView(R.id.tvName)
    TextView tvName;
    @BindView(R.id.tvFee)
    TextView tvFee;
    @BindView(R.id.tvComment)
    TextView tvComment;
    @BindView(R.id.tvSee)
    TextView tvSee;
    @BindView(R.id.flLesson)
    FrameLayout flLesson;
    @BindView(R.id.tvTime)
    TextView tvTime;

    private OnItemSimpleClickListener<ItemActivity> listener;

    public ActivityAdapterItem(Context context, OnItemSimpleClickListener<ItemActivity> listener) {
        this.listener = listener;
        this.context = context;
    }

    @Override
    public int getLayoutResId() {
        return R.layout.item_activity;
    }

    @Override
    public void handleData(ItemActivity item, int position) {
        Glide.with(context).load(item.getCoverPhoto()).into(ivPic);
        tvName.setText(item.getActivityName());
        tvSee.setText(item.getViewCount());
        tvComment.setText(item.getCommentCount());
        tvStatus.setText("0".equals(item.getStatus()) ? "报名中" : "已结束");
        tvTime.setText("开始时间：" + item.getActivityTime());

        SpannableStringBuilder builder = new SpannableStringBuilder("报名费：¥");
        builder.append(item.getUseMoney());
        builder.setSpan(new ForegroundColorSpan(context.getResources().getColor(R.color.red)), builder.length() - 1 - item.getUseMoney().length(), builder.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        tvFee.setText(builder);
    }

    @OnClick(R.id.flLesson)
    public void onClick() {
        listener.onItemClick(curItem);
    }
}
