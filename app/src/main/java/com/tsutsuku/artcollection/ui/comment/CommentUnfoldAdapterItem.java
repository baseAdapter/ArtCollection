package com.tsutsuku.artcollection.ui.comment;

import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.tsutsuku.artcollection.R;
import com.tsutsuku.artcollection.contract.base.OnItemSimpleClickListener;
import com.tsutsuku.artcollection.model.comment.CommentUnfoldBean;
import com.tsutsuku.artcollection.ui.base.BaseAdapterItem;
import com.tsutsuku.artcollection.utils.TimeUtils;
import com.tsutsuku.artcollection.utils.glideTransform.CircleTransform;

import butterknife.BindView;

/**
 * @Author Sun Renwei
 * @Create 2017/1/17
 * @Description
 */

public class CommentUnfoldAdapterItem extends BaseAdapterItem<CommentUnfoldBean> implements View.OnClickListener{
    @BindView(R.id.ivAvatar)
    ImageView ivAvatar;
    @BindView(R.id.tvName)
    TextView tvName;
    @BindView(R.id.tvTime)
    TextView tvTime;
    @BindView(R.id.tvContent)
    TextView tvContent;
    @BindView(R.id.flBase)
    FrameLayout flBase;

    private OnItemSimpleClickListener listener;

    public CommentUnfoldAdapterItem(OnItemSimpleClickListener listener) {
        this.listener = listener;
    }

    @Override
    public int getLayoutResId() {
        return R.layout.item_comment;
    }

    @Override
    public void bindViews(View root) {
        super.bindViews(root);
        flBase.setOnClickListener(this);
    }

    @Override
    public void handleData(CommentUnfoldBean item, int position) {
        Glide.with(context).load(item.getUserPhoto()).placeholder(R.drawable.ic_default_avatar).transform(new CircleTransform(context)).into(ivAvatar);
        tvName.setText(item.getUserName());
        tvTime.setText(TimeUtils.parsePostTime(Long.valueOf(item.getPostTime())));

        if ("0".equals(item.getToUserId())){
            tvContent.setText(item.getContent());
        } else {
            SpannableStringBuilder builder = new SpannableStringBuilder();
            String toUser = "回复 " + item.getToUserName() + ":";
            builder.append(toUser);
            builder.setSpan(new ForegroundColorSpan(context.getResources().getColor(R.color.d)),0 , toUser.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            builder.append(item.getContent());
            tvContent.setText(builder);
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.flBase){
            listener.onItemClick(curItem);
        }
    }
}
