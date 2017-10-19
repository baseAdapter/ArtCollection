package com.tsutsuku.artcollection.ui.mine;

import android.content.Context;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.tsutsuku.artcollection.R;
import com.tsutsuku.artcollection.contract.base.OnItemClickListener;
import com.tsutsuku.artcollection.model.ItemFollow;
import com.tsutsuku.artcollection.ui.base.BaseAdapterItem;
import com.tsutsuku.artcollection.utils.glideTransform.CircleTransform;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @Author Sun Renwei
 * @Create 2017/3/2
 * @Description Content
 */

public class FollowAdapterItem extends BaseAdapterItem<ItemFollow> {
    @BindView(R.id.ivAvatar)
    ImageView ivAvatar;
    @BindView(R.id.tvName)
    TextView tvName;
    @BindView(R.id.flBase)
    FrameLayout flBase;
    private OnItemClickListener<ItemFollow> listener;

    public FollowAdapterItem(Context context, OnItemClickListener listener) {
        this.context = context;
        this.listener = listener;
    }

    @Override
    public void bindViews(View root) {
        super.bindViews(root);
        flBase.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                listener.onItemLongClick(curItem);
                return true;
            }
        });
    }

    @Override
    public int getLayoutResId() {
        return R.layout.item_follow;
    }

    @Override
    public void handleData(ItemFollow item, int position) {
        Glide.with(context).load(item.getPic()).placeholder(R.drawable.ic_default_avatar).transform(new CircleTransform(context)).into(ivAvatar);
        tvName.setText(item.getFarmName());
    }

    @OnClick(R.id.flBase)
    public void onClick() {
        listener.onItemClick(curItem);
    }
}
