package com.tsutsuku.artcollection.live.player;

import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.tsutsuku.artcollection.R;
import com.tsutsuku.artcollection.ui.base.BaseAdapterItem;
import com.tsutsuku.artcollection.utils.glideTransform.CircleTransform;

import butterknife.BindView;

/**
 * @Author Tsutsuku
 * @Create 2017/5/7
 * @Description
 */

public class LiveMessageAdapterItem extends BaseAdapterItem<ItemLiveMessage> {
    @BindView(R.id.ivAvatar)
    ImageView ivAvatar;
    @BindView(R.id.tvName)
    TextView tvName;

    @Override
    public int getLayoutResId() {
        return R.layout.item_live_message;
    }

    @Override
    public void handleData(ItemLiveMessage item, int position) {
        Glide.with(context).load(item.getAvatar()).transform(new CircleTransform(context)).error(R.drawable.default_small_pic).into(ivAvatar);
        SpannableStringBuilder builder = new SpannableStringBuilder(item.getName() + "：" + item.getContent());
        builder.setSpan(new ForegroundColorSpan(context.getResources().getColor(R.color.orange)), 0, item.getName().length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        tvName.setText(builder);

    }
}
