package com.tsutsuku.artcollection.ui.comment;

import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.tsutsuku.artcollection.R;
import com.tsutsuku.artcollection.contract.base.OnItemSimpleClickListener;
import com.tsutsuku.artcollection.model.comment.CommentFoldBean;
import com.tsutsuku.artcollection.ui.base.BaseAdapterItem;
import com.tsutsuku.artcollection.utils.glideTransform.CircleTransform;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @Author Tsutsuku
 * @Create 2017/3/21
 * @Description
 */

public class CommentFoldAdapterItem extends BaseAdapterItem<CommentFoldBean> {
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

    private OnItemSimpleClickListener<CommentFoldBean> listener;

    public CommentFoldAdapterItem(OnItemSimpleClickListener<CommentFoldBean> listener) {
        this.listener = listener;
    }

    @Override
    public int getLayoutResId() {
        return R.layout.item_comment;
    }

    @Override
    public void handleData(CommentFoldBean item, int position) {
        Glide.with(context).load(item.getPhoto()).placeholder(R.drawable.ic_default_avatar).transform(new CircleTransform(context)).into(ivAvatar);
        tvName.setText(item.getNickName());
        tvTime.setText(item.getComTime());
        tvContent.setText(item.getComcontent());
    }

    @OnClick(R.id.flBase)
    public void onClick() {
        listener.onItemClick(curItem);
    }
}
