package com.tsutsuku.artcollection.ui.comment;

import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.tsutsuku.artcollection.R;
import com.tsutsuku.artcollection.contract.base.OnItemSimpleClickListener;
import com.tsutsuku.artcollection.model.comment.ReplayCommnetsBean;
import com.tsutsuku.artcollection.ui.base.BaseAdapterItem;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @Author Tsutsuku
 * @Create 2017/3/21
 * @Description
 */

public class CommentReplayAdapterItem extends BaseAdapterItem<ReplayCommnetsBean> {
    @BindView(R.id.tvContent)
    TextView tvContent;
    @BindView(R.id.flBase)
    FrameLayout flBase;

    private OnItemSimpleClickListener<ReplayCommnetsBean> listener;

    public CommentReplayAdapterItem( OnItemSimpleClickListener<ReplayCommnetsBean> listener) {
        this.listener = listener;
    }

    @Override
    public int getLayoutResId() {
        return R.layout.item_comment_replay;
    }

    @Override
    public void handleData(ReplayCommnetsBean item, int position) {
        SpannableStringBuilder builder = new SpannableStringBuilder(item.getDisplayName());
        builder.setSpan(new ForegroundColorSpan(context.getResources().getColor(R.color.orange)), 0, builder.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        builder.append(" 回复 " + item.getToDisplayName());
        builder.setSpan(new ForegroundColorSpan(context.getResources().getColor(R.color.orange)), builder.length() - item.getToDisplayName().length(), builder.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        builder.append("：" + item.getComcontent());
        tvContent.setText(builder);
    }

    @OnClick(R.id.flBase)
    public void onClick() {
        listener.onItemClick(curItem);
    }
}
