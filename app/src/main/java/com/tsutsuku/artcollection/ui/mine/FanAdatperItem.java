package com.tsutsuku.artcollection.ui.mine;

import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.tsutsuku.artcollection.R;
import com.tsutsuku.artcollection.model.ItemFan;
import com.tsutsuku.artcollection.ui.base.BaseAdapterItem;
import com.tsutsuku.artcollection.utils.glideTransform.CircleTransform;

import butterknife.BindView;

/**
 * @Author Tsutsuku
 * @Create 2017/4/2
 * @Description
 */

public class FanAdatperItem extends BaseAdapterItem<ItemFan> {
    @BindView(R.id.tvName)
    TextView tvName;
    @BindView(R.id.tvBrief)
    TextView tvBrief;
    @BindView(R.id.tvDate)
    TextView tvDate;
    @BindView(R.id.ivAvatar)
    ImageView ivAvatar;

    @Override
    public int getLayoutResId() {
        return R.layout.item_fan;
    }

    @Override
    public void handleData(ItemFan item, int position) {
        Glide.with(context).load(item.getPic()).placeholder(R.drawable.ic_default_avatar).transform(new CircleTransform(context)).into(ivAvatar);
        tvName.setText(item.getNickname());
        tvBrief.setText(item.getPersonalSign());
        tvDate.setText(item.getCreateTime());
    }
}
